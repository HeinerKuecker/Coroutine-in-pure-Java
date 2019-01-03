package de.heinerkuecker.coroutine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import de.heinerkuecker.coroutine.expression.GetProcedureArgument;
import de.heinerkuecker.coroutine.expression.GetProcedureArgumentNotInProcedureException;
import de.heinerkuecker.coroutine.expression.UnresolvedBreakOrContinueException;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.complex.ComplexStep;
import de.heinerkuecker.coroutine.step.complex.ComplexStepState;
import de.heinerkuecker.coroutine.step.complex.StepSequence;
import de.heinerkuecker.coroutine.step.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.step.result.CoroIterStepResult;

/**
 * Class to generate a sequence of values
 * with an emulated processor in the manner of
 * <a href="TODO">Wikipedia Coroutine</a>
 * without {@link Thread} or
 * memory consuming buffer or
 * bytecode manipulation.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIterator<RESULT>
implements AbstrCoroIterator<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * Global switch to check
     * coroutine on initialization.
     *
     * For better performance of well tested
     * coroutines switch this off.
     */
    public static boolean initializationChecks = true;

    /**
     * Global switch to save source position
     * (file name, line number, class, method)
     * on step creation as debug info.
     *
     * For better performance of well tested
     * coroutines switch this off.
     */
    public static boolean saveCreationSourcePosition = true;

    /**
     * Es muss ein ComplexStep sein,
     * weil dieser mit ComplexStepState
     * einen State hat, welcher bei
     * einem SimpleStep nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden müsste.
     */
    private final ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/> complexStep;
    private ComplexStepState<?, ?, RESULT /*, CoroutineIterator<RESULT>*/> nextComplexStepState;

    // for debug
    private ComplexStepState<?, ?, RESULT /*, CoroutineIterator<RESULT>*/> lastComplexStepState;


    private boolean finallyReturnRaised;

    /**
     * Tri state boolean for result
     * of method {@link #hasNext()}.
     *
     * <code>null</code> enforces calling lookahead code in {@link #hasNext()}.
     * Non <code>null</code> is the computed and cached result.
     */
    private Boolean hasNext;

    /**
     * Lookahead return value
     * for method {@link #next()},
     */
    private RESULT next;

    /**
     * Variables.
     */
    public final HashMap<String, Object> vars = new HashMap<>();

    private final HashMap<String, Procedure<RESULT>> procedures = new HashMap<>();

    /**
     * Constructor.
     *
     * @param procedures can be <code>null</code>
     * @param initialVariableValues key value pairs to put initial in variables {@link #vars}, can be <code>null</code>
     * @param steps steps for coroutine processor
     */
    @SafeVarargs
    public CoroutineIterator(
            final Iterable<Procedure<RESULT>> procedures ,
            final Map<String, ? extends Object> initialVariableValues ,
            final CoroIterStep<RESULT /*, /*PARENT * / CoroutineIterator<RESULT>*/>... steps )
    {
        //this( steps );
        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.complexStep =
                    (ComplexStep<?, ?, RESULT /*, CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            this.complexStep =
                    new StepSequence<>(
                            // creationStackOffset
                            4 ,
                            steps );
        }

        if ( procedures != null )
        {
            for ( final Procedure<RESULT> procedure : procedures )
            {
                if ( this.procedures.containsKey( procedure.name ) )
                {
                    throw new IllegalArgumentException(
                            "procedure name already in use: " +
                            procedure.name );
                }

                this.procedures.put(
                        procedure.name ,
                        procedure );
            }
        }

        if ( initialVariableValues != null )
        {
            this.vars.putAll( initialVariableValues );
        }

        if ( initializationChecks )
        {
            checkForUnresolvedBreaksAndContinues();
            checkForGetProcedureArgumentNotInProcedure();
            this.complexStep.checkLabelAlreadyInUse(
                    new HashSet<>() ,
                    this ,
                    new HashSet<>() );
        }
    }

    /**
     * Constructor.
     *
     * @param steps steps for coroutine processor
     */
    @SafeVarargs
    public CoroutineIterator(
            final CoroIterStep<RESULT /*, /*PARENT * / CoroutineIterator<RESULT>*/>... steps )
    {
        if ( steps.length == 1 &&
                steps[ 0 ] instanceof ComplexStep )
        {
            this.complexStep = (ComplexStep<?, ?, RESULT /*, CoroutineIterator<RESULT>*/>) steps[ 0 ];
        }
        else
        {
            this.complexStep =
                    new StepSequence<>(
                            // creationStackOffset
                            4 ,
                            steps );
        }

        if ( initializationChecks )
        {
            checkForUnresolvedBreaksAndContinues();
            checkForGetProcedureArgumentNotInProcedure();
            this.complexStep.checkLabelAlreadyInUse(
                    new HashSet<>() ,
                    this ,
                    new HashSet<>() );
        }
    }

    // TODO remove
    //public void add(
    //        final Procedure<RESULT> procedureToAdd )
    //{
    //    if ( this.procedures.containsKey( procedureToAdd.name ) )
    //    {
    //        throw new IllegalArgumentException(
    //                "procedure already added: " +
    //                procedureToAdd.name );
    //    }
    //    this.procedures.put(
    //            procedureToAdd.name ,
    //            procedureToAdd );
    //}

    @Override
    public Procedure<RESULT> getProcedure(
            final String procedureName )
    {
        return this.procedures.get( procedureName );
    }

    /**
     * Check for unresolved breaks and continues.
     */
    private void checkForUnresolvedBreaksAndContinues()
    {
        final List<BreakOrContinue<RESULT>> unresolvedBreaksOrContinues =
                complexStep.getUnresolvedBreaksOrContinues(
                        new HashSet<>() ,
                        this );

        if ( ! unresolvedBreaksOrContinues.isEmpty() )
        {
            throw new UnresolvedBreakOrContinueException(
                    "unresolved breaks or continues: " +
                    unresolvedBreaksOrContinues );
        }
    }

    /**
     * Check for {@link GetProcedureArgument} outside of procedure.
     */
    private void checkForGetProcedureArgumentNotInProcedure()
    {
        final List<GetProcedureArgument<?>> getProcedureArgumentsNotInProcedure =
                complexStep.getProcedureArgumentGetsNotInProcedure();

        if ( ! getProcedureArgumentsNotInProcedure.isEmpty() )
        {
            throw new GetProcedureArgumentNotInProcedureException(
                    "ProcedureArguments not in procedure: " +
                    getProcedureArgumentsNotInProcedure );
        }
    }

    /**
     * @see Iterator#hasNext()
     */
    @Override
    public boolean hasNext()
    {
        if ( this.hasNext != null )
        {
            return this.hasNext;
        }

        if ( finallyReturnRaised )
        {
            return false;
        }

        // --- lookahead ---

        if ( this.nextComplexStepState == null )
            // no existing state from previous execute call
        {
            this.nextComplexStepState =
                    complexStep.newState(
                            this );
        }
        else if ( this.nextComplexStepState.isFinished() )
        {
            return false;
        }

        final CoroIterStepResult<RESULT> executeResult =
                this.nextComplexStepState.execute(
                        this );

        if ( executeResult == null ||
                executeResult instanceof CoroIterStepResult.ContinueCoroutine )
            // end of sub complex state without result
        {
            // Iterator ends
            this.hasNext = false;
            this.nextComplexStepState = null;
        }
        else if ( executeResult instanceof CoroIterStepResult.YieldReturnWithResult )
        {
            final CoroIterStepResult.YieldReturnWithResult<RESULT> yieldReturnWithResult =
                    (CoroIterStepResult.YieldReturnWithResult<RESULT>) executeResult;

            this.next = yieldReturnWithResult.result;
            this.hasNext = true;
        }
        else if ( executeResult instanceof CoroIterStepResult.FinallyReturnWithResult )
        {
            final CoroIterStepResult.FinallyReturnWithResult<RESULT> yieldReturnWithResult =
                    (CoroIterStepResult.FinallyReturnWithResult<RESULT>) executeResult;

            this.next = yieldReturnWithResult.result;
            this.hasNext = true;
            finallyReturnRaised = true;
            this.nextComplexStepState = null;
        }
        else if ( executeResult instanceof CoroIterStepResult.FinallyReturnWithoutResult )
        {
            // Iterator ends
            this.hasNext = false;
            this.nextComplexStepState = null;
        }
        else
        {
            throw new IllegalStateException( String.valueOf( executeResult ) );
        }

        return this.hasNext;
    }

    /**
     * @see Iterator#next()
     */
    @Override
    public RESULT next()
    {
        if ( ! hasNext() )
        {
            throw new NoSuchElementException();
        }

        // enforce calling lookahead code in hasNext()
        this.hasNext = null;

        final RESULT result = this.next;

        // free memory
        this.next = null;

        return result;
    }

    /**
     * @see CoroIteratorOrProcedure#saveLastStepState()
     */
    @Override
    public void saveLastStepState()
    {
        this.lastComplexStepState = nextComplexStepState.createClone();
    }

    /**
     * @see CoroIteratorOrProcedure#localVars()
     */
    @Override
    public Map<String, Object> localVars()
    {
        return this.vars;
    }

    /**
     * @see CoroIteratorOrProcedure#globalVars()
     */
    @Override
    public Map<String, Object> globalVars()
    {
        return this.vars;
    }

    /**
     * @see CoroIteratorOrProcedure#procedureArgumentValues()
     */
    @Override
    public Map<String, Object> procedureArgumentValues()
    {
        // TODO code smell ausgeschlagenes Erbe Refused bequest
        //return null;
        throw new IllegalStateException( this.getClass().getSimpleName() + " has no procedure arguments" );
    }

    /**
     * @see CoroIteratorOrProcedure#getRootParent()
     */
    @Override
    public CoroutineIterator<RESULT> getRootParent()
    {
        return this;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() + "\n" +
                "finallyReturnRaised=" + this.finallyReturnRaised + ", " +
                "hasNext=" + this.hasNext + ", " +
                "next=" + this.next + ", " +
                "vars=" + this.vars + "\n" +
                this.complexStep.toString(
                        //parent
                        this ,
                        //indent min length for last and next
                        "     " ,
                        this.lastComplexStepState ,
                        this.nextComplexStepState );
    }

}
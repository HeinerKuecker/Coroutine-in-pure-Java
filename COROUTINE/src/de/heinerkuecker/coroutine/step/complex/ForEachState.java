package de.heinerkuecker.coroutine.step.complex;

import java.util.Iterator;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.coroutine.step.simple.DeclareVariable;
import de.heinerkuecker.util.HCloneable;

class ForEachState<RESULT/*, PARENT extends CoroutineIterator<RESULT>*/ , RESUME_ARGUMENT , ELEMENT>
extends ComplexStepState<
    ForEachState<RESULT/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT>,
    ForEach<RESULT/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT>,
    RESULT ,
    //PARENT
    RESUME_ARGUMENT
    >
{
    private final ForEach<RESULT/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> forEach;

    // TODO getter
    boolean runInInitializer = true;
    boolean runInBody;
    boolean runInConditionAndUpdate;

    // TODO getter
    ComplexStepState<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexState;

    Iterator<ELEMENT> iterator;

    private ELEMENT variableValue;

    //private final CoroutineIterator<RESULT> rootParent;
    private final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent;

    /**
     * Constructor.
     */
    public ForEachState(
            final ForEach<RESULT/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> forEach ,
            //final CoroutineIterator<RESULT> rootParent
            final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent )
    {
        super( parent );
        this.forEach = forEach;

        //this.rootParent = Objects.requireNonNull( rootParent );

        this.parent =
                Objects.requireNonNull(
                        parent );

        new DeclareVariable<RESULT, RESUME_ARGUMENT, ELEMENT>(
                forEach.variableName ,
                forEach.elementType ).execute(
                        this );
    }

    @Override
    public CoroIterStepResult<RESULT> execute(
            //final CoroutineOrProcedureOrComplexstep<RESULT, RESUME_ARGUMENT> parent
            )
    {
        if ( runInInitializer )
        {
            parent.saveLastStepState();

            final Iterable<ELEMENT> iterable = forEach.iterableExpression.evaluate( parent );
            this.iterator = iterable.iterator();

            if ( ! iterator.hasNext() )
            {
                finish();
                return CoroIterStepResult.continueCoroutine();
            }

            this.variableValue = iterator.next();

            /*parent*/this.localVars().set(
                    forEach.variableName ,
                    variableValue );

            // for toString
            this.bodyComplexState =
                    forEach.bodyComplexStep.newState(
                            //this.parent
                            this );

            runInInitializer = false;
            runInBody = true;
        }

        while ( true )
        {
            if ( runInConditionAndUpdate )
            {
                parent.saveLastStepState();

                if ( ! iterator.hasNext() )
                {
                    finish();
                    return CoroIterStepResult.continueCoroutine();
                }

                this.variableValue = iterator.next();

                /*parent*/this.localVars().set(
                        forEach.variableName ,
                        variableValue );

                this.runInConditionAndUpdate = false;
                this.runInBody = true;
            }

            if ( runInBody )
            {
                final ComplexStep<?, ?, RESULT/*, PARENT*/ , RESUME_ARGUMENT> bodyComplexStep =
                        forEach.bodyComplexStep;

                if ( this.bodyComplexState == null )
                    // no existing state from previous execute call
                {
                    this.bodyComplexState =
                            bodyComplexStep.newState(
                                    //this.rootParent
                                    //this.parent
                                    this );
                }

                // TODO only before executing simple step: parent.saveLastStepState();

                final CoroIterStepResult<RESULT> bodyExecuteResult =
                        this.bodyComplexState.execute(
                                //parent
                                //this
                                );

                if ( this.bodyComplexState.isFinished() )
                {
                    this.runInBody = false;
                    this.runInConditionAndUpdate = true;
                    this.bodyComplexState = null;
                }

                if ( bodyExecuteResult instanceof CoroIterStepResult.Break )
                {
                    finish();
                    final String label = ( (CoroIterStepResult.Break<?>) bodyExecuteResult ).label;
                    if ( label == null ||
                            label.equals( forEach.label ) )
                    {
                        return CoroIterStepResult.continueCoroutine();
                    }
                    // break outer loop
                    return bodyExecuteResult;
                }
                else if ( bodyExecuteResult instanceof CoroIterStepResult.ContinueLoop )
                {
                    final String label = ( (CoroIterStepResult.ContinueLoop<?>) bodyExecuteResult ).label;
                    if ( label != null &&
                            ! label.equals( forEach.label ) )
                        // continue outer loop
                    {
                        finish();
                        return bodyExecuteResult;
                    }
                    // default behaviour
                }
                else if ( ! ( bodyExecuteResult == null ||
                        bodyExecuteResult instanceof CoroIterStepResult.ContinueCoroutine ) )
                {
                    return bodyExecuteResult;
                }

                this.runInBody = false;
                this.runInConditionAndUpdate = true;
                this.bodyComplexState = null;
            }
        }
    }

    private void finish()
    {
        this.runInInitializer = false;
        this.runInBody = false;
        //this.bodySequence.reset();
        this.runInConditionAndUpdate = false;
        this.variableValue = null;
    }

    /**
     * @see ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished()
    {
        return
                ( ! runInInitializer ) &&
                ( ! runInBody ) &&
                ( ! runInConditionAndUpdate );
    }

    /**
     * @see ComplexStepState#getStep()
     */
    @Override
    public ForEach<RESULT/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> getStep()
    {
        return forEach;
    }

    /**
     * @see HCloneable#createClone()
     */
    @Override
    public ForEachState<RESULT/*, PARENT*/ , RESUME_ARGUMENT, ELEMENT> createClone()
    {
        final ForEachState<RESULT/*, PARENT*/ , RESUME_ARGUMENT , ELEMENT> clone =
                new ForEachState<>(
                        forEach ,
                        //this.rootParent
                        this.parent ) ;

        clone.runInInitializer = runInInitializer;
        clone.runInBody = runInBody;
        clone.runInConditionAndUpdate = runInConditionAndUpdate;

        clone.bodyComplexState = ( bodyComplexState != null ? bodyComplexState.createClone() : null );

        // Attention, iterator is not cloned, not necessary for toString, not good for using clone in flow control

        return clone;
    }

}

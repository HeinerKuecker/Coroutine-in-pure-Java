package de.heinerkuecker.coroutine.step.complex;

import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.Procedure;
import de.heinerkuecker.coroutine.VariablesOrLocalVariables;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.step.CoroIterStepResult;
import de.heinerkuecker.util.HCloneable;

/**
 * Extern instruction pointer and stack
 * for {@link ComplexStep}.
 *
 * @param <STEP>
 * @param <COROUTINE_RETURN>
 * @param <PARENT>
 * @author Heiner K&uuml;cker
 *
 * TODO rename to ComplexStepExecuteState
 */
abstract public /*interface*/class ComplexStepState<
    STEP_STATE extends ComplexStepState<STEP_STATE, STEP, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    STEP extends ComplexStep<STEP, STEP_STATE, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT>
    RESUME_ARGUMENT
>
implements
    CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> ,
    HCloneable<STEP_STATE>
{
    protected final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    private final BlockLocalVariables blockLocalVariables;

    /**
     * Constructor.
     */
    protected ComplexStepState(
            //final BlockLocalVariables blockLocalVariables
            final CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        this.parent = Objects.requireNonNull( parent );

        //System.out.println( new Exception().getStackTrace()[ 0 ] + " " + this.getClass().getSimpleName() + " new blockLocalVariables " + parent.localVars() );
        this.blockLocalVariables =
                new BlockLocalVariables(
                        parent.localVars() );
    }

    /**
     * Execute one complex step.
     *
     * @param parent
     * @return object to return a value and to control the flow
     */
    abstract public CoroIterStepResult<COROUTINE_RETURN> execute(
            //PARENT parent
            //CoroutineOrProcedureOrComplexstep<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            );

    /**
     * This method returns if all sub steps have been executed.
     *
     * @return all sub steps have been executed or not
     */
    abstract public boolean isFinished();

    abstract public STEP getStep();

    @Override
    public void saveLastStepState()
    {
        //getRootParent().saveLastStepState();
        this.parent.saveLastStepState();
    }

    //@Override
    //public CoroutineIterator<COROUTINE_RETURN> getRootParent()
    //{
    //    return parent.getRootParent();
    //}

    //@Override
    //public boolean isCoroutineRoot()
    //{
    //    return parent.isCoroutineRoot();
    //}

    @Override
    public Procedure<COROUTINE_RETURN , RESUME_ARGUMENT> getProcedure(
            final String procedureName )
    {
        return parent.getProcedure( procedureName );
    }

    @Override
    //public VariablesOrLocalVariables localVars()
    public BlockLocalVariables localVars()
    {
        return this.blockLocalVariables;
    }

    @Override
    public VariablesOrLocalVariables globalVars()
    {
        return parent.globalVars();
    }

    @Override
    public Arguments procedureArgumentValues()
    {
        return parent.procedureArgumentValues();
    }

    @Override
    public Map<String, Class<?>> procedureParameterTypes()
    {
        return parent.procedureParameterTypes();
    }

    @Override
    public Arguments globalArgumentValues()
    {
        return parent.globalArgumentValues();
    }

    @Override
    public Map<String, Class<?>> globalParameterTypes()
    {
        return parent.globalParameterTypes();
    }

    protected String getVariablesStr(
            final String indent )
    {
        if ( this.blockLocalVariables.isEmpty() )
        {
            return "";
        }

        final String variablesStr;

        variablesStr =
                    indent +
                    "variables: " +
                    blockLocalVariables +
                    "\n";

        return variablesStr;
    }

    @Override
    public RESUME_ARGUMENT getResumeArgument()
    {
        return this.parent.getResumeArgument();
    }

}

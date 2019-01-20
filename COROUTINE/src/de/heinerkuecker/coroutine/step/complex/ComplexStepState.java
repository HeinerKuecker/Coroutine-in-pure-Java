package de.heinerkuecker.coroutine.step.complex;

import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineIterator;
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
 * @param <RESULT>
 * @param <PARENT>
 * @author Heiner K&uuml;cker
 *
 * TODO rename to ComplexStepExecuteState
 */
abstract public /*interface*/class ComplexStepState<
    STEP_STATE extends ComplexStepState<STEP_STATE, STEP, RESULT /*, PARENT*/>,
    STEP extends ComplexStep<STEP, STEP_STATE, RESULT /*, PARENT*/>,
    RESULT
    //PARENT extends CoroutineOrProcedureOrComplexstep<RESULT>
>
implements CoroutineOrProcedureOrComplexstep , HCloneable<STEP_STATE>
{
    protected final CoroutineOrProcedureOrComplexstep<RESULT> parent;

    private final BlockLocalVariables blockLocalVariables;

    /**
     * Constructor.
     */
    protected ComplexStepState(
            //final BlockLocalVariables blockLocalVariables
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
        this.parent = Objects.requireNonNull( parent );

        System.out.println( new Exception().getStackTrace()[ 0 ] + " " + this.getClass().getSimpleName() + " new blockLocalVariables " + parent.localVars() );
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
    abstract public CoroIterStepResult<RESULT> execute(
            //PARENT parent
            //CoroutineOrProcedureOrComplexstep<RESULT> parent
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
        getRootParent().saveLastStepState();
    }

    @Override
    public CoroutineIterator<RESULT> getRootParent()
    {
        return parent.getRootParent();
    }

    //@Override
    //public boolean isCoroutineRoot()
    //{
    //    return parent.isCoroutineRoot();
    //}

    @Override
    public Procedure<RESULT> getProcedure(
            final String procedureName )
    {
        return parent.getProcedure( procedureName );
    }

    @Override
    public VariablesOrLocalVariables localVars()
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
}

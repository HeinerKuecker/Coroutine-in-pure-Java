package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
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
implements HCloneable<STEP_STATE>
{
    private final BlockLocalVariables blockLocalVariables;

    /**
     * @param blockLocalVariables
     */
    protected ComplexStepState(
            //final BlockLocalVariables blockLocalVariables
            final CoroutineOrProcedureOrComplexstep<RESULT> parent )
    {
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
            CoroutineOrProcedureOrComplexstep<RESULT> parent );

    /**
     * This method returns if all sub steps have been executed.
     *
     * @return all sub steps have been executed or not
     */
    abstract public boolean isFinished();

    abstract public STEP getStep();

    //abstract public void setRootParent( final CoroutineIterator<RESULT> rootParent );
}

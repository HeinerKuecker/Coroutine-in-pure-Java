package de.heinerkuecker.coroutine.step.complex;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.step.result.CoroIterStepResult;
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
public interface ComplexStepState<
    STEP_STATE extends ComplexStepState<STEP_STATE, STEP, RESULT /*, PARENT*/>,
    STEP extends ComplexStep<STEP, STEP_STATE, RESULT /*, PARENT*/>,
    RESULT
    //PARENT extends CoroIteratorOrProcedure<RESULT>
>
extends HCloneable<STEP_STATE>
{
    /**
     * Execute one complex step.
     *
     * @param parent
     * @return object to return a value and to control the flow
     */
    CoroIterStepResult<RESULT> execute(
            //final PARENT parent
            final CoroIteratorOrProcedure<RESULT> parent );

    /**
     * This method returns if all sub steps have been executed.
     *
     * @return all sub steps have been executed or not
     */
    boolean isFinished();

    STEP getStep();

    //abstract public void setRootParent( final CoroutineIterator<RESULT> rootParent );
}

package de.heinerkuecker.coroutine_iterator.step.complex;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.result.CoroIterStepResult;

public class ProcedureState<RESULT>
implements ComplexStepState<
    ProcedureState<RESULT>,
    Procedure<RESULT>,
    RESULT
    //Procedure<RESULT>
    >
{
    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.step.complex.ComplexStepState#execute(de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure)
     */
    @Override
    public CoroIterStepResult<RESULT> execute(CoroIteratorOrProcedure<RESULT> parent) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.step.complex.ComplexStepState#isFinished()
     */
    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.step.complex.ComplexStepState#getStep()
     */
    @Override
    public Procedure<RESULT> getStep() {
        // TODO Auto-generated method stub
        return null;
    }
    /* (non-Javadoc)
     * @see de.heinerkuecker.util.HCloneable#createClone()
     */
    @Override
    public ProcedureState<RESULT> createClone() {
        // TODO Auto-generated method stub
        return null;
    }

}

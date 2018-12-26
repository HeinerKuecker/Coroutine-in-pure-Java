package de.heinerkuecker.coroutine_iterator.step.complex;

import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine_iterator.step.flow.BreakOrContinue;

public class Procedure<RESULT/*, PARENT extends CoroIteratorOrProcedure<RESULT, PARENT>*/>
extends ComplexStep<
    Procedure<RESULT/*, PARENT*/> ,
    ProcedureState<RESULT> ,
    RESULT
    //PARENT
    >
implements CoroIteratorOrProcedure<RESULT/*, CoroutineIterator<RESULT>*/>
{
    /**
     * @param creationStackOffset
     */
    protected Procedure(int creationStackOffset) {
        super(creationStackOffset);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure#saveLastStepState()
     */
    @Override
    public void saveLastStepState() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure#vars()
     */
    @Override
    public Map<String, Object> vars() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.step.complex.ComplexStep#newState()
     */
    @Override
    public ProcedureState<RESULT> newState() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.step.complex.ComplexStep#getUnresolvedBreaksOrContinues()
     */
    @Override
    public List<BreakOrContinue<RESULT>> getUnresolvedBreaksOrContinues() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see de.heinerkuecker.coroutine_iterator.step.complex.ComplexStep#toString(java.lang.String, de.heinerkuecker.coroutine_iterator.step.complex.ComplexStepState, de.heinerkuecker.coroutine_iterator.step.complex.ComplexStepState)
     */
    @Override
    public String toString(String indent, ComplexStepState<?, ?, RESULT> lastStepExecuteState,
            ComplexStepState<?, ?, RESULT> nextStepExecuteState) {
        // TODO Auto-generated method stub
        return null;
    }

}

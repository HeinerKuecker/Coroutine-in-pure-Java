package de.heinerkuecker.coroutine.stmt.ret;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.NullValue;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmtState;

/**
 * Short notation of
 * <code>
 * new YieldReturn<>( NullValue.nullValue() )
 * </code>.
 *
 * @author Heiner K&uuml;cker
 */
public class YieldReturnVoid<COROUTINE_RETURN , RESUME_ARGUMENT>
extends YieldReturn</*FUNCTION_RETURN*/Void , COROUTINE_RETURN , RESUME_ARGUMENT>
{

    /**
     * Constructor.
     */
    public YieldReturnVoid()
    {
        super(
        		// creationStackOffset 
        		3 ,
                NullValue.nullValue() );
    }

    @Override
    public String toString(
            final CoroutineOrFunctioncallOrComplexstmt<?, COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, ?, COROUTINE_RETURN, RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, ?, ?, COROUTINE_RETURN, RESUME_ARGUMENT> nextStmtExecuteState )
    {
        // TODO Auto-generated method stub
        return super.toString(parent, indent, lastStmtExecuteState, nextStmtExecuteState);
    }

}

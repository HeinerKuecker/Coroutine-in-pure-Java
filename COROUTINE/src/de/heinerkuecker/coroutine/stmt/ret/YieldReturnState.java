package de.heinerkuecker.coroutine.stmt.ret;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpressionState;
import de.heinerkuecker.coroutine.exprs.exc.WrongExpressionResultValueClassException;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult;
import de.heinerkuecker.coroutine.stmt.CoroStmtResult.FunctionReturnWithResult;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmtState;

/**
 * TODO
 *
 * @author Heiner K&uuml;cker
 */
public class YieldReturnState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends ComplexStmtState<
    YieldReturnState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> ,
    YieldReturn<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    RESUME_ARGUMENT
    >
{
    private final YieldReturn<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> yieldReturn;

    private boolean runInYieldReturn = true;

    ComplexExpressionState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, ? super PARENT*/, RESUME_ARGUMENT> currentComplexExpressionState;


    /**
     * Constructor.
     *
     * @param parent
     * @param yieldReturn
     */
    YieldReturnState(
            final YieldReturn<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> yieldReturn ,
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        super(parent);
        this.yieldReturn = yieldReturn;
    }

    /**
     * Compute result value and wrap it in yield return.
     */
    @Override
    public CoroStmtResult<FUNCTION_RETURN, COROUTINE_RETURN> execute()
    {
        if ( runInYieldReturn )
        {
            if ( currentComplexExpressionState == null )
            {
                currentComplexExpressionState =
                        this.yieldReturn.expression.newState(
                                (CoroutineOrFunctioncallOrComplexstmt) this );
            }

            final CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> executeResult =
                    this.currentComplexExpressionState.execute();

            if ( this.currentComplexExpressionState.isFinished() )
            {
                this.runInYieldReturn = false;
            }

            if ( executeResult != null &&
                    executeResult instanceof FunctionReturnWithResult )
            {
                final FUNCTION_RETURN resultValue = ( (FunctionReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>) executeResult ).result;

                if ( resultValue != null &&
                        ! yieldReturn.coroutineReturnType.isInstance( resultValue ) )
                {
                    throw new WrongExpressionResultValueClassException(
                            //valueExpression
                            yieldReturn.expression ,
                            //expectedClass
                            yieldReturn.coroutineReturnType ,
                            //wrongValue
                            resultValue );
                }

                return new CoroStmtResult.YieldReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>(
                        yieldReturn.coroutineReturnType.cast(
                                resultValue ) );
            }

            return executeResult;
        }
        return CoroStmtResult.continueCoroutine();


        /*


        if ( resultValue != null &&
                ! coroutineReturnType.isInstance( resultValue ) )
        {
            throw new WrongExpressionClassException(
                    //valueExpression
                    expression ,
                    //expectedClass
                    coroutineReturnType ,
                    //wrongValue
                    resultValue );
        }

        //System.out.println( "execute " + this );

        return new CoroStmtResult.YieldReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>(
                coroutineReturnType.cast(
                        resultValue ) );

         */
        //throw new RuntimeException( "not implemented" );
    }

    @Override
    public boolean isFinished()
    {
        return ! runInYieldReturn;
    }

    @Override
    public YieldReturn<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> getStmt()
    {
        return yieldReturn;
    }

    @Override
    public YieldReturnState<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> createClone()
    {
        final YieldReturnState<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> clone =
                new YieldReturnState<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT>(
                        yieldReturn ,
                        this.parent );

        clone.runInYieldReturn = runInYieldReturn;
        clone.currentComplexExpressionState = ( currentComplexExpressionState != null ? currentComplexExpressionState.createClone() : null );

        return clone;
    }

}

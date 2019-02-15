package de.heinerkuecker.coroutine.stmt.ret;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.complex.ComplexExpression;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmt;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmtState;
import de.heinerkuecker.coroutine.stmt.complex.SimpleExpressionWrapper;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;

/**
 * Stmt {@link CoroStmt} to
 * return a specified value
 * and suspend run.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public class YieldReturn<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>
//extends SimpleStmt<FUNCTION_RETURN , COROUTINE_RETURN/*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
extends ComplexStmt<
    YieldReturn<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> ,
    YieldReturnState<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    RESUME_ARGUMENT
    >
{
    //public final SimpleExpression<? extends COROUTINE_RETURN , COROUTINE_RETURN> expression;
    final ComplexExpression<
    /*ComplexExpression<COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/ ? ,
    /*ComplexExpressionState<COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/ ? ,
    COROUTINE_RETURN ,
    COROUTINE_RETURN ,
    RESUME_ARGUMENT
    > expression;

    /**
     * Reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
     */
    Class<? extends COROUTINE_RETURN> coroutineReturnType;

    /**
     * Constructor.
     */
    public YieldReturn(
            //final SimpleExpression<? extends COROUTINE_RETURN , COROUTINE_RETURN> expression
            final ComplexExpression<
                /*ComplexExpression<COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/ ? ,
                /*ComplexExpressionState<COROUTINE_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>*/ ? ,
                COROUTINE_RETURN ,
                COROUTINE_RETURN ,
                RESUME_ARGUMENT
                > expression )
    {
        super(
                //creationStackOffset
                3 );

        this.expression =
                Objects.requireNonNull(
                        expression );
    }

    /**
     * Constructor.
     */
    public YieldReturn(
            final SimpleExpression<? extends COROUTINE_RETURN , COROUTINE_RETURN> expression )
    {
        super(
                //creationStackOffset
                3 );

        this.expression =
                new SimpleExpressionWrapper(
                        Objects.requireNonNull(
                                expression ) );
    }

    /**
     * Constructor.
     */
    public YieldReturn(
            final COROUTINE_RETURN value )
    {
        super(
                //creationStackOffset
                2 );

        this.expression =
                new SimpleExpressionWrapper<>(
                        new Value<COROUTINE_RETURN , COROUTINE_RETURN>(
                                (Class<? extends COROUTINE_RETURN>) value.getClass() ,
                                value ) );
    }

    ///**
    // * Compute result value and wrap it in yield return.
    // */
    //@Override
    //public CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN> execute(
    //        final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    //{
    //    //final COROUTINE_RETURN resultValue =
    //    //        expression.evaluate(
    //    //                parent );
    //
    //
    //
    //    final COROUTINE_RETURN resultValue =
    //
    //
    //    if ( resultValue != null &&
    //            ! coroutineReturnType.isInstance( resultValue ) )
    //    {
    //        throw new WrongExpressionClassException(
    //                //valueExpression
    //                expression ,
    //                //expectedClass
    //                coroutineReturnType ,
    //                //wrongValue
    //                resultValue );
    //    }
    //
    //    //System.out.println( "execute " + this );
    //
    //    return new CoroStmtResult.YieldReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>(
    //            coroutineReturnType.cast(
    //                    resultValue ) );
    //}

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return expression.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void setStmtCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType )
    {
        this.coroutineReturnType = coroutineReturnType;

        this.expression.setStmtCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.expression.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes ,
                localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.expression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
    }

    //@Override
    //public String toString()
    //{
    //    return
    //            this.getClass().getSimpleName() +
    //            "[" +
    //            expression +
    //            "]" +
    //            ( this.creationStackTraceElement != null
    //                ? " " + this.creationStackTraceElement
    //                : "" );
    //}

    @Override
    public YieldReturnState<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final YieldReturnState<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT> newState =
                new YieldReturnState<FUNCTION_RETURN, COROUTINE_RETURN, RESUME_ARGUMENT>(
                        this ,
                        parent );

        return newState;
    }

    @Override
    public List<BreakOrContinue<?, ?, ?>> getUnresolvedBreaksOrContinues(
            HashSet<String> alreadyCheckedFunctionNames ,
            CoroutineOrFunctioncallOrComplexstmt<?, COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return this.expression.getUnresolvedBreaksOrContinues(
                alreadyCheckedFunctionNames ,
                parent );
    }

    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        this.expression.checkLabelAlreadyInUse(
                alreadyCheckedFunctionNames ,
                parent ,
                labels );
    }

    @Override
    public String toString(
            CoroutineOrFunctioncallOrComplexstmt<?, COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            String indent ,
            ComplexStmtState<?, ?, ?, COROUTINE_RETURN, RESUME_ARGUMENT> lastStmtExecuteState ,
            ComplexStmtState<?, ?, ?, COROUTINE_RETURN, RESUME_ARGUMENT> nextStmtExecuteState )
    {
        //throw new RuntimeException( "not implemented" );
        return
                indent +
                this.getClass().getSimpleName() +
                ( this.creationStackTraceElement != null ? " " + this.creationStackTraceElement : "" ) +
                "\n" +
                "(" +
                this.expression +
                ")\n";
    }

}

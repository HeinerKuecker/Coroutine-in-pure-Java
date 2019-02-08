package de.heinerkuecker.coroutine.exprs;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

abstract public class AbstrExprsUseExprs<EXPRESSION_RETURN , ARGUMENT>
implements CoroExpression<EXPRESSION_RETURN>
{
    public final Class<? extends EXPRESSION_RETURN> type;

    public final CoroExpression<ARGUMENT> argumentExpression;

    abstract public EXPRESSION_RETURN execute(
            final ARGUMENT argument );

    /**
     * Constructor.
     *
     * @param type
     * @param argumentExpression
     */
    protected AbstrExprsUseExprs(
            Class<? extends EXPRESSION_RETURN> type ,
            CoroExpression<ARGUMENT> argumentExpression )
    {
        this.type = Objects.requireNonNull( type );
        this.argumentExpression = Objects.requireNonNull( argumentExpression );
    }

    @Override
    public EXPRESSION_RETURN evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        final ARGUMENT argument = argumentExpression.evaluate( parent );

        final EXPRESSION_RETURN ret =
                execute(
                        argument );

        return type.cast( ret );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends EXPRESSION_RETURN>[] type()
    {
        return new Class[]{ type };
    }

    @Override
    public List<GetFunctionArgument<?>> getFunctionArgumentGetsNotInFunction()
    {
        return this.argumentExpression.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.argumentExpression.checkUseVariables(
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
        this.argumentExpression.checkUseArguments(
                alreadyCheckedFunctionNames ,
                parent );
    }

}

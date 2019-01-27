package de.heinerkuecker.coroutine.expression;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

abstract public class AbstrExprsUseExprs<RETURN , ARGUMENT>
implements CoroExpression<RETURN>
{
    public final Class<? extends RETURN> type;

    public final CoroExpression<ARGUMENT> argumentExpression;

    abstract public RETURN execute(
            final ARGUMENT argument );

    /**
     * Constructor.
     *
     * @param type
     * @param argumentExpression
     */
    protected AbstrExprsUseExprs(
            Class<? extends RETURN> type ,
            CoroExpression<ARGUMENT> argumentExpression )
    {
        this.type = Objects.requireNonNull( type );
        this.argumentExpression = Objects.requireNonNull( argumentExpression );
    }

    @Override
    public RETURN evaluate(
            final HasArgumentsAndVariables<?> parent )
    {
        final ARGUMENT argument = argumentExpression.evaluate( parent );

        final RETURN ret =
                execute(
                        argument );

        return type.cast( ret );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends RETURN>[] type()
    {
        return new Class[]{ type };
    }

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return this.argumentExpression.getProcedureArgumentGetsNotInProcedure();
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.argumentExpression.checkUseVariables(alreadyCheckedProcedureNames, parent, globalVariableTypes, localVariableTypes);
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.argumentExpression.checkUseArguments(
                alreadyCheckedProcedureNames ,
                parent );
    }

}

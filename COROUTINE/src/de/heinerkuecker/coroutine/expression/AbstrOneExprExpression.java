package de.heinerkuecker.coroutine.expression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.step.CoroIterStep;

/**
 * Like map from expression
 * to expression.
 *
 * @author Heiner K&uuml;cker
 * @param <T>
 */
public abstract class AbstrOneExprExpression<RETURN, ARGUMENT>
implements CoroExpression<RETURN>
{
    /**
     * Expression to map.
     */
    public final CoroExpression<? extends ARGUMENT> expr;

    /**
     * Constructor.
     */
    protected AbstrOneExprExpression(
            final CoroExpression<? extends ARGUMENT> expr )
    {
        this.expr = Objects.requireNonNull( expr );
    }

    @Override
    final public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        result.addAll(
                expr.getProcedureArgumentGetsNotInProcedure() );

        return result;
    }

    @Override
    final public void checkUseVariables(
            //final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes)
    {
        this.expr.checkUseVariables(
                //isCoroutineRoot ,
                alreadyCheckedProcedureNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    final public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        this.expr.checkUseArguments(
                alreadyCheckedProcedureNames ,
                parent );
    }

}

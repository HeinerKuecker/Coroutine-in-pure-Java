package de.heinerkuecker.coroutine.exprs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;

/**
 * Abstract super class for
 * expressions without access
 * to variables or arguments.
 *
 * @param <EXPRESSION_RETURN> expression return type
 * @author Heiner K&uuml;cker
 */
abstract public class AbstrNoVarsNoArgsExpression<EXPRESSION_RETURN , COROUTINE_RETURN>
implements CoroExpression<EXPRESSION_RETURN , COROUTINE_RETURN>
{

    @Override
    final public List<GetFunctionArgument<? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        // nothing to do
        return Collections.emptyList();
    }

    @Override
    final public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    final public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        // nothing to do
    }

    @Override
    final public void setExprCoroutineReturnType(
            HashSet<String> alreadyCheckedFunctionNames ,
            CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            Class<?> coroutineReturnType )
    {
        // do nothing
    }

}

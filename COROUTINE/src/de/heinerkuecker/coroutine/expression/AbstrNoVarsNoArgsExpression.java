package de.heinerkuecker.coroutine.expression;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;

/**
 * Abstract super class for
 * expressions without access
 * to variables or arguments.
 *
 * @param <T> expression return type
 * @author Heiner K&uuml;cker
 */
abstract public class AbstrNoVarsNoArgsExpression<T>
implements CoroExpression<T>
{

    @Override
    public final List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        // nothing to do
        return Collections.emptyList();
    }

    @Override
    public final void checkUseVariables(
            HashSet<String> alreadyCheckedProcedureNames ,
            CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            Map<String, Class<?>> globalVariableTypes ,
            Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public final void checkUseArguments(HashSet<String> alreadyCheckedProcedureNames,
            CoroutineOrProcedureOrComplexstep<?, ?> parent)
    {
        // nothing to do
    }

}

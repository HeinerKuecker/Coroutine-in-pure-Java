package de.heinerkuecker.coroutine.condition;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.expression.AbstrNoVarsNoArgsExpression;

/**
 * Constant false {@link Condition}
 * to control flow in
 * {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class False
extends AbstrNoVarsNoArgsExpression<Boolean>
implements ConditionOrBooleanExpression/*Condition/*<CoroutineIterator<?>>*/
{
    /**
     * Returns always false.
     *
     * @see Condition#execute
     */
    @Override
    public boolean execute(
            final HasArgumentsAndVariables<?>/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        return false;
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    return Collections.emptyList();
    //}

    //@Override
    //public void checkUseVariables(
    //        //final boolean isCoroutineRoot ,
    //        final HashSet<String> alreadyCheckedProcedureNames ,
    //        final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
    //        final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    //{
    //    // nothing to do
    //}

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    //{
    //    // nothing to do
    //}

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return "false";
    }

}

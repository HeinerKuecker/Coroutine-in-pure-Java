package de.heinerkuecker.coroutine.stmt.flow;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.stmt.CoroIterStep;
import de.heinerkuecker.coroutine.stmt.simple.SimpleStep;

/**
 * Common interface of
 * {@link Break} and
 * {@link Continue}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
//public interface BreakOrContinue<COROUTINE_RETURN>
abstract public class BreakOrContinue<COROUTINE_RETURN , RESUME_ARGUMENT>
extends SimpleStep<COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>
{
    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    protected BreakOrContinue(
            //final int creationStackOffset
            )
    {
        super(
                //creationStackOffset
                4 );
    }

    abstract public String getLabel();

    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        return Collections.emptyList();
    }

    /**
     * @see CoroIterStep#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        // do nothing
    }

    @Override
    public void checkUseVariables(
            ////final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        // nothing to do
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        // nothing to do
    }

}

package de.heinerkuecker.coroutine.step.simple;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.expression.GetProcedureArgument;

abstract public class SimpleStepWithoutArguments<RESULT , RESUME_ARGUMENT>
extends SimpleStep<RESULT , RESUME_ARGUMENT>
{

    @Override
    final public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        // nothing to do
        return Collections.emptyList();
    }

    @Override
    final public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    {
        // nothing to do
    }

}

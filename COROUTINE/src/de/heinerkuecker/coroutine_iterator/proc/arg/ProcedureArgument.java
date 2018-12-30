package de.heinerkuecker.coroutine_iterator.proc.arg;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

public interface ProcedureArgument
{
    String getName();

    Object getValue(
            final CoroIteratorOrProcedure<?> parent );
}

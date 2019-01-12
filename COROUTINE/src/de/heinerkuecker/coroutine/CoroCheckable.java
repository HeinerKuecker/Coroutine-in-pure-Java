package de.heinerkuecker.coroutine;

import java.util.List;

import de.heinerkuecker.coroutine.expression.GetProcedureArgument;

public interface CoroCheckable
{

    abstract public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure();

}

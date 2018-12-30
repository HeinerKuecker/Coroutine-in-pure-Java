package de.heinerkuecker.coroutine_iterator.proc.arg;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

public class GlobalVarProcedureArgument
implements ProcedureArgument
{
    public final String procedureArgumentName;

    public final String globalVarName;

    /**
     * Constructor.
     *
     * @param procedureArgumentName
     * @param globalVarName
     */
    public GlobalVarProcedureArgument(
            final String procedureArgumentName ,
            final String globalVarName )
    {
        this.procedureArgumentName = procedureArgumentName;
        this.globalVarName = globalVarName;
    }

    /**
     * @see ProcedureArgument#getName()
     */
    @Override
    public String getName()
    {
        return procedureArgumentName;
    }

    /**
     * @see ProcedureArgument#getValue()
     */
    @Override
    public Object getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        return parent.globalVars().get( globalVarName );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                "[" +
                "procedureArgumentName=" + this.procedureArgumentName + ", " +
                "globalVarName=" + this.globalVarName +
                "]";
    }

}

package de.heinerkuecker.coroutine_iterator.proc.arg;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

public class LocalVarProcedureArgument
implements ProcedureArgument
{
    public final String procedureArgumentName;

    public final String localVarName;

    /**
     * Constructor.
     *
     * @param procedureArgumentName
     * @param localVarName
     */
    public LocalVarProcedureArgument(
            final String procedureArgumentName ,
            final String localVarName )
    {
        this.procedureArgumentName = procedureArgumentName;
        this.localVarName = localVarName;
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
        return parent.localVars().get( localVarName );
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
                "localVarName=" + this.localVarName +
                "]";
    }

}

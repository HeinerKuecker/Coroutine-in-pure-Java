package de.heinerkuecker.coroutine_iterator.proc.arg;

import de.heinerkuecker.coroutine_iterator.CoroIteratorOrProcedure;

public class ValueProcedureArgument
implements ProcedureArgument
{
    public final String procedureArgumentName;

    public final Object value;

    /**
     * Constructor.
     *
     * @param name
     * @param value
     */
    public ValueProcedureArgument(
            final String procedureArgumentName ,
            final Object value )
    {
        this.procedureArgumentName = procedureArgumentName;
        this.value = value;
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
     * @see ProcedureArgument#getValue
     */
    @Override
    public Object getValue(
            final CoroIteratorOrProcedure<?> parent )
    {
        return value;
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
                "value=" + this.value +
                "]";
    }

}

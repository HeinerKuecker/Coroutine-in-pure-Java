package de.heinerkuecker.coroutine_iterator.proc.arg;

public class ValueProcedureArgument
implements ProcedureArgument
{
    public final String name;

    public final Object value;

    /**
     * Constructor.
     *
     * @param name
     * @param value
     */
    public ValueProcedureArgument(
            final String name ,
            final Object value )
    {
        this.name = name;
        this.value = value;
    }

    /**
     * @see ProcedureArgument#getName()
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * @see ProcedureArgument#getValue()
     */
    @Override
    public Object getValue()
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
                this.getClass().getSimpleName() + " " +
                "name=" + this.name + ", " +
                "value=" + this.value;
    }

}

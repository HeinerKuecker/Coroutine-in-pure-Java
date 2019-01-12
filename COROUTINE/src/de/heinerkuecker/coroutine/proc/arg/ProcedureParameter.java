package de.heinerkuecker.coroutine.proc.arg;

import java.util.Objects;

public class ProcedureParameter
{
    public final String name;

    public final boolean isMandantory;

    public final Class<?> type;

    /**
     * Constructor.
     *
     * @param name
     * @param isMandantory
     * @param type
     */
    public ProcedureParameter(
            final String name ,
            final boolean isMandantory ,
            final Class<?> type )
    {
        this.name =
                Objects.requireNonNull(
                        name );

        this.isMandantory = isMandantory;

        this.type =
                Objects.requireNonNull(
                        type );
    }

}

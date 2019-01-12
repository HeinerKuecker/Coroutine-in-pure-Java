package de.heinerkuecker.coroutine.arg;

import java.util.Objects;

public class Parameter
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
    public Parameter(
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

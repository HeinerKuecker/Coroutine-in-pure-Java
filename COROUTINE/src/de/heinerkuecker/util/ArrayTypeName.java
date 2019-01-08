package de.heinerkuecker.util;

/**
 * Util class to print
 * array type names
 * to {@link String}.
 *
 * @author Heiner K&uuml;cker
 */
public class ArrayTypeName
{

    /**
     * @param type
     * @return
     */
    public static String toStr(
            final Class<?> type )
    {
        if ( type == null )
        {
            return null;
        }

        if ( type.isArray() )
        {
            return
                    toStr(
                            type.getComponentType() ) +
                    "[]";
        }

        return type.getName();
    }

}

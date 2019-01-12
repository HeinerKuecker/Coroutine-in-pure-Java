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
     * Get type name of array as readable {@link String}.
     *
     * @param type array type
     * @return type name of array as readable {@link String}
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

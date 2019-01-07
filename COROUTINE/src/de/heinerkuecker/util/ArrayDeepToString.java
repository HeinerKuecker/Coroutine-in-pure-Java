package de.heinerkuecker.util;

import java.util.Arrays;

/**
 * Util class to convert
 * multi-dimensional arrays
 * to {@link String}.
 *
 * @author Heiner K&uuml;cker
 */
public final class ArrayDeepToString
{
    /**
     * Constructor for util class with only static methods.
     */
    private ArrayDeepToString()
    {
        super();
    }

    public static String deepToString(
            final Object objToStr )
    {
        if ( objToStr != null &&
                objToStr.getClass().isArray() &&
                ! objToStr.getClass().getComponentType().isPrimitive() )
        {
            return deepToString(
                    (Object[]) objToStr );
        }

        return String.valueOf( objToStr );
    }

    /**
     * Util class to convert
     * multi-dimensional arrays
     * to {@link String}.
     *
     * No code to avoid
     * {@link StackOverflowError}
     * on circular references.
     *
     * @author Heiner K&uuml;cker
     */
    public static String deepToString(
            final Object[] arrToStr )
    {
        if ( arrToStr == null ||
                arrToStr.length == 0 )
        {
            return Arrays.toString( arrToStr );
        }

        final StringBuilder buff = new StringBuilder();

        boolean isFirst = true;

        for ( final Object elementToStr : arrToStr )
        {
            if ( isFirst )
            {
                isFirst = false;
            }
            else
            {
                buff.append( ", " );
            }

            if ( elementToStr instanceof Object[] )
            {
                buff.append(
                        deepToString(
                                (Object[]) elementToStr ) );
            }
            else if ( elementToStr instanceof boolean[] )
            {
                buff.append(
                        Arrays.toString(
                                (boolean[]) elementToStr ) );
            }
            else if ( elementToStr instanceof byte[] )
            {
                buff.append(
                        Arrays.toString(
                                (byte[]) elementToStr ) );
            }
            else if ( elementToStr instanceof short[] )
            {
                buff.append(
                        Arrays.toString(
                                (short[]) elementToStr ) );
            }
            else if ( elementToStr instanceof char[] )
            {
                buff.append(
                        Arrays.toString(
                                (char[]) elementToStr ) );
            }
            else if ( elementToStr instanceof int[] )
            {
                buff.append(
                        Arrays.toString(
                                (int[]) elementToStr ) );
            }
            else if ( elementToStr instanceof long[] )
            {
                buff.append(
                        Arrays.toString(
                                (long[]) elementToStr ) );
            }
            else if ( elementToStr instanceof float[] )
            {
                buff.append(
                        Arrays.toString(
                                (float[]) elementToStr ) );
            }
            else if ( elementToStr instanceof double[] )
            {
                buff.append(
                        Arrays.toString(
                                (double[]) elementToStr ) );
            }
            else
            {
                buff.append( elementToStr );
            }
        }

        return "[" + buff.toString() + "]";
    }

}

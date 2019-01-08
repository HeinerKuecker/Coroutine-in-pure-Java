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
            return
                    getFinalComponentClassName( (Object[]) objToStr ) +
                    deepToString(
                    (Object[]) objToStr );
        }
        else if ( objToStr instanceof boolean[] )
        {
            return
                    objToStr.getClass().getComponentType().getName() +
                    Arrays.toString(
                            (boolean[]) objToStr );
        }
        else if ( objToStr instanceof byte[] )
        {
            return
                    objToStr.getClass().getComponentType().getName() +
                    Arrays.toString(
                            (byte[]) objToStr );
        }
        else if ( objToStr instanceof short[] )
        {
            return
                    objToStr.getClass().getComponentType().getName() +
                    Arrays.toString(
                            (short[]) objToStr );
        }
        else if ( objToStr instanceof char[] )
        {
            return
                    objToStr.getClass().getComponentType().getName() +
                    Arrays.toString(
                            (char[]) objToStr );
        }
        else if ( objToStr instanceof int[] )
        {
            return
                    objToStr.getClass().getComponentType().getName() +
                    Arrays.toString(
                            (int[]) objToStr );
        }
        else if ( objToStr instanceof long[] )
        {
            return
                    objToStr.getClass().getComponentType().getName() +
                    Arrays.toString(
                            (long[]) objToStr );
        }
        else if ( objToStr instanceof float[] )
        {
            return
                    objToStr.getClass().getComponentType().getName() +
                    Arrays.toString(
                            (float[]) objToStr );
        }
        else if ( objToStr instanceof double[] )
        {
            return
                    objToStr.getClass().getComponentType().getName() +
                    Arrays.toString(
                            (double[]) objToStr );
        }

        return String.valueOf( objToStr );
    }

    private static String getFinalComponentClassName(
            final Object[] arr )
    {
        Class<?> componentType = arr.getClass().getComponentType();

        while ( componentType.isArray() )
        {
            componentType = componentType.getComponentType();
        }

        return componentType.getName();
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
                        // TODO print primitive type name as string literal
                        elementToStr.getClass().getComponentType().getName() +
                        Arrays.toString(
                                (boolean[]) elementToStr ) );
            }
            else if ( elementToStr instanceof byte[] )
            {
                buff.append(
                        // TODO print primitive type name as string literal
                        elementToStr.getClass().getComponentType().getName() +
                        Arrays.toString(
                                (byte[]) elementToStr ) );
            }
            else if ( elementToStr instanceof short[] )
            {
                buff.append(
                        // TODO print primitive type name as string literal
                        elementToStr.getClass().getComponentType().getName() +
                        Arrays.toString(
                                (short[]) elementToStr ) );
            }
            else if ( elementToStr instanceof char[] )
            {
                buff.append(
                        // TODO print primitive type name as string literal
                        elementToStr.getClass().getComponentType().getName() +
                        Arrays.toString(
                                (char[]) elementToStr ) );
            }
            else if ( elementToStr instanceof int[] )
            {
                buff.append(
                        // TODO print primitive type name as string literal
                        elementToStr.getClass().getComponentType().getName() +
                        Arrays.toString(
                                (int[]) elementToStr ) );
            }
            else if ( elementToStr instanceof long[] )
            {
                buff.append(
                        // TODO print primitive type name as string literal
                        elementToStr.getClass().getComponentType().getName() +
                        Arrays.toString(
                                (long[]) elementToStr ) );
            }
            else if ( elementToStr instanceof float[] )
            {
                buff.append(
                        // TODO print primitive type name as string literal
                        elementToStr.getClass().getComponentType().getName() +
                        Arrays.toString(
                                (float[]) elementToStr ) );
            }
            else if ( elementToStr instanceof double[] )
            {
                buff.append(
                        // TODO print primitive type name as string literal
                        elementToStr.getClass().getComponentType().getName() +
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

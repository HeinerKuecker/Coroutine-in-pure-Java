package de.heinerkuecker.util;

/**
 * Umwandeln von checked exceptions in unchecked exceptions.
 *
 * siehe <a href=
 * "http://brixomatic.wordpress.com/2010/04/29/hack-of-the-day-unchecking-checked-exceptions/"
 * >Unchecking checked exceptions</a>
 *
 * @author Heiner K&uuml;cker
 */
public final class ExceptionUnchecker
{
    /**
     * @param checkedException Exception to uncheck
     */
    public static void rethrow(
            final Throwable checkedException )
    {
        ExceptionUnchecker.<RuntimeException>thrownInsteadOf( checkedException );
    }

    /**
     * @param checkedException Exception to uncheck
     * @return will never occur
     */
    public static <R> R returnRethrow(
            final Throwable checkedException )
    {
        ExceptionUnchecker.<RuntimeException>thrownInsteadOf( checkedException );
        return null;
    }

    /**
     * @param t Exception object to uncheck
     * @throws T Type-Parameter, Type of Exception to uncheck
     */
    @SuppressWarnings( "unchecked" )
    private static <T extends Throwable> void thrownInsteadOf(
            final Throwable t )
    throws T
    {
        throw (T) t;
    }

//    /**
//     * Test.
//     *
//     * @param args unused
//     */
//    public static void main(
//            final String[] args )
//    {
//        // throw new java.io.IOException(); // we don’t want to declare this exception
//        UnChecker.rethrow( new IOException() );
//    }
}

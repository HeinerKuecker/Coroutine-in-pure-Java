package de.heinerkuecker.util;

/**
 * Util class.
 *
 * @author Heiner K&uuml;cker
 */
public class StringUtil
{

    /**
     * Convert the specified string to Java source literal string.
     *
     * @param str string to return as Java source literal string
     * @return Java source literal string
     */
    public static String strAsJavaLiteral(
            final String str )
    {
        if ( str == null )
        {
            return String.valueOf( null );
        }

        final StringBuilder buff = new StringBuilder( str.length() );
        for ( int index = 0 ; index < str.length() ; index++ )
        {
            final char c = str.charAt( index );

            switch ( c ) {
            //case '\\':
            //    buff.append( "\\" );
            //    break;

            case '\n':
                buff.append( "\\n" );
                break;

            case '\r':
                buff.append( "\\r" );
                break;

            case '\t':
                buff.append( "\\t" );
                break;

            case '\"':
                buff.append( "\\\"" );
                break;

                // TODO more escape chars

            default:
                buff.append( c );
                break;
            }
        }

        return '"' + buff.toString() + '"';
    }


}

package de.heinerkuecker.coroutine;

/**
 * Central switch for generating
 * debug informations for
 * {@link Coroutine} and
 * {@link CoroutineIterator}.
 *
 * Set this constants to <code>false</code>
 * for better performance and memory saving
 * on well tested coroutines.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineDebugSwitches
{
    /**
     * Global switch to check
     * coroutine on initialization.
     *
     * For better performance of well tested
     * coroutines switch this off.
     */
    public static boolean initializationChecks = true;

    /**
     * Global switch to save source position
     * (file name, line number, class, method)
     * on statement creation as debug info.
     *
     * For better performance of well tested
     * coroutines switch this off.
     */
    public static boolean saveCreationSourcePosition = true;

    /**
     * Global switch to save informations
     * to show last executed statement and
     * next statement to execute in
     * {@link Coroutine#toString()} and
     * {@link CoroutineIterator#toString()}.
     */
    public static boolean saveToStringInfos = true;
}

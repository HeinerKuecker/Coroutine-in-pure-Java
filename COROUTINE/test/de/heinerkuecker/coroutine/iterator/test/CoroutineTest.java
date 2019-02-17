package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Assert;
import org.junit.Test;

import de.heinerkuecker.coroutine.Coroutine;
import de.heinerkuecker.coroutine.CoroutineDebugSwitches;

/**
 * JUnit4 test case for {@link Coroutine}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineTest
{
    // https://www.tutorialspoint.com/java_xml/java_sax_parse_document.htm

    @Test
    public void testEmpty()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final Coroutine<Void, Void> coro =
                new Coroutine<>(
                        // coroutineReturnType
                        Void.class ,
                        // resumeArgumentType
                        Void.class
                        // stmts
                        );

        Assert.assertFalse(
                coro.isFinished() );

        Assert.assertEquals(
                //expected
                null ,
                //actual
                coro.resume( null ) );

        Assert.assertTrue(
                coro.isFinished() );
    }

}

package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.step.complex.StepSequence;
import de.heinerkuecker.coroutine.step.complex.TryCatch;
import de.heinerkuecker.coroutine.step.flow.Throw;
import de.heinerkuecker.coroutine.step.ret.YieldReturn;


/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CroutineIteratorTryCatchTest
{
    @Test
    public void test_TryCatch_0()
    {
        CoroutineIterator.initializationChecks = true;

        @SuppressWarnings("serial")
        class TestException
        extends RuntimeException
        {
            /**
             * Constructor.
             *
             * @param message
             */
            public TestException(
                    final String message )
            {
                super( message );
            }
        }

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // steps
                        TryCatch.<Integer/*, CoroutineIterator<Integer>*/>newTryCatch(
                                // tryStep
                                new StepSequence<Integer/*, CoroutineIterator<Integer>*/>(
                                        // creationStackOffset
                                        0 ,
                                        new Throw<Integer>( new TestException( "test" ) ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 0 ) ) ,
                                // catchExceptionClass
                                TestException.class ,
                                // catchBodySteps
                                new YieldReturn<>( 1 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    // TODO more tests

}

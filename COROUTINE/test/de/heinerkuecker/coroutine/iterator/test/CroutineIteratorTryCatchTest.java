package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.StepSequence;
import de.heinerkuecker.coroutine.step.complex.TryCatch;
import de.heinerkuecker.coroutine.step.flow.Throw;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;


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
                super(message);
            }
        }

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        TryCatch.<Integer/*, CoroutineIterator<Integer>*/>newTryCatch(
                                // tryStep
                                new StepSequence<Integer/*, CoroutineIterator<Integer>*/>(
                                        // creationStackOffset
                                        0 ,
                                        new Throw<Integer>( new TestException( "test" ) ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>(
                                                new Value<>( 0 ) ) ) ,
                                // catchExceptionClass
                                TestException.class ,
                                // catchBodySteps
                                new YieldReturn<>(
                                        new Value<>( 1 ) ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }

    // TODO more tests

}

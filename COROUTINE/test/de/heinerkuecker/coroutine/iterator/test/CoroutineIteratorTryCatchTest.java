package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.NullValue;
import de.heinerkuecker.coroutine.stmt.complex.Block;
import de.heinerkuecker.coroutine.stmt.complex.TryCatch;
import de.heinerkuecker.coroutine.stmt.flow.Throw;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;


/**
 * JUnit4 test case for {@link CoroutineIterator}.
 *
 * @author Heiner K&uuml;cker
 */
public class CoroutineIteratorTryCatchTest
{

    @SuppressWarnings("serial")
    static class TestException
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

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.getMessage() == null) ? 0 : this.getMessage().hashCode());
            return result;
        }

        @Override
        public boolean equals(
                final Object obj )
        {
            if ( this == obj )
            {
                return true;
            }

            if ( obj == null )
            {
                return false;
            }

            if ( getClass() != obj.getClass() )
            {
                return false;
            }

            final TestException other = (TestException) obj;

            if ( this.getMessage() == null )
            {
                if (other.getMessage() != null)
                {
                    return false;
                }
            }
            else if ( ! this.getMessage().equals( other.getMessage() ) )
            {
                return false;
            }

            return true;
        }
    }

    @Test
    @SuppressWarnings("javadoc")
    public void test_TryCatch_0()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        final CoroutineIterator<Integer> coroIter =
                new CoroutineIterator<Integer>(
                        // type
                        Integer.class ,
                        // stmts
                        TryCatch.newTryCatch(
                                // tryStmt
                                new Block<Integer , Void>(
                                        // creationStackOffset
                                        0 ,
                                        new Throw<>(
                                                new TestException(
                                                        // message
                                                        "test" ) ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( 0 ) ) ,
                                // catchExceptionClass
                                TestException.class ,
                                // catchedExceptionVariableName
                                "e" ,
                                // catchBodyStmts
                                new YieldReturn<>( 1 ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                1 );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }
    @Test
    @SuppressWarnings("javadoc")
    public void test_TryCatch_1()
    {
        CoroutineDebugSwitches.initializationChecks = true;
        //CoroutineDebugSwitches.saveToStringInfos = false;

        final CoroutineIterator<TestException> coroIter =
                new CoroutineIterator<TestException>(
                        // type
                        TestException.class ,
                        // stmts
                        TryCatch.<TestException , Void>newTryCatch(
                                // tryStmt
                                new Block<TestException , Void>(
                                        // creationStackOffset
                                        0 ,
                                        new Throw<>(
                                                new TestException(
                                                        // message
                                                        "test" ) ) ,
                                        // this yield return is never executed
                                        new YieldReturn<>( NullValue.nullValue() ) ) ,
                                // catchExceptionClass
                                TestException.class ,
                                // catchedExceptionVariableName
                                "e" ,
                                // catchBodyStmts
                                new YieldReturn<>(
                                        new GetLocalVar<>(
                                                //localVarName
                                                "e" ,
                                                //type
                                                TestException.class ) ) ) );

        CoroutineIteratorTest.assertNext(
                coroIter ,
                new TestException( "test" ) );

        CoroutineIteratorTest.assertHasNextFalse(
                coroIter );
    }


    // TODO more tests

}

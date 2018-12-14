package de.heinerkuecker.coroutine_iterator.step.result;

import de.heinerkuecker.coroutine_iterator.CoroutineIterator;
import de.heinerkuecker.coroutine_iterator.step.complex.DoWhile;
import de.heinerkuecker.coroutine_iterator.step.complex.For;
import de.heinerkuecker.coroutine_iterator.step.complex.While;

/**
 * Interface for result of one step
 * in {@link CoroutineIterator}.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public interface CoroIterStepResult<RESULT>
{
    /**
     * This method returns a instance to enforce continue stepping.
     *
     * @return Continue stepping
     * @param <RESULT> result type of method {@link CoroutineIterator#next()}
     */
    @SuppressWarnings("unchecked")
    public static <RESULT> ContinueCoroutine<RESULT> continueCoroutine()
    {
        return (ContinueCoroutine<RESULT>) ContinueCoroutine.INSTANCE;
    }

    /**
     * Class to enforce continue stepping.
     *
     * @param <RESULT> result type of method {@link CoroutineIterator#next()}
     */
    public class ContinueCoroutine<RESULT>
    implements CoroIterStepResult<RESULT>
    {
        @SuppressWarnings("rawtypes")
        private static ContinueCoroutine INSTANCE = new ContinueCoroutine<>();

        private ContinueCoroutine()
        {
            super();
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            return this.getClass().getSimpleName();
        }
    }

    /**
     * Class to enforce suspend stepping and set result.
     *
     * @param <RESULT> result type of method {@link CoroutineIterator#next()}
     */
    public class YieldReturnWithResult<RESULT>
    implements CoroIterStepResult<RESULT>
    {
        public final RESULT result;

        public YieldReturnWithResult(
                final RESULT result )
        {
            this.result = result;
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString()
        {
            return this.getClass().getSimpleName() + ": " + result;
        }
    }

    /**
     * Class to enforce stop stepping and set result.
     *
     * @param <RESULT> result type of method {@link CoroutineIterator#next()}
     */
    public class FinallyReturnWithResult<RESULT>
    implements CoroIterStepResult<RESULT>
    {
        public final RESULT result;

        public FinallyReturnWithResult(
                final RESULT result )
        {
            this.result = result;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            return this.getClass().getSimpleName() + ": " + result;
        }
    }

    /**
     * Class to enforce stop stepping without result.
     *
     * @param <RESULT> result type of method {@link CoroutineIterator#next()}
     */
    public class FinallyReturnWithoutResult<RESULT>
    implements CoroIterStepResult<RESULT>
    {
        /**
         * @see Object#toString()
         */
        @Override
        public String toString()
        {
            return this.getClass().getSimpleName();
        }
    }

    /**
     * Class to enforce break current loop like
     * {@link For},
     * {@link While} or
     * {@link DoWhile}.
     *
     * @param <RESULT> result type of method {@link CoroutineIterator#next()}
     */
    public class Break<RESULT>
    implements CoroIterStepResult<RESULT>
    {
        /**
         * Label of loop to break.
         */
        public final String label;

        /**
         * Constructor.
         *
         * @param label label of loop to break
         */
        public Break(
                final String label )
        {
            this.label = label;
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString()
        {
            return this.getClass().getSimpleName();
        }
    }

    /**
     * Class to enforce continue current loop like
     * {@link For},
     * {@link While} or
     * {@link DoWhile}.
     *
     * @param <RESULT> result type of method {@link CoroutineIterator#next()}
     */
    public class ContinueLoop<RESULT>
    implements CoroIterStepResult<RESULT>
    {
        /**
         * Label of loop to continue.
         */
        public final String label;

        /**
         * Constructor.
         *
         * @param label label of loop to continue
         */
        public ContinueLoop(
                final String label )
        {
            this.label = label;
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString()
        {
            return this.getClass().getSimpleName();
        }
    }

}

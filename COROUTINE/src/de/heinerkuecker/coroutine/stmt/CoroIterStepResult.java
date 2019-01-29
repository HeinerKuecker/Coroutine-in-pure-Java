package de.heinerkuecker.coroutine.stmt;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.stmt.complex.DoWhile;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.complex.ForEach;
import de.heinerkuecker.coroutine.stmt.complex.While;

/**
 * Interface for result of one step
 * in {@link CoroutineIterator}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public interface CoroIterStepResult<COROUTINE_RETURN>
{
    /**
     * This method returns a instance to enforce continue stepping.
     *
     * @return Continue stepping
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    @SuppressWarnings("unchecked")
    public static <COROUTINE_RETURN> ContinueCoroutine<COROUTINE_RETURN> continueCoroutine()
    {
        return (ContinueCoroutine<COROUTINE_RETURN>) ContinueCoroutine.INSTANCE;
    }

    /**
     * Class to enforce continue stepping.
     *
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class ContinueCoroutine<COROUTINE_RETURN>
    implements CoroIterStepResult<COROUTINE_RETURN>
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
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class YieldReturnWithResult<COROUTINE_RETURN>
    implements CoroIterStepResult<COROUTINE_RETURN>
    {
        public final COROUTINE_RETURN result;

        public YieldReturnWithResult(
                final COROUTINE_RETURN result )
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
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class FinallyReturnWithResult<COROUTINE_RETURN>
    implements CoroIterStepResult<COROUTINE_RETURN>
    {
        public final COROUTINE_RETURN result;

        public FinallyReturnWithResult(
                final COROUTINE_RETURN result )
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
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class FinallyReturnWithoutResult<COROUTINE_RETURN>
    implements CoroIterStepResult<COROUTINE_RETURN>
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
     * {@link ForEach},
     * {@link While} or
     * {@link DoWhile}.
     *
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class Break<COROUTINE_RETURN>
    implements CoroIterStepResult<COROUTINE_RETURN>
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
     * {@link ForEach},
     * {@link While} or
     * {@link DoWhile}.
     *
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class ContinueLoop<COROUTINE_RETURN>
    implements CoroIterStepResult<COROUTINE_RETURN>
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

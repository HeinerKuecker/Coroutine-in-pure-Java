package de.heinerkuecker.coroutine.stmt;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.stmt.complex.DoWhile;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.complex.ForEach;
import de.heinerkuecker.coroutine.stmt.complex.While;

/**
 * Interface for result of one statement
 * in {@link CoroutineIterator}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @author Heiner K&uuml;cker
 */
public interface CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN>
{
    /**
     * This method returns a instance to enforce continue run.
     *
     * @return Continue run
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    @SuppressWarnings("unchecked")
    public static <FUNCTION_RETURN , COROUTINE_RETURN> ContinueCoroutine<FUNCTION_RETURN , COROUTINE_RETURN> continueCoroutine()
    {
        return (ContinueCoroutine<FUNCTION_RETURN , COROUTINE_RETURN>) ContinueCoroutine.INSTANCE;
    }

    /**
     * Class to enforce continue run.
     *
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class ContinueCoroutine<FUNCTION_RETURN , COROUTINE_RETURN>
    implements CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN>
    {
        @SuppressWarnings("rawtypes")
        private static ContinueCoroutine INSTANCE = new ContinueCoroutine<>();

        private ContinueCoroutine()
        {
            super();
        }

        @Override
        public String toString()
        {
            return this.getClass().getSimpleName();
        }
    }

    /**
     * Class to enforce suspend run and set result.
     *
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class YieldReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>
    implements CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN>
    {
        public final COROUTINE_RETURN result;

        public YieldReturnWithResult(
                final COROUTINE_RETURN result )
        {
            this.result = result;
        }

        @Override
        public String toString()
        {
            return this.getClass().getSimpleName() + ": " + result;
        }
    }

    /**
     * Class to enforce stop run and set result.
     *
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class FinallyReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>
    implements CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN>
    {
        public final COROUTINE_RETURN result;

        public FinallyReturnWithResult(
                final COROUTINE_RETURN result )
        {
            this.result = result;
        }

        @Override
        public String toString()
        {
            return this.getClass().getSimpleName() + ": " + result;
        }
    }

    /**
     * Class to enforce stop run without result.
     *
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class FinallyReturnWithoutResult<FUNCTION_RETURN , COROUTINE_RETURN>
    implements CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN>
    {
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
    public class BreakLoop<FUNCTION_RETURN , COROUTINE_RETURN>
    implements CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN>
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
        public BreakLoop(
                final String label )
        {
            this.label = label;
        }

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
    public class ContinueLoop<FUNCTION_RETURN , COROUTINE_RETURN>
    implements CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN>
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

        @Override
        public String toString()
        {
            return this.getClass().getSimpleName();
        }
    }

    /**
     * Class to return value from function.
     *
     * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
     */
    public class ComplexExprReturn<FUNCTION_RETURN , COROUTINE_RETURN>
    implements CoroStmtResult<FUNCTION_RETURN , COROUTINE_RETURN>
    {
        /**
         * Function return value.
         */
        public final FUNCTION_RETURN result;

        /**
         * Constructor.
         *
         * @param result function return value
         */
        public ComplexExprReturn(
                final FUNCTION_RETURN result )
        {
            this.result = result;
        }

        @Override
        public String toString()
        {
            return this.getClass().getSimpleName() + ": " + result;
        }
    }

    public class FunctionReturnWithResult<FUNCTION_RETURN , COROUTINE_RETURN>
    extends ComplexExprReturn<FUNCTION_RETURN , COROUTINE_RETURN>
    {
        /**
         * Constructor.
         *
         * @param result
         */
        public FunctionReturnWithResult(
                final FUNCTION_RETURN result )
        {
            super(result);
        }
    }

}

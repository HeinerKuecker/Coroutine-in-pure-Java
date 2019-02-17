package de.heinerkuecker.coroutine.stmt;

import java.util.HashSet;

import de.heinerkuecker.coroutine.CoroCheckable;
import de.heinerkuecker.coroutine.Coroutine;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasCreationStackTraceElement;

/**
 * Abstract super class for one statement
 * in {@link CoroutineIterator}.
 *
 * @param <COROUTINE_RETURN> result type of method {@link Coroutine#resume} or {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
abstract public class CoroStmt<FUNCTION_RETURN, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>
extends HasCreationStackTraceElement
implements CoroCheckable
{
    ///**
    // * Execute one statement.
    // *
    // * @param parent
    // * @return object to return a value and to control the flow
    // */
    //CoroIterStmtResult<COROUTINE_RETURN> execute(
    //        final PARENT parent );

    /**
     * Constructor with safe creation line number optional.
     */
    protected CoroStmt(
            final int creationStackOffset )
    {
        super( creationStackOffset + 1 );
    }

    //abstract public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction();

    ///**
    // * Set reifier for type param {@link #COROUTINE_RETURN} to solve unchecked casts.
    // */
    //abstract public void setStmtCoroutineReturnType(
    //        final HashSet<String> alreadyCheckedFunctionNames ,
    //        final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
    //        final Class<? extends COROUTINE_RETURN> coroutineReturnType );

    /**
     * Set reifier for type param {@link #COROUTINE_RETURN} and {@link #RESUME_ARGUMENT} to solve unchecked casts.
     */
    abstract public void setStmtCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType );
}

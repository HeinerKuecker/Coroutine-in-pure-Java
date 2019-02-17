package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;

/**
 * Sequence of {@link CoroStmt} statements.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
public class Block<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends ComplexStmt<
    Block<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> ,
    BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> ,
    FUNCTION_RETURN ,
    COROUTINE_RETURN ,
    /*,PARENT*/
    RESUME_ARGUMENT
    >
{
    private final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT>[] stmts;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Block(
            final int creationStackOffset ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>... stmts )
    {
        // TODO HasCreationStackTraceElement.creationStackTraceElement never used
        super( creationStackOffset );

        for ( final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> stmt : stmts )
        {
            Objects.requireNonNull( stmt );
        }
        this.stmts = Objects.requireNonNull( stmts );
    }

    /**
     * @return count of subsequent stmts
     */
    int length()
    {
        return stmts.length;
    }

    CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, ? super PARENT*/ , RESUME_ARGUMENT> getStmt(
            final int index )
    {
        return stmts[ index ];
    }

    @Override
    public BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrFunctioncallOrComplexstmt<FUNCTION_RETURN , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new BlockState<>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<?, ?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<?, ?, ?>> result = new ArrayList<>();

        for ( final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> stmt : this.stmts )
        {
            if ( stmt instanceof BreakOrContinue )
            {
                result.add(
                        (BreakOrContinue<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>) stmt );
            }
            else if ( stmt instanceof ComplexStmt )
            {
                result.addAll(
                        ((ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) stmt).getUnresolvedBreaksOrContinues(
                                alreadyCheckedFunctionNames ,
                                parent ) );
            }
        }

        return result;
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<? , ? , ?>> result = new ArrayList<>();

        for ( final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> stmt : this.stmts )
        {
            result.addAll(
                    stmt.getFunctionArgumentGetsNotInFunction() );
        }

        return result;
    }

    @Override
    public void setStmtCoroutineReturnTypeAndResumeArgumentType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ? , ?> parent ,
            //final Class<? /*extends COROUTINE_RETURN*/> coroutineReturnType ,
            final Class<? extends COROUTINE_RETURN> coroutineReturnType ,
            final Class<? extends RESUME_ARGUMENT> resumeArgumentType )
    {
        for ( final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> stmt : this.stmts )
        {
            ( (CoroStmt<FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>) stmt ).setStmtCoroutineReturnTypeAndResumeArgumentType(
                    alreadyCheckedFunctionNames ,
                    (CoroutineOrFunctioncallOrComplexstmt) parent ,
                    coroutineReturnType ,
                    resumeArgumentType );
        }
    }

    /**
     * @see ComplexStmt#checkLabelAlreadyInUse
     */
    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        for ( final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> stmt : this.stmts )
        {
            if ( stmt instanceof ComplexStmt )
            {
                ((ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>) stmt).checkLabelAlreadyInUse(
                        alreadyCheckedFunctionNames ,
                        parent ,
                        labels );
            }
        }
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        final Map<String, Class<?>> thisLocalVariableTypes =
                new HashMap<>(
                        localVariableTypes );

        for ( final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> stmt : this.stmts )
        {
            if ( stmt instanceof DeclareVariable )
            {
                final DeclareVariable<?, ?, ?, ?> declareLocalVar = (DeclareVariable<?, ?, ?, ?>) stmt;

                if ( thisLocalVariableTypes.containsKey( declareLocalVar.varName ) )
                {
                    throw new DeclareVariable.VariableAlreadyDeclaredException(
                            declareLocalVar );
                }

                thisLocalVariableTypes.put(
                        declareLocalVar.varName ,
                        declareLocalVar.type );
            }
            else
            {
                stmt.checkUseVariables(
                        alreadyCheckedFunctionNames ,
                        parent ,
                        //thisGlobalVariableTypes
                        globalVariableTypes ,
                        thisLocalVariableTypes );
            }
        }
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        for ( final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> stmt : this.stmts )
        {
            stmt.checkUseArguments(
                    alreadyCheckedFunctionNames ,
                    parent );
        }
    }

    /**
     * @see ComplexStmt#toString
     */
    @Override
    public String toString(
            final CoroutineOrFunctioncallOrComplexstmt</*FUNCTION_RETURN*/? , COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStmtExecuteState ,
            //final BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, ?, /*FUNCTION_RETURN*/? , COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStmtExecuteState
            //BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> nextStmtExecuteState
            )
    {
        @SuppressWarnings("unchecked")
        final BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> lastSequenceExecuteState =
                (BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextSequenceExecuteState =
                (BlockState<FUNCTION_RETURN , COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final StringBuilder buff = new StringBuilder();

        if ( nextSequenceExecuteState != null )
        {
            buff.append( nextSequenceExecuteState.getVariablesStr( indent ) );
        }

        for ( int i = 0 ; i < stmts.length ; i++ )
        {
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> stmt = this.stmts[ i ];

            if ( stmt instanceof ComplexStmt )
            {
                final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> lastSubStmtExecuteState;
                if ( lastSequenceExecuteState != null &&
                        lastSequenceExecuteState.currentStmtIndex == i )
                {
                    lastSubStmtExecuteState = lastSequenceExecuteState.currentComplexState;
                }
                else
                {
                    lastSubStmtExecuteState = null;
                }

                final ComplexStmtState<?, ?, FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> nextSubStmtExecuteState;
                if ( nextSequenceExecuteState != null &&
                        nextSequenceExecuteState.currentStmtIndex == i )
                {
                    nextSubStmtExecuteState = nextSequenceExecuteState.currentComplexState;
                }
                else
                {
                    nextSubStmtExecuteState = null;
                }

                buff.append(
                        ( (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT>) stmt ).toString(
                                parent ,
                                //indent
                                indent /*+ " "*/ ,
                                lastSubStmtExecuteState ,
                                nextSubStmtExecuteState ) );
            }
            else
            {
                if ( indent.length() > "next".length() )
                {
                    if ( nextSequenceExecuteState != null &&
                            nextSequenceExecuteState.currentStmtIndex == i )
                    {
                        buff.append( "next:" );
                    }
                    else if ( lastSequenceExecuteState != null &&
                            lastSequenceExecuteState.currentStmtIndex == i )
                    {
                        buff.append( "last:" );
                    }
                    else
                    {
                        buff.append( nextOrLastSpaceStr() );
                    }

                    buff.append(
                            indentStrWithoutNextOrLastPart(
                                    indent ) );
                }

                buff.append( stmt );

                buff.append( ";\n" );
            }
        }

        return buff.toString();
    }

    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <FUNCTION_RETURN , COROUTINE_RETURN , RESUME_ARGUMENT> ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT> convertStmtsToComplexStmt(
            final int creationStackOffset ,
            final CoroStmt<FUNCTION_RETURN , ? extends COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>... stmts )
    {
        final ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT> complexStmt;
        if ( stmts.length == 1 &&
                stmts[ 0 ] instanceof ComplexStmt )
        {
            complexStmt =
                    (ComplexStmt<?, ?, FUNCTION_RETURN , COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) stmts[ 0 ];
        }
        else
        {
            complexStmt =
                    new Block<>(
                            creationStackOffset ,
                            stmts );
        }

        return complexStmt;
    }

}

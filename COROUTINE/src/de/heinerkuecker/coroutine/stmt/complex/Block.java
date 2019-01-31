package de.heinerkuecker.coroutine.stmt.complex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.exprs.GetProcedureArgument;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.flow.BreakOrContinue;
import de.heinerkuecker.coroutine.stmt.simple.DeclareVariable;

/**
 * Sequence of {@link CoroIterStmt} statements.
 *
 * @param <COROUTINE_RETURN> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
public class Block<COROUTINE_RETURN /*, PARENT extends CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT>
extends ComplexStmt<
    Block<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> ,
    BlockState<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> ,
    COROUTINE_RETURN ,
    /*,PARENT*/
    RESUME_ARGUMENT
    >
{
    private final CoroIterStmt<? extends COROUTINE_RETURN/*, PARENT*/>[] stmts;

    /**
     * Constructor.
     */
    @SafeVarargs
    public Block(
            final int creationStackOffset ,
            final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/>... stmts )
    {
        // TODO HasCreationStackTraceElement.creationStackTraceElement never used
        super( creationStackOffset );

        for ( final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/> stmt : stmts )
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

    CoroIterStmt<? extends COROUTINE_RETURN /*, ? super PARENT*/> getStmt(
            final int index )
    {
        return stmts[ index ];
    }

    @Override
    public BlockState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> newState(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        return new BlockState<>(
                this ,
                parent );
    }

    @Override
    public List<BreakOrContinue<?, ?>> getUnresolvedBreaksOrContinues(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        final List<BreakOrContinue<?, ?>> result = new ArrayList<>();

        for ( final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/> stmt : this.stmts )
        {
            if ( stmt instanceof BreakOrContinue )
            {
                result.add(
                        (BreakOrContinue<COROUTINE_RETURN , RESUME_ARGUMENT>) stmt );
            }
            else if ( stmt instanceof ComplexStmt )
            {
                result.addAll(
                        ((ComplexStmt<?, ?, COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) stmt).getUnresolvedBreaksOrContinues(
                                alreadyCheckedProcedureNames ,
                                parent ) );
            }
        }

        return result;
    }

    /**
     * @see CoroIterStmt#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        for ( final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/> stmt : this.stmts )
        {
            result.addAll(
                    stmt.getProcedureArgumentGetsNotInProcedure() );
        }

        return result;
    }

    /**
     * @see CoroIterStmt#setResultType(Class)
     */
    @Override
    public void setResultType(
            final Class<? extends COROUTINE_RETURN> resultType )
    {
        for ( final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/> stmt : this.stmts )
        {
            ( (CoroIterStmt<COROUTINE_RETURN>) stmt ).setResultType( resultType );
        }
    }

    /**
     * @see ComplexStmt#checkLabelAlreadyInUse
     */
    @Override
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final Set<String> labels )
    {
        for ( final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/> stmt : this.stmts )
        {
            if ( stmt instanceof ComplexStmt )
            {
                ((ComplexStmt<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT>) stmt).checkLabelAlreadyInUse(
                        alreadyCheckedProcedureNames ,
                        parent ,
                        labels );
            }
        }
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        final Map<String, Class<?>> thisLocalVariableTypes =
                new HashMap<>(
                        localVariableTypes );

        for ( final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/> stmt : this.stmts )
        {
            if ( stmt instanceof DeclareVariable )
            {
                final DeclareVariable<?, ?, ?> declareLocalVar = (DeclareVariable<?, ?, ?>) stmt;

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
                        alreadyCheckedProcedureNames ,
                        parent ,
                        //thisGlobalVariableTypes
                        globalVariableTypes ,
                        thisLocalVariableTypes );
            }
        }
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<?, ?> parent )
    {
        for ( final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/> stmt : this.stmts )
        {
            stmt.checkUseArguments(
                    alreadyCheckedProcedureNames ,
                    parent );
        }
    }

    /**
     * @see ComplexStmt#toString
     */
    @Override
    public String toString(
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent ,
            final String indent ,
            final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> lastStmtExecuteState ,
            final ComplexStmtState<?, ?, COROUTINE_RETURN/*, PARENT*/ , RESUME_ARGUMENT> nextStmtExecuteState )
    {
        @SuppressWarnings("unchecked")
        final BlockState<COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT> lastSequenceExecuteState =
                (BlockState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) lastStmtExecuteState;

        @SuppressWarnings("unchecked")
        final BlockState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT> nextSequenceExecuteState =
                (BlockState<COROUTINE_RETURN /*, PARENT*/ , RESUME_ARGUMENT>) nextStmtExecuteState;

        final StringBuilder buff = new StringBuilder();

        if ( nextSequenceExecuteState != null )
        {
            buff.append( nextSequenceExecuteState.getVariablesStr( indent ) );
        }

        for ( int i = 0 ; i < stmts.length ; i++ )
        {
            final CoroIterStmt<? extends COROUTINE_RETURN /*, PARENT*/> stmt = this.stmts[ i ];

            if ( stmt instanceof ComplexStmt )
            {
                final ComplexStmtState<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT> lastSubStmtExecuteState;
                if ( lastSequenceExecuteState != null &&
                        lastSequenceExecuteState.currentStmtIndex == i )
                {
                    lastSubStmtExecuteState = lastSequenceExecuteState.currentComplexState;
                }
                else
                {
                    lastSubStmtExecuteState = null;
                }

                final ComplexStmtState<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT> nextSubStmtExecuteState;
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
                        ( (ComplexStmt<?, ?, COROUTINE_RETURN , RESUME_ARGUMENT>) stmt ).toString(
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
    public static <COROUTINE_RETURN , RESUME_ARGUMENT> ComplexStmt<?, ?, COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT> convertStmtsToComplexStmt(
            final int creationStackOffset ,
            final CoroIterStmt<? extends COROUTINE_RETURN /*, /*PARENT * / CoroutineIterator<COROUTINE_RETURN>*/>... stmts )
    {
        final ComplexStmt<?, ?, COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/, RESUME_ARGUMENT> complexStmt;
        if ( stmts.length == 1 &&
                stmts[ 0 ] instanceof ComplexStmt )
        {
            complexStmt =
                    (ComplexStmt<?, ?, COROUTINE_RETURN /*, CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT>) stmts[ 0 ];
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

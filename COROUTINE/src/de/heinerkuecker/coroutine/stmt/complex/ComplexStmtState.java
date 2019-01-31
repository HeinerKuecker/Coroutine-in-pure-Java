package de.heinerkuecker.coroutine.stmt.complex;

import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstmt;
import de.heinerkuecker.coroutine.Procedure;
import de.heinerkuecker.coroutine.VariablesOrLocalVariables;
import de.heinerkuecker.coroutine.arg.Arguments;
import de.heinerkuecker.coroutine.stmt.CoroIterStmtResult;
import de.heinerkuecker.util.HCloneable;

/**
 * Extern instruction pointer and stack
 * for {@link ComplexStmt}.
 *
 * @param <STMT>
 * @param <COROUTINE_RETURN>
 * @param <PARENT>
 * @author Heiner K&uuml;cker
 *
 * TODO rename to ComplexStmtExecuteState
 */
abstract public /*interface*/class ComplexStmtState<
    STMT_STATE extends ComplexStmtState<STMT_STATE, STMT, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    STMT extends ComplexStmt<STMT, STMT_STATE, COROUTINE_RETURN /*, PARENT*/, RESUME_ARGUMENT>,
    COROUTINE_RETURN ,
    //PARENT extends CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT>
    RESUME_ARGUMENT
>
implements
    CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> ,
    HCloneable<STMT_STATE>
{
    protected final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent;

    private final BlockLocalVariables blockLocalVariables;

    /**
     * Constructor.
     */
    protected ComplexStmtState(
            //final BlockLocalVariables blockLocalVariables
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        this.parent = Objects.requireNonNull( parent );

        //System.out.println( new Exception().getStackTrace()[ 0 ] + " " + this.getClass().getSimpleName() + " new blockLocalVariables " + parent.localVars() );
        this.blockLocalVariables =
                new BlockLocalVariables(
                        parent.localVars() );
    }

    /**
     * Execute one complex stmt.
     *
     * @param parent
     * @return object to return a value and to control the flow
     */
    abstract public CoroIterStmtResult<COROUTINE_RETURN> execute(
            //PARENT parent
            //CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent
            );

    /**
     * This method returns if all sub statements have been executed.
     *
     * @return all sub statements have been executed or not
     */
    abstract public boolean isFinished();

    abstract public STMT getStmt();

    @Override
    public void saveLastStmtState()
    {
        //getRootParent().saveLastStmtState();
        this.parent.saveLastStmtState();
    }

    //@Override
    //public CoroutineIterator<COROUTINE_RETURN> getRootParent()
    //{
    //    return parent.getRootParent();
    //}

    @Override
    public Procedure<COROUTINE_RETURN , RESUME_ARGUMENT> getProcedure(
            final String procedureName )
    {
        return parent.getProcedure( procedureName );
    }

    @Override
    //public VariablesOrLocalVariables localVars()
    public BlockLocalVariables localVars()
    {
        return this.blockLocalVariables;
    }

    @Override
    public VariablesOrLocalVariables globalVars()
    {
        return parent.globalVars();
    }

    @Override
    public Arguments procedureArgumentValues()
    {
        return parent.procedureArgumentValues();
    }

    @Override
    public Map<String, Class<?>> procedureParameterTypes()
    {
        return parent.procedureParameterTypes();
    }

    @Override
    public Arguments globalArgumentValues()
    {
        return parent.globalArgumentValues();
    }

    @Override
    public Map<String, Class<?>> globalParameterTypes()
    {
        return parent.globalParameterTypes();
    }

    protected String getVariablesStr(
            final String indent )
    {
        if ( this.blockLocalVariables.isEmpty() )
        {
            return "";
        }

        final String variablesStr;

        variablesStr =
                    indent +
                    "variables: " +
                    blockLocalVariables +
                    "\n";

        return variablesStr;
    }

    @Override
    public RESUME_ARGUMENT getResumeArgument()
    {
        return this.parent.getResumeArgument();
    }

}

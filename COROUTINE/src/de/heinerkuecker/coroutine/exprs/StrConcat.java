package de.heinerkuecker.coroutine.exprs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.stmt.CoroStmt;
import de.heinerkuecker.util.ArrayDeepToString;

public class StrConcat
implements CoroExpression<String>
{
    /**
     * Left hand side expression.
     */
    public final CoroExpression<?> lhs;

    /**
     * Right hand side expression to add.
     */
    public final CoroExpression<?> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public StrConcat(
            final CoroExpression<?> lhs ,
            final CoroExpression<?> rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public StrConcat(
            final String lhs ,
            final CoroExpression<?> rhs )
    {
        this.lhs = Value.strValue( lhs );
        this.rhs = Objects.requireNonNull( rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public StrConcat(
            final CoroExpression<?> lhs ,
            final String rhs )
    {
        this.lhs = Objects.requireNonNull( lhs );
        this.rhs = Value.strValue( rhs );
    }

    /**
     * Add.
     */
    @Override
    public String evaluate(
            final HasArgumentsAndVariables<?>/*CoroutineOrFunctioncallOrComplexstmt<?, ?>*/ parent )
    {
        final Object lhsResult = lhs.evaluate( parent );
        final Object rhsResult = rhs.evaluate( parent );


        return
                ArrayDeepToString.deepToString( lhsResult ) +
                ArrayDeepToString.deepToString( rhsResult );
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<?>> getFunctionArgumentGetsNotInFunction()
    {
        final List<GetFunctionArgument<?>> result = new ArrayList<>();

        result.addAll(
                lhs.getFunctionArgumentGetsNotInFunction() );

        result.addAll(
                rhs.getFunctionArgumentGetsNotInFunction() );

        return result;
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.lhs.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );

        this.rhs.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.lhs.checkUseArguments( alreadyCheckedFunctionNames, parent );
        this.rhs.checkUseArguments( alreadyCheckedFunctionNames, parent );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends String>[] type()
    {
        //return String.class;
        return new Class[] { String.class };
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return lhs + " + " + rhs;
    }

}

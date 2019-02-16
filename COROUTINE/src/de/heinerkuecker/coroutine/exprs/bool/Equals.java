package de.heinerkuecker.coroutine.exprs.bool;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.CoroExpression;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.exprs.Value;

/**
 * Equals condition
 * to check equality of
 * the results of two given
 * {@link CoroExpression}.
 *
 * @param <TO_EQUAL> type of expression results to compare
 * @author Heiner K&uuml;cker
 */
public class Equals<TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT>
extends LhsRhsBoolExpression<TO_EQUAL, COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final SimpleExpression<? extends TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final SimpleExpression<? extends TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
    {
        super(
                lhs ,
                rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final TO_EQUAL lhsValue ,
            final SimpleExpression<? extends TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT> rhs )
    {
        super(
                new Value<TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        (Class<? extends TO_EQUAL>) Object.class ,
                        lhsValue ) ,
                rhs );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final SimpleExpression<? extends TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT> lhs ,
            final TO_EQUAL rhsValue )
    {
        super(
                lhs ,
                new Value<TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        (Class<? extends TO_EQUAL>) Object.class ,
                        rhsValue ) );
    }

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public Equals(
            final TO_EQUAL lhsValue ,
            final TO_EQUAL rhsValue )
    {
        super(
                new Value<TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        (Class<? extends TO_EQUAL>) Object.class ,
                        lhsValue ) ,
                new Value<TO_EQUAL , COROUTINE_RETURN , RESUME_ARGUMENT>(
                        (Class<? extends TO_EQUAL>) Object.class ,
                        rhsValue ) );
    }

    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    {
        if ( CoroutineDebugSwitches.logSimpleStatementsAndExpressions )
        {
            System.out.println( "evaluate " + this );
        }

        final TO_EQUAL lhsResult = lhs.evaluate( parent );
        final TO_EQUAL rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null && rhsResult == null )
        {
            return true;
        }

        if ( lhsResult == null )
        {
            return false;
        }

        return lhsResult.equals( rhsResult );
    }

    //@Override
    //public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    //{
    //    final List<GetFunctionArgument<? , ? , ?>> result = new ArrayList<>();
    //
    //    result.addAll(
    //            lhs.getFunctionArgumentGetsNotInFunction() );
    //
    //    result.addAll(
    //            rhs.getFunctionArgumentGetsNotInFunction() );
    //
    //    return result;
    //}

    //@Override
    //public void checkUseVariables(
    //        final HashSet<String> alreadyCheckedFunctionNames ,
    //        final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
    //        final Map<String, Class<?>> globalVariableTypes ,
    //        final Map<String, Class<?>> localVariableTypes )
    //{
    //    this.lhs.checkUseVariables(
    //            alreadyCheckedFunctionNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //
    //    this.rhs.checkUseVariables(
    //            alreadyCheckedFunctionNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //}

    //@Override
    //public void checkUseArguments(
    //        final HashSet<String> alreadyCheckedFunctionNames ,
    //        final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    //{
    //    this.lhs.checkUseArguments( alreadyCheckedFunctionNames, parent );
    //    this.rhs.checkUseArguments( alreadyCheckedFunctionNames, parent );
    //}

    //@Override
    //public void setCoroutineReturnType(
    //        final HashSet<String> alreadyCheckedFunctionNames ,
    //        final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
    //        final Class<?> coroutineReturnType )
    //{
    //    this.lhs.setCoroutineReturnType(
    //            alreadyCheckedFunctionNames ,
    //            parent ,
    //            coroutineReturnType );
    //
    //    this.rhs.setCoroutineReturnType(
    //            alreadyCheckedFunctionNames ,
    //            parent ,
    //            coroutineReturnType );
    //}

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return "( " + lhs + " == " + rhs + " )";
    }

}

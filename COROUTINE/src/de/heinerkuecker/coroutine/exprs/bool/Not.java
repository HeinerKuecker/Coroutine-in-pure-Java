package de.heinerkuecker.coroutine.exprs.bool;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrFunctioncallOrComplexstmt;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.exprs.GetFunctionArgument;
import de.heinerkuecker.coroutine.exprs.SimpleExpression;
import de.heinerkuecker.coroutine.stmt.CoroStmt;

public class Not<COROUTINE_RETURN , RESUME_ARGUMENT>
extends CoroBooleanExpression<COROUTINE_RETURN , RESUME_ARGUMENT>
{
    /**
     * Expression to negate.
     */
    public final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> conditionToNegate;

    /**
     * Constructor.
     */
    public Not(
            final SimpleExpression<Boolean , COROUTINE_RETURN , RESUME_ARGUMENT> conditionToNegate )
    {
        this.conditionToNegate = Objects.requireNonNull( conditionToNegate );
    }

    ///**
    // * Constructor.
    // */
    //public Not(
    //        final CoroExpression<Boolean> conditionToNegate )
    //{
    //    this.conditionToNegate =
    //            new IsTrue(
    //                    conditionToNegate );
    //}

    /**
     * Negates the specified condition.
     */
    @Override
    public Boolean evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    {
        return ! conditionToNegate.evaluate( parent );
    }

    /**
     * @see CoroStmt#getFunctionArgumentGetsNotInFunction()
     */
    @Override
    public List<GetFunctionArgument<? , ? , ?>> getFunctionArgumentGetsNotInFunction()
    {
        return this.conditionToNegate.getFunctionArgumentGetsNotInFunction();
    }

    @Override
    public void checkUseVariables(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        this.conditionToNegate.checkUseVariables(
                alreadyCheckedFunctionNames ,
                parent ,
                globalVariableTypes, localVariableTypes );
    }

    @Override
    public void checkUseArguments(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent )
    {
        this.conditionToNegate.checkUseArguments( alreadyCheckedFunctionNames , parent );
    }

    @Override
    public void setExprCoroutineReturnType(
            final HashSet<String> alreadyCheckedFunctionNames ,
            final CoroutineOrFunctioncallOrComplexstmt<?, ?, ?> parent, Class<?> coroutineReturnType )
    {
        this.conditionToNegate.setExprCoroutineReturnType(
                alreadyCheckedFunctionNames ,
                parent ,
                coroutineReturnType );
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        //return "Not [conditionToNegate=" + this.conditionToNegate + "]";
        return "( ! " + this.conditionToNegate + " )";
    }

}

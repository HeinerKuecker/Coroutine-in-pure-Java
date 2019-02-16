package de.heinerkuecker.coroutine.exprs;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public class NewIllegalStateException<COROUTINE_RETURN , RESUME_ARGUMENT>
extends AbstrOneExprExpression<IllegalStateException , String , COROUTINE_RETURN , RESUME_ARGUMENT>
{

    public NewIllegalStateException(
            final SimpleExpression<String , COROUTINE_RETURN , RESUME_ARGUMENT> messageExpr )
    {
        super( messageExpr );
    }

    @Override
    public IllegalStateException evaluate(
            final HasArgumentsAndVariables<? extends RESUME_ARGUMENT> parent )
    {
        final String message = super.expr.evaluate( parent );
        return new IllegalStateException( message );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<IllegalStateException>[] type()
    {
        return new Class[]{ IllegalStateException.class };
    }

}

package de.heinerkuecker.coroutine.exprs;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public class NewIllegalStateException<COROUTINE_RETURN>
extends AbstrOneExprExpression<IllegalStateException , String , COROUTINE_RETURN>
{

    public NewIllegalStateException(
            final SimpleExpression<String , COROUTINE_RETURN> messageExpr )
    {
        super( messageExpr );
    }

    @Override
    public IllegalStateException evaluate(
            final HasArgumentsAndVariables<?> parent )
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

package de.heinerkuecker.coroutine.expression;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

public class NewIllegalStateException
extends AbstrOneExprExpression<IllegalStateException , String>
{

    public NewIllegalStateException(
            final CoroExpression<String> messageExpr )
    {
        super( messageExpr );
    }

    @Override
    public IllegalStateException evaluate(
            final HasArgumentsAndVariables parent )
    {
        final String message = super.expr.evaluate( parent );
        return new IllegalStateException( message );
    }

    @Override
    public Class<IllegalStateException>[] type()
    {
        return new Class[]{ IllegalStateException.class };
    }

}

package de.heinerkuecker.coroutine.expression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroIteratorOrProcedure;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.util.ArrayTypeName;

public class NewArray<ELEMENT>
implements CoroExpression<ELEMENT[]>
{
    private final Class<? extends ELEMENT> elementClass;

    private final CoroExpression<ELEMENT>[] arrayElementExpressions;

    /**
     * Constructor.
     *
     * @param elementClass
     * @param arrayElementExpressions
     */
    @SafeVarargs
    public NewArray(
            final Class<? extends ELEMENT> elementClass ,
            CoroExpression<ELEMENT>... arrayElementExpressions )
    {
        this.elementClass =
                Objects.requireNonNull(
                        elementClass );

        this.arrayElementExpressions =
                Objects.requireNonNull(
                        arrayElementExpressions );
    }

    /**
     * @see CoroExpression#evaluate(CoroIteratorOrProcedure)
     */
    @Override
    public ELEMENT[] evaluate(
            final HasArgumentsAndVariables/*CoroIteratorOrProcedure<?>*/ parent )
    {
        final Class<? extends ELEMENT> componentClass = elementClass;

        @SuppressWarnings("unchecked")
        final ELEMENT[] result = (ELEMENT[]) Array.newInstance( componentClass ,  this.arrayElementExpressions.length );

        for ( int i = 0 ; i < this.arrayElementExpressions.length ; i++ )
        {
            result[ i ] = this.arrayElementExpressions[ i ].evaluate( parent );
        }

        return result;
    }

    /**
     * @see CoroIterStep#getProcedureArgumentGetsNotInProcedure()
     */
    @Override
    public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    {
        final List<GetProcedureArgument<?>> result = new ArrayList<>();

        for ( final CoroExpression<ELEMENT> arrayElementExpression : arrayElementExpressions )
        {
            result.addAll(
                    arrayElementExpression.getProcedureArgumentGetsNotInProcedure() );
        }

        return result;
    }

    @Override
    public void checkUseVariables(
            HashSet<String> alreadyCheckedProcedureNames ,
            final CoroIteratorOrProcedure<?> parent ,
            final Map<String, Class<?>> globalVariableTypes, final Map<String, Class<?>> localVariableTypes )
    {
        for ( final CoroExpression<ELEMENT> arrayElementExpression : arrayElementExpressions )
        {
            arrayElementExpression.checkUseVariables(
                    alreadyCheckedProcedureNames ,
                    parent ,
                    globalVariableTypes, localVariableTypes );
        }
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroIteratorOrProcedure<?> parent )
    {
        for ( final CoroExpression<ELEMENT> arrayElementExpression : arrayElementExpressions )
        {
            arrayElementExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
        }
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString()
    {
        return
                this.getClass().getSimpleName() +
                "<" +
                ArrayTypeName.toStr( this.elementClass ) +
                ">" +
                Arrays.toString( this.arrayElementExpressions );
    }

}

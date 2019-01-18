package de.heinerkuecker.coroutine.expression;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.CoroutineOrProcedureOrComplexstep;
import de.heinerkuecker.coroutine.HasArgumentsAndVariables;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.util.ArrayTypeName;

public class NewArray<ELEMENT>
implements CoroExpression<ELEMENT[]>
{
    //public final Class<? extends ELEMENT> elementClass;
    public final Class<? extends ELEMENT[]> arrayClass;

    private final CoroExpression<ELEMENT>[] arrayElementExpressions;

    /**
     * Constructor.
     *
     * @param elementClass
     * @param arrayElementExpressions
     */
    @SafeVarargs
    public NewArray(
            //final Class<? extends ELEMENT> elementClass ,
            final Class<? extends ELEMENT[]> arrayClass ,
            final CoroExpression<ELEMENT>... arrayElementExpressions )
    {
        //this.elementClass = Objects.requireNonNull( elementClass );
        this.arrayClass = Objects.requireNonNull( arrayClass );

        this.arrayElementExpressions =
                Objects.requireNonNull(
                        arrayElementExpressions );
    }

    @Override
    public ELEMENT[] evaluate(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?>*/ parent )
    {
        //final Class<? extends ELEMENT> componentClass = elementClass;
        final Class<?> componentClass = arrayClass.getComponentType();

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
            final boolean isCoroutineRoot ,
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstep<?> parent ,
            final Map<String, Class<?>> globalVariableTypes ,
            final Map<String, Class<?>> localVariableTypes )
    {
        for ( final CoroExpression<ELEMENT> arrayElementExpression : arrayElementExpressions )
        {
            arrayElementExpression.checkUseVariables(
                    isCoroutineRoot ,
                    alreadyCheckedProcedureNames ,
                    parent ,
                    globalVariableTypes, localVariableTypes );
        }
    }

    @Override
    public void checkUseArguments(
            HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?> parent )
    {
        for ( final CoroExpression<ELEMENT> arrayElementExpression : arrayElementExpressions )
        {
            arrayElementExpression.checkUseArguments( alreadyCheckedProcedureNames, parent );
        }
    }

    //@Override
    //public Class<? extends ELEMENT[]> type()
    //{
    //    return elementClass[].class;
    //}
    @Override
    public Class<? extends ELEMENT[]>[] type()
    {
        return new Class[]{ arrayClass };
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
                //ArrayTypeName.toStr( this.elementClass ) +
                ArrayTypeName.toStr( this.arrayClass.getComponentType() ) +
                ">" +
                Arrays.toString( this.arrayElementExpressions );
    }

}

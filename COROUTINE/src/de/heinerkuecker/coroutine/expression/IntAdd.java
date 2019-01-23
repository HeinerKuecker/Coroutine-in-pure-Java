package de.heinerkuecker.coroutine.expression;

import de.heinerkuecker.coroutine.HasArgumentsAndVariables;

/**
 * {@link Integer} add
 * result of the right
 * expression {@link CoroExpression}
 * to the result of the left
 * expression {@link CoroExpression}.
 *
 * @author Heiner K&uuml;cker
 */
public class IntAdd
//implements CoroExpression<Integer>
extends LhsRhsExpression<Integer>
{
    /**
     * Left hand side expression.
     */
    //public final CoroExpression<Integer> lhs;

    /**
     * Right hand side expression to add.
     */
    //public final CoroExpression<Integer> rhs;

    /**
     * Constructor.
     *
     * @param lhs
     * @param rhs
     */
    public IntAdd(
            final CoroExpression<Integer> lhs ,
            final CoroExpression<Integer> rhs )
    {
        //this.lhs = Objects.requireNonNull( lhs );
        //this.rhs = Objects.requireNonNull( rhs );
        super(
                lhs ,
                rhs );
    }

    /**
     * Convenience constructor.
     *
     * @param lhs
     * @param rhs
     */
    public IntAdd(
            final CoroExpression<Integer> lhs ,
            final Integer rhs )
    {
        //this.lhs = Objects.requireNonNull( lhs );
        //this.rhs = new Value<Integer>( rhs );
        super(
                lhs ,
                new Value<Integer>( rhs ) );
    }

    /**
     * Add.
     */
    @Override
    public Integer evaluate(
            final HasArgumentsAndVariables/*CoroutineOrProcedureOrComplexstep<?, ?>*/ parent )
    {
        final Integer lhsResult = lhs.evaluate( parent );
        final Integer rhsResult = rhs.evaluate( parent );

        if ( lhsResult == null )
        {
            throw new NullPointerException( "lhs: " + lhs );
        }

        if ( rhsResult == null )
        {
            throw new NullPointerException( "rhs: " + rhs );
        }

        return lhsResult + rhsResult;
    }

    //@Override
    //public List<GetProcedureArgument<?>> getProcedureArgumentGetsNotInProcedure()
    //{
    //    final List<GetProcedureArgument<?>> result = new ArrayList<>();
    //
    //    result.addAll(
    //            lhs.getProcedureArgumentGetsNotInProcedure() );
    //
    //    result.addAll(
    //            rhs.getProcedureArgumentGetsNotInProcedure() );
    //
    //    return result;
    //}

    //@Override
    //public void checkUseVariables(
    //        //final boolean isCoroutineRoot ,
    //        final HashSet<String> alreadyCheckedProcedureNames ,
    //        final CoroutineOrProcedureOrComplexstep<?, ?> parent,
    //        final Map<String, Class<?>> globalVariableTypes ,
    //        final Map<String, Class<?>> localVariableTypes)
    //{
    //    this.lhs.checkUseVariables(
    //            //isCoroutineRoot ,
    //            alreadyCheckedProcedureNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //
    //    this.rhs.checkUseVariables(
    //            //isCoroutineRoot ,
    //            alreadyCheckedProcedureNames ,
    //            parent ,
    //            globalVariableTypes, localVariableTypes );
    //}

    //@Override
    //public void checkUseArguments(
    //        HashSet<String> alreadyCheckedProcedureNames, final CoroutineOrProcedureOrComplexstep<?, ?> parent )
    //{
    //    this.lhs.checkUseArguments( alreadyCheckedProcedureNames, parent );
    //    this.rhs.checkUseArguments( alreadyCheckedProcedureNames, parent );
    //}

    @SuppressWarnings("unchecked")
    @Override
    public Class<Integer>[] type()
    {
        //return (Class<? extends T>) Number.class;
        return new Class[] { Integer.class };
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

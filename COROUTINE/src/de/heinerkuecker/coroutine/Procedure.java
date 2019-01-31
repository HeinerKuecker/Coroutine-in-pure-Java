package de.heinerkuecker.coroutine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.stmt.CoroIterStmt;
import de.heinerkuecker.coroutine.stmt.complex.Block;
import de.heinerkuecker.coroutine.stmt.complex.ComplexStmt;

public class Procedure<COROUTINE_RETURN, RESUME_ARGUMENT>
extends HasCreationStackTraceElement
{
    public final String name;

    /**
     * Es muss ein ComplexStmt sein,
     * weil dieser mit ComplexStmtState
     * einen State hat, welcher bei
     * einem SimpleStmt nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden muesste.
     */
    public final ComplexStmt<?, ?, COROUTINE_RETURN /*, /*PARENT* / CoroutineIterator<COROUTINE_RETURN>*/ , RESUME_ARGUMENT> bodyComplexStmt;

    public final Map<String, Parameter> params;

    /**
     * Constructor.
     *
     * @param params procedure parameter, can be <code>null</code>
     *
     */
    @SafeVarargs
    public Procedure(
            final String name ,
            final Parameter[] params ,
            final CoroIterStmt<COROUTINE_RETURN/*, ? super PARENT/*CoroutineIterator<COROUTINE_RETURN>*/> ... bodyStmts )
    {
        super(
                //creationStackOffset
                3 );

        this.name = Objects.requireNonNull( name );

        this.params =
                initParams(
                        params );

        if ( bodyStmts.length == 0 )
        {
            throw new IllegalArgumentException( "procedure body is empty" );
        }

        this.bodyComplexStmt =
                Block.convertStmtsToComplexStmt(
                        //creationStackOffset
                        4 ,
                        bodyStmts );
    }

    /**
     * Initialize parameter.
     *
     * @param params procedure parameter, can be <code>null</code>
     * @return unmodifiable parameter map
     */
    public static Map<String, Parameter> initParams(
            final Parameter[] params )
    {
        final Map<String, Parameter> paramsMap = new HashMap<>();

        if ( params != null )
        {
            for ( final Parameter param : params )
            {
                if ( paramsMap.containsKey( param.name ) )
                {
                    throw new IllegalArgumentException(
                            "procedure parameter already defined: " +
                            param.name );
                }

                paramsMap.put(
                        param.name ,
                        param );
            }
        }

        return Collections.unmodifiableMap( paramsMap );
    }

    /**
     *
     */
    public void checkLabelAlreadyInUse(
            final HashSet<String> alreadyCheckedProcedureNames ,
            final CoroutineOrProcedureOrComplexstmt<COROUTINE_RETURN, RESUME_ARGUMENT> parent )
    {
        this.bodyComplexStmt.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                new HashSet<>() );
    }

}

package de.heinerkuecker.coroutine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

import de.heinerkuecker.coroutine.arg.Parameter;
import de.heinerkuecker.coroutine.step.CoroIterStep;
import de.heinerkuecker.coroutine.step.complex.ComplexStep;
import de.heinerkuecker.coroutine.step.complex.StepSequence;

public class Procedure<RESULT>
extends HasCreationStackTraceElement
{
    public final String name;

    /**
     * Es muss ein ComplexStep sein,
     * weil dieser mit ComplexStepState
     * einen State hat, welcher bei
     * einem SimpleStep nicht vorhanden
     * ist und dessen State in dieser
     * Klasse verwaltet werden muesste.
     */
    public final ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/> bodyComplexStep;

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
            final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... bodySteps )
    {
        super(
                //creationStackOffset
                3 );

        this.name = Objects.requireNonNull( name );

        this.params =
                initParams(
                        params );

        if (bodySteps.length == 0 )
        {
            throw new IllegalArgumentException( "procedure body is empty" );
        }

        if ( bodySteps.length == 1 &&
                bodySteps[ 0 ] instanceof ComplexStep )
        {
            this.bodyComplexStep =
                    (ComplexStep<?, ?, RESULT/*, PARENT/*? super CoroutineIterator<RESULT>*/>) bodySteps[ 0 ];
        }
        else
        {
            this.bodyComplexStep =
                    new StepSequence<>(
                            // creationStackOffset
                            3 ,
                            bodySteps );
        }
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
            final CoroIteratorOrProcedure<RESULT> parent )
    {
        this.bodyComplexStep.checkLabelAlreadyInUse(
                alreadyCheckedProcedureNames ,
                parent ,
                new HashSet<>() );
    }

}

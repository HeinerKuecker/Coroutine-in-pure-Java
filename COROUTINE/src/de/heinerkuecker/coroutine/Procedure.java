package de.heinerkuecker.coroutine;

import java.util.HashSet;
import java.util.Objects;

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
     * Klasse verwaltet werden müsste.
     */
    public final ComplexStep<?, ?, RESULT /*, /*PARENT* / CoroutineIterator<RESULT>*/> bodyComplexStep;

    /**
     * Constructor.
     *
     * @param creationStackOffset
     */
    @SafeVarargs
    public Procedure(
            final String name ,
            final CoroIterStep<RESULT/*, ? super PARENT/*CoroutineIterator<RESULT>*/> ... bodySteps )
    {
        super(
                //creationStackOffset
                3 );

        this.name = Objects.requireNonNull( name );

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
                    new StepSequence(
                            // creationStackOffset
                            3 ,
                            bodySteps );
        }
    }

    /**
     *
     */
    public void checkLabelAlreadyInUse()
    {
        this.bodyComplexStep.checkLabelAlreadyInUse(
                new HashSet<>() );
    }

}

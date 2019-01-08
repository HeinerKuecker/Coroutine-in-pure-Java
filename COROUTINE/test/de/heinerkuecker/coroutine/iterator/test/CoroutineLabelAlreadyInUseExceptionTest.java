package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.expression.GetLocalVar;
import de.heinerkuecker.coroutine.expression.Value;
import de.heinerkuecker.coroutine.step.complex.DoWhile;
import de.heinerkuecker.coroutine.step.complex.For;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.flow.Continue;
import de.heinerkuecker.coroutine.step.flow.exc.LabelAlreadyInUseException;
import de.heinerkuecker.coroutine.step.retrn.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

public class CoroutineLabelAlreadyInUseExceptionTest
{
    @Test( expected = LabelAlreadyInUseException.class )
    public void test_Negative_LabelAlreadyInUse_While()
    {
        CoroutineIterator.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // steps
                new SetLocalVar<>(
                        "number" ,
                        new Value<>( 0 ) ) ,
                new While<Integer/*, CoroutineIterator<Integer>*/>(
                        //label
                        "label_already_in_use" ,
                        //condition
                        new Lesser<>(
                                new GetLocalVar<>(
                                        "number" ,
                                        Integer.class ) ,
                                1 ) ,
                        //steps
                        new While<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "label_already_in_use" ,
                                //condition
                                new True() ,
                                //steps
                                new IncrementLocalVar<>( "number" ) ,
                                new Continue<>(
                                        //label
                                        "label_already_in_use" ) ,
                                // this step is never executed
                                new NoOperation<>() ) ) );
    }

    @Test( expected = LabelAlreadyInUseException.class )
    public void test_Negative_LabelAlreadyInUse_DoWhile()
    {
        CoroutineIterator.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // steps
                new SetLocalVar<>(
                        "number" ,
                        new Value<>( 0 ) ) ,
                new DoWhile<Integer/*, CoroutineIterator<Integer>*/>(
                        //label
                        "label_already_in_use" ,
                        //condition
                        new Lesser<>(
                                new GetLocalVar<>(
                                        "number" ,
                                        Integer.class ) ,
                                1 ) ,
                        //steps
                        new DoWhile<Integer/*, CoroutineIterator<Integer>*/>(
                                //label
                                "label_already_in_use" ,
                                //condition
                                new True() ,
                                //steps
                                new IncrementLocalVar<>( "number" ) ,
                                new Continue<>(
                                        //label
                                        "label_already_in_use" ) ,
                                // this step is never executed
                                new NoOperation<>() ) ) );
    }

    @Test( expected = LabelAlreadyInUseException.class )
    public void test_Negative_LabelAlreadyInUse_For()
    {
        CoroutineIterator.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // steps
                new SetLocalVar<>(
                        "number" ,
                        new Value<>( 0 ) ) ,
                new For<Integer/*, CoroutineIterator<Integer>*/>(
                        //label
                        "label_already_in_use" ,
                        // initialStep
                        new NoOperation<>() ,
                        // condition
                        new Lesser<>(
                                new GetLocalVar<>(
                                        "number" ,
                                        Integer.class ) ,
                                1 ) ,
                        // updateStep
                        new IncrementLocalVar<>( "number" ) ,
                        // steps
                        new For<>(
                                //label
                                "label_already_in_use" ,
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                new True() ,
                                // updateStep
                                new NoOperation<>() ,
                                // steps
                                new Continue<>(
                                        //label
                                        "label_already_in_use" ) ,
                                // this yield return is never executed
                                new YieldReturn<>(
                                        new Value<>( 1 ) ) ) ) );
    }

}

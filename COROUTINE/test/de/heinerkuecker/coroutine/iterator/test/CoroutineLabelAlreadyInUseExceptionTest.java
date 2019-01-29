package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.condition.Lesser;
import de.heinerkuecker.coroutine.condition.True;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.step.complex.DoWhile;
import de.heinerkuecker.coroutine.step.complex.For;
import de.heinerkuecker.coroutine.step.complex.While;
import de.heinerkuecker.coroutine.step.flow.Continue;
import de.heinerkuecker.coroutine.step.flow.exc.LabelAlreadyInUseException;
import de.heinerkuecker.coroutine.step.ret.YieldReturn;
import de.heinerkuecker.coroutine.step.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.step.simple.NoOperation;
import de.heinerkuecker.coroutine.step.simple.SetLocalVar;

public class CoroutineLabelAlreadyInUseExceptionTest
{
    @Test( expected = LabelAlreadyInUseException.class )
    public void test_Negative_LabelAlreadyInUse_While()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // steps
                new SetLocalVar<>(
                        "number" ,
                        0 ) ,
                new While<Integer , Void>(
                        // label
                        "label_already_in_use" ,
                        // condition
                        new Lesser<>(
                                new GetLocalVar<>(
                                        "number" ,
                                        Integer.class ) ,
                                1 ) ,
                        // steps
                        new While<Integer , Void>(
                                // label
                                "label_already_in_use" ,
                                // condition
                                new True() ,
                                // steps
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
        CoroutineDebugSwitches.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // steps
                new SetLocalVar<>(
                        "number" ,
                        0 ) ,
                new DoWhile<Integer , Void>(
                        // label
                        "label_already_in_use" ,
                        // condition
                        new Lesser<>(
                                new GetLocalVar<>(
                                        "number" ,
                                        Integer.class ) ,
                                1 ) ,
                        // steps
                        new DoWhile<Integer , Void>(
                                // label
                                "label_already_in_use" ,
                                // condition
                                new True() ,
                                // steps
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
        CoroutineDebugSwitches.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // steps
                new SetLocalVar<>(
                        "number" ,
                        0 ) ,
                new For<Integer , Void>(
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
                                new YieldReturn<>( 1 ) ) ) );
    }

}

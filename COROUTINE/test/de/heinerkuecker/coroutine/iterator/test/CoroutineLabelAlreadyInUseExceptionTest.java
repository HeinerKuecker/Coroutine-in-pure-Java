package de.heinerkuecker.coroutine.iterator.test;

import org.junit.Test;

import de.heinerkuecker.coroutine.CoroutineDebugSwitches;
import de.heinerkuecker.coroutine.CoroutineIterator;
import de.heinerkuecker.coroutine.exprs.GetLocalVar;
import de.heinerkuecker.coroutine.exprs.Value;
import de.heinerkuecker.coroutine.exprs.bool.Lesser;
import de.heinerkuecker.coroutine.stmt.complex.DoWhile;
import de.heinerkuecker.coroutine.stmt.complex.For;
import de.heinerkuecker.coroutine.stmt.complex.While;
import de.heinerkuecker.coroutine.stmt.flow.Continue;
import de.heinerkuecker.coroutine.stmt.flow.exc.LabelAlreadyInUseException;
import de.heinerkuecker.coroutine.stmt.ret.YieldReturn;
import de.heinerkuecker.coroutine.stmt.simple.IncrementLocalVar;
import de.heinerkuecker.coroutine.stmt.simple.NoOperation;
import de.heinerkuecker.coroutine.stmt.simple.SetLocalVar;

public class CoroutineLabelAlreadyInUseExceptionTest
{
    @Test( expected = LabelAlreadyInUseException.class )
    public void test_Negative_LabelAlreadyInUse_While()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // stmts
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
                        // stmts
                        new While<Integer , Void>(
                                // label
                                "label_already_in_use" ,
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new IncrementLocalVar<>( "number" ) ,
                                new Continue<>(
                                        //label
                                        "label_already_in_use" ) ,
                                // this statement is never executed
                                new NoOperation<>() ) ) );
    }

    @Test( expected = LabelAlreadyInUseException.class )
    public void test_Negative_LabelAlreadyInUse_DoWhile()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // stmts
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
                        // stmts
                        new DoWhile<Integer , Void>(
                                // label
                                "label_already_in_use" ,
                                // condition
                                Value.trueValue() ,
                                // stmts
                                new IncrementLocalVar<>( "number" ) ,
                                new Continue<>(
                                        //label
                                        "label_already_in_use" ) ,
                                // this statement is never executed
                                new NoOperation<>() ) ) );
    }

    @Test( expected = LabelAlreadyInUseException.class )
    public void test_Negative_LabelAlreadyInUse_For()
    {
        CoroutineDebugSwitches.initializationChecks = true;

        new CoroutineIterator<Integer>(
                // type
                Integer.class ,
                // stmts
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
                        // stmts
                        new For<>(
                                //label
                                "label_already_in_use" ,
                                // initialStep
                                new NoOperation<>() ,
                                // condition
                                Value.trueValue() ,
                                // updateStep
                                new NoOperation<>() ,
                                // stmts
                                new Continue<>(
                                        //label
                                        "label_already_in_use" ) ,
                                // this yield return is never executed
                                new YieldReturn<>( 1 ) ) ) );
    }

}

package de.heinerkuecker.coroutine;

import java.util.Iterator;

//public interface AbstrCoroIterator<RESULT, /*PARENT*/ THIS extends AbstrCoroIterator<RESULT, /*PARENT*/ THIS>>
//extends CoroutineOrProcedureOrComplexstep<RESULT, THIS> , Iterator<RESULT>
public interface AbstrCoroIterator<RESULT>
extends CoroutineOrProcedureOrComplexstep<RESULT> , Iterator<RESULT>
{

}

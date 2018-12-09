package de.heinerkuecker.coroutine_iterator;

import java.util.Iterator;

/**
 * Interface for {@link CoroutineIterator}
 * to satisfy Java generics.
 *
 * @param <RESULT> result type of method {@link CoroutineIterator#next()}
 * @param <PARENT> type the {@link CoroutineIterator} instance
 * @author Heiner K&uuml;cker
 */
public interface AbstrCoroIterator<RESULT, /*PARENT*/ THIS extends AbstrCoroIterator<RESULT, /*PARENT*/ THIS>>
extends Iterator<RESULT>
{
    // no implementation
}

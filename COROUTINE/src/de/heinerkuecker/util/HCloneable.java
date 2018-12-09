package de.heinerkuecker.util;

/**
 * Interface for classes with
 * ability to clone instances.
 * The name is choosed to
 * avoid name collisions with
 * {@link Cloneable}.
 *
 * @author Heiner K&uuml;cker
 */
public interface HCloneable<THIS>
{
    THIS createClone();
}

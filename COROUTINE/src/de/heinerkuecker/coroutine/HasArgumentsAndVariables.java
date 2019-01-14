package de.heinerkuecker.coroutine;

import java.util.Map;

import de.heinerkuecker.coroutine.arg.Arguments;

public interface HasArgumentsAndVariables
{

    /**
     * @return Map with local variables
     */
    //abstract public Map<String, Object> localVars();
    abstract public Variables localVars();

    /**
     * @return Map with global variables
     */
    //abstract public Map<String, Object> globalVars();
    abstract public Variables globalVars();

    /**
     * @return Map with procedure arguments
     */
    //abstract public Map<String, Object> procedureArgumentValues();
    abstract public Arguments procedureArgumentValues();

    abstract public Map<String, Class<?>> procedureParameterTypes();

    /**
     * @return Map with global arguments (arguments of coroutine)
     */
    abstract public Arguments globalArgumentValues();

    abstract public Map<String, Class<?>> globalParameterTypes();
}

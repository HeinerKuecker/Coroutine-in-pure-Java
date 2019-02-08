package de.heinerkuecker.coroutine;

import java.util.Map;

import de.heinerkuecker.coroutine.arg.Arguments;

public interface HasArgumentsAndVariables<RESUME_ARGUMENT>
{

    /**
     * @return Map with local globalVariables
     */
    //abstract public Map<String, Object> localVars();
    //abstract public GlobalVariables localVars();
    //abstract public BlockLocalVariables localVars();
    abstract public VariablesOrLocalVariables localVars();

    /**
     * @return Map with global globalVariables
     */
    //abstract public Map<String, Object> globalVars();
    //abstract public GlobalVariables globalVars();
    abstract public VariablesOrLocalVariables globalVars();

    /**
     * @return Map with function arguments
     */
    //abstract public Map<String, Object> functionArgumentValues();
    abstract public Arguments functionArgumentValues();

    abstract public Map<String, Class<?>> functionParameterTypes();

    /**
     * @return Map with global arguments (arguments of coroutine)
     */
    abstract public Arguments globalArgumentValues();

    abstract public Map<String, Class<?>> globalParameterTypes();

    abstract RESUME_ARGUMENT getResumeArgument();

}

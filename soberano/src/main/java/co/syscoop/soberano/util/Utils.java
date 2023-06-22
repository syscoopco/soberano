package co.syscoop.soberano.util;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Utils {

	public static String evaluate(String arithmeticExpression) throws ScriptException {
	    ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    if (engine != null) {
	    	return engine.eval(arithmeticExpression).toString();
	    }
	    else {
	    	//from some java version ScriptEngineManager is deprecated
	    	return arithmeticExpression;
	    }	    
    } 
}

package co.syscoop.soberano.util;

import com.fathzer.soft.javaluator.DoubleEvaluator;

/*
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
*/

public class Utils {

	public static String evaluate(String arithmeticExpression) /*throws ScriptException*/ {
	    
		DoubleEvaluator eval = new DoubleEvaluator();
		return eval.evaluate(arithmeticExpression).toString();
		
		/* ScriptEngineManager not supported from Java > 8
		ScriptEngineManager mgr = new ScriptEngineManager();
	    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	    if (engine != null) {
	    	return engine.eval(arithmeticExpression).toString();
	    }
	    else {
	    	//from some java version ScriptEngineManager is deprecated
	    	return arithmeticExpression;
	    }
	    */	    
    } 
}

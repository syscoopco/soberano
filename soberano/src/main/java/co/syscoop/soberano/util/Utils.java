package co.syscoop.soberano.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
	
	public static String getValidURL(String invalidURLString){
	    try {
	    	URL url = new URL(URLDecoder.decode(invalidURLString, StandardCharsets.UTF_8.toString()));
	        URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
	        return uri.toString();
	    } catch (URISyntaxException | UnsupportedEncodingException | MalformedURLException ignored) {
	        return null;
	    }
	}
}

package co.syscoop.soberano.ws;

import java.util.*;
import javax.ws.rs.core.Application;

public class RESTApplication extends Application {

	private static final Set<Object> emptySet = Collections.emptySet();
	
	public RESTApplication() {}
   
	public Set<Class<?>> getClasses() {
		
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		
		//TODO: add REST web service resource
		
		return set;
   }
	
   public Set<Object> getSingletons() {
	   return emptySet;
   }
}
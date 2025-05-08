package co.syscoop.soberano.ws;

import java.util.*;
import jakarta.ws.rs.core.Application;

public class RESTApplication extends Application {

	private static final Set<Object> emptySet = Collections.emptySet();
	
	public RESTApplication() {}
   
	public Set<Class<?>> getClasses() {
		
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		
		set.add(TestResource.class);
		
		return set;
   }
	
   public Set<Object> getSingletons() {
	   return emptySet;
   }
}
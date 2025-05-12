package co.syscoop.soberano.ws;

import javax.ws.rs.*;

@Path("/test/justauth")
public class TestResource {

	@SuppressWarnings({ })
	@GET
	@Produces("application/json")
	public String getTestObjects() throws Exception {
		
		try {
			StringBuilder testObjects = new StringBuilder();
			testObjects.append("{\"testObjects\":[");
			for(Integer i = 1; i <= 5; i++) {
				TestObject testObject = new TestObject(i, i.toString());
				testObjects.append(testObject.toJSON());
				if (i < 5) {
					testObjects.append(",");
					i++;
				}
			}
			testObjects.append("]}");
			return testObjects.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
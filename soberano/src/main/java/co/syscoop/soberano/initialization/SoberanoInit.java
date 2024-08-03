package co.syscoop.soberano.initialization;

import java.util.HashMap;

import javax.servlet.ServletContext;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppInit;

import co.syscoop.soberano.vocabulary.LabelsLocatorForVocabulary;
import co.syscoop.soberano.beans.WebApplicationProperties;
import co.syscoop.soberano.util.SpringUtility;

public class SoberanoInit implements WebAppInit {
	
	private HashMap<Integer /*order id*/, 
					HashMap<Integer /*production line id*/, 
						HashMap<Integer /*allocation id*/, 
								Boolean /*true if allocation was already printed*/>>> printedAllocations =
				new HashMap<Integer, HashMap<Integer, HashMap<Integer, Boolean>>>();
	
	@Override
	public void init(WebApp wapp) throws Exception {
		
		//register proper zk labels resource, according to the vocabulary of this Soberano instance
		WebApplicationProperties soberanoProperties = (WebApplicationProperties) SpringUtility.applicationContext().getBean("soberanoProperties");
		Labels.register(new LabelsLocatorForVocabulary((ServletContext)wapp.getServletContext(), soberanoProperties.getVocabulary()));
		
		//initialize printed allocations
		wapp.setAttribute("printed_allocations", printedAllocations);
	}
}

package co.syscoop.soberano.vocabulary;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import javax.servlet.ServletContext;

public class LabelsLocatorForVocabulary implements org.zkoss.util.resource.LabelLocator {
	
	private ServletContext _svlctx;
	private String _vocabulary;
	
	public LabelsLocatorForVocabulary(ServletContext svlctx, String vocabulary) {
		_svlctx = svlctx;
		_vocabulary = vocabulary;
	}

	@Override
	public URL locate(Locale locale) throws MalformedURLException {
		URL resourceURL = null;
		try {
			resourceURL = _svlctx.getResource("/WEB-INF/labels/zk-label_" + _vocabulary + "_" + locale.getLanguage() + ".properties");
			if (resourceURL == null) {
				resourceURL = _svlctx.getResource("/WEB-INF/labels/zk-label_" + _vocabulary + "_en.properties");
			}
		}
		catch(Exception ex) {
			resourceURL = _svlctx.getResource("/WEB-INF/labels/zk-label_" + _vocabulary + "_en.properties");
		}	
		return resourceURL;
	}
}
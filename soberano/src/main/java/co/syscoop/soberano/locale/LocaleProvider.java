package co.syscoop.soberano.locale;

import java.util.Locale;
import jakarta.servlet.http.HttpServletRequest;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.RequestInterceptor;

public class LocaleProvider implements RequestInterceptor {
	
	public void request(Session sess, Object request, Object response) {
		
		Locale locale = ((HttpServletRequest) request).getLocale();
        sess.setAttribute(Attributes.PREFERRED_LOCALE, locale);
        return;
	}
}
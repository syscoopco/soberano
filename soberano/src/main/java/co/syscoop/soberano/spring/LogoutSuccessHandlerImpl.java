package co.syscoop.soberano.spring;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class LogoutSuccessHandlerImpl extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
 
    @Override
    public void onLogoutSuccess(HttpServletRequest request, 
    							HttpServletResponse response, 
    							Authentication authentication) throws IOException, ServletException {
  
        this.setUseReferer(true);
        super.onLogoutSuccess(request, response, authentication);
    }
}
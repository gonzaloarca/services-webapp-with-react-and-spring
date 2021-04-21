package ar.edu.itba.paw.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HireNetAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private MessageSource messageSource;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		setDefaultFailureUrl("/login?error");
		super.onAuthenticationFailure(request, response, exception);
		String errorMessage = messageSource.getMessage("login.error",null, LocaleContextHolder.getLocale());
		if(exception.getMessage().equalsIgnoreCase("Bad credentials")){
			errorMessage = messageSource.getMessage("login.badcredentials",null, LocaleContextHolder.getLocale());
		}
		else if(exception.getMessage().equalsIgnoreCase("User is disabled")){
			errorMessage = messageSource.getMessage("login.usernotverified",null, LocaleContextHolder.getLocale());
		}
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
	}
}

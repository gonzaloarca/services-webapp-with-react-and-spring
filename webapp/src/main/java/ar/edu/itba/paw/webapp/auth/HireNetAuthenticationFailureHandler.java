package ar.edu.itba.paw.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HireNetAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		setDefaultFailureUrl("/login?error");
		super.onAuthenticationFailure(request, response, exception);
		String errorMessage = messageSource.getMessage("login.error",null, localeResolver.resolveLocale(request));
		if(exception.getMessage().equalsIgnoreCase("Bad credentials")){
			errorMessage = messageSource.getMessage("login.badcredentials",null, localeResolver.resolveLocale(request));
		}
		else if(exception.getMessage().equalsIgnoreCase("User is disabled")){
			errorMessage = messageSource.getMessage("login.usernotverified",null, localeResolver.resolveLocale(request));
		}
		request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
	}
}

package pe.com.fabrica.aldesa.security.auth.jwt;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Esta clase hace posible omitir la verificación de los endpoints token y login
 * 
 * @author Anthony Lopez
 *
 */
public class SkipPathRequestMatcher implements RequestMatcher {

	private OrRequestMatcher matchers;
	private RequestMatcher processingMatcher;

	public SkipPathRequestMatcher(List<String> pathsToSkip, String processingPath) {
		List<RequestMatcher> requestMatchers = pathsToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
		matchers = new OrRequestMatcher(requestMatchers);
		processingMatcher = new AntPathRequestMatcher(processingPath);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		if (matchers.matches(request)) {
			return false;
		}
		return processingMatcher.matches(request);
	}
}

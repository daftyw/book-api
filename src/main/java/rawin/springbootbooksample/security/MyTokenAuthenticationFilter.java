package rawin.springbootbooksample.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class MyTokenAuthenticationFilter extends GenericFilterBean {
    
    private static final Logger log = LoggerFactory.getLogger(MyTokenAuthenticationFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        
        HttpServletRequest httpReq = (HttpServletRequest) request;

        Optional<String> xtoken = Optional.ofNullable(httpReq.getHeader("x-token"));

        log.debug("check x-token is there");
        if(xtoken.isPresent()) {
            log.debug("create authentication object with token");
            createTokenAuthentication(xtoken);
        }

        chain.doFilter(request, response);
	}

    private void createTokenAuthentication(Optional<String> token) {
        log.debug("token = {}", token);
        Authentication auth = new MyToken(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
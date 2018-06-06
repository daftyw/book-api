package rawin.springbootbooksample.security;

import java.util.Optional;

import com.google.common.base.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import rawin.springbootbooksample.mongo.model.User;
import rawin.springbootbooksample.mongo.repo.UserRepository;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthenticationProvider.class);
    private UserRepository userRepository;

    public TokenAuthenticationProvider(UserRepository userRepo) {
        this.userRepository = userRepo;
    }

	@Override
    public Authentication authenticate(Authentication authentication) 
                    throws AuthenticationException {

        log.debug("running check on token");

        Optional<String> token = (Optional<String>) authentication.getCredentials();

        if(!token.isPresent()) {
            log.debug("token not here");
            throw new BadCredentialsException("x-token : not found");
        }

        User user = userRepository.findByLoginToken(token.get());
        if(user == null) {
            log.debug("token not found in db");
            throw new BadCredentialsException("x-token : incorrect");
        }

        Authentication authOut = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        log.debug("token create complete!");

        return authOut;
	}

	@Override
	public boolean supports(Class<?> authentication) {
        log.debug("class checker on authentication: {}", authentication);
		return Objects.equal(authentication, MyToken.class);
	}

}
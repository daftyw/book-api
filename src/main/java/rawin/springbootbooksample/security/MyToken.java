package rawin.springbootbooksample.security;

import java.util.Optional;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class MyToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -3786193541053338765L;

    private Optional<String> myToken;

    public MyToken(Optional<String> token) {
        super(null);
        this.myToken = token;
    }

	@Override
	public Object getCredentials() {
		return myToken;
	}

	@Override
	public Object getPrincipal() {
		return myToken;
	}

}
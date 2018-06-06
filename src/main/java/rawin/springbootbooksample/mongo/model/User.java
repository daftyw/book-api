package rawin.springbootbooksample.mongo.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.DigestUtils;

@Document(collection = "book_user")
public class User implements UserDetails {

    private static final long serialVersionUID = -7076243366944692618L;

    @Id
    @JsonIgnore
    private String id;

    private String username;
    @JsonIgnore
    private String encPassword;
    @JsonProperty("date_of_birth")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dob;
    @JsonIgnore
    private String loginToken;
    private Set<Integer> orders;
    
    public User() {
    }

    public User(String username, String encPass) {
        this.username = username;
        this.encPassword = encPass;
    }

    public static String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * @return new user instance with encrypted password
     */
    public static User createWithEncryptedPassword(String username, String barePassword) {
        return new User(username, encryptPassword(barePassword));
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the encPassword
     */
    public String getEncPassword() {
        return encPassword;
    }

    /**
     * @param encPassword the encPassword to set
     */
    public void setEncPassword(String encPassword) {
        this.encPassword = encPassword;
    }

    /**
     * @return the dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || (!(obj instanceof User)))
            return false;
        User that = (User) obj;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.username);
    }

    @Override
    public String toString() {
        return String.format("User[username=%s, password=%s, dob=%s]", username, encPassword, dob);
    }

    @JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("USER"));
    }
    
    /**
     * @return the loginToken
     */
    public String getLoginToken() {
        return loginToken;
    }

    /**
     * @param loginToken the loginToken to set
     */
    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    /**
     * @return the orders
     */
    public Set<Integer> getOrders() {
        return orders;
    }
    
    /**
     * @param orders the orders to set
     */
    public void setOrders(Set<Integer> orders) {
        this.orders = orders;
    }

    @JsonIgnore
	@Override
	public String getPassword() {
		return getEncPassword();
	}

    @JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return false;
    }
    
    @JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

    @JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return false;
    }
    
    @JsonIgnore
	@Override
	public boolean isEnabled() {
		return false;
    }
    

}
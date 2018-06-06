package rawin.springbootbooksample.mongo.model;

import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.Objects;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class UserTests {

    @Test
    public void test_createWithEncryptedPassword_shouldReturnUsernameAndPasswordValue() {
        User user1 = User.createWithEncryptedPassword("rawin", "password");
        String encPassword = User.encryptPassword("password");
        System.out.printf("Password: %s\n", encPassword);
        assertThat("username is in the object", user1.getUsername(), is("rawin"));
        assertThat("password is in the object", user1.getEncPassword(), is(encPassword));
    }

    @Test
    public void test_equals_byDifferenceUsername_shouldBeFalse() {
        User user1 = User.createWithEncryptedPassword("rawin", "password");
        User user2 = User.createWithEncryptedPassword("notrawin", "password");

        assertThat("difference username should be false", Objects.equals(user1, user2), is(false));
    }

    @Test
    public void test_create_bySameBarePassword_shouldBeEquals() {
        User user1 = User.createWithEncryptedPassword("rawin", "password");
        User user2 = User.createWithEncryptedPassword("notrawin", "password");

        assertThat("difference password should be true", Objects.equals(user1.getEncPassword(), user2.getEncPassword()), 
            is(true));
    }

    @Test
    public void test_encryptPassword_shouldReturnDifferenceValueAndNotNull() {
        String result = User.encryptPassword("password");
        assertThat("password must not null", result, is(notNullValue()));
        assertThat("password must have 32 chars", result.length(), is(32));
        assertThat("password must difference from original", result, is(not("password")));
    }

    @Test
    public void test_toString_shouldReturnAllInformation() {
        User user1 = User.createWithEncryptedPassword("rawin", "password");
        String out = user1.toString();
        System.out.printf("String out: %s%n", out);
        assertThat("username appears", out, is(containsString("rawin")));
        assertThat("password appears", out, is(containsString(User.encryptPassword("password"))));        
    }
}
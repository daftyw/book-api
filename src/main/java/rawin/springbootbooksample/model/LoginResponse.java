package rawin.springbootbooksample.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(content = Include.NON_NULL)
public class LoginResponse {
    private String token;
    @JsonProperty("result_code")
    private String resultCode;
    @JsonProperty("result_desc")
    private String resultDesc;

    public LoginResponse() {}

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * @param resultCode the resultCode to set
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * @return the resultDesc
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     * @param resultDesc the resultDesc to set
     */
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
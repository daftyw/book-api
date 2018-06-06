package rawin.springbootbooksample.model;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;

public class OrderRequestTests {
    @Test
    public void mapperJson_shouldBe_success() throws Exception {
        String json = "{\"orders\":[1,4]}";
        ObjectMapper objectMapper = new ObjectMapper();
        OrderRequest orderRequest = objectMapper.readValue(json.getBytes(), OrderRequest.class);
        System.out.println(orderRequest);
        assertThat(orderRequest, is(notNullValue()));
    }    
}
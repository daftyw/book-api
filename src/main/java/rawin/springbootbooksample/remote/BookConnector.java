package rawin.springbootbooksample.remote;

import java.util.List;

import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import rawin.springbootbooksample.mongo.model.Book;

@Component
public class BookConnector {

    private static final String URL_GET_ALL_BOOK = "https://scb-test-book-publisher.herokuapp.com/books";
    private static final String URL_GET_RECOMMEND_BOOK = "https://scb-test-book-publisher.herokuapp.com/books/recommendation";

    private RestTemplate restTemplate;

    /**
     * @param restTemplate the restTemplate to set
     */
    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Book> getBookList() throws RestClientException {
        ResponseEntity<List<Book>> response = restTemplate.exchange(URL_GET_ALL_BOOK, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {
                });

        if(response.getStatusCode() != org.springframework.http.HttpStatus.OK) {
            return null;
        }

        return response.getBody();
    }

    public List<Book> getRecommendBookList() throws RestClientException {
        ResponseEntity<List<Book>> response = restTemplate.exchange(URL_GET_RECOMMEND_BOOK, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Book>>() {
                });

        if(response.getStatusCode() != org.springframework.http.HttpStatus.OK) {
            return null;
        }

        return response.getBody();
    }
}
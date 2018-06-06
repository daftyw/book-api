package rawin.springbootbooksample.mongo.util;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import rawin.springbootbooksample.mongo.model.Book;

import static org.hamcrest.CoreMatchers.*;

public class BookNameAndRecommendedComparatorTests { 
    @Test
    public void compare_books_success() {
        Comparator<Book> bookComparator = new BookNameAndRecommendedComparator();

        List<Book> bookList = Arrays.asList(
            Book.builder().name("A").recommended(Boolean.FALSE).build(),
            Book.builder().name("B").recommended(Boolean.TRUE).build(),
            Book.builder().name("C").recommended(Boolean.TRUE).build(),
            Book.builder().name("D").recommended(Boolean.FALSE).build(),
            Book.builder().name("E").recommended(Boolean.TRUE).build()
            );

        Collections.sort(bookList, bookComparator);

        bookList.stream()
            .map(book -> String.format("%d %s %s", book.getId(), book.getName(), book.getRecommended()))
            .forEach(System.out::println);

        assertThat("B must be first", bookList.get(0).getName(), is("B"));
    }
}
package rawin.springbootbooksample.mongo.util;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rawin.springbootbooksample.mongo.model.Book;

public class BookNameAndRecommendedComparator implements Comparator<Book> {

    private static final Logger log = LoggerFactory.getLogger(BookNameAndRecommendedComparator.class);

	@Override
	public int compare(Book o1, Book o2) {
        if(o1 == null && o2 == null) return 0;
        if(o2 == null) return 1;
        if(o1 == null) return -1;
		if(o1.getRecommended().compareTo(o2.getRecommended()) == 0) {            
            return o1.getName().compareTo(o2.getName());
        }    
        return o2.getRecommended().compareTo(o1.getRecommended());
	}

}
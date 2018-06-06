package rawin.springbootbooksample.controller;

import java.util.Arrays;
import java.util.Collections;

import com.google.common.collect.Lists;

import org.junit.Test;

public class UserControllerTests {
    @Test
    public void stream_willPresents_whenItHas() {
        Arrays.asList("1", "2", "3").stream()
            .map(s -> { 
                if(s.equals("1")) 
                    return null; 
                else
                    return Integer.parseInt(s); 
            })
            .forEach(System.out::println);            
    }
}
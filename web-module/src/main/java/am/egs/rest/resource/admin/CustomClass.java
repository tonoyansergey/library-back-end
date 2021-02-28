package am.egs.rest.resource.admin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class CustomClass {

    public static void main(String[] args) {
        TestGenerics testGenerics = new TestGenerics();

        System.out.println(testGenerics.getMap("str", 45));

    }
}

class TestGenerics {
    public <T> Map getMap(T key, Integer t) {
        Map<T,Integer> map = new HashMap<>();
        map.put(key, t);
        return map;
    }
}




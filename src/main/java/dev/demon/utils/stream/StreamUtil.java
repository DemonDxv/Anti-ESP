package dev.demon.utils.stream;

import java.util.*;
import java.util.function.Predicate;

public class StreamUtil {

    public static <T> Collection<T> filter(Collection<T> data, Predicate<T> filter) {

        List<T> list = new LinkedList<>();

        if (filter == null || data.isEmpty()) return list;

        for (T object : data) {

            if (filter.test(object)) list.add(object);
        }

        return list;
    }
}
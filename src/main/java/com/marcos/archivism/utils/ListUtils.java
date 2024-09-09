package com.marcos.archivism.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {

    public static List<Integer> sort(List<Integer> list) {
        List<Integer> mutableList = new ArrayList<>(list);
        Collections.sort(mutableList);
        return mutableList;

    }

    public static List<Integer> handleStringListToIntegerList(List<String> input) {
        return input.stream()
                .map(item -> item.replace(".", ""))
                .map(Integer::parseInt)
                .toList();
    }

    public static <T> List<T> handleListArrayToList(List<T[]> input) {
        input.removeFirst();
        return input.stream()
                .map(item -> item[0])
                .toList();

    }

    public static List<String[]> handleStringListToArrayStringList(List<String> input){
        return input.stream()
                .map(item -> new String[]{item})
                .toList();
    }

    public static List<String> handleIntegerListToStringList(List<Integer> input){
        return input.stream()
                .map(item -> String.format("%0" + 6 + "d", item))
                .toList();
    }

    public static <T> void printList(List<T> input) {
        int count = 1;
        for (T item : input){
            System.out.println(count + ": " + item);
            count++;
        }
    }
}

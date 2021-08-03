package com.gorkem.mongodatabases;

import com.gorkem.mongodatabases.models.dtos.UserLocationGetDto;

import java.util.*;

public class DatabaseManager {

    public static String findTheDBWithLoadBalance(List<UserLocationGetDto> userLocationGetDtos) {
        Map<String, Integer> hashMap = createHashMap(userLocationGetDtos);

        List<Integer> dataNumberContainingArray = createTheDataNumberArray(hashMap);

        Integer minValue = findTheMinimumNumber(dataNumberContainingArray);

        List<String> sameDataNumberContainingDb = new ArrayList<>();

        for (Integer i : dataNumberContainingArray) {
            if (i == minValue) {
                sameDataNumberContainingDb = createTheSameDataNumberContainingDb(hashMap, minValue);
            }
        }

        Optional<String> firstDatabase = sameDataNumberContainingDb
                .stream()
                .findFirst();

        return firstDatabase.get();
    }

    public static String findTheKey(Map hashMap, Integer minValue) {
        String key = "";
        for (Object s : hashMap.keySet()) {
            if (minValue.equals(hashMap.get(s))) {
                key = (String) s;
            }
        }
        return key;
    }

    public static int findTheMinimumNumber(List<Integer> array) {
        Integer minValue = array.get(0);
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) < minValue) {
                minValue = array.get(i);
            }
        }
        return minValue;
    }

    public static Map<String, Integer> createHashMap(List<UserLocationGetDto> userLocationGetDtos) {
        Map<String, Integer> hashMap = new HashMap<>();

        for (UserLocationGetDto u : userLocationGetDtos) {
            if (hashMap.containsKey(u.getDatabaseAddress())) {
                hashMap.put(u.getDatabaseAddress(), hashMap.get(u.getDatabaseAddress()) + 1);
            } else {
                hashMap.put(u.getDatabaseAddress(), 1);
            }
        }
        return hashMap;
    }

    public static List<Integer> createTheDataNumberArray(Map<String, Integer> hashMap) {
        List<Integer> array = new ArrayList<>();

        for (Map.Entry<String, Integer> stringIntegerEntry : hashMap.entrySet()) {

            Map.Entry pair = (Map.Entry) stringIntegerEntry;

            int a = (int) pair.getValue();
            if (array.contains(a)) {
                continue;
            } else {
                array.add(a);
            }
        }
        return array;
    }

    public static List<String> createTheSameDataNumberContainingDb(Map<String, Integer> hashMap, Integer minValue) {
        List<String> sameDataNumberContainsDb = new ArrayList<>();
        for (Object s : hashMap.keySet()) {
            if (minValue.equals(hashMap.get(s))) {
                sameDataNumberContainsDb.add((String) s);
            }
        }
        return sameDataNumberContainsDb;
    }
}

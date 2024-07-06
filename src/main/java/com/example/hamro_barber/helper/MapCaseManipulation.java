package com.example.hamro_barber.helper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapCaseManipulation {


    public Map<String, Object> keyToCamelCase(Map<String, Object> input) {
        Map<String, Object> updated = new HashMap<>();
        for (Map.Entry<String, Object> set : input.entrySet()) {
            String key = set.getKey();
            String camelCaseKey = toCamelCase(key);
            updated.put(camelCaseKey, set.getValue());
        }
        return updated;
    }

    public List<Map<String, Object>> keyToCamelCase(List<Map<String, Object>> input) {
        List<Map<String, Object>> updated = new ArrayList<>();
        input.forEach(i->{
            Map<String, Object> map = this.keyToCamelCase(i);
            updated.add(map);
        });
        return updated;
    }

    String toCamelCase(String input) {
        StringBuilder builder = new StringBuilder(input);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {
                builder.deleteCharAt(i);
                builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }
        return builder.toString();
    }

    public String toSnakeCase(String input) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        input = input.replaceAll(regex, replacement).toLowerCase();
        return input;
    }
}

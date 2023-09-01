package com.iCareapi.sms;

import java.util.HashMap;
import java.util.Map;

public class Criteria {
    private Map<String, Object> criteriaMap = new HashMap<>();

    // Additional methods for working with criteria
    public void addParameter(String key, Object value) {
        criteriaMap.put(key, value);
    }

    public Map<String, Object> getCriteriaMap() {
        return criteriaMap;
    }

    public Object getParameter(String key) {
        return criteriaMap.get(key);
    }

    public boolean containsParameter(String key) {
        return criteriaMap.containsKey(key);
    }

    public boolean isEmpty() {
        return criteriaMap.isEmpty();
    }

    public int size() {
        return criteriaMap.size();
    }

    public void removeParameter(String key) {
        criteriaMap.remove(key);
    }

    public void clear() {
        criteriaMap.clear();
    }

}





package com.framework.context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<ContextKey, Object> context =
            new HashMap<>();

    public void set(ContextKey key, Object value) {

        context.put(key, value);
    }

    public Object get(ContextKey key) {

        return context.get(key);
    }
}
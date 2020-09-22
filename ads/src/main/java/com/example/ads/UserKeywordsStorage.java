package com.example.ads;

import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class UserKeywordsStorage {

    private final Map<String, Set<String>> keywordsByUser = new HashMap<>();

    public void add(String userId, String[] terms) {
        Set<String> keywords = keywordsByUser.computeIfAbsent(userId, u -> new HashSet<>());
        keywords.addAll(Arrays.asList(terms));
    }

    public Collection<String> getKeywords(String userId) {
        return keywordsByUser.getOrDefault(userId, Collections.emptySet());
    }
}

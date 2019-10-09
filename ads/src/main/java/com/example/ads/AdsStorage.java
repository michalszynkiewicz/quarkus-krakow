package com.example.ads;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 09/10/2019
 */
@Singleton
public class AdsStorage {
    private final Map<String, List<String>> adsByKeyword = new HashMap<>();

    @PostConstruct
    public void init() {
        adsByKeyword.put("java",
              asList("Work efficiently with IntelliJ IDEA",
                    "Check out the great Java projects from Apache Software Foundation")
        );
        adsByKeyword.put("awesome", asList("Have you already heard the latest single from Tones and I?"));
        adsByKeyword.put("cloud",
              asList("Simplify your deployments with OpenShift",
                    "Go to the cloud with Amazon Web Services",
                    "Have you tried DigitalOcean's droplets?"
              ));
    }

    public List<String> getAds(String keyword) {
        return adsByKeyword.getOrDefault(keyword, Collections.emptyList());
    }
}

package com.example.ads;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 09/10/2019
 */
@Path("/ads")
@ApplicationScoped
public class AdsResource {

    private static final Logger log = Logger.getLogger(AdsResource.class);

    private Map<String, List<String>> keywordsByUser = new HashMap<>();

    @Inject
    AdsStorage storage;

    @Incoming("queries")
    public void consume(JsonObject userQuery) {
        String userId = userQuery.getString("userId");
        List<String> keywords = keywordsByUser.computeIfAbsent(userId, u -> new ArrayList<>());
        String[] queries = userQuery.getString("query").split("[^\\w]+");
        keywords.addAll(Arrays.asList(queries));
        log.infof("added keywords %s for user %s", Arrays.toString(queries), userId);
    }

    @GET
    @Path("{userId}")
    public String getAd(@PathParam("userId") String userId) {
        List<String> keywords = keywordsByUser.getOrDefault(userId, Collections.emptyList());
        List<String> ads = keywords.stream()
              .map(storage::getAds)
              .flatMap(List::stream)
              .collect(Collectors.toList());
        if (ads.size() == 0) {
            return "Check out the sources of the demo: http://bit.ly/quarkus-search";
        }
        int adIndex = new Random().nextInt(ads.size());
        return ads.get(adIndex);
    }

}

package com.example.ads;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Collection;
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

    @Inject
    AdsStorage storage;

    @Inject
    UserKeywordsStorage userKeywordsStorage;

    @GET
    @Path("{userId}")
    public String getAd(@PathParam("userId") String userId) {
        Collection<String> keywords = userKeywordsStorage.getKeywords(userId);
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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    private Map<String, List<String>> keywordsByUser = new HashMap<>();

    @Inject
    AdsStorage storage;

    @GET
    @Path("{userId}")
    public String getAd(@PathParam("userId") String userId) {
        List<String> keywords = keywordsByUser.getOrDefault(userId, Collections.emptyList());
        List<String> ads = keywords.stream()
              .map(storage::getAds)
              .flatMap(List::stream)
              .collect(Collectors.toList());
        if (ads.size() == 0) {
            return "Have you heard of Red Hat Forum Warsaw?";
        }
        int adIndex = new Random().nextInt(ads.size());
        return ads.get(adIndex);
    }
}

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 09/10/2019
 */
@Singleton
public class AdsStorage {
    private final Map<String, List<String>> adsByKeywords = new HashMap<>();

    public List<String> getAds(String keyword) {
        return adsByKeywords.getOrDefault(keyword, Collections.emptyList());
    }
}

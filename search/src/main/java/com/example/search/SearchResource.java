package com.example.search;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 08/10/2019
 */
@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    @Stream("search-terms")
    Emitter<JsonObject> queryEmitter;

    @GET
    public List<String> getPages(@QueryParam("userId") String userId,
                                 @QueryParam("query") String query) {


        if (StringUtils.isBlank(query)) {
            return Collections.emptyList();
        }

        List<String> terms = asList(query.split("[^\\w]+"));
        PanacheQuery<PageKeyword> dbQuery =
              PageKeyword.find("keyword in ?1", terms);

        queryEmitter.send(new JsonObject().put("userId", userId).put("query", query));

        return dbQuery.stream()
              .map(pk -> pk.page)
              .distinct()
              .collect(Collectors.toList());
    }
}

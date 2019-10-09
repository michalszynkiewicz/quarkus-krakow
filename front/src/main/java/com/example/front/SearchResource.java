package com.example.front;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.resteasy.spi.metadata.ResourceBuilder;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 08/10/2019
 */
@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SearchResource {

    @ConfigProperty(name = "search.service.url")
    URI searchUri;

    private SearchClient searchClient;

    @PostConstruct
    void setUp() {
        searchClient = RestClientBuilder.newBuilder().baseUri(searchUri).build(SearchClient.class);
    }

    @GET
    @Path("{userId}/{query}")
    @Fallback(SearchFallbackHandler.class)
    public List<String> search(@PathParam("userId") String userId,
                               @PathParam("query") String query) {
        return searchClient.getPages(query, userId);
    }

    private static class SearchFallbackHandler implements FallbackHandler<List<String>> {
        @Override
        public List<String> handle(ExecutionContext context) {
            context.getFailure().printStackTrace();
            return asList("Fallback", "http://twitter.com/mszynkiewicz");
        }
    }
}

package com.example;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
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

@Path("/search")
public class SearchEndpoint {

    @Inject
    QueryForwarder forwarder;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> search(@QueryParam("userId") String userId, @QueryParam("query") String searchTerm) {
        if (userId == null || searchTerm == null) {
            return Collections.emptyList();
        }
        forwarder.send(userId, searchTerm);

        List<String> keywords = asList(searchTerm.split("[^\\w]+"));

        PanacheQuery<PageKeyword> query = PageKeyword.find("keyword in ?1", keywords);
        return query.stream()
                .map(pk -> pk.page)
                .collect(Collectors.toList());
    }
}
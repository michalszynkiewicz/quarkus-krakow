package com.example.front;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * mstodo: Header
 *
 * @author Michal Szynkiewicz, michal.l.szynkiewicz@gmail.com
 * <br>
 * Date: 09/10/2019
 */
@Path("/ads")
@Produces(MediaType.TEXT_PLAIN)
public interface AdsClient {

    @GET
    @Path("{userId}")
    String getAds(@PathParam("userId") String userId);
}

package com.example;

import com.example.queries.QueriesGrpc;
import com.example.queries.Query;
import io.quarkus.grpc.runtime.annotations.GrpcService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class QueryForwarder {

    @Inject
    @GrpcService("queries")
    QueriesGrpc.QueriesBlockingStub queriesBlockingStub;

    public void send(String userId, String query) {
        queriesBlockingStub.registerQuery(Query.newBuilder().setQuery(query).setUserId(userId).build());
    }
}

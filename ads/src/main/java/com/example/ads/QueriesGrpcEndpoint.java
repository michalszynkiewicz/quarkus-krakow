package com.example.ads;

import com.example.queries.Empty;
import com.example.queries.QueriesGrpc;
import com.example.queries.Query;
import io.grpc.stub.StreamObserver;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class QueriesGrpcEndpoint extends QueriesGrpc.QueriesImplBase {
    private static final Logger log = Logger.getLogger(QueriesGrpcEndpoint.class);

    @Inject
    UserKeywordsStorage userKeywordsStorage;

    @Override
    public void registerQuery(Query request, StreamObserver<Empty> responseObserver) {
        String query = request.getQuery();
        String userId = request.getUserId();

        String[] terms = query.split("[^\\w]+");
        userKeywordsStorage.add(userId, terms);
        log.infof("added keywords %s for user %s", Arrays.toString(terms), userId);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}

package mjuarez.graphql_github.graphql;

import java.io.IOException;
import java.util.List;

public interface IGraphQLOperationResponseHandler<D> {
    void onResponse(D data);
    // TODO: Condensate all error reporting
    void onNetworkError(IOException error);
    void onGraphQLError(List<GraphQLError> errors);
}

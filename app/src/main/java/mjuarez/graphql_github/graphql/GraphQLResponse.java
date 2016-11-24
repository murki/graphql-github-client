package mjuarez.graphql_github.graphql;

import java.util.List;

public class GraphQLResponse<D> {
    public D data;
    public List<GraphQLError> errors;
}

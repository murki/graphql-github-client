package mjuarez.graphql_github.graphql;

import java.lang.reflect.Type;

public interface IGraphQLQuery {
    String getQueryDocument();
    Type getResponseType();
}

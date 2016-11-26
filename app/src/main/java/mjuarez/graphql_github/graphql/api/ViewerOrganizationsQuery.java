package mjuarez.graphql_github.graphql.api;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import mjuarez.graphql_github.graphql.GraphQLResponse;
import mjuarez.graphql_github.graphql.IGraphQLQuery;
import mjuarez.graphql_github.graphql.model.ViewerOrganizations;

// FIXME: This class should be auto-generated
public class ViewerOrganizationsQuery implements IGraphQLQuery {
    private final static String operationDefinition =
            "query ViewerOrganizations {" +
                    "  viewer {" +
                    "    login" +
                    "    name" +
                    "    organizations(first: 3) {" +
                    "      edges {" +
                    "        org: node {" +
                    "          name" +
                    "        }" +
                    "      }" +
                    "    }" +
                    "  }" +
                    "}";

    private final static Type responseType = new TypeToken<GraphQLResponse<ViewerOrganizations>>(){}.getType();

    @Override
    public String getQueryDocument() {
        return operationDefinition;
    }

    @Override
    public Type getResponseType() {
        return responseType;
    }
}

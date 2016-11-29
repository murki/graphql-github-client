package mjuarez.graphql_github.graphql;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpRetryException;

import mjuarez.graphql_github.BuildConfig;
import mjuarez.graphql_github.HttpService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GraphQLClient {
    private final static String TAG = GraphQLClient.class.getCanonicalName();

    private final HttpService httpService = new HttpService(Uri.parse("https://api.github.com/graphql"), BuildConfig.GITHUB_OAUTH_BEARER);
    private final Gson gson = new Gson();

    public <D> void fetch(final IGraphQLQuery query, final IGraphQLOperationResponseHandler<D> handler) {
        httpService.post("{\"query\": \"" + query.getQueryDocument() + "\"}", new Callback() {
            @Override
            public void onFailure(Call call, IOException error) {
                handler.onNetworkError(error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // TODO: tmp wrong Exception
                    handler.onNetworkError(new HttpRetryException(response.message(), response.code()));
                    return;
                }
                String jsonResponse = response.body().string();
                Log.v(TAG, "Parsing json response=" + jsonResponse + " on Thread=" + Thread.currentThread().getName());
                GraphQLResponse<D> objectResponse = gson.fromJson(jsonResponse, query.getResponseType());
                if (objectResponse.errors != null) {
                    handler.onGraphQLError(objectResponse.errors);
                    return;
                }
                handler.onResponse(objectResponse.data);
            }
        });
    }
}

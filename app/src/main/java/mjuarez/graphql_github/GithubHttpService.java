package mjuarez.graphql_github;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GithubHttpService {

    private static final String GITHUB_GRAPHQL_ENDPOINT = "https://api.github.com/graphql";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client;

    private Call inflightCall;

    public GithubHttpService() {
        client = new OkHttpClient();
    }

    public void post(String json, Callback responseCallback){
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + BuildConfig.GITHUB_OAUTH_BEARER)
                .url(GITHUB_GRAPHQL_ENDPOINT)
                .post(body)
                .build();

        inflightCall = client.newCall(request);
        inflightCall.enqueue(responseCallback);
    }

    public void cancelPost() {
        if (inflightCall != null && !inflightCall.isCanceled()) {
            inflightCall.cancel();
        }
    }

}

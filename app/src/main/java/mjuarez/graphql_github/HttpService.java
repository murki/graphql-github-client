package mjuarez.graphql_github;

import android.net.Uri;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpService {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client;
    private final Uri graphQLUri;
    private final String authToken;

    public HttpService(Uri graphQLUri, String authToken) {
        client = new OkHttpClient();
        this.graphQLUri = graphQLUri;
        this.authToken = authToken;
    }

    public void post(String json, Callback responseCallback){
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + authToken)
                .url(graphQLUri.toString())
                .post(body)
                .build();

        client.newCall(request).enqueue(responseCallback);
    }
}

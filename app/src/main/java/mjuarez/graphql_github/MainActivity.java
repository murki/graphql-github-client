package mjuarez.graphql_github;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import mjuarez.graphql_github.graphql.GraphQLResponse;
import mjuarez.graphql_github.graphql.model.ViewerQueryResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Callback {

    private final static String TAG = MainActivity.class.getCanonicalName();

    private final GithubHttpService githubHttpService = new GithubHttpService();
    private final Gson gson = new Gson();
    private EditText mainTextView;
    private Button queryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTextView = (EditText) findViewById(R.id.mainText);
        queryButton = (Button) findViewById(R.id.queryButton);
    }

    public void queryButtonClick(View view) {
        loadData();
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        e.printStackTrace();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainTextView.setText(e.toString());
                queryButton.setEnabled(true);
            }
        });
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        final String jsonResponse = response.body().string();
        final GraphQLResponse<ViewerQueryResponse> objectResponse = parseJson(jsonResponse);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mainTextView.setText(jsonResponse);
                mainTextView.setText("login=" + objectResponse.data.viewer.login + "\nname=" + objectResponse.data.viewer.name);
                queryButton.setEnabled(true);
            }
        });
    }

    private void loadData() {
        queryButton.setEnabled(false);
        githubHttpService.post("{ \"query\": \"query {viewer{login,name,organizations(first:3){edges{org:node{name}}}}}\" }", this);
    }

    private GraphQLResponse<ViewerQueryResponse> parseJson(String json) {
        Log.v(TAG, "Parsing json response=" + json + " on Thread=" + Thread.currentThread().getName());
        Type responseType = new TypeToken<GraphQLResponse<ViewerQueryResponse>>(){}.getType();
        return gson.fromJson(json, responseType);
    }
}

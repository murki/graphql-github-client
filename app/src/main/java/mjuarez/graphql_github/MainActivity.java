package mjuarez.graphql_github;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;

import mjuarez.graphql_github.graphql.GraphQLClient;
import mjuarez.graphql_github.graphql.GraphQLError;
import mjuarez.graphql_github.graphql.IGraphQLOperationResponseHandler;
import mjuarez.graphql_github.graphql.api.ViewerOrganizationsQuery;
import mjuarez.graphql_github.graphql.model.ViewerOrganizations;

public class MainActivity extends AppCompatActivity implements IGraphQLOperationResponseHandler<ViewerOrganizations> {

    private final static String TAG = MainActivity.class.getCanonicalName();

    private final GraphQLClient graphQLClient = new GraphQLClient();
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
    protected void onDestroy() {
        super.onDestroy();
        // TODO: Cancel in-flight requests
    }

    @Override
    public void onResponse(final ViewerOrganizations data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainTextView.setText("login=" + data.viewer.login + "\nname=" + data.viewer.name);
                queryButton.setEnabled(true);
            }
        });
    }

    @Override
    public void onNetworkError(final IOException error) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainTextView.setText("networkError=" + error.toString());
                queryButton.setEnabled(true);
            }
        });
    }

    @Override
    public void onGraphQLError(final List<GraphQLError> errors) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainTextView.setText("graphQLError=" + errors.get(0).message);
                queryButton.setEnabled(true);
            }
        });
    }

    private void loadData() {
        queryButton.setEnabled(false);
        graphQLClient.fetch(new ViewerOrganizationsQuery(), this);
    }
}

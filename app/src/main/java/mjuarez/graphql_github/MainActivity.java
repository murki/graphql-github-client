package mjuarez.graphql_github;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements Callback {

    private final GithubHttpService githubHttpService = new GithubHttpService();

    private EditText mainTextView;
    private Button queryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainTextView = (EditText) findViewById(R.id.mainText);
        queryButton = (Button) findViewById(R.id.queryButton);
    }

    private void loadData() {
        queryButton.setEnabled(false);
        githubHttpService.post("{ \"query\": \"query { viewer { login }}\" }", this);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mainTextView.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    queryButton.setEnabled(true);
                }
            }
        });
    }

    public void queryButtonClick(View view) {
        loadData();
    }
}

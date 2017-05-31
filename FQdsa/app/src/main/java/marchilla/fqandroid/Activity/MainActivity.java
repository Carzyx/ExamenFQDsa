package marchilla.fqandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import marchilla.fqandroid.Model.Follower;
import marchilla.fqandroid.Model.User;
import marchilla.fqandroid.R;
import marchilla.fqandroid.Service.IGithubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    IGithubService _gitHubService;
    private List<Follower> followers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").
                addConverterFactory(GsonConverterFactory.create()).
                build();

        _gitHubService = retrofit.create(IGithubService.class);
    }



    public void doUserRequest(View view)
    {
        String username = ((EditText) findViewById(R.id.userGithub)).getText().toString();

        Call<User> call = _gitHubService.user(username);
        try
        {
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    User res = response.body();
                    Intent intent = new Intent(MainActivity.this, Display.class);
                    intent.putExtra("User", (Serializable) res);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

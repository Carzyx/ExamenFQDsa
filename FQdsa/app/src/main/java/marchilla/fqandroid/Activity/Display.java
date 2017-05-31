package marchilla.fqandroid.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import marchilla.fqandroid.Model.Follower;
import marchilla.fqandroid.Model.User;
import marchilla.fqandroid.R;
import marchilla.fqandroid.Service.FollowerAdapter;
import marchilla.fqandroid.Service.IGithubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Display extends AppCompatActivity {

    IGithubService _gitHubService;
    User user;
    ListView listView;
    FollowerAdapter adapter;
    private ArrayList<Follower> followers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        user = (User) getIntent().getSerializableExtra("User");

        TextView name = (TextView) findViewById(R.id.NameText);
        TextView nRepo = (TextView) findViewById(R.id.ReposText);
        ImageView image = (ImageView) findViewById(R.id.imageView);

        name.setText(user.getLogin());
        nRepo.setText(user.getPublicRepos().toString());
        Picasso.with(Display.this).load(user.getAvatarUrl()).into(image);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").
                addConverterFactory(GsonConverterFactory.create()).
                build();

        _gitHubService = retrofit.create(IGithubService.class);

        listView = (ListView) findViewById(R.id.arrayFollowes);
        doFollowersRequest();

        adapter = new FollowerAdapter(Display.this, followers);
        listView.setAdapter(adapter);
    }

    public void doFollowersRequest() {
        String username = ((TextView) findViewById(R.id.NameText)).getText().toString();

        Call<List<Follower>> call = _gitHubService.getFollowers(username);
        try {
            call.enqueue(new Callback<List<Follower>>() {
                @Override
                public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {

                    if (response.isSuccessful()) {
                        followers = (ArrayList<Follower>) response.body();
                    }
                }

                @Override
                public void onFailure(Call<List<Follower>> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


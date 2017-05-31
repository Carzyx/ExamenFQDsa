package marchilla.fqandroid.Service;

import java.util.List;

import marchilla.fqandroid.Model.Follower;
import marchilla.fqandroid.Model.User;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Miguel Angel on 31/05/2017.
 */

public interface IGithubService {


    @GET("users/{owner}/followers")
    Call<List<Follower>> getFollowers(
            @Path("owner") String owner);



    @GET("/users/{username}")
    Call<User> user(
            @Path("username") String login);
}

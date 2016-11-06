package com.randompeople.randompeople;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String URL = "https://randomuser.me/api/";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RandomPeopleService randomPeopleService = retrofit.create(RandomPeopleService.class);
        randomPeopleService.getRandomPeople(30).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, final Response<ApiResponse> response) {

                if (response.isSuccessful() && response.body().results != null) {
                    Log.d(TAG, "onResponse: Response size: " + response.body().results.size());

                    for (RandomPerson randomPerson : response.body().results) {
                        Log.d(TAG, "onResponse: First name: " + randomPerson.name.first);
                        Log.d(TAG, "onResponse: Last name: " + randomPerson.name.last);
                        Log.d(TAG, "onResponse: Gender: " + randomPerson.gender);
                        Log.d(TAG, "onResponse: City: " + randomPerson.location.city);
                        Log.d(TAG, "onResponse: Image res: " + randomPerson.picture.thumbnail);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showRandomPeople(response.body().results);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void showRandomPeople(List<RandomPerson> randomPeople) {
        RandomPersonAdapter randomPersonAdapter = new RandomPersonAdapter(this, randomPeople);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(randomPersonAdapter);
    }
}

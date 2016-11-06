package com.randompeople.randompeople;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Patryk on 2016-11-02
 */
public interface RandomPeopleService {

    @GET(".")
    Call<ApiResponse> getRandomPeople(@Query("results") int resultsCount);
}

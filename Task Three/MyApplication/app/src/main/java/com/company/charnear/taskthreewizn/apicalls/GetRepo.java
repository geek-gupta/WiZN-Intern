package com.company.charnear.taskthreewizn.apicalls;

import com.company.charnear.taskthreewizn.model.ResultModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gaurav on 14/4/18.
 */

public interface GetRepo {



    @GET("users/{user}/repos")
    Call<ArrayList<ResultModel>> listRepos(@Path("user") String user);
}

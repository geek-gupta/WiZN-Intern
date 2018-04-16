package com.company.charnear.taskthreewizn.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.company.charnear.taskthreewizn.R;
import com.company.charnear.taskthreewizn.adapter.MainAdapter;
import com.company.charnear.taskthreewizn.apicalls.GetRepo;
import com.company.charnear.taskthreewizn.model.ResultModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<ResultModel> mResultModel;
    private MainAdapter adapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        mResultModel = new ArrayList<>();

        getRepo();

        adapter = new MainAdapter(this,mResultModel);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void getRepo(){
        Gson gson = new Gson();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").
                addConverterFactory(GsonConverterFactory.create(gson)).build();

        GetRepo getRepo = retrofit.create(GetRepo.class);

        Call<ArrayList<ResultModel>> call = getRepo.listRepos("google");
        Callback<ArrayList<ResultModel>> callback = new Callback<ArrayList<ResultModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ResultModel>> call, Response<ArrayList<ResultModel>> response) {
//                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                mResultModel = response.body();
                progressBar.setVisibility(View.GONE);
                adapter.update(mResultModel);
            }

            @Override
            public void onFailure(Call<ArrayList<ResultModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();

            }
        };

        call.enqueue(callback);
    }



}

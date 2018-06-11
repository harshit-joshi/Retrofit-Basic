package com.example.harshitjoshi.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listView=findViewById(R.id.listView);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api=retrofit.create(Api.class);
        retrofit2.Call<List<Hero>> call=api.getHeroes();
        call.enqueue(new Callback<List<Hero>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Hero>> call, Response<List<Hero>> response)
            {
                List<Hero> heroes=response.body();
                for (Hero h:heroes)
                {
                    Log.d("name",h.getName());
                    Log.d("realname",h.getRealname());
                    Log.d("imageurl",h.getImageurl());
                }
                String[] heroNames =new String[heroes.size()];
                for (int i=0;i<heroes.size();i++)
                {
                    heroNames[i] = heroes.get(i).getName();
                }
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,heroNames)
                );
            }

            @Override
            public void onFailure(retrofit2.Call<List<Hero>> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.dta.dtawallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.dta.dtawallpaper.adapter.WallpaperAdapter;
import com.dta.dtawallpaper.entity.WallPaperEntity;
import com.dta.dtawallpaper.util.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private GridView gv_main;
    private WallpaperAdapter adapter;
    private List<WallPaperEntity> wallPaperEntityList;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        gv_main=findViewById(R.id.gv_main);
        adapter = new WallpaperAdapter(this,wallPaperEntityList);
        gv_main.setAdapter(adapter);
        getData();
    }

    private void getData() {
        Map<String,String> params = new HashMap<>();
        params.put("classify","yzmn");
        params.put("pageNum",page+"");
        params.put("limit","20");
        OkHttpUtil.post("http://www.dtasecurity.cn:18080/demo01/getWallpaper", params, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseText = response.body().string();
                new Gson().fromJson()
            }
        });
        page++;
    }


}
package com.dta.dtawallpaper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import com.dta.dtawallpaper.adapter.WallpaperAdapter;
import com.dta.dtawallpaper.entity.ResponseEntity;
import com.dta.dtawallpaper.entity.WallPaperEntity;
import com.dta.dtawallpaper.util.OkHttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private GridView gv_main;
    private WallpaperAdapter adapter;
    private List<WallPaperEntity> wallPaperEntities = new ArrayList<>();
    private int page = 1;
    private boolean isLoading = false;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                adapter.notifyDataSetChanged();
            }
            return false;
        }
    });

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initViews() {
        gv_main = findViewById(R.id.gv_main);
        adapter = new WallpaperAdapter(this,wallPaperEntities);
        gv_main.setAdapter(adapter);
        gv_main.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (((GridView)v).getLastVisiblePosition() > wallPaperEntities.size() - 6){
                    getData();
                }
            }
        });

        getData();
    }

    private void getData() {
        if (isLoading){
            return;
        }
        isLoading = true;
        Map<String, String> params = new HashMap<>();
        params.put("classify","yzmn");
        params.put("pageNum",page+"");
        params.put("limit","20");
        OkHttpUtil.post("http://www.dtasecurity.cn:18080/demo01/getWallpaper",
                params,
                new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        isLoading = false;
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseText = response.body().string();
                        Type responseType = new TypeToken<ResponseEntity<WallPaperEntity>>(){}.getType();
                        ResponseEntity<WallPaperEntity> data = new Gson().fromJson(responseText, responseType);
                        wallPaperEntities.addAll(data.getData());
                        //adapter.notifyDataSetChanged();
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                        isLoading = false;

                    }
                });
        page++;
    }
}
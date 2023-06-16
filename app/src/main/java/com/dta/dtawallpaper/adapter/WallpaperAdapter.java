package com.dta.dtawallpaper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.dta.dtawallpaper.R;
import com.dta.dtawallpaper.entity.WallPaperEntity;

import java.util.List;

public class WallpaperAdapter extends BaseAdapter {
    Context context;
    List<WallPaperEntity> wallPaperEntityList;

    public WallpaperAdapter(Context context, List<WallPaperEntity> wallPaperEntityList) {
        this.context = context;
        this.wallPaperEntityList = wallPaperEntityList;
    }

    @Override
    public int getCount() {
        return wallPaperEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return wallPaperEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View.inflate(context, R.layout.item_wallpaper,null);

        return null;
    }
}

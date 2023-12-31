package com.dta.dtawallpaper.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = View.inflate(context, R.layout.item_wallpaper, null);
        ImageView img_wallpaper = inflate.findViewById(R.id.img_item_wallpaper);
        String smallPicUrl = wallPaperEntityList.get(position).getSmallPic();
        RoundedCorners corners = new RoundedCorners(20);
        RequestOptions options = new RequestOptions().bitmapTransform(corners);
        Glide.with(img_wallpaper).load(smallPicUrl).apply(options).into(img_wallpaper);
        return inflate;
    }
}

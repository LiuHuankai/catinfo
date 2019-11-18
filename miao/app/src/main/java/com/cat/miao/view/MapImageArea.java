package com.cat.miao.view;

import android.content.Context;
import android.graphics.Canvas;

import androidx.appcompat.widget.AppCompatImageView;

public class MapImageArea extends AppCompatImageView {

    Context mContext;

    public MapImageArea(Context context)
    {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private float toDip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue * scale + 0.5f);
    }
}

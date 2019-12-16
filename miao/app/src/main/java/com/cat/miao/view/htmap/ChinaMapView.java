package com.cat.miao.view.htmap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.cat.miao.gesture.ScrollScaleGestureDetector;
import com.cat.miao.hotmapmodel.TjMapModel;
import com.cat.miao.hotmapmodel.ProvinceModel;

import java.util.ArrayList;
import java.util.List;

public class ChinaMapView extends View {
    private float viewWidth;
    private Paint innerPaint,outerPaint;
    private boolean isFirst;
    private ScrollScaleGestureDetector scrollScaleGestureDetector;
    private TjMapModel map;
    private float map_scale=0;
    private int selectPosition;
    private ScrollScaleGestureDetector.OnScrollScaleGestureListener onScrollScaleGestureListener=new ScrollScaleGestureDetector.OnScrollScaleGestureListener() {
        @Override
        public void onClick(float x, float y) {
            for (ProvinceModel p:map.getProvinceslist()){
                for (Region region:p.getRegionList()){
                    if (region.contains((int)x, (int)y)){
                        map.getProvinceslist().get(selectPosition).setSelect(false);
                        map.getProvinceslist().get(selectPosition).setLinecolor(Color.GRAY);
                        p.setSelect(true);
                        p.setLinecolor(Color.BLACK);
                        Log.e("error", "我找到了 " );
                        invalidate();
                        return;
                    }
                }
                }
        }
    };

    private onProvinceClickLisener onProvinceClickLisener;
    public ChinaMapView(Context context) {
        super(context);
    }
    public void setOnChoseProvince(onProvinceClickLisener lisener){
        this.onProvinceClickLisener=lisener;
    }
    //初始化准备工作
    public ChinaMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        innerPaint=new Paint();
        innerPaint.setColor(Color.BLUE);
        innerPaint.setAntiAlias(true);
        outerPaint=new Paint();
        outerPaint.setColor(Color.GRAY);
        outerPaint.setAntiAlias(true);
        outerPaint.setStrokeWidth(1);
        outerPaint.setStyle(Paint.Style.STROKE);
        scrollScaleGestureDetector=new ScrollScaleGestureDetector(this,onScrollScaleGestureListener);
    }
    public ChinaMapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width= MeasureSpec.getSize(widthMeasureSpec);
        if (map!=null){
            map_scale=width/map.getMax_x();
        }
        int height=(int) (map.getMax_y()*map_scale);
        setMeasuredDimension(width, height);
    }

    public void setMap(TjMapModel map){
        this.map=map;
        isFirst=true;
        invalidate();
    }
    public void chingeMapColors(){
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (isFirst){
        viewWidth=getWidth()-getPaddingLeft()-getPaddingRight();
            if (map!=null){
                map_scale=viewWidth/map.getMax_x();
            }
            scalePoints(canvas,map_scale);
            isFirst=false;
        }else{
            scrollScaleGestureDetector.connect(canvas);
            scrollScaleGestureDetector.setScaleMax(3);
            scrollScaleGestureDetector.setScalemin(1);
            drawMap(canvas);
        }
        super.onDraw(canvas);
    }

    private void drawMap(Canvas canvas) {
        if (map.getProvinceslist().size()>0){
            outerPaint.setStrokeWidth(1);
            for (int i=0;i<map.getProvinceslist().size();i++){
                if (map.getProvinceslist().get(i).isSelect()){
                    selectPosition=i;
                }else{
                    innerPaint.setColor(map.getProvinceslist().get(i).getColor());
                    outerPaint.setColor(map.getProvinceslist().get(i).getLinecolor());
                    for (Path p:map.getProvinceslist().get(i).getListpath()){
                        canvas.drawPath(p, innerPaint);
                        canvas.drawPath(p, outerPaint);
                    }
                }
            }
            innerPaint.setColor(map.getProvinceslist().get(selectPosition).getColor());
            outerPaint.setColor(map.getProvinceslist().get(selectPosition).getLinecolor());
            outerPaint.setStrokeWidth(2.5f);
            for (Path p:map.getProvinceslist().get(selectPosition).getListpath()){
                canvas.drawPath(p, innerPaint);
                canvas.drawPath(p, outerPaint);
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return scrollScaleGestureDetector.onTouchEvent(event);
    }

    private void scalePoints(Canvas canvas, float scale) {
        if (map.getProvinceslist().size()>0)
            map.setMax_x(map.getMax_x()*scale);
        map.setMin_x(map.getMin_x()*scale);
        map.setMax_y(map.getMax_y()*scale);
        map.setMin_y(map.getMin_y()*scale);
            for (ProvinceModel province:map.getProvinceslist()){
                innerPaint.setColor(province.getColor());
                List<Region> regionList=new ArrayList<>();
                List<Path> pathList=new ArrayList<>();
                for (Path p:province.getListpath()){
                    Path newpath=resetPath(p, scale, regionList);
                    pathList.add(newpath);
                    canvas.drawPath(newpath,innerPaint);
                    canvas.drawPath(newpath,outerPaint);
                }
                    province.setListpath(pathList);
                province.setRegionList(regionList);
            }
    }

    private Path resetPath(Path path, float scale, List<Region> regionList) {
        List<PointF> list=new ArrayList<>();
        PathMeasure pathmesure=new PathMeasure(path,true);
        float[] s=new float[2];
        for (int i=0;i<pathmesure.getLength();i=i+2) {
            pathmesure.getPosTan(i, s, null);
            PointF p=new PointF(s[0]*scale,s[1]*scale);
            list.add(p);
        }

        Path path1=new Path();
        for (int i=0;i<list.size();i++){
            if (i==0){
                path1.moveTo(list.get(i).x,list.get(i).y);
            }else{
                path1.lineTo(list.get(i).x, list.get(i).y);
            }
        }
        path1.close();

        RectF rf=new RectF();
        Region re=new Region();
        path1.computeBounds(rf,true);
        re.setPath(path1,new Region((int)rf.left,(int)rf.top,(int)rf.right,(int)rf.bottom));
        regionList.add(re);
        return path1;
    }

    public interface onProvinceClickLisener{
        public void onChose(String provincename);
    }
}

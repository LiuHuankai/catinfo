package com.cat.miao.view.catfragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cat.miao.R;

import java.util.ArrayList;

public class CatRecycleAdapter extends RecyclerView.Adapter<CatRecycleAdapter.catViewHolder>{

    private Context context;
    private ArrayList<CatListEntity> catEntityList;

    //创建构造函数
    public CatRecycleAdapter(Context context, ArrayList<CatListEntity> catEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.catEntityList = catEntityList;//实体类数据ArrayList
    }

    /**
     * 创建viewholder，相当于listview中getview中的创建view和viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public catViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.cat_list_layout, null);
        return new catViewHolder(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(catViewHolder holder, int position) {
        //根据点击位置绑定数据
        CatListEntity data = catEntityList.get(position);
        Glide.with(context).load(data.catimagepath).into(holder.catimg);
        holder.catname.setText(data.catname);//获取实体类中的name字段并设置
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return catEntityList.size();
    }

    //自定义viewholder
    class catViewHolder extends RecyclerView.ViewHolder {
        private ImageView catimg;
        private TextView catname;

        public catViewHolder(View itemView) {
            super(itemView);
            catimg = (ImageView) itemView.findViewById(R.id.cat_list_img);
            catname = (TextView) itemView.findViewById(R.id.cat_list_name);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, catEntityList.get(getLayoutPosition()));
                    }
                }
            });

        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, CatListEntity data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

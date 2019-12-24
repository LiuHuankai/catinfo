package com.cat.miao.view.AdoptFragment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cat.miao.R;

import java.util.ArrayList;

public class AdoptRecycleAdapter extends RecyclerView.Adapter<AdoptRecycleAdapter.adaptViewHolder>{
    private Context context;
    private ArrayList<AdoptListEntity> adoptEntityList;

    //创建构造函数
    public AdoptRecycleAdapter(Context context, ArrayList<AdoptListEntity> adoptEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.adoptEntityList = adoptEntityList;//实体类数据ArrayList
    }

    /**
     * 创建viewholder，相当于listview中getview中的创建view和viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public adaptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.adopt_list_layout, null);
        return new adaptViewHolder(itemView);
    }

    /**
     * 绑定数据，数据与view绑定
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(adaptViewHolder holder, int position) {
        //根据点击位置绑定数据
        AdoptListEntity data = adoptEntityList.get(position);
        //        holder.catimg;
        Glide.with(context).load("http://q2wxpbxdw.bkt.clouddn.com/"+data.adoptimagepath).into(holder.adoptimg);
        holder.adoptname.setText(data.adoptname);//获取实体类中的name字段并设置
    }

    /**
     * 得到总条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return adoptEntityList.size();
    }

    //自定义viewholder
    class adaptViewHolder extends RecyclerView.ViewHolder {
        private ImageView adoptimg;
        private TextView adoptname;

        public adaptViewHolder(View itemView) {
            super(itemView);
            adoptimg = (ImageView) itemView.findViewById(R.id.adopt_list_img);
            adoptname = (TextView) itemView.findViewById(R.id.adopt_list_name);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, adoptEntityList.get(getLayoutPosition()));
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
        public void OnItemClick(View view, AdoptListEntity data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

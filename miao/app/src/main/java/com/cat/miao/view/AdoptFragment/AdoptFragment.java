package com.cat.miao.view.AdoptFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cat.miao.R;
import com.cat.miao.model.AdoptInfoBean;
import com.cat.miao.model.CatBean;
import com.cat.miao.network.RxRetrofitForAdoptInfo;
import com.cat.miao.network.RxRetrofitForCat;

import java.util.ArrayList;

public class AdoptFragment extends Fragment {
    private View view;//定义view用来设置fragment的layout
    //定义以AdoptListEntity实体类为对象的数据集合
    private ArrayList<AdoptListEntity> adoptEntityList = new ArrayList<AdoptListEntity>();
    //自定义recyclerview的适配器
    private AdoptRecycleAdapter mAdoptRecycleAdapter;

    ArrayList<AdoptInfoBean.Result> infoResult;
    CatBean.Result catinfoResult;
    int infosize;

    public static AdoptFragment getInstance(){
        AdoptFragment adoptFragment = new AdoptFragment();
        return adoptFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.adopt_fragment, container, false);
        TextView toolBarTitle = (TextView) view.findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //对recycleview进行配置
        //获取RecyclerView
        RecyclerView mAdoptRecyclerView = (RecyclerView) this.view.findViewById(R.id.adopt_recycler_view);
        //创建adapter
        mAdoptRecycleAdapter = new AdoptRecycleAdapter(getActivity(), adoptEntityList);
        //给RecyclerView设置adapter
        mAdoptRecyclerView.setAdapter(mAdoptRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mAdoptRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mAdoptRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mAdoptRecycleAdapter.setOnItemClickListener(new AdoptRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, AdoptListEntity data) {
                //此处进行监听事件的业务处理
                //Toast.makeText(getActivity(),"我是item", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AdoptInfoActivity.class);
                intent.putExtra("adoptid", data.adoptid);
                startActivity(intent);
            }
        });
        //模拟数据
        SharedPreferences sp = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String userLoginState = sp.getString("login_state", "1");
        /*
        if(userLoginState == "1"){
//            RxRetrofitForAdoptInfo.getInstens().getAdoptInformation(new RxRetrofitForAdoptInfo.CallBack() {
//                @Override
//                public void onSuccess(AdoptInfoBean adoptInfoBean) {
//                    infoResult = adoptInfoBean.getResult();
//                    infosize = infoResult.size();
//                    Log.e( "infosize",""+infosize);
//                    for(int i=0;i<infosize;i++){
//                        Log.e( "testadoptcat",""+i);
//                        Log.e( "adoptcatID",""+infoResult.get(i).getCatId());
//                        initData(infoResult.get(i).getCatId());
//                    }
//                }
//
//                @Override
//                public void onError() {
//
//                }
//            });
            initData(2);
            initData(3);
            Log.e( "登陆成功了: ", userLoginState);
        }
        else{
            Log.e( "登陆失败了: ", userLoginState);
            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
        }

*/
        //为页面上方toolbar设定标题
        toolBarTitle.setText("我的领养");
        toolbar.setTitle("  ");

        return view;
    }
    }



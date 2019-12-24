package com.cat.miao.view.catfragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
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
import com.cat.miao.model.CatInfoBean;
import com.cat.miao.network.RxRetrofitForCatInfo;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

;

public class CatInfoFragment extends Fragment {
    private int flag = 1;
    ArrayList<CatInfoBean.Result> infoResult;
//    CatListEntity catListEntity=new CatListEntity();
    private View view;//定义view用来设置fragment的layout
    //定义以AdoptListEntity实体类为对象的数据集合
    private ArrayList<CatListEntity> catEntityList = new ArrayList<CatListEntity>();
    //自定义recyclerview的适配器
    private CatRecycleAdapter mCatRecycleAdapter;
    private SearchView searchView;
    RecyclerView mCatRecyclerView;

    public static CatInfoFragment getInstance(){
        CatInfoFragment catInfoFragment = new CatInfoFragment();
        return catInfoFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.catinfo_fragment, container, false);
        TextView toolBarTitle = (TextView)view.findViewById(R.id.toobar_title);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        //对recycleview进行配置
        //获取RecyclerView
        mCatRecyclerView=(RecyclerView)this.view.findViewById(R.id.cat_recycler_view);
        //创建adapter
        mCatRecycleAdapter = new CatRecycleAdapter(getActivity(), catEntityList);
        //给RecyclerView设置adapter
        mCatRecyclerView.setAdapter(mCatRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCatRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCatRecycleAdapter.setOnItemClickListener(new CatRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, CatListEntity data) {
                //此处进行监听事件的业务处理
                //Toast.makeText(getActivity(),"我是item", Toast.LENGTH_SHORT).show();
                Log.e( "OnItemClick: ", ""+data.catid);

                Intent intent = new Intent(getActivity(), CatInfoActivity.class);
                intent.putExtra("catid",data.catid);

                startActivity(intent);
            }
        });

        SharedPreferences sp= getActivity().getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
        String userid=sp.getString("id","1");

        //模拟数据
        initData();

        //为页面上方toolbar设定标题
        toolBarTitle.setText("猫咪信息");
        toolbar.setTitle("  ");

        RefreshLayout refreshLayout = (RefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                catEntityList.clear();
                flag=1;
                initData();
                refreshlayout.finishRefresh(500/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                flag++;
                Log.e("flag: ",""+flag );
                initData();
                mCatRecycleAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadMore(500/*,false*/);//传入false表示加载失败
            }
        });

        searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query){
                if(TextUtils.isEmpty(query)) {
                    catEntityList.clear();
                    flag=1;
                    initData();
                }
                else{
                    mCatRecyclerView.removeAllViews();
                    searchdata(query);
                    Toast.makeText(getActivity(), "查找成功", Toast.LENGTH_SHORT).show();
                    mCatRecycleAdapter.notifyDataSetChanged();
                }
                return true;
            }
            public boolean onQueryTextChange(String newText){
                return true;
            }
        });

        return view;
    }

    /**
     * TODO 模拟数据
     */
    private void initData() {
        RxRetrofitForCatInfo.getInstens().getCatInformation(new RxRetrofitForCatInfo.CallBack() {
            @Override
            public void onSuccess(CatInfoBean catInfoBean) {
                infoResult = catInfoBean.getResult();
                Log.e("CatCode", catInfoBean.getCode() );
                for (int i=0;i<10;i++){
                    CatListEntity catListEntity=new CatListEntity();
                    catListEntity.setCatid(infoResult.get(i).getID());
                    Log.e("Catimg: ", i+infoResult.get(i).getUrl());
                    if(infoResult.get(i).getName() == null){
                        catListEntity.setCatname("暂缺");
                    }
                    else {
                        Log.e("Catname: ", i+infoResult.get(i).getName());
                        catListEntity.setCatname(infoResult.get(i).getName());
                    }
                    if(infoResult.get(i).getUrl() == null){
//                        Log.e("Catimg: ", i+infoResult.get(i).getUrl());
                        Log.e("别搞我，Catimg炸了吗？: ", i+infoResult.get(i).getUrl());
//                        catListEntity.setCatimagepath("http://img1.imgtn.bdimg.com/it/u=697661107,2424028308&fm=26&gp=0.jpg");

                    }
                    else{
                        Log.e("Catimg炸了吗？: ", i+infoResult.get(i).getUrl());
                        String url = infoResult.get(i).getUrl();
                        String m = url.substring(url.length()-1,url.length());
                        url = url.substring(0,url.length()-1);
                        int flag = Integer.parseInt(m);
                        flag--;
                        url = url+flag;
                        catListEntity.setCatimagepath(url);
                    }

                    catEntityList.add(catListEntity);
                }
            }

            @Override
            public void onError() {

            }
        },flag,10);
    }

    private void searchdata(String name){
        catEntityList.clear();
        RxRetrofitForCatInfo.getInstens().getSearchInformation(new RxRetrofitForCatInfo.CallBack() {
            @Override
            public void onSuccess(CatInfoBean catInfoBean) {
                infoResult = catInfoBean.getResult();
                int length=infoResult.size();
                Log.e("CatCode", catInfoBean.getCode() );
                for (int i=0;i<length;i++){
                    CatListEntity catListEntity=new CatListEntity();
                    catListEntity.setCatid(infoResult.get(i).getID());
                    if(infoResult.get(i).getName() == null){
                        catListEntity.setCatname("暂缺");
                    }
                    else {
                        Log.e("Catname: ", i+infoResult.get(i).getName());
                        catListEntity.setCatname(infoResult.get(i).getName());
                    }
                    if(infoResult.get(i).getUrl() == null){
//                        Log.e("Catimg: ", i+infoResult.get(i).getUrl());
                        Log.e("别搞我，Catimg炸了吗？: ", i+infoResult.get(i).getUrl());
//                        catListEntity.setCatimagepath("http://img1.imgtn.bdimg.com/it/u=697661107,2424028308&fm=26&gp=0.jpg");

                    }
                    else{
                        Log.e("Catimg炸了吗？: ", i+infoResult.get(i).getUrl());
                        String url = infoResult.get(i).getUrl();
                        String m = url.substring(url.length()-1,url.length());
                        url = url.substring(0,url.length()-1);
                        int flag = Integer.parseInt(m);
                        flag--;
                        url = url+flag;
                        catListEntity.setCatimagepath(url);
                    }
                    catEntityList.add(catListEntity);
                }
            }

            @Override
            public void onError() {

            }
        },1,name,10);
    }

}

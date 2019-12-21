package com.cat.miao.view.userfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cat.miao.R;
import com.cat.miao.model.UserInfoUpdateBean;
import com.cat.miao.network.RxRetrofitForUserInfo;
import com.cat.miao.util.QnUploadHelper;
import com.qiniu.android.http.ResponseInfo;

import java.util.Map;

public class UserInfo extends AppCompatActivity {
    String photoPath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        final TextView toolBarTitle = (TextView) findViewById(R.id.toobar_title);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        initView();

        toolBarTitle.setText("用户信息");

        toolbar.setTitle("  ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initQnUp();

        updata();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView(){
        final ImageView headImage = (ImageView) findViewById(R.id.avatar_image_view);
        Glide.with(UserInfo.this)
             .load("http://cat.sparkxyf.cn/test")
             .into(headImage);
    }

    private void updata(){
        RxRetrofitForUserInfo.getInstens().updateInfo(new RxRetrofitForUserInfo.UpdateCallBack() {
            @Override
            public Map<String, String> getMap() {
                return null;
            }

            @Override
            public void onSuccess(UserInfoUpdateBean userInfoUpdateBean) {
                Log.e("error", userInfoUpdateBean.getMessage() );
            }
        });
    }

    public void initQnUp(){
        final ImageView headImage = (ImageView) findViewById(R.id.avatar_image_view);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 0x1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        //当进更改头像进入手机图库时回调；
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                ContentResolver resolver = getContentResolver();

                try {
                    // 获取圖片URI
                    Uri uri = data.getData();
                    // 将URI转换为路径：
                    String[] proj = { MediaStore.Images.Media.DATA };
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    //  这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    // 最后根据索引值获取图片路径
                    photoPath = cursor.getString(column_index);

                    bitmap = BitmapFactory.decodeFile(photoPath);

                    ImageView headImage = (ImageView) findViewById(R.id.avatar_image_view);
                    headImage.setImageBitmap(bitmap);


                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //初始化七牛云
        QnUploadHelper.init("EeqSGbHhMFs4dE3xjh9VrdOs7616EIen2C2OiLFM", "UhF-yLm12TBTn1xsg6NTXIlkhGQKHhAL8wsgFRgw");
        QnUploadHelper.uploadPic(photoPath, "test", new QnUploadHelper.UploadCallBack() {
            @Override
            public void success(String url) {
                Log.i("image_url", url);

            }

            @Override
            public void fail(String key, ResponseInfo info) {
                Log.i("key", key+info.error);
                Log.e("error", "七牛云牛逼" );
            }
        });
    }
}

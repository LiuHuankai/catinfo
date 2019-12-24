package com.cat.miao.view.userfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cat.miao.MainActivity;
import com.cat.miao.R;
import com.cat.miao.model.UserInfoUpdateBean;
import com.cat.miao.network.RxRetrofitForUserInfo;
import com.cat.miao.util.QnUploadHelper;
import com.cat.miao.view.LoginActivity;
import com.cat.miao.view.SignupActivity;
import com.qiniu.android.http.ResponseInfo;

import java.util.HashMap;
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

        //初始化toolbar
        toolBarTitle.setText("用户信息");
        toolbar.setTitle("  ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initQnUp();

    }

    private void initView(){
        EditText nickNameText = (EditText) findViewById(R.id.name_text_view);
        EditText emailText = (EditText) findViewById(R.id.email_text_view);
        EditText phoneText = (EditText) findViewById(R.id.phone_set_text_view);
        final ImageView headImage = (ImageView) findViewById(R.id.avatar_image_view);

        SharedPreferences sp = getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
        String image = sp.getString("headImage", "default");
        Log.e("init", image);
        Glide.with(UserInfo.this)
             .load("http://q2wxpbxdw.bkt.clouddn.com/" + image)
             .into(headImage);

        phoneText.setText(sp.getString("phone", "default"));
        emailText.setText(sp.getString("email", "default"));
        nickNameText.setText(sp.getString("username", "default"));

    }

    private void update(){
        RxRetrofitForUserInfo.getInstens().updateInfo(new RxRetrofitForUserInfo.UpdateCallBack() {
            @Override
            public Map<String, String> getMap() {
                EditText nickNameText = (EditText) findViewById(R.id.name_text_view);
                EditText emailText = (EditText) findViewById(R.id.email_text_view);
                EditText phoneText = (EditText) findViewById(R.id.phone_set_text_view);
                Map<String, String> map = new HashMap<>();

                SharedPreferences sp = getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username", nickNameText.getText().toString());
                editor.putString("email", emailText.getText().toString());
                editor.putString("phone", phoneText.getText().toString());
                editor.apply();

                //将要更新的数据打包
                map.put("image", sp.getString("headImage", "default"));
                map.put("name", sp.getString("username", "default"));
                map.put("email", sp.getString("email", "default"));
                map.put("phone", sp.getString("phone", "default"));
                return map;
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

                    // 压缩成800*480
                    bitmap = BitmapFactory.decodeFile(photoPath);

                    ImageView headImage = (ImageView) findViewById(R.id.avatar_image_view);
                    headImage.setImageBitmap(bitmap);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //初始化七牛云
        SharedPreferences sp = getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
        String key = sp.getString("account", "default");
        SharedPreferences.Editor editor = sp.edit();

        QnUploadHelper.init("EeqSGbHhMFs4dE3xjh9VrdOs7616EIen2C2OiLFM", "UhF-yLm12TBTn1xsg6NTXIlkhGQKHhAL8wsgFRgw");
        QnUploadHelper.uploadPic(photoPath, key, new QnUploadHelper.UploadCallBack() {
            @Override
            public void success(String url) {
                Log.i("image_url", url);
                final ImageView headImage = (ImageView) findViewById(R.id.avatar_image_view);

                SharedPreferences sp = getSharedPreferences("sp_user_state", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("headImage", url);
                editor.apply();
                Glide.with(UserInfo.this)
                        .load("http://q2wxpbxdw.bkt.clouddn.com/" + url)
                        .into(headImage);
            }

            @Override
            public void fail(String key, ResponseInfo info) {
                Log.i("key", key+info.error);
                Log.e("error", "七牛云牛逼" );
            }
        });
    }

    @Override
    protected void onDestroy() {
        //在用户退出信息界面的时候自动更新用户信息
        update();
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);

        startActivity(intent);
        super.onDestroy();
    }
}

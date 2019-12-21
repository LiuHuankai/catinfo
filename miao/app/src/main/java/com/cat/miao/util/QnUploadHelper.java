package com.cat.miao.util;


import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class QnUploadHelper {
    private final String TAG = QnUploadHelper.class.getSimpleName();
    //七牛后台的key
    private static String accessKey;
    //七牛后台的secret
    private static String secretKey;
    private static String BucketName = "tjmmtm";
    private final static String MAC_NAME = "HmacSHA1";
    private final static String ENCODING = "UTF-8";
    private static Configuration configuration;
    private static long delayTimes = 3029414400l; //有效时间

    // 初始化
    public static void init(String AccessKey, String SecretKey) {
        accessKey = AccessKey;
        secretKey = SecretKey;
        configuration = new Configuration.Builder().build();
    }

    public static void uploadPic(final String path, final String keys, final UploadCallBack callBack){
        try {
            // 1:第一种方式 构造上传策略
            JSONObject _json = new JSONObject();
            _json.put("deadline", delayTimes);
            _json.put("scope", BucketName);
            String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                    .toString().getBytes());

            byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, secretKey);

            String _encodedSign = UrlSafeBase64.encodeToString(_sign);

            //生成token
            final String _uploadToken = accessKey + ':' + _encodedSign + ':'
                    + _encodedPutPolicy;

            //重用uploadManager。一般地，只需要创建一个uploadManager对象
            UploadManager uploadManager = new UploadManager(configuration);

            uploadManager.put(path, keys, _uploadToken,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info,
                                             JSONObject response) {
                            Log.d("error", "response = " + response);
                            if (info.isOK()) {
                                callBack.success(key);
                            }else
                                callBack.fail(key, info);
                            }
                        },null);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    //反馈接口
    public interface UploadCallBack {
        void success(String url);
        void fail(String key, ResponseInfo info);
    }
}




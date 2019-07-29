package com.dchip.parking.api.util.jpush;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;

import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

public class JGuangPush {
	private static final Logger log = LoggerFactory.getLogger(JGuangPush.class);
    private static String masterSecret = "f6d677351d29fbbf54403959";
    private static String appKey = "a67389319f40a53b3b5bb4f1";
    private static String pushUrl = "https://api.jpush.cn/v3/push";    
    private static boolean apns_production = true;    
    private static int time_to_live = 86400;
    private static final String ALERT = "推送信息";    
    /**
     * 
     * @param registrationId  标识（必填）
     * @param message  推送的内容（必填）
     * @param msgId 消息id（非必填）
     * @param type  发送的消息类型（必填）
     */
    public static void jiguangPush(String registrationId,String message,Integer msgId,Integer type){
        try{
            String result = push(pushUrl,registrationId,message,appKey,masterSecret,apns_production,time_to_live,msgId,type);
            JSONObject resData = JSONObject.fromObject(result);
                if(resData.containsKey("error")){
                    log.info("针对registrationId为" + registrationId + "的信息推送失败！");
                    JSONObject error = JSONObject.fromObject(resData.get("error"));
                    log.info("错误信息为：" + error.get("message").toString());
                }else {
                	log.info("针对registrationId为" + registrationId + "的信息推送成功！");
                }
        }catch(Exception e){
            log.error("针对registrationId为" + registrationId + "的信息推送失败！",e);
        }
    }
    
    
    /**
     * 组装极光推送专用json串
     * @param alias
     * @param alert
     * @return json
     * @param msgId 消息id
     * @param type 消息类型
     */
    public static JSONObject generateJson(String registrationId,String alert,boolean apns_production,int time_to_live,int msgId,int type){
        JSONObject json = new JSONObject();
        JSONArray platform = new JSONArray();//平台
        platform.add("android");
        platform.add("ios");
        
        JSONObject audience = new JSONObject();//推送目标
        JSONArray registrationId1 = new JSONArray();
        registrationId1.add(registrationId);
        audience.put("registration_id", registrationId1);
        
        JSONObject notification = new JSONObject();//通知内容
        JSONObject android = new JSONObject();//android通知内容
        android.put("alert", alert);
        android.put("builder_id", 1);
        JSONObject android_extras = new JSONObject();//android额外参数
        android_extras.put("msgId", msgId);
        android_extras.put("type", type);
        android.put("extras", android_extras);
        
        JSONObject ios = new JSONObject();//ios通知内容
        ios.put("alert", alert);
        ios.put("sound", "default");
        ios.put("badge", "+1");
        JSONObject ios_extras = new JSONObject();//ios额外参数
        ios_extras.put("msgId", msgId);
        ios_extras.put("type", type);
        ios.put("extras", ios_extras);
        notification.put("android", android);
        notification.put("ios", ios);
        
        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", Integer.valueOf(time_to_live));
        options.put("apns_production", apns_production);
        
        json.put("platform", platform);
        json.put("audience", audience);
        json.put("notification", notification);
        json.put("options", options);
        return json;
        
    }
    
    /**
     * 推送方法-调用极光API
     * @param reqUrl
     * @param alias
     * @param alert
     * @return result
     * @param msgId 消息id
     * @param type 消息类型
     */
    public static String push(String reqUrl,String alias,String alert,String appKey,String masterSecret,boolean apns_production,int time_to_live,int msgId,int type){
        String base64_auth_string = encryptBASE64(appKey + ":" + masterSecret);
        String authorization = "Basic " + base64_auth_string;
        return sendPostRequest(reqUrl,generateJson(alias,alert,apns_production,time_to_live,msgId,type).toString(),"UTF-8",authorization);
    }
    
    /**
     * 发送Post请求（json格式）
     * @param reqURL
     * @param data
     * @param encodeCharset
     * @param authorization
     * @return result
     */
    @SuppressWarnings({ "resource" })
    public static String sendPostRequest(String reqURL, String data, String encodeCharset,String authorization){
        HttpPost httpPost = new HttpPost(reqURL);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        String result = "";
        try {
             StringEntity entity = new StringEntity(data, encodeCharset);
             entity.setContentType("application/json");
             httpPost.setEntity(entity);
             httpPost.setHeader("Authorization",authorization.trim());
             response = client.execute(httpPost);
             result = EntityUtils.toString(response.getEntity(), encodeCharset);
        } catch (Exception e) {
            log.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);  
        }finally{
            client.getConnectionManager().shutdown();
        }
        return result;
    }
     /** 
　　　　* BASE64加密工具
　　　　*/
     public static String encryptBASE64(String str) {
         byte[] key = str.getBytes();
       BASE64Encoder base64Encoder = new BASE64Encoder();
       String strs = base64Encoder.encodeBuffer(key);
         return strs;
     }
}

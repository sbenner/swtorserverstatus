package test.test;

/**
 * Created by IntelliJ IDEA.
 * User: siggi
 * Date: 30/01/12
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */


import android.app.Activity;
import com.swtorserversstatus.R;

import org.apache.http.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

public class ConnectsqlActivity extends Activity {


       @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String result = null;
        InputStream is = null;
        StringBuilder sb=null;
        setContentView(R.layout.b);
//        try{
//            DefaultHttpClient httpclient = new DefaultHttpClient();
//            Utils.setProxy(httpclient,"10.10.0.161",8080);
//            HttpGet httppost = new HttpGet("http://188.40.32.145/abdcdeg.php");
//            HttpResponse response = httpclient.execute(httppost);
//            HttpEntity entity = response.getEntity();
//            is = entity.getContent();
//        }catch(Exception e){
//            Log.e("log_tag", "Error in http connection"+e.toString());
//        }
//        try{
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            sb = new StringBuilder();
////            sb.append(reader.readLine() + "\n");
//            String line=null;
//
//            while ((line = reader.readLine()) != null) {
//                sb.append(line + "\n");
//            }
//
//            is.close();
//            result=sb.toString();
//
//        }catch(Exception e){
//            Log.e("log_tag", "Error converting result "+e.toString());
//        }
//        System.out.println(result);
//        //paring data
//        int fd_id=0;
//        String fd_name=null;
//        JSONArray json_data=null;
//
//        try{
//        JSONObject jArray = new JSONObject(result);
//        TextView tv = (TextView)findViewById(R.id.test_text);
//        json_data = jArray.getJSONArray("test");
//
//
//        for(int i=0;i<json_data.length();i++){
//                JSONObject obj = json_data.getJSONObject(i);
//                fd_id=obj.getInt("id");
//                fd_name=obj.getString("test1");
//                tv.setText(tv.getText()+" "+fd_name);
//        }
//
//        }catch(JSONException e1){
//            e1.printStackTrace();
//            Toast.makeText(getBaseContext(), "No Food Found", Toast.LENGTH_LONG).show();
//        }catch (ParseException e1){
//            e1.printStackTrace();
//        }



//      JSONObject json = JSONfunctions
//                        .getJSONfromURL("http://188.40.32.145/abdcdeg.php");

        String json = "{\"1\": [{\"StoreName\": \"??????????\",\"StoreTel\": \"047-480-3660\"," +
                "\"StoreAddress\": \"?276-0049 ??????????????????\"," +
                "\"WorkingTimeInNormalDay\": \"7:30 AM - 9:00PM\"," +
                "\"WorkingTimeInWeekend\": \"9:00-22:00\"," +
                "\"HaveKidProduct\": \"N\"" +
                "}]}";


                try {
                    JSONObject e = new JSONObject(json);
                    JSONArray jArray = e.getJSONArray("1");
                     for(int i=0;i<jArray.length();i++){
                    JSONObject obj = jArray.getJSONObject(i);
                     System.out.println(obj.getString("WorkingTimeInNormalDay"));
                    }

                    System.out.println(jArray.getString(0));
                } catch (JSONException e) {
                    Log.e("log_tag", "Error parsing data " + e.toString());
                }





    }


    public static class JSONfunctions {

    public static JSONObject getJSONfromURL(String url) {
        InputStream is = null;
        String result = "regulatory";
        JSONObject jArray = null;

        // http post
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
         //   Utils.setProxy(httpclient,"10.10.0.161",8080);
            HttpPost httppost = new HttpPost(url);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();

        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

        // convert response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try {

            jArray = new JSONObject(result);
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return jArray;
    }
}




}

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 15/02/12
 * Time: 21:53
 * Purpose:
 */
public class OnAlarmReceiver extends Service {
@Override
public IBinder onBind(Intent intent) {
    return null;
}

    @Override
    public void onCreate() {
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost("http://www.yoursite.com/script.php");

    try {
        // Add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("id", "12345"));
        nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        // Execute HTTP Post Request
        HttpResponse response = httpclient.execute(httppost);

    } catch (ClientProtocolException e) {
        // TODO Auto-generated catch block
    } catch (IOException e) {
        // TODO Auto-generated catch block
    }
    }
}
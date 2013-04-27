/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 15/02/12
 * Time: 21:53
 * Purpose:
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.swtorserversstatus.R;

public class MenuActivity extends Activity {
 WebView mWebView;
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);

   startService(new Intent(this, OnAlarmReceiver.class));

   setContentView(R.layout.main);
    if (savedInstanceState != null)
        ((WebView)findViewById(R.id.webviewnew)).restoreState(savedInstanceState);

    mWebView = (WebView) findViewById(R.id.webviewnew);
    mWebView.getSettings().setJavaScriptEnabled(true); // w??czenie JS
    mWebView.getSettings().setSupportZoom(false); // blokada ZOOMu



    //mWebView.loadUrl("http://onet.home.pl/"); // strona do wy?wietlenia
    mWebView.setWebViewClient(new MenuClient());
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
    {
        mWebView.loadUrl("http://onet.home.pl/");
    }

    else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
    {
        mWebView.loadUrl("http://onet.home.pl/");
    }
}
private class MenuClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
protected void onSaveInstanceState(Bundle outState) {
   mWebView.saveState(outState);
 }

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
    if ((keyCode == KeyEvent.KEYCODE_HOME || keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_CALL || keyCode == KeyEvent.KEYCODE_POWER || keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_CLEAR || keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_POWER)) {
        //mWebView.goBack();
        return false;
    }
    return super.onKeyDown(keyCode, event);
}

@Override
public void onConfigurationChanged(Configuration newConfig) {
   if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
    {
        mWebView.loadUrl("http://onet.home.pl/");
    }

        else if (getResources().getConfiguration().orientation ==   Configuration.ORIENTATION_LANDSCAPE)
        {
            mWebView.loadUrl("http://onet.home.pl/onet");
    }
   super.onConfigurationChanged(newConfig);
}
}
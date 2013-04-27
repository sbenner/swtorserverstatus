package com.swtorserversstatus;

/**
 * Created by IntelliJ IDEA.
 * User: siggi
 * Date: 06/01/12
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.RemoteViews;
import com.swtorserversstatus.model.Server;
import com.swtorserversstatus.ui.ImageAdapter;
import com.swtorserversstatus.utils.Utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;



public class SWTORStatusWidget extends AppWidgetProvider {
     LayoutInflater  inflater;
    int[] appWidgetIds;
    AppWidgetManager appWidgetManager;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        this.appWidgetIds=appWidgetIds;
        this.appWidgetManager=appWidgetManager;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTime(context, appWidgetManager), 1, 30000);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
            final int appWidgetId = intent.getExtras().getInt(
                                AppWidgetManager.EXTRA_APPWIDGET_ID,
                                AppWidgetManager.INVALID_APPWIDGET_ID);

            if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                this.onDeleted(context, new int[]{appWidgetId});
            }
        } else {
            super.onReceive(context, intent);
        }
    }

    private class MyTime extends TimerTask {
        RemoteViews remoteViews;
        AppWidgetManager appWidgetManager;
        ComponentName thisWidget;
        Context context;
        public MyTime(Context context, AppWidgetManager appWidgetManager) {
            this.context = context;
            this.appWidgetManager = appWidgetManager;
            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_message);
            thisWidget = new ComponentName(context, SWTORStatusWidget.class);
        }

        @Override
        public void run() {
            remoteViews.removeAllViews(R.id.widget);
             Date date = new Date();
            DateFormat format = SimpleDateFormat.getTimeInstance(
            SimpleDateFormat.LONG, Locale.getDefault());
            System.out.println("BLECH!!!!!!!!!!!!");
            InputStream is = null;
            ImageAdapter myServers =null;
            HashSet<Server> mylist =null;
            try {
                is = new ByteArrayInputStream(Utils.fetch("http://www.swtor.com/server-status", null).toByteArray());
            } catch (Exception cte) {
            }

            try {
                String l = Utils.readInputStream(is);
             HashSet<Server> list= Utils.getServers();
             mylist=   Utils.loadMyServerList(context, list);
            } catch (Exception e) {
                e.printStackTrace();
            }
             RemoteViews newView =null;
            for(Server srv:mylist){
              newView = new RemoteViews(context.getPackageName(), R.layout.widget_row);
              newView.setTextViewText(R.id.servername,srv.getServerName());
              newView.setTextViewText(R.id.serverstatus,srv.getServerStatus());
              remoteViews.addView(R.id.widget,newView);
            }
            newView = new RemoteViews(context.getPackageName(), R.layout.datetime);
            newView.setTextViewText(R.id.time,"Last Updated At: "+format.format(date));
            remoteViews.addView(R.id.widget,newView);
           appWidgetManager.updateAppWidget(thisWidget, remoteViews);

        }
    }

}
package com.swtorserversstatus.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.swtorserversstatus.model.Server;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 20/08/12
 * Time: 01:47
 * Purpose:
 */
public class ServerListLoader extends AsyncTaskLoader<HashSet<Server>> {
//    final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
//    final PackageManager mPm;

   public static HashSet<Server> servers=null;

    Context context;
     int tab=0;
    public ServerListLoader(Context context,Bundle bundle) {
        super(context);
       this.context=context;

       tab = bundle.getInt("tab");

        // Retrieve the package manager for later use; note we don't
        // use 'context' directly but instead the save global application
        // context returned by getContext().
        //mPm = getContext().getPackageManager();
    }

    /**
     * This is where the bulk of our work is done.  This function is
     * called in a background thread and should generate a new set of
     * data to be published by the loader.
     */
    @Override public HashSet<Server>  loadInBackground() {

        HashSet<Server> servers=null;
        servers = Utils.getServers();

        return servers;
    }

    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override public void deliverResult(HashSet<Server> apps) {

        servers=apps;
        if(tab==0)
                {
                    try {
                        apps =Utils.loadMyServerList(context,apps);
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                if(tab==1){
                    apps = Utils.makeZonedList(apps, true);
                }
                if(tab==2){
                    apps = Utils.makeZonedList(apps, false);
                }



        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (apps != null) {
                onReleaseResources(apps);
            }
        }
        HashSet<Server>  oldApps = apps;


        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            Log.d(ServerListLoader.this.getClass().getName(),String.valueOf(apps.size()));
                         super.deliverResult(apps);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override protected void onStartLoading() {
        if (servers != null) {
            // If we currently have a result available, deliver it
            // immediately.
        Log.d("servers size", String.valueOf(servers.size()));
            deliverResult(servers);
        }
        else
        {
            forceLoad();
        }


    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();

    }

    /**
     * Handles a request to cancel a load.
     */
    @Override public void onCanceled(HashSet<Server>  apps) {
        super.onCanceled(apps);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(apps);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (servers != null) {
            onReleaseResources(servers);
            servers = null;
        }

        // Stop monitoring for changes.

    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(HashSet<Server>  apps) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }
}

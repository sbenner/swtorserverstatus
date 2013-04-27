package com.swtorserversstatus.ui;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.widget.*;
import com.swtorserversstatus.model.Server;
import com.swtorserversstatus.utils.ServerListLoader;
import com.swtorserversstatus.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


import static android.R.string.cancel;


//import org.apache.http.client.HttpClient;


public class AddServerDialog extends AlertDialog.Builder {

    public AddServerDialog(final Activity activity, final HashSet<Server> list, final ViewPager viewPager) {
        super(activity);

       setTitle("Add My Server");

       final List<Server> s = new ArrayList<Server>();
       s.addAll(list);

       Utils.ServerCompare serverCompare = new Utils.ServerCompare();
       Collections.sort(s, serverCompare);

       ArrayAdapter<Server> adapter = new ArrayAdapter<Server>(activity,
                android.R.layout.simple_dropdown_item_1line, s);

        final AutoCompleteTextView textView = new AutoCompleteTextView(activity);
        textView.setAdapter(adapter);

        setView(textView);
        setIcon(android.R.drawable.ic_menu_add);
        setPositiveButton("Add Server", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {

                                            HashSet<Server> myServerList=null;
                                            try{
                                                myServerList = Utils.loadMyServerList(activity, ServerListLoader.servers);

                                                for (Server srv : list) {
                                                if (srv.getServerName().toUpperCase().equals(textView.getText().toString())) {
                                                   myServerList.add(srv);
                                                }
                                            }

                                            }catch (Exception e){e.printStackTrace();}
                                            Utils.writeMyServerListToXml(activity,Utils.makeXml(myServerList));
                             // StatusListFragment.list = myServerList;

                                            viewPager.getAdapter().notifyDataSetChanged();
//                                           lv.setAdapter(myServers);

                              dialog.dismiss();
                        } });

        setNeutralButton(cancel,new DialogInterface.OnClickListener(){
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.dismiss();
             }
         });
         create();



    }

    }




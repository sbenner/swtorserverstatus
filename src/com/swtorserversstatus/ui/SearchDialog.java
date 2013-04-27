
package com.swtorserversstatus.ui;


import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.widget.*;


import com.swtorserversstatus.model.Server;
import com.swtorserversstatus.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static android.R.string.cancel;




public class SearchDialog extends AlertDialog.Builder {

    public SearchDialog(final Activity activity,final ListView list) {
        super(activity);

        if(list.getCount()==0){
             Utils.showToast(activity,"No Servers Were loaded");

             return;
         }
       setTitle("Search Server");

       final List<Server> s = new ArrayList<Server>();

       HashSet<Server> serverHashSet = (HashSet<Server>)
                                        ((ImageAdapter)list.getAdapter()).getDataList();

       s.addAll(serverHashSet);

       Utils.ServerCompare serverCompare = new Utils.ServerCompare();
       Collections.sort(s, serverCompare);

       ArrayAdapter<Server> adapter = new ArrayAdapter<Server>(activity,
                android.R.layout.simple_dropdown_item_1line, s);


        final AutoCompleteTextView textView = new AutoCompleteTextView(activity);
        textView.setAdapter(adapter);


        setView(textView);
        setIcon(android.R.drawable.ic_menu_search);
        setPositiveButton(android.R.string.search_go, new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                               for(Server name: s)
                {
                     if(name.getServerName().equals(textView.getText().toString().toUpperCase()))
                     {
                         list.setSelection(s.indexOf(name));
                     }
                }
                              return;
                        } });
         setNeutralButton(cancel,new DialogInterface.OnClickListener(){
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.dismiss();
             }
         });
         create();



    }


}


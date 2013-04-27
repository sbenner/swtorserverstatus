package com.swtorserversstatus.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.swtorserversstatus.R;



/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 24/12/11
 * Time: 18:39
 * Purpose:
 */
public class AboutDialog extends Dialog {

    public AboutDialog(Context context) {
        super(context,R.style.Theme_Dialog);
        //LayoutInflater  inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout ll = new LinearLayout(context);

        TextView tv = new TextView(context);
        tv.setTextColor(Color.parseColor("#3ccbf5"));

       // this.getWindow().setLayout(280, 300);
        tv.setText("LucasArts, the LucasArts logo, STAR WARS and related properties are trademarks in the United States and/or in other countries of Lucasfilm Ltd. and/or its affiliates. © 2011 Lucasfilm Entertainment Company Ltd. or Lucasfilm Ltd. All rights reserved. BioWare and the BioWare logo are trademarks of EA International (Studio and Publishing) Ltd. EA and the EA logo are trademarks of Electronic Arts Inc. All other trademarks are the property of their respective owners.");
        tv.setPadding(15, 15, 15, 15);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        ll.addView(tv);

        setContentView(ll, new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }
    public boolean onTouchEvent (MotionEvent event)
    {
        this.dismiss();
        return true;

    }

}

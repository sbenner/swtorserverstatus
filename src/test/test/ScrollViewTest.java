package test.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.HashSet;

public class ScrollViewTest extends Activity {

    ScrollView sv;
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        final LinearLayout ll = new LinearLayout(this);
        final LinearLayout l1 = new LinearLayout(this);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        ll.setLayoutParams(lp1);
        ll.setOrientation(1);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        l1.setLayoutParams(lp2);
        l1.setOrientation(1);

        Button b = new Button(this);
        b.setText("strike items");
        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                   TextView tv = new TextView(ScrollViewTest.this);
                   tv.setTextSize(24);
                   createDialog(tv,l1,sv).show();
                System.out.println(l1.getChildCount());
            }
        });

        ll.addView(b);


        sv = new ScrollView(this);
        ll.addView(sv,lp1);
        sv.addView(l1);


        for(int i=0;i<100;i++)
        {
            TextView tv =new TextView(this);
            tv.setTextSize(24);

            tv.setText("item"+i);
            l1.addView(tv);
        }



        final HashSet<String> arr = new HashSet<String>();

        setContentView(ll);
    }

    public AlertDialog createDialog(final TextView tv, final LinearLayout ll, final ScrollView sv){
        final EditText et = new EditText(this);
       return new AlertDialog.Builder(this).setView(et).setPositiveButton("OK",new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialogInterface, int i) {
               tv.setText(et.getText());
               ll.addView(tv);
               sv.post(new Runnable() {
                   public void run() {
                    sv.fullScroll(View.FOCUS_DOWN);
                   }
               });
           }
       }).create();


    }

}

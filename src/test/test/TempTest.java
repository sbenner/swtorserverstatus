package test.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;

import java.util.Formatter;

public class TempTest extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(1);
        Button b = new Button(this);
        b.setText("get temperature");
        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                   getCelciusOrFarenheit();
            }
        });
        ll.addView(b);
        setContentView(ll);
    }

    public AlertDialog getCelciusOrFarenheit(){
    AlertDialog.Builder cof = new AlertDialog.Builder(this);
    cof.setTitle("Select Temperature Conversion");
    String[] types = {"To Celcius", "To Farenheit"};
    cof.setItems(types, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            switch(which){
            case 0:
                createTempDialog(true).show();
                break;
            case 1:
                createTempDialog(false).show();
                break;
            }
        }
    });
    return cof.show();
    }
    public AlertDialog createTempDialog(final boolean cf){
       final EditText et = new EditText(this);
       et.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED);
       final Formatter f = new Formatter();
       return new AlertDialog.Builder(this).setView(et).setTitle(cf?"Enter Farenheit temperature":"Enter Celcius temperature").setPositiveButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialogInterface, int i) {
               double answer = 0;
               if (cf) {
                   answer = (Double.parseDouble(et.getText().length() > 1 ? et.getText().toString() : "0") - 32) * 5 / 9;
               } else {
                   answer = Double.parseDouble(et.getText().length() > 1 ? et.getText().toString() : "0")*9/5 + 32;
               }
               new AlertDialog.Builder(TempTest.this).setMessage(f.format("%.2f", answer).toString()).setTitle(cf?"Celcius":"Farenheit")
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       })
                       .create().show();
           }
       }).create();
    }
}

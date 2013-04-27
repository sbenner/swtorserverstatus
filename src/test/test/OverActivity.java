package test.test;

/**
 * Created by IntelliJ IDEA.
 * User: siggi
 * Date: 30/01/12
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;


public class OverActivity extends Activity  {
private TextView multiplydisplay;
private TextView multiply2display;
private Button btntocalculator;


private EditText number1;
private EditText number2;

private double number1calc = 0;
private double number2calc = 0;
private double multiply     = 0;
private double multiply2     = 0;

/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    LinearLayout rl = new LinearLayout (this);

    rl.setOrientation(1);

    multiplydisplay = new TextView(this);
    multiply2display    =  new TextView(this);
    btntocalculator = new Button(this);

    btntocalculator.setText("calc");
    rl.addView(multiplydisplay);
    rl.addView(multiply2display);
    rl.addView(btntocalculator);


    btntocalculator.setOnClickListener(new Button.OnClickListener() {
        public void onClick (View v)    {

            final Dialog dialogcalc = new Dialog(OverActivity.this);
            //dialogcalc.setContentView(R.layout.main);
            LinearLayout rl = new LinearLayout (OverActivity.this);

    rl.setOrientation(1);
            number1 = new EditText(OverActivity.this);
            number2 = new EditText(OverActivity.this);
            number1.setWidth(50);
            number2.setWidth(50);
            rl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            rl.addView(number1);
            rl.addView(number2);


            dialogcalc.setTitle("calc");
            dialogcalc.setCancelable(true);

            Button buttoncalculate = new Button(OverActivity.this);
            buttoncalculate.setText("do calc");
            rl.addView(buttoncalculate);
            dialogcalc.setContentView(rl);
            buttoncalculate.setOnClickListener(new OnClickListener() {
                 public void onClick(View v) {
                           try{
                        if(number1.getText().toString().trim().length() < 1 ){number1calc=0;}
                        else{number1calc=Double.parseDouble(number1.getText().toString());}

                        if(number2.getText().toString().trim().length() < 1 ){number2calc=0;}
                        else{number2calc=Double.parseDouble(number2.getText().toString());}
                           }catch (NumberFormatException nfe)
                           {
                               nfe.printStackTrace();
                           }


                        //calculate
                        multiply=(number1calc*number2calc)*0.222;
                        multiply2=(number1calc*number2calc)*0.888;

                        //display
                        multiplydisplay.setText(Double.toString(multiply));
                        multiply2display.setText(Double.toString(multiply2));

                    dialogcalc.dismiss();
                }
            });

            dialogcalc.show();

        }
    });


  setContentView(rl);
}
}

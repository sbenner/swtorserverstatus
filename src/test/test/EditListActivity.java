package test.test;

/**
 * Created by IntelliJ IDEA.
 * User: siggi
 * Date: 24/01/12
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class EditListActivity extends Activity {

 //   public EditListActivity(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(lp1);
        ll.setOrientation(1);
//        ll.setFocusable(true);
//        ll.setFocusableInTouchMode(true);
        final ListView lv = new ListView(this);
        MyListAdapter adapter = new MyListAdapter(this, null);
        lv.setAdapter(adapter);
        ll.addView(lv);
        final Button b = new Button(this);
        b.setText("calc");
       b.setId(12341241);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 int count = lv.getChildCount();
     int sum=0;
        for (int i=0; i<count; i++) {



            EditText t = (EditText)((LinearLayout)lv.getChildAt(i)).getChildAt(1);
            sum+=Integer.parseInt(t.getText().toString());


        }
                System.out.println(sum);
               Toast.makeText(EditListActivity.this,String.valueOf(sum),5).show();
               // b.setText(sum);
            }});
               ll.addView(b);
               setContentView(ll);
            }

            @Override
            protected void onDestroy() {
                super.onDestroy();
            }


            //class MyListAdapter extends BaseAdapter implements View.OnLongClickListener, View.OnFocusChangeListener {
    class MyListAdapter extends BaseAdapter {
                private final String TAG = ListActivity.class.getName();
                List<String> stringsarray;
                private Context context;

                public MyListAdapter(Context context, List<String> phonebook) {
                    this.stringsarray = new ArrayList<String>();

                    this.stringsarray.add("1");
                    this.stringsarray.add("2");
                    this.stringsarray.add("3");
                    Log.d(TAG, "created list adapter");
                    this.setContext(context);
                }

                public int getCount() {
                    return this.stringsarray.size();
                }

                public String getItem(int position) {
                    return this.stringsarray.get(position);
                }

                public long getItemId(int position) {
                    return position;
                }


                public View getView(final int position, View convertView, ViewGroup parent) {
                    LinearLayout rl = new LinearLayout(getContext());

                    rl.setOrientation(0);
                    TextView tv = new TextView(getContext());
                    tv.setText("SWTOR");
                    rl.addView(tv);
                    EditText text = new EditText(getContext());

                  //  text.setOnLongClickListener(this);
                  //  text.setOnFocusChangeListener(this);
                    text.setText(this.stringsarray.get(position));

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    lp.setMargins(5,0,55,0);
                    //  text.setFocusable(true);
                    rl.addView(text, lp);
                    TextView tv1 = new TextView(getContext());

                    LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.FILL_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);


                    tv1.setText("$10");
                     lp1.setMargins(5,0,5,0);
                    rl.addView(tv1,lp1);

                    return rl;
                }

//                public void onFocusChange(View view, boolean b) {
//                    EditText et = (EditText) view;
//                    et.setFilters(new InputFilter[]{
//                            new InputFilter() {
//                                public CharSequence filter(CharSequence src, int start,
//                                                           int end, Spanned dst, int dstart, int dend) {
//                                    return src.length() < 1 ? dst.subSequence(dstart, dend) : "";
//                                }
//                            }
//                    });
//                    if (b) {
//
//                        Log.d(TAG, "hasFocus true called " + et.getText());
//                        et.setText("focused");
//
//                        //et.setSelection(et.length());
//
//                    } else {
//
//                        Log.d(TAG, "hasFocus false called " + et.getText());
//
//                        et.setText("unfocused");
//                        //TODO Save to DB
//                    }
//                }
//
//                public boolean onLongClick(View view) {
//                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                    final EditText et = (EditText) view;
//                    et.setFilters(new InputFilter[]{
//                            new InputFilter() {
//                                public CharSequence filter(CharSequence src, int start,
//                                                           int end, Spanned dst, int dstart, int dend) {
//                                    return src;
//                                }
//                            }
//                    });
//                    et.setText("editable");
//                    Log.d(TAG, "on long click called " + et.getText());
//                    et.requestFocus();
//                    et.setFocusable(true);
//                    et.setFocusableInTouchMode(true);
//
//                    return true;
//                }

                public Context getContext() {
                    return context;
                }

                public void setContext(Context context) {
                    this.context = context;
                }
            }

        }

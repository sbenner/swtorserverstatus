package test.test;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.HashSet;

public class ListActivityArrayAdapter extends Activity {

    ListView lv;
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        ll.setLayoutParams(lp1);
        ll.setOrientation(1);
        lv = new ListView(this);

        final String [] data = new String[]{"test","test1","test2","test3","test4","test5","test6"};
        final ArrayAdapter ad = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice,data){
            public View getView(int position, View convertView, ViewGroup parent){
                    TextView tv =new TextView(getContext());
                          tv.setText(data[position]);
                tv.setTextSize(24);
                return tv;
            }
        };
        lv.setAdapter(ad);
        final HashSet<String> arr = new HashSet<String>();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = ((TextView) view).getText().toString();
                arr.add(text);
                System.out.println("added: "+text);
            }
        });

        Button b = new Button(this);
        b.setText("strike items");
        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                for(String txt:arr)
                {
                   ((TextView)(lv.getChildAt(ad.getPosition(txt)))).
                   setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                }
            }
        });

        ll.addView(lv);
        ll.addView(b);
        setContentView(ll);
    }
}

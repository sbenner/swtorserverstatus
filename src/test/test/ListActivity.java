package test.test;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.swtorserversstatus.R;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {


    ListView lv;

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
        MyXMLHandler myXMLHandler = readXML();

        List<Phonebook> listOfPhonebook = myXMLHandler.getPhonebook();
        //adapter = new TaskListAdapter(this);
        MyListAdapter adapter = new MyListAdapter(this, listOfPhonebook);
        lv.setAdapter(adapter);
        ll.addView(lv);

        setContentView(ll);

    }

    private MyXMLHandler readXML() {
        MyXMLHandler myXMLHandler = null;
        try {

            /** Handling XML */
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            /** Send URL to parse XML Tags */
            InputSource is = new InputSource(getResources().openRawResource(R.raw.a));

            /** Create handler to handle XML Tags ( extends DefaultHandler ) */
            myXMLHandler = new MyXMLHandler();
            xr.setContentHandler(myXMLHandler);
            xr.parse(new InputSource(is.getByteStream()));

        } catch (Exception e) {
            System.out.println("XML Parsing Exception = " + e);
        }
        return myXMLHandler;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    class MyListAdapter extends BaseAdapter {

        private static final String TAG = "TaskListAdapter";


        List<test.test.Phonebook> phonebook;
        Context context;

        public MyListAdapter(Context context, List<test.test.Phonebook> phonebook) {
            this.phonebook = phonebook;
            Log.d(TAG, "created new task list adapter");
            this.context = context;
        }

        public int getCount() {
            return phonebook.size();
        }

        public test.test.Phonebook getItem(int position) {
            return phonebook.get(position);
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;

        if (convertView == null) {

            System.out.println("convert view is null");
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.test, null);
            ListView.LayoutParams listViewParams = new ListView.LayoutParams(
                    ListView.LayoutParams.FILL_PARENT, ListView.LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(listViewParams);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.titleofAsset);
            holder.header= (TextView) convertView.findViewById(R.id.headerText);
            convertView.setTag(holder);

    }    else {
        holder = (ViewHolder) convertView.getTag();
        }
            holder.header.setText(getItem(position).getName());
            holder.title.setText(getItem(position).getPhone());

            if(getItem(position).getEmail().equalsIgnoreCase("y")){
            ((TextView) holder.title ).setVisibility(View.GONE);
            ((TextView) holder.header).setVisibility(View.VISIBLE);
            }
            else{
            ((TextView) holder.title ).setVisibility(View.VISIBLE);
            ((TextView) holder.header).setVisibility(View.GONE);
            }

            return convertView;
        }

    }
      static class ViewHolder {
    ImageView icon;
    TextView title;
    TextView dt;
    TextView C;
    TextView header;
   }

//            RelativeLayout rl = new RelativeLayout(context);
//            TextView textPid = new TextView(context);
//            textPid.setId(222222);
//            textPid.setText(getItem(position).getName());
//
//            TextView textName = new TextView(context);
//            textName.setId(333333);
//            textName.setText(getItem(position).getEmail());
//
//
//            TextView textEmail = new TextView(context);
//            textEmail.setId(444444);
//            textEmail.setText(getItem(position).getEmail());
//
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
//
//            lp.setMargins(1, 0, 0, 0);
//            rl.addView(textName, lp);
//
//            RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
//            lp1.addRule(RelativeLayout.RIGHT_OF, textName.getId());
//            lp1.setMargins(1, 0, 0, 0);
//            rl.addView(textPid, lp1);
//
//
//            RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
//            lp2.addRule(RelativeLayout.RIGHT_OF, textPid.getId());
//            lp2.setMargins(1, 0, 0, 0);
//            rl.addView(textEmail, lp2);

//
//            return rl;
//        }
//
//    }

    class MyXMLHandler extends DefaultHandler {

        Boolean currentElement = false;
        String currentValue = null;
        public List<Phonebook> phonebook = new ArrayList<Phonebook>();
        int entryCount = 0;

        public List<Phonebook> getPhonebook() {
            return phonebook;
        }

        public void setPhonebook(List<Phonebook> p) {
            phonebook = p;
        }


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            currentElement = true;

            if (localName.equals("entry")) {
                /** Start */
                Phonebook pb = new Phonebook();
                getPhonebook().add(pb);

            }
        }

        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            /** set value */
            if (localName.equalsIgnoreCase("name"))
                (phonebook.get(entryCount)).setName(currentValue);
            if (localName.equalsIgnoreCase("phone"))
                (phonebook.get(entryCount)).setPhone(currentValue);
            if (localName.equalsIgnoreCase("mail"))
                (phonebook.get(entryCount)).setEmail(currentValue);

            if (localName.equalsIgnoreCase("entry")) entryCount++;
        }


        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {

            if (currentElement) {
                currentValue = new String(ch, start, length);
                currentElement = false;
            }

        }

    }

}

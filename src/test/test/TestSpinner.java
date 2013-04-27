package test.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.swtorserversstatus.R;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: siggi
 * Date: 01/02/12
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
public class TestSpinner extends Activity {

    private ArrayAdapter<CharSequence> adapter1;
    private Spinner spinner1;
    private ArrayAdapter<CharSequence> adapter2;
    private Spinner spinner2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        adapter1 = ArrayAdapter.createFromResource(
                this, R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        MyOnItemSelectedListener listener = new MyOnItemSelectedListener();

        spinner1.setOnItemSelectedListener(listener);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(
                this, R.array.planets_array1, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        SubMenu faqMenu = menu.addSubMenu(0, 100, 1, "FAQ").setIcon(android.R.drawable.ic_menu_rotate);
        faqMenu.add(1, 101, 0, "set other planents");
        SubMenu langMenu = menu.addSubMenu(0, 200, 2, "LANGAUGE").setIcon(android.R.drawable.ic_menu_rotate);
        langMenu.add(1, 201, 0, "Language1");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 101:
                AlertDialog.Builder textbox = new AlertDialog.Builder(TestSpinner.this);
                textbox.setMessage("You've set new planets");
                textbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int i) {
                        dlg.dismiss();
                    }
                });
                textbox.show();
                try {
                    spinner1.setAdapter(adapter2);
                    spinner2.setAdapter(adapter2);
                    spinner1.setSelection(1);
                    spinner2.setSelection(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 Locale locale = new Locale("en");
                          Locale.setDefault(locale);
                          Configuration config = getBaseContext().getResources().getConfiguration();
                          config.locale = locale;
                    //      getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                      getBaseContext().getResources().updateConfiguration(config, null);
                          Toast.makeText(this, "Locale in English!", 1).show();

                  refresh();

                return true;
            case 201:
                     locale = new Locale("nl");
                          Locale.setDefault(locale);
                          config = getBaseContext().getResources().getConfiguration();
                          config.locale = locale;
                    //      getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                      getBaseContext().getResources().updateConfiguration(config, null);
                          Toast.makeText(this, "Locale in Nederlands !", 1).show();

                try {
                    spinner1.setAdapter(adapter1);
                    spinner2.setAdapter(adapter1);
                    spinner1.setSelection(1);
                    spinner2.setSelection(2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refresh();
                return true;
        }
        return false;
    }

         private void refresh() {
            finish();
            Intent myIntent = new Intent(TestSpinner.this, TestSpinner.class);
            startActivity(myIntent);
        }

    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            Toast.makeText(parent.getContext(), "The planet is " +
                    parent.getItemAtPosition(pos).toString(), 1).show();
        }

        public void onNothingSelected(AdapterView parent) {
        }
    }
}

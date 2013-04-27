package test.test;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 01/03/12
 * Time: 20:53
 * Purpose:
 */
public class CreateNewForm extends Activity implements AdapterView.OnItemSelectedListener {


    EditText CaseName, CaseClothes, CaseMoreInfo, CaseAge;

    //For Browsering Picture
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);
        CaseAge = new EditText(this);
        CaseAge.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        CaseAge.setFilters(new InputFilter[]{new InputFilterMinMax(1, 100)});
        CaseAge.setInputType(InputType.TYPE_CLASS_NUMBER);
        ll.addView(CaseAge);
        setContentView(ll);
//To Call initializer Function
//        CaseName = (EditText) findViewById(R.id.caseNm);
//        GenderSelection = (RadioGroup) findViewById(R.id.radioGroup1);

//        tesst = (TextView) findViewById(R.id.textView8);
//        CaseDurationH = (Spinner) findViewById(R.id.Shr);
//        CaseDurationM = (Spinner) findViewById(R.id.Smin);
//        CaseClothes = (EditText) findViewById(R.id.caseClothes);
//        CaseMoreInfo = (EditText) findViewById(R.id.caseMrInfo);
//        CasePic = (ImageView) findViewById(R.id.casepic);
//        Browse = (Button) findViewById(R.id.browseCasePic);

//        final MyCase case1 = new MyCase();
//        case1.setName(CaseName.getText().toString());
//
//        GenderSelection.clearCheck();
//        GenderSelection.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.radio0:
//                        case1.setGender("Male");
//                        break;
//                    case R.id.radio1:
//                        case1.setGender("Female");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

//        Browse.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                // in onCreate or any event where your want the user to
//                // select a file
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,
//                        "Select Picture"), SELECT_PICTURE);
//            }
//        });


//String age = CaseAge.getText().toString(); //not used anywhere
// CaseAge.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "100")}); //this one can be used too
        //4-Duration Time
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                this, R.array.feedbacktypelist, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        CaseDurationH.setAdapter(adapter);
//        case1.setClothes(CaseClothes.getText().toString());
//        case1.setMoreInfo(CaseMoreInfo.getText().toString());
//        Next = (Button) findViewById(R.id.Next2);
//        Next.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.Next2:
//                        try {
//                            Intent k = new Intent(CreateNewForm.this, CreateNewForm_2.class);
//                            startActivity(k);
//                        } catch (Exception e) {
//                        }
//                        break;
//                }
//            }
//        });

//        //Spinner
//        CaseDurationH.setOnItemSelectedListener(new OnItemSelectedListener() {
//            int i = CaseDurationH.getSelectedItemPosition();
//
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                int i = CaseDurationH.getSelectedItemPosition();
//
//                if (i == 2) {
//                    CaseDurationM.setEnabled(false);
//                }
//                String str = parent.getSelectedItem().toString();
//
//                if (str.equals("hr0")) {
//                    CaseDurationM.setEnabled(true);
//                }
//
//                if (str.equals("hr1")) {
//                    CaseDurationM.setEnabled(true);
//                }
//                if (str.equals("hr2")) {
//                    CaseDurationM.setEnabled(false);
//                }
//            }
//
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                selectedImagePath = getPath(selectedImageUri);
//            }
//        }
//    }

    //For Uploading Picture
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        // TODO Auto-generated method stub

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }


        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }

}
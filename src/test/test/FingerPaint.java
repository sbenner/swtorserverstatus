package test.test;


import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.view.*;
import test.test.ColorPickerDialog;
import test.test.PictureLayout;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FingerPaint extends Activity
        implements ColorPickerDialog.OnColorChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);   // RED COLOR OF PAINT
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

        mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 },
                                       0.4f, 6, 3.5f);

        mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }


    @Override
    public void setContentView(View view) {
        if (false) { // set to true to test Picture
            ViewGroup vg = new PictureLayout(this);
            vg.addView(view);
            view = vg;
        }

        super.setContentView(view);
    }

    private Paint       mPaint;
    private MaskFilter  mEmboss;
    private MaskFilter  mBlur;

    public void colorChanged(int color) {
        mPaint.setColor(color);
    }

     /*
    *  menu
    *
    * */

    private static final int COLOR_MENU_ID = Menu.FIRST;
    private static final int EMBOSS_MENU_ID = Menu.FIRST + 1;
    private static final int BLUR_MENU_ID = Menu.FIRST + 2;
    private static final int ERASE_MENU_ID = Menu.FIRST + 3;
    private static final int SRCATOP_MENU_ID = Menu.FIRST + 4;
    private static final int SAVE_MENU_ID = Menu.FIRST + 5;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0, COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
        menu.add(0, EMBOSS_MENU_ID, 0, "Emboss").setShortcut('4', 's');
        menu.add(0, BLUR_MENU_ID, 0, "Blur").setShortcut('5', 'z');
        menu.add(0, ERASE_MENU_ID, 0, "Erase").setShortcut('5', 'z');
        menu.add(0, SRCATOP_MENU_ID, 0, "SrcATop").setShortcut('5', 'z');
        menu.add(0, SAVE_MENU_ID, 0, "Save").setShortcut('5', 'z');

        /****   Is this the mechanism to extend with filter effects?
        Intent intent = new Intent(null, getIntent().getData());
        intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
        menu.addIntentOptions(
                              Menu.ALTERNATIVE, 0,
                              new ComponentName(this, NotesList.class),
                              null, intent, 0, null);
        *****/
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xFF);

        switch (item.getItemId()) {
            case COLOR_MENU_ID:
                new ColorPickerDialog(this, this, mPaint.getColor()).show();
                return true;
            case EMBOSS_MENU_ID:
                if (mPaint.getMaskFilter() != mEmboss) {
                    mPaint.setMaskFilter(mEmboss);
                } else {
                    mPaint.setMaskFilter(null);
                }
                return true;
            case BLUR_MENU_ID:
                if (mPaint.getMaskFilter() != mBlur) {
                    mPaint.setMaskFilter(mBlur);
                } else {
                    mPaint.setMaskFilter(null);
                }
                return true;
            case ERASE_MENU_ID:
                mPaint.setXfermode(new PorterDuffXfermode(
                                                        PorterDuff.Mode.CLEAR));
                return true;
            case SRCATOP_MENU_ID:
                mPaint.setXfermode(new PorterDuffXfermode(
                                                    PorterDuff.Mode.SRC_ATOP));
                mPaint.setAlpha(0x80);
                return true;
            case SAVE_MENU_ID:
                try {
                    ViewGroup viewGroup = (ViewGroup)getWindow().getDecorView();
                    MyView myView = (MyView)viewGroup.findViewById(121212);//important
                    myView.getDrawingCache().
                      compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File("/sdcard/test.jpg")));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

     /*
     *
     *   MyView class with paints
     * */

    class MyView extends View {


           private Bitmap  mBitmap;
           private Canvas  mCanvas;
           private Path    mPath;
           private Paint   mBitmapPaint;

           public MyView(Context c) {
               super(c);
               setDrawingCacheEnabled(true);
               Display display = getWindowManager().getDefaultDisplay();

               int width = display.getWidth();
               int height = display.getHeight();
               mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
               mCanvas = new Canvas(mBitmap);
               mPath = new Path();
               mBitmapPaint = new Paint(Paint.DITHER_FLAG);
               this.setId(121212);    //important to get the object with drawing
           }

           @Override
           protected void onSizeChanged(int w, int h, int oldw, int oldh) {
               super.onSizeChanged(w, h, oldw, oldh);
           }

           @Override
           protected void onDraw(Canvas canvas) {
               canvas.drawColor(0xFFAAAAAA);    // gray background color

               canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

               canvas.drawPath(mPath, mPaint);
           }

           private float mX, mY;
           private static final float TOUCH_TOLERANCE = 4;

           private void touch_start(float x, float y) {
               mPath.reset();
               mPath.moveTo(x, y);
               mX = x;
               mY = y;
           }
           private void touch_move(float x, float y) {
               float dx = Math.abs(x - mX);
               float dy = Math.abs(y - mY);
               if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                   mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                   mX = x;
                   mY = y;
               }
           }
           private void touch_up() {
               mPath.lineTo(mX, mY);
               // commit the path to our offscreen
               mCanvas.drawPath(mPath, mPaint);
               // kill this so we don't double draw
               mPath.reset();
           }

           @Override
           public boolean onTouchEvent(MotionEvent event) {
               float x = event.getX();
               float y = event.getY();

               switch (event.getAction()) {
                   case MotionEvent.ACTION_DOWN:
                       touch_start(x, y);
                       invalidate();
                       break;
                   case MotionEvent.ACTION_MOVE:
                       touch_move(x, y);
                       invalidate();
                       break;
                   case MotionEvent.ACTION_UP:
                       touch_up();
                       invalidate();
                       break;
               }
               return true;
           }
       }




}

package test.test;

/**
 * User: siggi
 * Date: 14.08.12
 * Time: 12:54
 * To change this template use File | Settings | File Templates.
 */
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TempSensorTest extends Activity implements SensorEventListener {
    private TextView temperaturelabel;
    private SensorManager sensormanager;
    private Sensor temperature;

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    LinearLayout ll = new LinearLayout(this);
    TextView temperaturelabel = new TextView(this);

    sensormanager = (SensorManager)getSystemService(SENSOR_SERVICE);

    temperature= sensormanager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);


   temperaturelabel.setText("temp: "+0);
    ll.addView(temperaturelabel);

    setContentView(ll);
 }

 protected void onResume() {
     super.onResume();

     sensormanager.registerListener(this, sensormanager.getDefaultSensor(Sensor.TYPE_TEMPERATURE), SensorManager.SENSOR_DELAY_FASTEST);
 }

 protected void onPause() {
     super.onPause();
     sensormanager.unregisterListener(this);
 }

 public void onAccuracyChanged(Sensor sensor, int accuracy) {}

 public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_TEMPERATURE) return;

        temperaturelabel.setText(""+temperature.getPower());
    }
 }
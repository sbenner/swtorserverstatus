package test.test;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 03/03/12
 * Time: 22:29
 * Purpose:
 */
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.util.Log;



public class WeatherAppActivity extends Activity{
/** Called when the activity is first created. */
 //private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
 //private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
static final String tag = "Main"; // for Log

protected LocationManager locationManager;
protected LocationListener locationListener;
protected Button findButton;

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LinearLayout ll = new LinearLayout(this);
    findButton = new Button(this);
    findButton.setText("findMeh");
    ll.addView(findButton);
    setContentView(ll);
     locationListener  = new MyLocationListener();
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER, 0, 0, locationListener
            );



    findButton.setOnClickListener(new OnClickListener() {

                    public void onClick(View v) {
                        showCurrentLocation();
                    }
    });
}

    protected void showCurrentLocation() {

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (location != null) {
                    String message = String.format(
                            "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                            location.getLongitude(), location.getLatitude()
                    );
                    Toast.makeText(WeatherAppActivity.this, message,
                            Toast.LENGTH_LONG).show();

                }

    }

      class MyLocationListener implements LocationListener {

                        public void onLocationChanged(Location location) {
                             String message = String.format(
                                 "New Location \n Longitude: %1$s \n Latitude: %2$s",
                             location.getLongitude(), location.getLatitude()
                              );
                              Toast.makeText(WeatherAppActivity.this, message, Toast.LENGTH_LONG).show();
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {
                            /* This is called when the GPS status alters */
                            switch (status) {
                            case LocationProvider.OUT_OF_SERVICE:
                                Log.v(tag, "Status Changed: Out of Service");
                                Toast.makeText(WeatherAppActivity.this, "Status Changed: Out of Service",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                                Log.v(tag, "Status Changed: Temporarily Unavailable");
                                Toast.makeText(WeatherAppActivity.this, "Status Changed: Temporarily Unavailable",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case LocationProvider.AVAILABLE:
                                Log.v(tag, "Status Changed: Available");
                                Toast.makeText(WeatherAppActivity.this, "Status Changed: Available",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                        public void onProviderDisabled(String provider) {
                            Toast.makeText(WeatherAppActivity.this,
                                  "Provider disabled by the user. GPS turned off",
                                      Toast.LENGTH_LONG).show();
                            }

                        public void onProviderEnabled(String provider) {
                            Toast.makeText(WeatherAppActivity.this,
                                   "Provider enabled by the user. GPS turned on",
                                       Toast.LENGTH_LONG).show();
                          }


                }

}

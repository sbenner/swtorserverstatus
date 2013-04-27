/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 08/02/12
 * Time: 17:43
 * Purpose:
 */
/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 08/02/12
 * Time: 17:43
 * Purpose:
 */
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MyFragmentActivity extends FragmentActivity {

    /**
     * hosts a Fragment, and the Fragment will inflate a layout to show
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragMgr;
        FragmentTransaction fragTrans;

        fragMgr = getSupportFragmentManager();

    }
}
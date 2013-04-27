package com.swtorserversstatus;

import android.os.Message;
import android.widget.ListView;

/**
 * Created by IntelliJ IDEA.
 * User: Sergey Benner
 * Date: 18/02/12
 * Time: 19:37
 * Purpose:
 */
public interface ServerStatus {
    public ListView getServerList(String which);
    public void processDownload(String message);
    public void processMessage(Message msg);

}

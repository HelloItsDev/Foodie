package com.app.foodporn.fp.Model;

/**
 * Created by groupe-efrei on 08/04/16.
 */
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

@SuppressLint("ParcelCreator")
public class MyResultReceive extends ResultReceiver {

    private final static String TAG = "MyResultReceive";
    public final static int API_START = 1;
    public final static int API_END = 2;
    public final static int API_SUCCESS = 3;
    public final static int API_FAIL = 4;
    private Receive receive = null;

    public interface Receive {

        public void onReceiveResult(int resultCode, Bundle resultData);
    }

    public MyResultReceive(Handler handler) {
        super(handler);
    }

    public void setReceive(Receive receive) {
        this.receive = receive;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (receive != null) {
            receive.onReceiveResult(resultCode, resultData);
        }
    }
}

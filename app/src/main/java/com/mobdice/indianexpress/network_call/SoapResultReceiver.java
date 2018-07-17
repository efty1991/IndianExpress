package com.mobdice.indianexpress.network_call;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;


public class SoapResultReceiver extends ResultReceiver {
    public static final String ERROR = "ERROR";
    public static final String DATA = "DATA";
    OwnVolleyResponse ownVolleyResponse;

    public SoapResultReceiver(Handler handler, OwnVolleyResponse ownVolleyResponse) {
        super(handler);
        this.ownVolleyResponse = ownVolleyResponse;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == Activity.RESULT_OK) {
            String data = null;
            if (resultData != null)
                data = resultData.getString(DATA, null);
            ownVolleyResponse.response(true, data, null);
        } else {
            String error = null;
            if (resultData != null)
                error = resultData.getString(ERROR, null);
            ownVolleyResponse.response(false, null, error);
        }


    }
}

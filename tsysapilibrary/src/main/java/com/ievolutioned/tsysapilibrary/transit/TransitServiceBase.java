package com.ievolutioned.tsysapilibrary.transit;

import android.os.AsyncTask;

/**
 * Created by Daniel on 08/12/2016.
 */

public abstract class TransitServiceBase {
    private AsyncTask<Void, Void, TransitBase> task;

    public void cancel() {
        if (task != null)
            task.cancel(true);
    }

    public void execute(){
        if(task != null)
            task.execute();
    }

}

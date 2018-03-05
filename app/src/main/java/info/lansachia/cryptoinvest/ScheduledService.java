package info.lansachia.cryptoinvest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 3/5/2018.
 */

public class ScheduledService extends Service {
    //time to check and sync api data
    private Timer mTimer = new Timer();

    //rCurrency object
    private RCurrency mRCurrency = new RCurrency();

//    private Handler mHandler;
//
//    //default interval to query database
//    private final static long DEFAULT_SYNC_INTERVAL = 3*60*1000;
//
//
//    private Runnable mRunnable = new Runnable() {
//        @Override
//        public void run() {
//            syncData();
//            //repeat this method every 3 mins
//            mHandler.postDelayed(mRunnable, DEFAULT_SYNC_INTERVAL);
//        }
//    };

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        //creates the Handler object
//        mHandler = new Handler();
//
//        //executes task as soon as possible
//        mHandler.post(mRunnable);
//
//        return START_STICKY;
//    }
//
//    private synchronized void syncData(){
//
//    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mRCurrency.requestData();
            }
        }, 0, 3*60*1000); //every 3 minutes
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

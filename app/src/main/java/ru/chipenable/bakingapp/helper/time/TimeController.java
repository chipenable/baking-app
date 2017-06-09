package ru.chipenable.bakingapp.helper.time;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import io.reactivex.Observable;

/**
 * Created by Pavel.B on 10.06.2017.
 */

public class TimeController {

    private static final String TAG = TimeController.class.getName();
    private static final String PREF_NAME = "pref_name";
    private static final String UPDATE_DATE = "update_date";
    private static final long UPDATE_INTERVAL_S = 10*60;
    private SharedPreferences pref;

    public TimeController(Context context){
        this.pref = context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
    }

    public Observable<Boolean> isItTimeToUpdate(){
        return Observable.just(pref.getLong(UPDATE_DATE, 0))
                //.doOnNext(date -> Log.d(TAG, "Time: " + Long.toString(getCurDate() - date)))
                .map(date -> getCurDate() - date > UPDATE_INTERVAL_S);
    }

    public void saveTimeOfLastUpdate(){
        pref.edit()
                .putLong(UPDATE_DATE, getCurDate())
                .apply();
    }

    private long getCurDate() {
        long curTime =  System.currentTimeMillis();
        return curTime == 0? 0:curTime/1000;
    }

}

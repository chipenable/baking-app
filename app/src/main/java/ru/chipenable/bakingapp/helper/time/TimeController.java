package ru.chipenable.bakingapp.helper.time;

import android.content.SharedPreferences;

import io.reactivex.Observable;

/**
 * Created by Pavel.B on 10.06.2017.
 */

public class TimeController {

    private static final String TAG = TimeController.class.getName();
    private static final String UPDATE_DATE = "update_date";
    private static final long UPDATE_INTERVAL_S = 10*60;
    private SharedPreferences pref;

    public TimeController(SharedPreferences pref){
        this.pref = pref;
    }

    public Observable<Boolean> isItTimeToUpdate(){
        return Observable.just(pref.getLong(UPDATE_DATE, 0))
                .map(date -> getCurDate() - date > UPDATE_INTERVAL_S);
    }

    public void saveTimeOfLastUpdate(){
        pref.edit()
                .putLong(UPDATE_DATE, getCurDate())
                .apply();
    }

    private long getCurDate() {
        long curTime = System.currentTimeMillis();
        return curTime == 0? 0:curTime/1000;
    }

}

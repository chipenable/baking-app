package ru.chipenable.bakingapp.helper.player;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Pavel.B on 03.08.2017.
 */

@AutoValue
public abstract class VideoPlayerState implements Parcelable {

    public abstract long position();

    public abstract boolean isPlaying();

    public static VideoPlayerState create(long position, boolean isPlaying) {
        return new AutoValue_VideoPlayerState(position, isPlaying);
    }
}

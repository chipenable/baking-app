package ru.chipenable.bakingapp.helper.player;

/**
 * Created by Pavel.B on 05.06.2017.
 */

public interface IVideoPlayer {

    VideoPlayerState getState();

    void restoreState(VideoPlayerState state);

    void release();
}

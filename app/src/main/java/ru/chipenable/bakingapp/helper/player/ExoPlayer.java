package ru.chipenable.bakingapp.helper.player;

import android.content.Context;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import ru.chipenable.bakingapp.R;

import static android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING;

/**
 * Created by Pavel.B on 05.06.2017.
 */

public class ExoPlayer implements IVideoPlayer{

    private SimpleExoPlayer player;

    public static IVideoPlayer getInstance(Context context, SimpleExoPlayerView playerView,
                                           String videoUrl){
        return new ExoPlayer(context, playerView, videoUrl);
    }

    private ExoPlayer(Context context, SimpleExoPlayerView playerView, String videoUrl){

        //create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        //create the player
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);

        //bind the player to the view
        playerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        String appName = context.getString(R.string.app_name);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, appName));
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        Uri videoUri = Uri.parse(videoUrl);
        MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);

        // Prepare the player with the source.
        player.prepare(mediaSource);

    }

    @Override
    public VideoPlayerState getState() {
        long position = player.getCurrentPosition();
        boolean isPlaying = player.getPlayWhenReady();
        return VideoPlayerState.create(position, isPlaying);
    }

    @Override
    public void restoreState(VideoPlayerState state) {
        if (state != null) {
            player.seekTo(state.position());
            player.setPlayWhenReady(state.isPlaying());
        }

    }

    @Override
    public void release() {
        if (player != null){
            player.release();
        }
    }

}

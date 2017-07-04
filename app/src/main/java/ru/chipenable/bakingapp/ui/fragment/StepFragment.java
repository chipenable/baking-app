package ru.chipenable.bakingapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.helper.player.ExoPlayer;
import ru.chipenable.bakingapp.helper.player.IVideoPlayer;
import ru.chipenable.bakingapp.model.data.Step;
import ru.chipenable.bakingapp.presentation.presenter.StepPresenter;
import ru.chipenable.bakingapp.presentation.view.IStepView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends MvpAppCompatFragment implements IStepView {

    @InjectPresenter
    StepPresenter stepPresenter;

    @BindView(R.id.player_view) SimpleExoPlayerView playerView;
    @BindView(R.id.step_description)  TextView stepDescriptionView;

    private final String TAG = getClass().getName();
    private IVideoPlayer videoPlayer;

    @ProvidePresenter
    StepPresenter providePresenter(){
        AppComponent component = ((BakingApp)getActivity().getApplication()).getAppComponent();
        return new StepPresenter(component);
    }

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoPlayer.release();
        videoPlayer = null;
        Log.d(TAG, "onDestroy");
    }

    @OnClick(R.id.prev_but)
    public void toPreviousPart(){
        presenter.toPreviousPart();
    }

    @OnClick(R.id.next_but)
    public void toNextPart(){
        presenter.toNextPart();
    }

    @Override
    public void showStep(Step step) {
        videoPlayer = ExoPlayer.getInstance(getContext(), playerView,
                step.videoURL(), step.thumbnailURL());
        stepDescriptionView.setText(step.description());
    }
}

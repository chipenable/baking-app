package ru.chipenable.bakingapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    StepPresenter presenter;

    @BindView(R.id.empty_view) TextView emptyView;
    @BindView(R.id.video_frame) FrameLayout videoFrameView;
    @BindView(R.id.player_view) SimpleExoPlayerView playerView;
    @BindView(R.id.step_description)  TextView stepDescriptionView;

    private IVideoPlayer videoPlayer;
    private final String TAG = getClass().getName();
    private static final String RECIPE_ID_KEY = "recipe_id";
    private static final String STEP_NUM_KEY = "step_num";

    public static StepFragment newInstance(long recipeId, int step){
        Bundle args = new Bundle();
        args.putLong(RECIPE_ID_KEY, recipeId);
        args.putInt(STEP_NUM_KEY, step);
        StepFragment f = new StepFragment();
        f.setArguments(args);
        return f;
    }

    @ProvidePresenter
    StepPresenter providePresenter(){
        AppComponent component = ((BakingApp)getActivity().getApplication()).getAppComponent();
        return new StepPresenter(component);
    }

    public StepFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && savedInstanceState == null){
            presenter.init(args.getLong(RECIPE_ID_KEY), args.getInt(STEP_NUM_KEY));
        }
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
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseResources();
    }

    @Override
    public void showStep(Step step) {
        String videoUrl = step.videoURL();
        if (videoUrl == null || videoUrl.isEmpty()) {
            playerView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else{
            videoPlayer = ExoPlayer.getInstance(getContext(), playerView,
                    step.videoURL(), step.thumbnailURL());
        }
        stepDescriptionView.setText(step.description());
    }

    @Override
    public void releaseResources(){
        if (videoPlayer != null) {
            videoPlayer.release();
            videoPlayer = null;
        }
    }

}

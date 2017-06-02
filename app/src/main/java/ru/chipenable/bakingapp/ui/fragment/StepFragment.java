package ru.chipenable.bakingapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.model.data.Step;
import ru.chipenable.bakingapp.presentation.presenter.StepPresenter;
import ru.chipenable.bakingapp.presentation.view.IStepView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends MvpAppCompatFragment implements IStepView {

    @InjectPresenter
    StepPresenter stepPresenter;

    @BindView(R.id.video_frame) FrameLayout videoFrame;
    @BindView(R.id.step_description)  TextView stepDescriptionView;

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
    public void showStep(Step step) {
        stepDescriptionView.setText(step.description());
    }
}

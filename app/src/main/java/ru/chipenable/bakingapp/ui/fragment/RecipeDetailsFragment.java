package ru.chipenable.bakingapp.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.model.Recipe;
import ru.chipenable.bakingapp.presentation.presenter.RecipeDetailsPresenter;
import ru.chipenable.bakingapp.presentation.view.IRecipeDetailsView;
import ru.chipenable.bakingapp.ui.other.StepAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends MvpAppCompatFragment implements IRecipeDetailsView {

    @InjectPresenter RecipeDetailsPresenter presenter;

    @BindView(R.id.step_list) RecyclerView stepRecyclerView;

    private StepAdapter stepAdapter;

    @ProvidePresenter
    RecipeDetailsPresenter providePresenter(){
        AppComponent component = ((BakingApp)getActivity().getApplication()).getAppComponent();
        return new RecipeDetailsPresenter(component);
    }

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, view);

        stepAdapter = new StepAdapter();
        stepAdapter.setStepsClickListener(position -> {});
        stepAdapter.setIngredientsClickListener(() -> {});
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepRecyclerView.setAdapter(stepAdapter);

        return view;
    }

    @Override
    public void showDetails(Recipe recipe) {
        stepAdapter.setData(recipe);
    }
}

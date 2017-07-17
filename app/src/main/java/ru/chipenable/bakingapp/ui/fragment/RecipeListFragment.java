package ru.chipenable.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.presentation.presenter.RecipeListPresenter;
import ru.chipenable.bakingapp.presentation.view.IRecipeView;
import ru.chipenable.bakingapp.ui.other.RecipeAdapter;

/**
 * Created by Pavel.B on 12.05.2017.
 */

public class RecipeListFragment extends MvpAppCompatFragment implements IRecipeView {

    @BindView(R.id.recipe_list) RecyclerView recipeRecyclerView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.shield) FrameLayout shield;

    @InjectPresenter
    RecipeListPresenter presenter;

    private RecipeAdapter recipeAdapter;

    @ProvidePresenter
    RecipeListPresenter providePresenter(){
        AppComponent component = ((BakingApp)getActivity().getApplication()).getAppComponent();
        return new RecipeListPresenter(component);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        recipeAdapter = new RecipeAdapter(getContext());
        recipeAdapter.setItemClickListener(position -> presenter.showDetails(position));
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeRecyclerView.setAdapter(recipeAdapter);
        return view;
    }

    @Override
    public void showRecipes(List<Recipe> list) {
        recipeAdapter.setItems(list);
    }

    @Override
    public void showLoading() {
        enableProgressBar(true);
    }

    @Override
    public void hideLoading() {
        enableProgressBar(false);
    }

    private void enableProgressBar(boolean enable){
        int visibility = enable? View.VISIBLE:View.GONE;
        progressBar.setVisibility(visibility);
        shield.setVisibility(visibility);
    }

}

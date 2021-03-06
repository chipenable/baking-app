package ru.chipenable.bakingapp.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    @BindView(R.id.empty_view) TextView emptyListView;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.shield) FrameLayout shield;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefreshView;

    @InjectPresenter
    RecipeListPresenter presenter;

    private RecipeAdapter recipeAdapter;
    private Parcelable recipeListState;
    private static final String RECIPE_LIST_STATE = "recipe_list_state";

    @ProvidePresenter
    RecipeListPresenter providePresenter(){
        AppComponent component = ((BakingApp)getActivity().getApplication()).getAppComponent();
        return new RecipeListPresenter(component);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            recipeListState = savedInstanceState.getParcelable(RECIPE_LIST_STATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        recipeAdapter = new RecipeAdapter(getContext(), emptyListView);
        recipeAdapter.setItemClickListener(position -> presenter.showDetails(position));
        int columns = getResources().getInteger(R.integer.recipe_list_columns);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        recipeRecyclerView.setLayoutManager(layoutManager);
        recipeRecyclerView.setAdapter(recipeAdapter);

        swipeRefreshView.setOnRefreshListener(() -> {
            recipeListState = null;
            presenter.forceUpdateRecipes();
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_LIST_STATE,
                recipeRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    /** view interface */

    @Override
    public void showRecipes(List<Recipe> list) {
        recipeAdapter.setItems(list);
        if (recipeListState != null){
            recipeRecyclerView.getLayoutManager().onRestoreInstanceState(recipeListState);
        }
    }

    @Override
    public void showLoading() {
        enableProgressBar(true);
    }

    @Override
    public void hideLoading() {
        enableProgressBar(false);
    }

    @Override
    public void hideSwipeRefresh() {
        swipeRefreshView.setRefreshing(false);
    }

    @Override
    public void showNetworkError(){
        Toast.makeText(getContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    /** util methods */

    private void enableProgressBar(boolean enable){
        int visibility = enable? View.VISIBLE:View.GONE;
        progressBar.setVisibility(visibility);
        shield.setVisibility(visibility);
    }

}

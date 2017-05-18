package ru.chipenable.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.view.Recipe;
import ru.chipenable.bakingapp.presentation.presenter.RecipePresenter;
import ru.chipenable.bakingapp.presentation.view.IRecipeView;
import ru.chipenable.bakingapp.ui.other.RecipeAdapter;

/**
 * Created by Pavel.B on 12.05.2017.
 */

public class RecipeFragment extends MvpAppCompatFragment implements IRecipeView {

    @BindView(R.id.recipe_list) RecyclerView recipeRecyclerView;
    @InjectPresenter RecipePresenter presenter;

    private RecipeAdapter recipeAdapter;

    @ProvidePresenter
    RecipePresenter providePresenter(){
        return new RecipePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        ButterKnife.bind(this, view);
        recipeAdapter = new RecipeAdapter();
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recipeRecyclerView.setAdapter(recipeAdapter);
        return view;
    }

    @Override
    public void showRecipes(List<Recipe> list) {
        recipeAdapter.setItems(list);
    }
}

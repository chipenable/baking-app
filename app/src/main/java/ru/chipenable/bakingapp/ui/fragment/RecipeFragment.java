package ru.chipenable.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.presentation.presenter.RecipePresenter;
import ru.chipenable.bakingapp.presentation.view.IRecipeView;

/**
 * Created by Pavel.B on 12.05.2017.
 */

public class RecipeFragment extends MvpAppCompatFragment implements IRecipeView {

    @InjectPresenter
    RecipePresenter presenter;

    @ProvidePresenter
    RecipePresenter providePresenter(){
        return new RecipePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        return view;
    }
}

package ru.chipenable.bakingapp.ui.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.presentation.presenter.IngredientsPresenter;
import ru.chipenable.bakingapp.presentation.view.IIngredientsView;
import ru.chipenable.bakingapp.ui.other.IngredientAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends MvpAppCompatFragment implements IIngredientsView {

    @BindView(R.id.ingredient_list_view)
    RecyclerView ingredientListView;

    @InjectPresenter
    IngredientsPresenter presenter;

    private IngredientAdapter ingredientAdapter;
    private Parcelable listState;
    private static final String LIST_STATE = "list_state";
    private final static String RECIPE_ID_KEY = "recipe_id";

    public static IngredientsFragment newInstance(long recipeId) {
        Bundle args = new Bundle();
        args.putLong(RECIPE_ID_KEY, recipeId);
        IngredientsFragment f = new IngredientsFragment();
        f.setArguments(args);
        return f;
    }

    @ProvidePresenter
    IngredientsPresenter providePresenter() {
        AppComponent component = ((BakingApp) getActivity().getApplication()).getAppComponent();
        return new IngredientsPresenter(component);
    }

    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            presenter.init(args.getLong(RECIPE_ID_KEY));
        }

        if (savedInstanceState != null){
            listState = savedInstanceState.getParcelable(LIST_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);
        ingredientAdapter = new IngredientAdapter();
        ingredientListView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        ingredientListView.setAdapter(ingredientAdapter);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_STATE,
                ingredientListView.getLayoutManager().onSaveInstanceState());
    }

    /**
     * view interface methods
     */

    @Override
    public void showIngredients(List<Ingredient> list) {
        ingredientAdapter.setData(list);
        if (listState != null){
            ingredientListView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }

}

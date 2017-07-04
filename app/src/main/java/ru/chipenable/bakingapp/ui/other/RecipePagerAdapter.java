package ru.chipenable.bakingapp.ui.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.chipenable.bakingapp.ui.fragment.IngredientsFragment;
import ru.chipenable.bakingapp.ui.fragment.StepFragment;

/**
 * Created by Pavel.B on 14.06.2017.
 */

public class RecipePagerAdapter extends FragmentPagerAdapter {

    private final long recipeId;
    private final int count;

    public RecipePagerAdapter(FragmentManager fm, long recipeId, int count) {
        super(fm);
        this.recipeId = recipeId;
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return IngredientsFragment.newInstance(recipeId);
        }
        else {
            return StepFragment.newInstance(recipeId, position - 1);
        }
    }

    @Override
    public int getCount() {
        return count;
    }

}

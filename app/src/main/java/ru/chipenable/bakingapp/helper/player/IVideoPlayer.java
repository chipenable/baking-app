package ru.chipenable.bakingapp.helper.player;

import android.os.Bundle;

/**
 * Created by Pavel.B on 05.06.2017.
 */

public interface IVideoPlayer {

    void saveState(Bundle bundle);

    void restoreState(Bundle bundle);


}

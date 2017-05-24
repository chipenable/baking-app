package ru.chipenable.bakingapp.model.navigation;

import java.lang.ref.WeakReference;

/**
 * Created by Pavel.B on 25.05.2017.
 */

public class Router {

    private WeakReference<INavigator> navigator;

    public Router(){
        navigator = new WeakReference<>(command -> {});
    }

    public void attachToNavigator(INavigator navigator){
        this.navigator = new WeakReference<>(navigator);
    }

    public void putCommand(Command command){
        if (navigator.get() != null){
            navigator.get().handleCommand(command);
        }
    }

}

package ru.chipenable.bakingapp.model.navigation;

import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * Created by Pavel.B on 25.05.2017.
 */

public class Router {

    private WeakReference<INavigator> navigator;
    private Bundle arguments;

    public Router(){
        navigator = new WeakReference<>(command -> {});
    }

    public void attachToNavigator(INavigator navigator){
        this.navigator = new WeakReference<>(navigator);
    }

    public void putCommand(Command command, Bundle arguments){
        if (navigator.get() != null){
            this.arguments = arguments;
            navigator.get().handleCommand(command);
        }
    }

    public Bundle getArguments(){
        return arguments;
    }

}

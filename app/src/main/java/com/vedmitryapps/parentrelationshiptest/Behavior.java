package com.vedmitryapps.parentrelationshiptest;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;


public class Behavior extends CoordinatorLayout.Behavior<LinearLayout> {

    /**
     * Конструктор для создания экземпляра FancyBehavior через код.
     */
    public Behavior() {
    }

    /**
     * Конструктор для создания экземпляра FancyBehavior через разметку.
     *
     * @param context The {@link Context}.
     * @param attrs The {@link AttributeSet}.
     */
    public Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Извлекаем любые пользовательские атрибуты
        // в идеале с префиксом behavior_
        // чтобы обозначить принадлежность атрибута к Behavior
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
        child.setTranslationY(translationY);
        return true;
    }

}
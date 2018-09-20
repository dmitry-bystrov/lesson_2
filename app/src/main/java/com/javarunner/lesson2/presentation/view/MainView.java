package com.javarunner.lesson2.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView
{
    void setButtonOneText(CharSequence text);
    void setButtonTwoText(CharSequence text);
    void setButtonThreeText(CharSequence text);
    void setTextViewText(CharSequence text);
    void setIntervalCounterText(CharSequence text);
    void setButtonListenerText(CharSequence text);
    void showToast(String text);
}

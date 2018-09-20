package com.javarunner.lesson2.ui.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.javarunner.lesson2.R;
import com.javarunner.lesson2.events.ActivityEvent;
import com.javarunner.lesson2.events.ClickEvent;
import com.javarunner.lesson2.events.EventBus;
import com.javarunner.lesson2.presentation.presenter.MainPresenter;
import com.javarunner.lesson2.presentation.view.MainView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView {
    private Button buttonOne;
    private Button buttonTwo;
    private Button buttonThree;
    private Button buttonOneR;
    private Button buttonTwoR;
    private Button buttonThreeR;
    private TextView textView;
    private EditText editText;
    private TextView intervalCounter;
    private TextView buttonListener;

    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    public MainPresenter provideMainPresenter() {
        presenter = new MainPresenter(AndroidSchedulers.mainThread());
        return presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        subscribePresenter();
        subscribeEventBus();

        EventBus.getInstance().post(new ActivityEvent("onCreate"));
    }

    private void findViews() {
        buttonOne = findViewById(R.id.btn_one);
        buttonTwo = findViewById(R.id.btn_two);
        buttonThree = findViewById(R.id.btn_three);
        buttonOneR = findViewById(R.id.btn_one_r);
        buttonTwoR = findViewById(R.id.btn_two_r);
        buttonThreeR = findViewById(R.id.btn_three_r);
        textView = findViewById(R.id.text_view);
        editText = findViewById(R.id.edit_text);
        intervalCounter = findViewById(R.id.interval_counter);
        buttonListener = findViewById(R.id.button_listener);
    }

    private void subscribePresenter() {
        presenter.subscribeButtonOneClicks(RxView.clicks(buttonOne));
        presenter.subscribeButtonTwoClicks(RxView.clicks(buttonTwo));
        presenter.subscribeButtonThreeClicks(RxView.clicks(buttonThree));
        presenter.subscribeEditTextChanges(RxTextView.textChanges(editText));
        presenter.subscribeEventBus(EventBus.getInstance());
    }

    private void subscribeEventBus() {
        RxView.clicks(buttonOneR)
                .map(new Function<Object, ClickEvent>() {
                    @Override
                    public ClickEvent apply(Object o) throws Exception {
                        return new ClickEvent("button One");
                    }
                })
                .subscribe(EventBus.getInstance().getSubject());

        RxView.clicks(buttonTwoR)
                .map(new Function<Object, ClickEvent>() {
                    @Override
                    public ClickEvent apply(Object o) throws Exception {
                        return new ClickEvent("button Two");
                    }
                })
                .subscribe(EventBus.getInstance().getSubject());

        RxView.clicks(buttonThreeR)
                .map(new Function<Object, ClickEvent>() {
                    @Override
                    public ClickEvent apply(Object o) throws Exception {
                        return new ClickEvent("button Three");
                    }
                })
                .subscribe(EventBus.getInstance().getSubject());
    }

    @Override
    public void setButtonOneText(CharSequence text) {
        buttonOne.setText(text);
    }

    @Override
    public void setButtonTwoText(CharSequence text) {
        buttonTwo.setText(text);
    }

    @Override
    public void setButtonThreeText(CharSequence text) {
        buttonThree.setText(text);
    }

    @Override
    public void setTextViewText(CharSequence text) {
        textView.setText(text);
    }

    @Override
    public void setIntervalCounterText(CharSequence text) {
        intervalCounter.setText(text);
    }

    @Override
    public void setButtonListenerText(CharSequence text) {
        buttonListener.setText(text);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Timber.d("onDestroy");
        EventBus.getInstance().post(new ActivityEvent("onDestroy"));
    }
}

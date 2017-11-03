package com.vedmitryapps.parentrelationshiptest.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.vedmitryapps.parentrelationshiptest.R;
import com.vedmitryapps.parentrelationshiptest.fragments.QuestionFragment;
import com.vedmitryapps.parentrelationshiptest.fragments.ResultFragment;
import com.vedmitryapps.parentrelationshiptest.fragments.StartFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> questions;
    private FragmentManager fragmentManager;
    private int position = 0;
    private boolean[] mas = new boolean[61];
    private boolean canReturn = true;
    private boolean interrapted = false;

    private TextView count;
    private LinearLayout buttons;

    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;

    private Mode mode = Mode.START;

    private RelativeLayout mainLayout;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
  /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TAG21", "onCreate" );

        sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        editor.putInt("resultAdapterPosition", 0).apply();

        fragmentManager = getFragmentManager();
        questions = new ArrayList();
        questions = Arrays.asList(getResources().getStringArray(R.array.questions));

        initView();

        if(savedInstanceState != null){
            String stateString = savedInstanceState.getString("mode");
            //check if null!
            this.mode = Mode.valueOf(stateString);
            if(mode == Mode.TESTING){
               loadDataFromPrefs();
                /* position = savedInstanceState.getInt("position");
                mas = savedInstanceState.getBooleanArray("array");*/
                nextQuestion();
                Log.i("TAG21", String.valueOf(position));
            }
            if(mode == Mode.RESULT){
                loadDataFromPrefs();
                /* position = savedInstanceState.getInt("position");
                mas = savedInstanceState.getBooleanArray("array");*/
                showResult();
                Log.i("TAG21", String.valueOf(position));
            }
        } else
        startFragment();
    }

    private void initView() {
        buttons =  findViewById(R.id.buttons) ;
        count =  findViewById(R.id.count) ;
        mainLayout = findViewById(R.id.main_layout);

        mAdView = findViewById(R.id.adView);

        mAdView = findViewById(R.id.adView);
        //    mAdView.setVisibility(View.GONE);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mAdView.getVisibility() == View.GONE) {
                    mAdView.setVisibility(View.VISIBLE);
                }
            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("mode", mode.name());
        if(mode == Mode.TESTING){
            savedInstanceState.putBooleanArray("array", mas);
            savedInstanceState.putInt("position", position);
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    public enum Mode {
        START, TESTING, RESULT
    }

    public void onAnswer(View view) {
        Log.i("TAG21", "click" );
        switch (view.getId()){
            case R.id.posBtn:
                mas[position] = true;
        }
        position++;
        if(position == 5){
            Log.i("TAG21", "finish" );
            showResult();
            return;
        }
        canReturn = true;
        if(position>61)
            return;
        nextQuestion();
    }

    private void startFragment(){
        editor.putInt("resultAdapterPosition", 0).commit();
        Fragment fragment = new StartFragment();

        String stateString = sharedPrefs.getString("mode", null);
        if(stateString==null)
            stateString = "";

        Bundle bundle = new Bundle();
        bundle.putBoolean("interrupted", stateString.equals(Mode.TESTING.name()) || mode.equals(Mode.TESTING) || sharedPrefs.getBoolean("interrupted", false));
        Log.i("TAG21", "test" );
        Log.i("TAG21", mode.equals(Mode.TESTING)+"");
        Log.i("TAG21",sharedPrefs.getBoolean("interrupted", false)+"");
        fragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(mode == Mode.TESTING) {
            hideButtons();
            transaction.setCustomAnimations(R.animator.slide_from_right, R.animator.slide_to_right);
            mode = Mode.START;
        }

        //transaction.setCustomAnimations(R.animator.slide_from_left, R.animator.slide_to_left);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void nextQuestion(){
        if(mode == Mode.START || mode == Mode.RESULT){
            mode = Mode.TESTING;
        }
        if(buttons.getVisibility()==View.GONE){
            showButtons();
        }

        count.setText("Вопрос " + String.valueOf(position+1)+" из 61");

        Fragment fragment = QuestionFragment.newInstance(questions.get(position));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_from_left, R.animator.slide_to_left);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showResult(){
      int[] acceptance = {3, 5, 6, 8, 10, 12, 14, 15, 16, 18, 20, 23, 24, 26, 27, 29, 37, 38, 39, 40, 42, 43, 44, 45, 46, 47, 49, 51, 52, 53, 55, 56, 60};
        int[] cooperation = {21, 25, 31, 33, 34, 35, 36};
        int[] symbiosis = {1, 4, 7, 28, 32, 41, 58};
        int[] control = {2, 19, 30, 48, 50, 57, 59};
        int[] failures = {9, 11, 13, 17, 22, 54, 61};

        int acceptanceResult = 0;
        for (int i = 0; i < acceptance.length; i++) {
            if(mas[acceptance[i]-1]){
                acceptanceResult++;
            }
        }

        int cooperationResult = 0;
        for (int i = 0; i < cooperation.length; i++) {
            if(mas[cooperation[i] - 1]){
                cooperationResult++;
            }
        }

        int  symbiosisResult = 0;
        for (int i = 0; i < symbiosis.length; i++) {
            if(mas[symbiosis[i]-1]){
                symbiosisResult++;
            }
        }

        int  controlResult = 0;
        for (int i = 0; i < control.length; i++) {
            if (mas[control[i]-1]) {
                controlResult++;
            }
        }

        int  failuresResult = 0;
        for (int i = 0; i < failures.length; i++) {
            if (mas[failures[i]-1]) {
                failuresResult++;
            }
        }

        mode = Mode.RESULT;

        int[] result = {acceptanceResult, cooperationResult, symbiosisResult, controlResult, failuresResult};
        Fragment fragment = new ResultFragment();

        hideButtons();

        Bundle bundle = new Bundle();
        bundle.putIntArray("result", result);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_from_left, R.animator.slide_to_left);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showButtons() {
        buttons.setVisibility(View.VISIBLE);
        buttons.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom));

        count.setVisibility(View.VISIBLE);
        count.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_from_top));
    }

    private void hideButtons(){
        buttons.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
        buttons.setVisibility(View.GONE);

        animateHideUp();
    }

    void animateHideUp() {
        count.animate()
                .setDuration(50)
                .translationY(-count.getHeight())
                .alpha(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                    }
                });
        mainLayout.animate()
                .setDuration(50)
                .translationY(-count.getHeight())
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateFinish();
                    }
                });
    }

    void animateFinish() {
        mainLayout.animate().setListener(null);
        count.animate().setListener(null);
        count.setVisibility(View.GONE);
        mainLayout.setTranslationY(0);
        count.setTranslationY(0);
        count.setAlpha(1);
    }

    @Override
    public void onBackPressed() {
        if(mode == Mode.RESULT){
            new AlertDialog.Builder(this)
                    .setTitle("Выйти?")
                    .setPositiveButton("Да", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("Нет", null)
                    .show();
            return;
        }


        if(mode == Mode.START){
            finish();
            return;
        }
        if(position==0){
            startFragment();
            return;
        }

        if(canReturn && mode != Mode.RESULT){
            backQuestion();
            canReturn = false;
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Выйти?")
                    .setMessage("Процесс будет сохранен.")
                    .setPositiveButton("Выход", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            editor.putBoolean("interrupted", true).commit();
                            saveResultOnPrefs();
                            startFragment();
                        }

                    })
                    .setNegativeButton("Нет", null)
                 //   .setNeutralButton("Закончить тест", null)
                    .show();
            Log.i("TAG21", "back");
        }
    }

    private void backQuestion(){
        position--;
        mas[position] = false;
        count.setText("Вопрос " + String.valueOf(position+1)+" из 61");

        Fragment fragment = QuestionFragment.newInstance(questions.get(position));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_from_right, R.animator.slide_to_right);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void onClick(View view) {
        Log.i("TAG21", "onAnswer" );
        switch (view.getId()){
            case R.id.exit:
                finish();
                break;
            case R.id.btnStart:
                position = 0;
                mas = new boolean[61];
                nextQuestion();
                showButtons();
                break;
            case R.id.btnResume:
                loadDataFromPrefs();
                Log.i("TAG21", position+ "" );
                editor.putBoolean("interrupted", false).commit();
                nextQuestion();
                showButtons();
                break;
            case R.id.share:
                Log.i("TAG21", "share" );

                break;
            case R.id.rate:
                Log.i("TAG21", "rate" );

                break;
        }

    }

    private void loadDataFromPrefs() {
        String savedResult = sharedPrefs.getString("result_string", null);
        position = sharedPrefs.getInt("position", 0);
        String[] result = savedResult.split(" ");
        for (int i = 0; i < result.length; i++) {
            mas[i] = Boolean.valueOf(result[i]);
        }
    }


    @Override
    protected void onStop() {
        Log.i("TAG21", "onStop" );
        saveResultOnPrefs();
        super.onStop();
    }

    private void saveResultOnPrefs() {
        if(mode == Mode.TESTING || mode == Mode.RESULT){
            String result = "";
            for (int i = 0; i < mas.length; i++) {
                result += String.valueOf(mas[i] + " ");
            }
            Log.i("TAG21", result );
            editor.putString("result_string", result).apply();
            editor.putInt("position", position).apply();
            editor.putString("mode", mode.name()).apply();
        } else {
            editor.putString("mode", null).apply();
        }


    }

    @Override
    protected void onRestart() {
        Log.i("TAG21", "onRestart" );
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.i("TAG21", "onStart" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mAdView != null)
            mAdView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mAdView != null)
            mAdView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("TAG21", "onDestroy" );

        if (mAdView != null)
            mAdView.destroy();
    }
}

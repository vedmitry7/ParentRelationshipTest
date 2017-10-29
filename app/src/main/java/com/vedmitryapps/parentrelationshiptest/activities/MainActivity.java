package com.vedmitryapps.parentrelationshiptest.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

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

    private TextView count;
    private CoordinatorLayout buttons;

    private SharedPreferences sharedPrefs;

    Button resumeButton;

    State state = State.START;

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

        fragmentManager = getFragmentManager();

        questions = new ArrayList();
        questions = Arrays.asList(getResources().getStringArray(R.array.questions));

        buttons = (CoordinatorLayout) findViewById(R.id.buttons) ;
        resumeButton = (Button) findViewById(R.id.btnResume);

        count = (TextView) findViewById(R.id.count) ;
        startFragment();

        sharedPrefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        if(savedInstanceState != null){
            String stateString = savedInstanceState.getString("state");
            this.state = State.valueOf(stateString);
            if(state == State.INPROCESS){
                position = savedInstanceState.getInt("position");
                mas = savedInstanceState.getBooleanArray("array");
                nextQuestion();
                Log.i("TAG21", String.valueOf(position));
                for (boolean b:mas
                        ) {
                    Log.i("TAG21", String.valueOf(b) );
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("state", state.name());
        if(state == State.INPROCESS){
            savedInstanceState.putBooleanArray("array", mas);
            savedInstanceState.putInt("position", position);
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    enum State{
        START, INPROCESS, RESULT
    }

    public void onClick(View view) {
        Log.i("TAG21", "click" );
        switch (view.getId()){
            case R.id.posBtn:
                mas[position] = true;
        }
        position++;
        if(position == 3){
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
        Fragment fragment = new StartFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(state==State.INPROCESS) {
            hideButtons();
            transaction.setCustomAnimations(R.animator.slide_from_right, R.animator.slide_to_right);
            state = State.START;
        }

        //transaction.setCustomAnimations(R.animator.slide_from_left, R.animator.slide_to_left);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void nextQuestion(){
        if(state==State.START ){
            state = State.INPROCESS;
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

  /*      int[] acceptance = {1, 5};
        int[] cooperation = {2};
        int[] symbiosis = {3,4};
        int[] control = {};
        int[] failures = {5};*/

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

        state = State.RESULT;

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

        count.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_to_top));
        count.setVisibility(View.GONE);

/*
        fadeInAnimation.withEndAction(new Runnable() {
            @Override
            public void run() {
        //do stuff
            }
        }); */
    }

    @Override
    public void onBackPressed() {

        if(state==State.START){
            super.onBackPressed();
            return;
        }
        if(position==0){
            startFragment();
            return;
        }

        if(canReturn && state!=State.RESULT){
            backQuestion();
            canReturn = false;
        } else {
          /*  new AlertDialog.Builder(this)
                    .setTitle("Выйти?")
                    .setMessage("Процесс будет сохранен.")
                    .setPositiveButton("Выход", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startFragment();
                        }

                    })
                    .setNegativeButton("Нет", null)
                 //   .setNeutralButton("Закончить тест", null)
                    .show();
*/

            Snackbar myAwesomeSnackbar = Snackbar.make(
                    buttons,
                    "Прервать тест?" + "\r\n" +"Результат будет сохранен.",
                    Snackbar.LENGTH_LONG
            ).setAction("Да", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveResultOnPrefs();
                    startFragment();
                }
            });
            myAwesomeSnackbar.getView().setBackgroundColor(Color.parseColor("#99000000"));
            TextView textView = myAwesomeSnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            // textView.setTextSize(16);
            myAwesomeSnackbar.show();
            Log.i("TAG21", "back");
        }
    }

    private void backQuestion(){
        position--;
        mas[position] = false;
        count.setText(String.valueOf(position+1)+"/"+"61");

        Fragment fragment = QuestionFragment.newInstance(questions.get(position));
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_from_right, R.animator.slide_to_right);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void startTesting(View view) {
        switch (view.getId()){
            case R.id.btnStart:
                position = 0;
                mas = new boolean[61];
                break;
            case R.id.btnResume:

                String savedResult = sharedPrefs.getString("state", null);
                position = sharedPrefs.getInt("position", 0);

                String[] result = savedResult.split(" ");

                for (int i = 0; i < result.length; i++) {
                    mas[i] = Boolean.valueOf(result[i]);
                }

                for (boolean b:mas
                     ) {
                    Log.i("TAG21", String.valueOf(b) );
                }
                break;
        }
        nextQuestion();
        showButtons();
    }



    @Override
    protected void onStop() {

       saveResultOnPrefs();

        super.onStop();
    }

    private void saveResultOnPrefs() {
        String result = "";
        for (int i = 0; i < mas.length; i++) {
            result += String.valueOf(mas[i] + " ");
        }
        Log.i("TAG21", "onStop" );
        Log.i("TAG21", result );

        sharedPrefs.edit().putString("state", result).commit();
        sharedPrefs.edit().putInt("position", position).commit();
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
    protected void onDestroy() {
        Log.i("TAG21", "onDestroy" );
        super.onDestroy();
    }
}

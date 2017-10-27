package com.vedmitryapps.parentrelationshiptest.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
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
    private boolean[] mas = new boolean[5];
    private boolean inProcess;
    private boolean canReturn = true;

    private TextView count;
    private LinearLayout buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        questions = new ArrayList();
        questions = Arrays.asList(getResources().getStringArray(R.array.planets_array));

        buttons = (LinearLayout) findViewById(R.id.buttons) ;
        count = (TextView) findViewById(R.id.count) ;
        startFragment();
    }

    public void onClick(View view) {
        Log.i("TAG21", "click" );
        switch (view.getId()){
            case R.id.posBtn:
                if(position>mas.length)
                    return;
                mas[position] = true;
        }
        position++;
        if(position == 5){
            Log.i("TAG21", "finish" );
            showResult();
            return;
        }
        canReturn = true;
        nextQuestion();
    }

    private void startFragment(){
        Fragment fragment = new StartFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
      //  transaction.setCustomAnimations(R.animator.slide_from_left, R.animator.slide_to_left);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void nextQuestion(){
        count.setText(String.valueOf(position+1)+"/"+"61");
        if(!inProcess){
            buttons.setVisibility(View.VISIBLE);
            buttons.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));

            count.setVisibility(View.VISIBLE);
            count.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_left));
            inProcess = true;
        }
        Fragment fragment = QuestionFragment.newInstance(questions.get(position));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_from_left, R.animator.slide_to_left);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showResult(){
 /*     int[] acceptance = {3, 5, 6, 8, 10, 12, 14, 15, 16, 18, 20, 23, 24, 26, 27, 29, 37, 38, 39, 40, 42, 43, 44, 45, 46, 47, 49, 51, 52, 53, 55, 56, 60};
        int[] cooperation = {21, 25, 31, 33, 34, 35, 36};
        int[] symbiosis = {1, 4, 7, 28, 32, 41, 58};
        int[] control = {2, 19, 30, 48, 50, 57, 59};
        int[] failures = {9, 11, 13, 17, 22, 54, 61};*/

        int[] acceptance = {1, 5};
        int[] cooperation = {2};
        int[] symbiosis = {3,4};
        int[] control = {};
        int[] failures = {5};

        int acceptanceResult = 0;
        for (int i = 0; i < acceptance.length; i++) {
            if(mas[acceptance[i]-1] == true){
                acceptanceResult++;
            }
        }

        int cooperationResult = 0;
        for (int i = 0; i < cooperation.length; i++) {
            if(mas[cooperation[i]-1] == true){
                cooperationResult++;
            }
        }

        int  symbiosisResult = 0;
        for (int i = 0; i < symbiosis.length; i++) {
            if(mas[symbiosis[i]-1] == true){
                symbiosisResult++;
            }
        }

        int  controlResult = 0;
        for (int i = 0; i < control.length; i++) {
            if (mas[control[i]-1] == true) {
                controlResult++;
            }
        }

        int  failuresResult = 0;
        for (int i = 0; i < failures.length; i++) {
            if (mas[failures[i]-1] == true) {
                failuresResult++;
            }
        }

        int[] result = {acceptanceResult, cooperationResult, symbiosisResult, controlResult, failuresResult};
        Fragment fragment = new ResultFragment();

        buttons.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_down));
        buttons.setVisibility(View.GONE);

        count.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
        count.setVisibility(View.GONE);


        Bundle bundle = new Bundle();
        bundle.putIntArray("result", result);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_from_left, R.animator.slide_to_left);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(canReturn){
            backQuestion();
            canReturn = false;
        }
        Log.i("TAG21", "back" );
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

    public void startTest(View view) {
        nextQuestion();
    }
}

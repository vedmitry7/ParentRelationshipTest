package com.vedmitryapps.parentrelationshiptest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.vedmitryapps.parentrelationshiptest.fragments.QuestionFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> questions;
    private FragmentManager fragmentManager;
    private int position = 0;
    private boolean[] mas = new boolean[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        questions = new ArrayList();
        questions = Arrays.asList(getResources().getStringArray(R.array.planets_array));

        Log.i("TAG21", "S = " + questions.size());

        nextQuestion();

    }

    public void onClick(View view) {
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
        nextQuestion();
    }

    private void nextQuestion(){
        Fragment fragment = QuestionFragment.newInstance(questions.get(position));

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showResult(){
        Fragment fragment = QuestionFragment.newInstance("Finish!");

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.replace(R.id.main_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        Log.i("TAG21", "back" );
    }

}

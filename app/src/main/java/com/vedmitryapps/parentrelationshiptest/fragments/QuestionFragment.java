package com.vedmitryapps.parentrelationshiptest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vedmitryapps.parentrelationshiptest.R;

public class QuestionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static QuestionFragment newInstance(String question){
        QuestionFragment questionFragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString("args", question);
        questionFragment.setArguments(args);
        return questionFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_fragment_view, container, false);
        String question = getArguments().getString("args");
        TextView textView = view.findViewById(R.id.question);
        textView.setText(question);
        return view;
    }
}

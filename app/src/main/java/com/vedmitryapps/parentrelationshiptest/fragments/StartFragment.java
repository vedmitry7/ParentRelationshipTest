package com.vedmitryapps.parentrelationshiptest.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vedmitryapps.parentrelationshiptest.R;
import com.vedmitryapps.parentrelationshiptest.views.JustifiedTextView;

public class StartFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_fragment, container, false);


        Spannable span = Spannable.Factory.getInstance().newSpannable(getString(R.string.test_description));
        JustifiedTextView textView = view.findViewById(R.id.test_description);
        textView.setText(span.toString());

        return view;
    }

}

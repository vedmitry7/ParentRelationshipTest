package com.vedmitryapps.parentrelationshiptest.fragments;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.vedmitryapps.parentrelationshiptest.R;

public class StartFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_fragment, container, false);


        Spannable span = Spannable.Factory.getInstance().newSpannable(getString(R.string.test_description));
        WebView webView = view.findViewById(R.id.test_description);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(toHtmlString(getActivity().getApplicationContext().getString(R.string.test_description)),
                "text/html; charset=utf-8", "utf-8");

        return view;
    }
    private String toHtmlString(String s){

        String stringStart = "<body style=\"text-align:justify; line-height: 180%; color:black; opacity:0.78; background-color:#00000000;\">";
        String stringEnd =  "</body>";

        return stringStart + s + stringEnd;

    }
}

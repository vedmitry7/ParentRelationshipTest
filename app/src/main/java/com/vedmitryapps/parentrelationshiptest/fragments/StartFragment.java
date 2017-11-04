package com.vedmitryapps.parentrelationshiptest.fragments;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.vedmitryapps.parentrelationshiptest.R;

public class StartFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);

        WebView webView = view.findViewById(R.id.test_description);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadUrl("file:///android_asset/pages/main_page.html");

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        Button resumeButton =  view.findViewById(R.id.btnResume);
        Button startButton =  view.findViewById(R.id.btnStart);

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String stateString = sharedPrefs.getString("mode", null);

       // if(stateString != null && stateString.equals(MainActivity.Mode.TESTING.name())){
        if(this.getArguments().getBoolean("interrupted")){
            startButton.setText("Начать заново");
            resumeButton.setVisibility(View.VISIBLE);
        }

        return view;
    }
}

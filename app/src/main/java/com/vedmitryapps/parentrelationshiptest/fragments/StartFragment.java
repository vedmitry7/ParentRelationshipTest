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
        View view = inflater.inflate(R.layout.fragment_start, container, false);


        Spannable span = Spannable.Factory.getInstance().newSpannable(getString(R.string.test_description));
        WebView webView = view.findViewById(R.id.test_description);
        webView.setBackgroundColor(Color.TRANSPARENT);
   /*     webView.loadData(toHtmlString(getActivity().getApplicationContext().getString(R.string.test_description)),
                "text/html; charset=utf-8", "utf-8");*/
        webView.loadUrl("file:///android_asset/main_page.html");

        return view;
    }
    private String toHtmlString(String s){
/*
        String stringStart = "<body style=\"text-align:justify; line-height: 180%; color:black; opacity:0.78; background-color:#00000000;\">";
        String stringEnd =  "</body>";

        return stringStart + s + stringEnd;*/
        String head = " <html> <head> <style type=\"text/css\"> @font-face { font-family: Roboto-Black; src: url(\"file:///assets/Roboto-Black.ttf\") } body { font-family: Roboto-Black; text-align: justify; line-height: 180%; color:black; opacity:0.78; background-color:#00000000; } </style> </head> <body>";
        String htmlData= head + s +"</body></html>" ;
       // return htmlData;


        String pish = "<html><head><style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/Roboto-Black.ttf\")}body {font-family: MyFont;font-size: medium;text-align: justify;}</style></head><body>";
        String pas = "</body></html>"; String myHtmlString = pish + s + pas;
        return  myHtmlString;
    }
}

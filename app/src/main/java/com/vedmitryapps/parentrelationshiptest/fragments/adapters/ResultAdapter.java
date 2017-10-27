package com.vedmitryapps.parentrelationshiptest.fragments.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.vedmitryapps.parentrelationshiptest.R;

public class ResultAdapter extends PagerAdapter {

    private Context context;
    private int[] result;


    public ResultAdapter(Context context, int[] result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.result_fragmen_tab,  null);

       // result.setText("Ваш результат - " + this.result[0] + " "+ this.result[1] + " "+ this.result[2] + " "+ this.result[3] + " "+ this.result[4] );

        TextView name = view.findViewById(R.id.name);
        TextView result = view.findViewById(R.id.result);
        RoundCornerProgressBar progress = view.findViewById(R.id.progressBar);

        WebView webViewDesc = view.findViewById(R.id.description);
        webViewDesc.setBackgroundColor(Color.TRANSPARENT);

        WebView webView = view.findViewById(R.id.webViewShortDesc);
        webView.setBackgroundColor(Color.TRANSPARENT);

        switch (position){
            case 0:
                name.setText(context.getString(R.string.acceptance));
                result.setText("Ваш результат - " + this.result[0] + " из " + 33 );
                progress.setMax(33);
                progress.setSecondaryProgress(33);
                progress.setProgress(this.result[0]);
                webView.loadData(toHtmlString(context.getString(R.string.acceptance_description)),
                        "text/html; charset=utf-8", "utf-8");
                webViewDesc.loadData(toHtmlString(context.getString(R.string.acceptance_description_1)),
                        "text/html; charset=utf-8", "utf-8");
                break;
            case 1:
                name.setText(context.getString(R.string.cooperation));
                result.setText("Ваш результат - " + this.result[1] + " из " + 8 );
                progress.setMax(8);
                progress.setSecondaryProgress(8);
                progress.setProgress(this.result[1]);
                webView.loadData(toHtmlString(context.getString(R.string.cooperation_description)),
                        "text/html; charset=utf-8", "utf-8");
                webViewDesc.loadData(toHtmlString(context.getString(R.string.cooperation_description_1)),
                        "text/html; charset=utf-8", "utf-8");
                break;
            case 2:
                name.setText(context.getString(R.string.symbiosis));
                result.setText("Ваш результат - " + this.result[2] + " из " + 7 );
                progress.setMax(7);
                progress.setSecondaryProgress(7);
                progress.setProgress(this.result[2]);
                webView.loadData(toHtmlString(context.getString(R.string.symbiosis_description)),
                        "text/html; charset=utf-8", "utf-8");
                webViewDesc.loadData(toHtmlString(context.getString(R.string.symbiosis_description_1)),
                        "text/html; charset=utf-8", "utf-8");
                break;
            case 3:
                name.setText(context.getString(R.string.control));
                result.setText("Ваш результат - " + this.result[3] + " из " + 7 );
                progress.setMax(7);
                progress.setSecondaryProgress(7);
                progress.setProgress(this.result[3]);
                webView.loadData(toHtmlString(context.getString(R.string.control_description)),
                        "text/html; charset=utf-8", "utf-8");
                webViewDesc.loadData(toHtmlString(context.getString(R.string.control_description_1)),
                        "text/html; charset=utf-8", "utf-8");
                break;
            case 4:
                name.setText(context.getString(R.string.failures));
                result.setText("Ваш результат - " + this.result[4] + " из " + 7 );
                progress.setMax(7);
                progress.setSecondaryProgress(7);
                progress.setProgress(this.result[4]);
                webView.loadData(toHtmlString(context.getString(R.string.failures_description)),
                        "text/html; charset=utf-8", "utf-8");
                webViewDesc.loadData(toHtmlString(context.getString(R.string.failures_description_1)),
                        "text/html; charset=utf-8", "utf-8");
                break;
        }

        collection.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(ViewGroup viewGroup, int position, Object view) {
        viewGroup.removeView((View) view);
    }

    private String toHtmlString(String s){

        String stringStart = "<body style=\"text-align:justify; line-height: 180%; color:black; opacity:0.78; background-color:#00000000;\">";
        String stringEnd =  "</body>";

        return stringStart + s + stringEnd;

    }

}
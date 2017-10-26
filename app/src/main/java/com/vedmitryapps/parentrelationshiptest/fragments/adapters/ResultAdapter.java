package com.vedmitryapps.parentrelationshiptest.fragments.adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.vedmitryapps.parentrelationshiptest.views.JustifiedTextView;
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
        JustifiedTextView desc1 = view.findViewById(R.id.desc1);
        JustifiedTextView desc2 = view.findViewById(R.id.desc2);
        RoundCornerProgressBar progress = view.findViewById(R.id.progressBar);

        switch (position){
            case 0:
                name.setText(context.getString(R.string.acceptance));
                result.setText("Ваш результат - " + this.result[0] + " из " + 33 );
                progress.setMax(33);
                progress.setSecondaryProgress(33);
                progress.setProgress(this.result[0]);
                desc1.setText(context.getString(R.string.acceptance_description_1));
                desc2.setText(context.getString(R.string.acceptance_description_2));
                break;
            case 1:
                name.setText(context.getString(R.string.cooperation));
                result.setText("Ваш результат - " + this.result[1] + " из " + 8 );
                progress.setMax(8);
                progress.setSecondaryProgress(8);
                progress.setProgress(this.result[1]);
                desc1.setText(context.getString(R.string.cooperation_description_1));
                desc2.setText(context.getString(R.string.cooperation_description_2));
                break;
            case 2:
                name.setText(context.getString(R.string.symbiosis));
                result.setText("Ваш результат - " + this.result[2] + " из " + 7 );
                progress.setMax(7);
                progress.setSecondaryProgress(7);
                progress.setProgress(this.result[2]);
                desc1.setText(context.getString(R.string.symbiosis_description_1));
                desc2.setText(context.getString(R.string.symbiosis_description_2));
                break;
            case 3:
                name.setText(context.getString(R.string.control));
                result.setText("Ваш результат - " + this.result[3] + " из " + 7 );
                progress.setMax(7);
                progress.setSecondaryProgress(7);
                progress.setProgress(this.result[3]);
                desc1.setText(context.getString(R.string.control_description_1));
                desc2.setText(context.getString(R.string.control_description_2));
                break;
            case 4:
                name.setText(context.getString(R.string.failures));
                result.setText("Ваш результат - " + this.result[4] + " из " + 7 );
                progress.setMax(7);
                progress.setSecondaryProgress(7);
                progress.setProgress(this.result[4]);
                desc1.setText(context.getString(R.string.failures_description_1));
                desc2.setText(context.getString(R.string.failures_description_2));
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

}
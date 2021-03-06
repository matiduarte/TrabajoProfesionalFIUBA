package com.p2.sanatorioborattiapp.Activities.Holder;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by quimey on 01/08/16.
 */

public class TreatmentListViewHolder {
    private LinearLayout textViewWrap;
    private TextView textView;

    public TreatmentListViewHolder(LinearLayout textViewWrap, TextView textView) {
        super();
        this.textViewWrap = textViewWrap;
        this.textView = textView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public LinearLayout getTextViewWrap() {
        return textViewWrap;
    }

    public void setTextViewWrap(LinearLayout textViewWrap) {
        this.textViewWrap = textViewWrap;
    }
}
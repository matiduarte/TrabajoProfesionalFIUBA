package com.p2.sanatorioborattiapp.Activities.Animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;

import com.p2.sanatorioborattiapp.Activities.Adapter.TreatmentListAdapter;
import com.p2.sanatorioborattiapp.Activities.Model.TreatmentListItem;

/**
 * Created by quimey on 01/08/16.
 */

public class ResizeAnimation extends Animation {
    private View mView;
    private float mToHeight;
    private float mFromHeight;

    private float mToWidth;
    private float mFromWidth;

    private TreatmentListAdapter mTreatmentListAdapter;
    private TreatmentListItem mTreatmentListItem;

    public ResizeAnimation(TreatmentListAdapter treatmentListAdapter, TreatmentListItem treatmentListItem,
                           float fromWidth, float fromHeight, float toWidth, float toHeight) {
        mToHeight = toHeight;
        mToWidth = toWidth;
        mFromHeight = fromHeight;
        mFromWidth = fromWidth;
        mView = treatmentListItem.getHolder().getTextViewWrap();
        mTreatmentListAdapter = treatmentListAdapter;
        mTreatmentListItem = treatmentListItem;
        setDuration(200);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float height = (mToHeight - mFromHeight) * interpolatedTime
                + mFromHeight;
        float width = (mToWidth - mFromWidth) * interpolatedTime + mFromWidth;
        AbsListView.LayoutParams p = (AbsListView.LayoutParams) mView.getLayoutParams();
        p.height = (int) height;
        p.width = (int) width;
        mTreatmentListItem.setCurrentHeight(p.height);
        mTreatmentListAdapter.notifyDataSetChanged();
    }
}
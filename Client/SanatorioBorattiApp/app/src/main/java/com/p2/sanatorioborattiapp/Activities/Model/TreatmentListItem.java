package com.p2.sanatorioborattiapp.Activities.Model;

import com.p2.sanatorioborattiapp.Activities.Holder.TreatmentListViewHolder;
import com.p2.sanatorioborattiapp.Entities.Treatment;
import com.p2.sanatorioborattiapp.R;

/**
 * Created by quimey on 01/08/16.
 */

public class TreatmentListItem {

    private Treatment treatment;
    private int collapsedHeight, currentHeight, expandedHeight;
    private boolean isOpen;
    private TreatmentListViewHolder holder;
    private int drawable;

    public TreatmentListItem(Treatment treatment, int collapsedHeight, int currentHeight,
                             int expandedHeight) {
        super();
        this.treatment = treatment;
        this.collapsedHeight = collapsedHeight;
        this.currentHeight = currentHeight;
        this.expandedHeight = expandedHeight;
        this.isOpen = false;
        this.drawable = R.drawable.down;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
    }

    public int getCollapsedHeight() {
        return collapsedHeight;
    }

    public void setCollapsedHeight(int collapsedHeight) {
        this.collapsedHeight = collapsedHeight;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(int currentHeight) {
        this.currentHeight = currentHeight;
    }

    public int getExpandedHeight() {
        return expandedHeight;
    }

    public void setExpandedHeight(int expandedHeight) {
        this.expandedHeight = expandedHeight;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public TreatmentListViewHolder getHolder() {
        return holder;
    }

    public void setHolder(TreatmentListViewHolder holder) {
        this.holder = holder;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}

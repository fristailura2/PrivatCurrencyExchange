package com.fastsoft.testcurrencyexchange.presentation.graph;

import com.fastsoft.testcurrencyexchange.presentation.base.BaseView;

import java.util.List;

public interface GraphView extends BaseView {
    public abstract void showDataPickerDialogFrom();

    public abstract void showDataPickerDialogTo();

    void showProgressDialog(int total);

    void incProgressDialog();

    void hideProgressDialog();

    void addGraphPoint(GraphPoint graphPoint);

    void addGraphPoint(List<GraphPoint> graphPoint);

    void setFromDateText(String from);

    void setToDateText(String to);
}

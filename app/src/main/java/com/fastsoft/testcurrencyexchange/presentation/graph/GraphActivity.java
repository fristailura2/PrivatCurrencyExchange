package com.fastsoft.testcurrencyexchange.presentation.graph;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import com.fastsoft.testcurrencyexchange.R;
import com.fastsoft.testcurrencyexchange.presentation.base.BaseActivity;
import com.fastsoft.testcurrencyexchange.utils.ArraysUtils;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class GraphActivity extends BaseActivity<GraphPresenter> implements GraphView{
    private final static String FROM_DATE_KEY="from_key";
    private final static String TO_DATE_KEY="to_key";
    private final static String LOADING_KEY="loading_key";
    private final static String GRAPH_DATA_KEY="graph_data_key";

    private final static int MAX_PINTS_FOR_GRAPH=1500;

    @BindView(R.id.activity_graph_graphView)
    com.jjoe64.graphview.GraphView graphView;
    @BindView(R.id.activity_graph_from_date_textView)
    TextView fromDateTextView;
    @BindView(R.id.layout_nbu_item_date_textView)
    TextView toDateTextView;
    private LineGraphSeries<GraphPoint> lineGraphSeries;

    private ProgressDialog dialog;
    @Override
    public int getLayout() {
        return R.layout.activity_graph;
    }

    @Inject
    @Override
    public void setPresenter(GraphPresenter presenter) {
        this.presenter=presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGraphView();
    }
    @OnClick(R.id.view2)
    public void onPickDateFromButtonClick(){
        presenter.onPickDateFromButtonClick();
    }
    @OnClick(R.id.layout_nbu_item_date_picker_button_appCompatButton)
    public void onPickDateToButtonClick(){
        presenter.onPickDateToButtonClick();
    }

    public void showDataPickerDialog(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar=Calendar.getInstance();
        new DatePickerDialog(this,listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    @Override
    public void showDataPickerDialogFrom(){
        showDataPickerDialog((datePicker, year, monthOfYear, dayOfMonth) -> presenter.onDateFromPicked(year,monthOfYear,dayOfMonth));
    }
    @Override
    public void showDataPickerDialogTo(){
        showDataPickerDialog((datePicker, year, monthOfYear, dayOfMonth) -> presenter.onDateToPicked(year,monthOfYear,dayOfMonth));
    }
    @Override
    public void showProgressDialog(int total){
        dialog =new ProgressDialog(this);
        dialog.setTitle(getResources().getString(R.string.loading_title));
        dialog.setMessage(getResources().getString(R.string.loading_text));
        dialog.setCancelable(true);
        dialog.setOnCancelListener((dialogInterface)->{
            dialog=null;
            presenter.progressDialogCanceled();
        });
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(total);
        dialog.show();
    }
    @Override
    public void incProgressDialog(){
        dialog.setProgress(dialog.getProgress()+1);
    }
    @Override
    public void hideProgressDialog(){
        if(dialog!=null)
            dialog.cancel();
        dialog=null;
    }
    public void initGraphView() {
        lineGraphSeries = new LineGraphSeries<>();
        graphView.addSeries(lineGraphSeries);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setYAxisBoundsManual(true);

        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(4);

        graphView.getGridLabelRenderer().setHumanRounding(true);
        graphView.getGridLabelRenderer().setLabelsSpace(0);
        graphView.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.LEFT);
        graphView.getGridLabelRenderer().setVerticalLabelsVAlign(GridLabelRenderer.VerticalLabelsVAlign.ABOVE);
        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(30);
    }
    @Override
    public void addGraphPoint(GraphPoint graphPoint) {
        lineGraphSeries.appendData(graphPoint,true,MAX_PINTS_FOR_GRAPH);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Iterator<GraphPoint> graphPointIterator =lineGraphSeries.getValues(0,MAX_PINTS_FOR_GRAPH);
        ArrayList<GraphPoint> serialData=new ArrayList<>();
        while (graphPointIterator.hasNext())
            serialData.add(graphPointIterator.next());

        outState.putBoolean(LOADING_KEY,dialog==null);
        outState.putString(FROM_DATE_KEY,fromDateTextView.getText().toString());
        outState.putString(TO_DATE_KEY,toDateTextView.getText().toString());
        outState.putSerializable(GRAPH_DATA_KEY,serialData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean loading = savedInstanceState.getBoolean(LOADING_KEY);
        String fromDate = savedInstanceState.getString(FROM_DATE_KEY);
        String toDate = savedInstanceState.getString(TO_DATE_KEY);
        List<GraphPoint> graphData= (List<GraphPoint>) savedInstanceState.getSerializable(GRAPH_DATA_KEY);
        presenter.onOrientationChanged(loading,fromDate,toDate,graphData);
    }

    @Override
    public void addGraphPoint(List<GraphPoint> graphPoints) {
        if(graphPoints.isEmpty())
            return;

        GraphPoint lowest = graphPoints.get(0);
        GraphPoint heist=graphPoints.get(graphPoints.size()-1);

        lineGraphSeries.resetData(graphPoints.toArray(new GraphPoint[0]));

        graphView.getViewport().setMinX(lowest.getX());
        graphView.getViewport().setMaxX(heist.getX());

        Pair<GraphPoint,GraphPoint> minMax=ArraysUtils.findMinMax(graphPoints,(val1, val2) -> (int) Math.signum(val1.getY()-val2.getY()));

        double delta=(minMax.second.getY()-minMax.first.getY())/5;
        graphView.getViewport().setMaxY(minMax.second.getY()+delta);
        graphView.getViewport().setMinY(minMax.first.getY()-delta);
    }

    @Override
    public void setFromDateText(String from) {
        fromDateTextView.setText(from);
    }

    @Override
    public void setToDateText(String to) {
        toDateTextView.setText(to);
    }


}

package com.fastsoft.testcurrencyexchange.presentation.graph;

import com.jjoe64.graphview.series.DataPoint;

import java.io.Serializable;
import java.util.Date;

public class GraphPoint extends DataPoint implements Serializable {
    private Date x;
    private double y;

    public GraphPoint(Date x, double y) {
        super(x,y);
        this.x = x;
        this.y = y;
    }

}

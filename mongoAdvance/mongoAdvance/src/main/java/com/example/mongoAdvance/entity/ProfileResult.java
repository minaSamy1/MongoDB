package com.example.mongoAdvance.entity;

public class ProfileResult {

    private String _id ;
    private int SumExper;
    private int Max ;
    private int Min ;

    private String [] technical;
    public int getMax() {
        return Max;
    }

    public ProfileResult(String _id, int sumExper, int max, int min) {
        this._id = _id;
        SumExper = sumExper;
        Max = max;
        Min = min;
    }

    public void setMax(int max) {
        Max = max;
    }

    public int getMin() {
        return Min;
    }

    public void setMin(int min) {
        Min = min;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getSumExper() {
        return SumExper;
    }

    public void setSumExper(int sumExper) {
        SumExper = sumExper;
    }

    public ProfileResult() {
    }

    public ProfileResult(String _id, int maxExper) {
        this._id = _id;
        SumExper = maxExper;
    }

    public ProfileResult(String _id, int sumExper, int max, int min, String[] technical) {
        this._id = _id;
        SumExper = sumExper;
        Max = max;
        Min = min;
        this.technical = technical;
    }

    public String[] getTechnical() {
        return technical;
    }

    public void setTechnical(String[] technical) {
        this.technical = technical;
    }
}

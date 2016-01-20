package com.rapidsofttechnologies.myproject;

/**
 * Created by AND-18 on 11/26/2015.
 */
public class DataOfRecyclerView {
    private String level;
    private int color;
    public DataOfRecyclerView(){}
    public void setLevel(String level)
    {
        this.level=level;
    }

    public void setColor(int color)
    {
        this.color=color;
    }

    public String getLevel(){
        return level;
    }

    public int getColor(){
        return color;
    }
}

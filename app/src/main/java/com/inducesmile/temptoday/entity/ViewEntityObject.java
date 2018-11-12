package com.inducesmile.temptoday.entity;


import android.widget.RadioButton;

/**
 * Created by BalvirJha on 10-11-2018.
 */

public class ViewEntityObject {

    private RadioButton radioButton;

    private String radioName;

    public ViewEntityObject(RadioButton radioButton, String radioName) {
        this.radioButton = radioButton;
        this.radioName = radioName;
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }

    public void setRadioButton(RadioButton radioButton) {
        this.radioButton = radioButton;
    }

    public String getRadioName() {
        return radioName;
    }

    public void setRadioName(String radioName) {
        this.radioName = radioName;
    }
}
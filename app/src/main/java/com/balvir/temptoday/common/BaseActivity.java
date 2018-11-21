package com.balvir.temptoday.common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.balvir.temptoday.R;


/**
 * Created by Balvir Jha on 11/19/18.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}

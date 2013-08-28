package com.tipcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class TipCalculatorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip_calculator, menu);
        return true;
    }

    public void calculate(View v) {
        EditText etTotalAmount = (EditText) findViewById(R.id.tTotalAmt);
        double percentage = 0.0, tipAmt = 0.0;
        String totalAmount = etTotalAmount.getText().toString();
        if (!totalAmount.isEmpty()) {
            tipAmt = Double.valueOf(totalAmount);
            int viewId = v.getId();
            if (viewId == R.id.btnTen) {
                percentage = 0.1;
            } else if (viewId == R.id.btnFtn) {
                percentage = 0.15;
            } else if (viewId == R.id.btnTwenty) {
                percentage = 0.2;
            }
        }
        TextView tTipAmount = (TextView) findViewById(R.id.tTipAmt);
        String tipAmount = String.valueOf(tipAmt * percentage);
        tTipAmount.setText(tipAmount);
    }
}

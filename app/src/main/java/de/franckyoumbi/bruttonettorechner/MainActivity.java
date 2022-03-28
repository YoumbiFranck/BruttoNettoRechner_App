package de.franckyoumbi.bruttonettorechner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

    private EditText editText_figure;

    private RadioButton radioButton_7_percent, radioButton_19_percent;
    private RadioButton radioButton_gross, radioButton_net;

    private ConstraintLayout constraintLayout_result;
    private TextView textView_gross, textView_net, textView_tax;

    private MyOnCheckedChangeListener myOnCheckedChangeListener = new MyOnCheckedChangeListener();

    //zu einem Double-Wert mit 2 nachkommastellen formatieren.


    private DecimalFormat decimalFormat = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_figure = findViewById(R.id.editText_figure);

        radioButton_7_percent = findViewById(R.id.radioButton_7_percent);
        radioButton_19_percent = findViewById(R.id.radioButton_19_percent);
        radioButton_gross = findViewById(R.id.radioButton_gross);
        radioButton_net = findViewById(R.id.radioButton_net);

        radioButton_7_percent.setOnCheckedChangeListener(myOnCheckedChangeListener);
        radioButton_19_percent.setOnCheckedChangeListener(myOnCheckedChangeListener);
        radioButton_gross.setOnCheckedChangeListener(myOnCheckedChangeListener);
        radioButton_net.setOnCheckedChangeListener(myOnCheckedChangeListener);

        constraintLayout_result = findViewById(R.id.constraintLayout_result);
        textView_gross = findViewById(R.id.textView_gross);
        textView_net = findViewById(R.id.textView_net);
        textView_tax = findViewById(R.id.textView_tax);


        editText_figure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateTax();
            }
        });
    }

    private void calculateTax() {
        String figure = editText_figure.getEditableText().toString();
        if (figure.isEmpty()) {
            constraintLayout_result.setVisibility(View.INVISIBLE);
        } else {
            constraintLayout_result.setVisibility(View.VISIBLE);
            double percentage = radioButton_7_percent.isChecked() ? 1.07 : 1.19;  // if RadioButton for 7 percent is selected (checked) use 7 percent, else 19.
            boolean net = radioButton_net.isChecked();
            double dFigure = Double.parseDouble(figure);
            double dNet = 0, dGross = 0;

            if (net) {
                dGross = dFigure * percentage;
                dNet = dFigure;
            } else {
                dNet = dFigure / percentage;
                dGross = dFigure;
            }
            double tax = dGross - dNet;

            textView_gross.setText(decimalFormat.format(dGross));
            textView_tax.setText(decimalFormat.format(tax));
            textView_net.setText(decimalFormat.format(dNet));
        }
    }

    class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            calculateTax();
        }
    }

}

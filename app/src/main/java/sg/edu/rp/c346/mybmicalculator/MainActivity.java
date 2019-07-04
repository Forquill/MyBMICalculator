package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText Weight;
    EditText Height;
    Button btnCalc;
    Button btnReset;
    TextView tvDate;
    TextView tvBMI;
    TextView tvStatus;


    @Override
    protected void onPause() {
        super.onPause();

        String date = tvDate.getText().toString();
        float bmi = Float.parseFloat(tvBMI.getText().toString());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("date", date);
        prefEdit.putFloat("bmi", bmi);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Float floatBMI = prefs.getFloat("bmi", 0);
        String date = prefs.getString("date", "");

        tvBMI.setText(floatBMI.toString());
        tvDate.setText(date + "");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Weight = findViewById(R.id.editTextWeight);
        Height = findViewById(R.id.editTextHeight);
        btnCalc = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        tvDate = findViewById(R.id.tvDate);
        tvBMI = findViewById(R.id.tvBMI);
        tvStatus = findViewById(R.id.tvStatus);

        Calendar now = Calendar.getInstance();
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                now.get(Calendar.MINUTE);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float result = 0;
                float h = Float.parseFloat(Height.getText().toString());
                float w = Float.parseFloat(Weight.getText().toString());
                result = w / (h * h);

                if (result >= 30)
                {
                    tvStatus.setText("You are obese");
                }
                else if (result >= 25)
                {
                    tvStatus.setText("You are overweight");
                }
                else if (result >= 18.5)
                {
                    tvStatus.setText("Your BMI is normal");
                }
                else
                {
                    tvStatus.setText("You are underweight");
                }

                tvBMI.setText(String.format("%.3f",result));
                tvDate.setText(datetime);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.putString("date", "");
                prefEdit.putFloat("bmi", 0);

                prefEdit.commit();

                tvStatus.setText(" ");

                onResume();
            }
        });




    }
}

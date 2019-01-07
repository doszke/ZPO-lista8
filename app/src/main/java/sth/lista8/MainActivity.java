package sth.lista8;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    private PPM ppm = new PPM();
    private int year;
    private boolean isDataValid = false;

    public static class DatePickerFragment extends DialogFragment {

        //delegated value to outer class
        private int year;

        public int getYear() {
            return year;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);



            this.year = year;


            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = new GregorianCalendar(year, month, dayOfMonth);

        long msDiff = Calendar.getInstance().getTimeInMillis() - cal.getTimeInMillis();
        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

        isDataValid = daysDiff > 0;

        setDate(cal);

    }

    private void setDate(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        if(isDataValid) {
            ((TextView) findViewById(R.id.date_label)).setText(dateFormat.format(calendar.getTime()));
        } else {
            ((TextView) findViewById(R.id.date_label)).setText(R.string.invalid_date);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCalcBtn(View view) {
        TextView weightTextView = findViewById(R.id.weight_input);
        TextView heightTextView = findViewById(R.id.height_input);
        Spinner genderSpinner = findViewById(R.id.gender);
        Spinner calcTypeSpinner = findViewById(R.id.calcType);
        TextView answer = findViewById(R.id.answer);


        try {
            double mass = Double.parseDouble(weightTextView.getText().toString());
            double height = Double.parseDouble(heightTextView.getText().toString());
            String gender =  String.valueOf(genderSpinner.getSelectedItem());

            String calcType = String.valueOf(calcTypeSpinner.getSelectedItem());

            boolean whichOne = calcType.equals("Harris-Benedict");//porównuje z 1wszym

            ppm.update(mass, height, gender, year, whichOne);

            answer.setText(String.valueOf(ppm.calculatePPM()));

        }catch(NumberFormatException e){
            answer.setText(R.string.error);
        }


    }

    public void onClickPickDateBtn(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
        this.year = fragment.getYear();
    }

}

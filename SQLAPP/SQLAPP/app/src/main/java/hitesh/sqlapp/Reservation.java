package hitesh.sqlapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;


import java.util.Calendar;

/**
 * Created by ColetteUmbach on 3/5/16.
 */
public class Reservation extends FragmentActivity{

    //ConnectionClass connectionClass;
    static EditText edtdate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        edtdate = (EditText) findViewById(R.id.edtdate);
    }

    public void showDatePickerDialog(View v) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public DatePickerFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            edtdate.setText(new StringBuilder().append(day).append("/")
                    .append(month).append("/").append(year));
        }
    }
}

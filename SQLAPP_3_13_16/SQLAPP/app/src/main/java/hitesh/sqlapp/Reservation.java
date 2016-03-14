package hitesh.sqlapp;

import android.app.Dialog;
import android.app.ListFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.TimePicker;
import 	android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by ColetteUmbach on 3/5/16.
 */
public class Reservation extends FragmentActivity{

    //ConnectionClass connectionClass;
    static EditText edtdate;
    static EditText edtstarttime;
    static EditText edtendtime;

    static Calendar date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        edtdate = (EditText) findViewById(R.id.edtdate);
        edtstarttime = (EditText) findViewById(R.id.edtstarttime);
        edtendtime = (EditText) findViewById(R.id.edtendtime);

    }

    /*public void setDate(GregorianCalendar date){
        this.date = date;
        edtdate.setText(new StringBuilder().append(this.date.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(this.date.get(Calendar.MONTH)).append("/").append(this.date.get(Calendar.YEAR)));
    }*/

    public void showDatePickerDialog(View v) {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new DatePickerFragment();
        dialog.show(getSupportFragmentManager(), "datePicker");
    }

    Handler h_StartTime = new Handler(){
        @Override
        public void handleMessage(Message msg){
            edtstarttime.setText(msg.getData().getString("starttime"));
        }
    };

    public void showStartTimePickerDialog(View v){
        DialogFragment dialog = new TimePickerFragment(h_StartTime,"starttime");
        dialog.show(getSupportFragmentManager(), "timePicker");
    }

    Handler h_EndTime = new Handler(){
        @Override
        public void handleMessage(Message msg){
            edtendtime.setText(msg.getData().getString("endtime"));
        }
    };

    public void showEndTimePickerDialog(View v){
        DialogFragment dialog = new TimePickerFragment(h_EndTime,"endtime");
        dialog.show(getSupportFragmentManager(), "timePicker");
    }

    public void showRoomPickerDialog(View v){
        DialogFragment dialog = new ListFragment();
        dialog.show(getSupportFragmentManager(), "roomPicker");
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
            //Note: android starts counting months at 0, so add 1 to get expected value
            edtdate.setText(new StringBuilder().append(month+1).append("/")
                    .append(day).append("/").append(year));
        }
    }

    public static class TimePickerFragment extends DialogFragment{

        Handler h;
        String timeStr;
        public TimePickerFragment(Handler arg_h, String outputStr){
            h = arg_h;
            timeStr = outputStr;
        }
        private TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString(timeStr, hourOfDay + ":" + minute);
                msg.setData(data);
                h.sendMessage(msg);
            }
        };

        public Dialog onCreateDialog(Bundle bundle){
            int hourOfDay = 12;
            int minute = 20;
            boolean is24HourView = false;
            TimePickerDialog tpdialog = new TimePickerDialog(getActivity(), callback, hourOfDay, minute, is24HourView);
            return tpdialog;
        }
    }

    public static ListFragment extends DialogFragment{

        public ListFragment(){
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.pick_color)
                    .setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            // of the selected item
                        }
                    });
            return builder.create();
        }
    }
}

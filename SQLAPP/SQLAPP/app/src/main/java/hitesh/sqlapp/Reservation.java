package hitesh.sqlapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.ListView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.*;
import java.util.Calendar;


/**
 * Created by ColetteUmbach on 3/5/16.
 */
public class Reservation extends FragmentActivity {

    static EditText edtdate;
    static EditText edtstarttime;
    static EditText edtendtime;
    static EditText edtroom;

    static String[] availRooms;
    static String listTitle = "Select a room:";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        edtdate = (EditText) findViewById(R.id.edtdate);
        edtdate.setInputType(InputType.TYPE_NULL);
        edtstarttime = (EditText) findViewById(R.id.edtstarttime);
        edtstarttime.setInputType(InputType.TYPE_NULL);
        edtendtime = (EditText) findViewById(R.id.edtendtime);
        edtendtime.setInputType(InputType.TYPE_NULL);
        edtroom = (EditText)findViewById(R.id.edtroom);
        edtroom.setInputType(InputType.TYPE_NULL);

    }

    public static String[] getAvailRooms(){
        //TODO: add code to get array from database instead of using hardcoded values
        availRooms = new String[] {"AB 1819", "Fish Bowl", "Heritage Room", "Women's resource center",
                        "McKinnon Theatre", "ECE conference room", "AB 1817", "Cribathon", "CHME Lounge",
                        "Physics Lounge", "CS Lounge", "Galaxy Lab", "Security Lab", "AB 3338"};
        return availRooms;
    }

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

    Handler h_Room = new Handler(){
        @Override
        public void handleMessage(Message msg){
            edtendtime.setText(msg.getData().getString("room"));
        }
    };

    public void showRoomPickerDialog(View view) {
        DialogFragment dialog = new MyDialogFragment(getAvailRooms());
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

    public static class MyDialogFragment extends DialogFragment implements
           AdapterView.OnItemClickListener {

        String[] listitems;// = { "item01", "item02", "item03", "item04" };

        ListView mylist;

        public MyDialogFragment(String[] list){
            listitems = list;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.list_fragment, null, false);
            mylist = (ListView) view.findViewById(R.id.list);

            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {

            super.onActivityCreated(savedInstanceState);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, listitems);

            mylist.setAdapter(adapter);

            mylist.setOnItemClickListener(this);

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            dismiss();
            //Toast.makeText(getActivity(), listitems[position], Toast.LENGTH_SHORT).show();
            edtroom.setText(new StringBuilder().append(listitems[position]));
        }

    }

}

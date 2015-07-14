package com.chenggzhang.datepicker.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import com.chenggzhang.datepicker.app.model.IDatePicker;
import com.chenggzhang.datepicker.app.presenter.DateTimePickerPresenter;
import com.chenggzhang.datepicker.app.view.IChooseDateView;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener, IChooseDateView {

    private DateTimePickerPresenter dateTimePickerPresenter;
    private int choose_year, choose_month, choose_day, choose_hour, choose_minute;
    private int default_year, default_month, default_day, default_hour, default_minute;
    private String initDateTime;
    private Boolean isDateChanged, isTimeChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_show_date_picker).
                setOnClickListener(this);
        initDateTime = "2014-1-1 11:11";
        isDateChanged = false;
        isTimeChanged = false;
//        initDateTime = null;
        dateTimePickerPresenter = new DateTimePickerPresenter(MainActivity.this, initDateTime);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show_date_picker:
                dateTimePickerPresenter.showDatePickerDialog(MainActivity.this);
                break;
            default:
                break;
        }
    }

    @Override
    public void showChooseTimeDialog() {
        View view = View.inflate(getApplicationContext(), R.layout.date_time_picker, null);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        TimePicker timePicker = (TimePicker) view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);

        //change the padding of datePicker and timePicker
//        LinearLayout cupOfDateContainer = (LinearLayout) datePicker.getChildAt(0);
//        LinearLayout cupOfDateHorizontal = (LinearLayout) cupOfDateContainer.getChildAt(0);
//        for(int i = 0 ; i<cupOfDateHorizontal.getChildCount();i++){
//            NumberPicker numberPicker = (NumberPicker) cupOfDateHorizontal.getChildAt(i);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(120, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.leftMargin = 0;
//            layoutParams.rightMargin = 30;
//            numberPicker.setLayoutParams(layoutParams);
//        }
//        LinearLayout cupOfTimeContainer = (LinearLayout) timePicker.getChildAt(0);
//        LinearLayout cupOfTimeHorizontal = (LinearLayout) cupOfTimeContainer.getChildAt(0);
//        for(int i = 0;i<cupOfTimeHorizontal.getChildCount();i++){
//            //the index of 1 is ' : '
//            if(i == 1){
//                continue;
//            }
//            NumberPicker numberPicker = (NumberPicker) cupOfTimeHorizontal.getChildAt(i);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);
//            layoutParams.leftMargin = 0 ;
//            layoutParams.rightMargin = 30 ;
//            numberPicker.setLayoutParams(layoutParams);
//        }

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        if (!TextUtils.isEmpty(initDateTime)) {
            String[] date_time = initDateTime.split("\\s+");
            String year_month_day = date_time[0];
            String hour_minute = date_time[1];

            String[] y_m_d = year_month_day.split("\\-");
            default_year = Integer.parseInt(y_m_d[0]);
            default_month = Integer.parseInt(y_m_d[1]);
            default_day = Integer.parseInt(y_m_d[2]);

            String[] h_m = hour_minute.split(":");
            default_hour = Integer.parseInt(h_m[0]);
            default_minute = Integer.parseInt(h_m[1]);
            calendar.set(default_year, default_month, default_day, default_hour, default_minute);

        } else {
            default_year = calendar.get(Calendar.YEAR);
            default_month = calendar.get(Calendar.MONTH);
            default_day = calendar.get(Calendar.DAY_OF_MONTH);
            default_hour = calendar.get(Calendar.HOUR_OF_DAY);
            default_minute = calendar.get(Calendar.MINUTE);
        }

        datePicker.init(default_year, default_month, default_day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //do something
                choose_year = year;
                choose_month = monthOfYear + 1;
                choose_day = dayOfMonth;
                isDateChanged = true;
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                choose_hour = hourOfDay;
                choose_minute = minute;
                isTimeChanged = true;
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);
        builder.setTitle("Please choose the date and time");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (dateTimePickerPresenter.isNewDateTime(isDateChanged, isTimeChanged)) {
                    if (!isDateChanged) {
                        choose_year = default_year;
                        choose_month = default_month;
                        choose_day = default_day;
                    } else if (!isTimeChanged) {
                        choose_hour = default_hour;
                        choose_minute = default_minute;
                    }
                    initDateTime = choose_year + "-" + choose_month + "-" + choose_day + " " + choose_hour + ":" + choose_minute;

                }
                dateTimePickerPresenter.doFinishLoadTime(getApplicationContext());
            }
        });
        builder.show();

    }


    @Override
    public String loadDateTime() {
        return initDateTime;
    }


}

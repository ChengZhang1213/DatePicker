package com.chenggzhang.datepicker.app.presenter;

import android.content.Context;
import android.widget.Toast;
import com.chenggzhang.datepicker.app.model.IDatePicker;
import com.chenggzhang.datepicker.app.view.IChooseDateView;

/**
 * Created by zhangcheng on 15/7/13.
 * for this module,you can control the model(Logic method) and the view(update view)
 */
public class DateTimePickerPresenter implements IDatePicker {
    private IChooseDateView iChooseDateView;
    private String initDateTime;

    public DateTimePickerPresenter(IChooseDateView iChooseDateView, String initDateTime) {
        this.iChooseDateView = iChooseDateView;
        this.initDateTime = initDateTime;

    }

    public void showDatePickerDialog(Context context) {
        iChooseDateView.showChooseTimeDialog();
    }

    public void doFinishLoadTime(Context context) {
        String time = iChooseDateView.loadDateTime();
        Toast.makeText(context, time, Toast.LENGTH_SHORT).show();

    }


    @Override
    public boolean isNewDateTime(boolean isDateChanged, boolean isTimeChanged) {
        return isDateChanged || isTimeChanged;
    }
}

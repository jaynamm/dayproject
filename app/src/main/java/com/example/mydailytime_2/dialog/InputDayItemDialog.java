package com.example.mydailytime_2.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mydailytime_2.R;
import com.google.android.material.textfield.TextInputEditText;

public class InputDayItemDialog extends DialogFragment implements View.OnClickListener {
    private static final String DayItem_DIALOG_ITEM_ID = "DayItem_item_id";
    private static final String DayItem_DIALOG_ITEM_TITLE = "DayItem_item_title";
    private static final String DayItem_DIALOG_ITEM_CONTENT = "DayItem_item_content";
    private static final String DayItem_DIALOG_ITEM_TITLE_TIME = "DayItem_item_title_time";
    private  static final String REQUEST_RESET_DayItem  = "request_reset_DayItem";
    //private static final int LAYOUT = R.layout.input_DayItem_dialog;

    private onSaveButtonClickListener mSaveListener;
    private TextInputEditText DayItemTitleDialog;
    private TextInputEditText DayItemContentDialog;
    private TextView timeTitle;
    private TextView saveButton;
    private TextView backButton;
    private int mDayItemId;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.DayItemInputDialog_save:
                String DayItemTimeTitle = timeTitle.getText().toString();
                String DayItemTitle = DayItemTitleDialog.getText().toString();
                String DayItemContent = DayItemContentDialog.getText().toString();
                mSaveListener.onSaveButtonClick(mDayItemId,DayItemTimeTitle,DayItemTitle,DayItemContent);
                dismiss();
                break;
            case R.id.DayItemInputDialog_cancle:
                mSaveListener.onCancelButtonClick();
                dismiss();
                break;
        }

    }

    public interface onSaveButtonClickListener{
        void onSaveButtonClick(int mDayItemId,String mDayItemTime,String DayItemTitle,String DayItemContent);
        void onCancelButtonClick();
    }
    public void setOnSaveButtonClickListener(onSaveButtonClickListener listener){
        mSaveListener = listener;
    }


    public static InputDayItemDialog newInstance(int itemId,String time, String title, String content) {
        Bundle bundle = new Bundle();
        bundle.putInt(DayItem_DIALOG_ITEM_ID, itemId);
        bundle.putString(DayItem_DIALOG_ITEM_TITLE, title);
        bundle.putString(DayItem_DIALOG_ITEM_CONTENT, content);
        bundle.putString(DayItem_DIALOG_ITEM_TITLE_TIME, time);

        InputDayItemDialog fragment = new InputDayItemDialog();
        fragment.setArguments(bundle);
        return fragment;
    }
    //onCreateView 주석처리
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_dayitem_dialog, container, false);
        DayItemTitleDialog = (TextInputEditText)view.findViewById(R.id.DayItemInputdialog_Title);
        DayItemContentDialog = (TextInputEditText)view.findViewById(R.id.DayItemInputDialog_Content);

        timeTitle = (TextView)view.findViewById(R.id.DayItemInputDialog_MainTitle_time);
        saveButton = (TextView) view.findViewById(R.id.DayItemInputDialog_save);
        backButton = (TextView) view.findViewById(R.id.DayItemInputDialog_cancle);

        if (getArguments() != null){
            mDayItemId = getArguments().getInt(DayItem_DIALOG_ITEM_ID,-1);
            DayItemTitleDialog.setText(getArguments().getString(DayItem_DIALOG_ITEM_TITLE));
            DayItemContentDialog.setText(getArguments().getString(DayItem_DIALOG_ITEM_CONTENT));
            timeTitle.setText(getArguments().getString(DayItem_DIALOG_ITEM_TITLE_TIME));
        }
        saveButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        getArgument에 값이 없으면 데이터를 설정
//        if (getArguments() != null) {
//            mSalutation = getArguments().getString(WHICH_SALUTATION);
//        }
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}

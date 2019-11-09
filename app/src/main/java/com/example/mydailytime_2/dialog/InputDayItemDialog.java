package com.example.mydailytime_2.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.mydailytime_2.R;
import com.example.mydailytime_2.helper.DayItemVO;
import com.google.android.material.textfield.TextInputEditText;

public class InputDayItemDialog extends DialogFragment implements View.OnClickListener {
    private  static final String INPUT_DIALOG_DAYITEM  = "input_dialog_dayitem";
    //private static final int LAYOUT = R.layout.input_DayItem_dialog;

    private onSaveButtonClickListener mSaveListener;
    private TextInputEditText DayItemTitleDialog;
    private TextInputEditText DayItemContentDialog;
    private TextView timeTitle;
    private TextView saveButton;
    private TextView backButton;
    private DayItemVO itemTemp;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.DayItemInputDialog_save:
                String DayItemTitle = DayItemTitleDialog.getText().toString();
                String DayItemContent = DayItemContentDialog.getText().toString();
                itemTemp.setItemTitle(DayItemTitle);
                itemTemp.setItemContent(DayItemContent);

                mSaveListener.onSaveButtonClick(itemTemp);
                dismiss();
                break;
            case R.id.DayItemInputDialog_cancle:
                mSaveListener.onCancelButtonClick();
                dismiss();
                break;
        }

    }

    public interface onSaveButtonClickListener{
        void onSaveButtonClick(DayItemVO dayItemVO);
        void onCancelButtonClick();
    }
    public void setOnSaveButtonClickListener(onSaveButtonClickListener listener){
        mSaveListener = listener;
    }


    public static InputDayItemDialog newInstance(DayItemVO dayItemVO) {
        Bundle bundle = new Bundle();
//        bundle.putInt(DayItem_DIALOG_ITEM_ID, itemId);
//        bundle.putString(DayItem_DIALOG_ITEM_TITLE, title);
//        bundle.putString(DayItem_DIALOG_ITEM_CONTENT, content);
//        bundle.putString(DayItem_DIALOG_ITEM_TITLE_TIME, time);
        Log.d("InputDayItemDialog", "newInstance실행 ");
        bundle.putSerializable(INPUT_DIALOG_DAYITEM,dayItemVO);
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
            itemTemp= (DayItemVO) getArguments().get(INPUT_DIALOG_DAYITEM);
            DayItemTitleDialog.setText(itemTemp.getItemTitle());
            DayItemContentDialog.setText(itemTemp.getItemContent());
            timeTitle.setText(itemTemp.getItemTime());
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

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
import com.example.mydailytime_2.helper.MemoVO;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;

public class InputMemoDialog extends DialogFragment implements View.OnClickListener{
    private static final String MEMO_DIALOG_ITEM_ID = "memo_item_id";
    private static final String MEMO_DIALOG_ITEM_TITLE = "memo_item_title";
    private static final String MEMO_DIALOG_ITEM_CONTENT = "memo_item_content";
    private  static final String MEMO_DIALOG_ITEMS  = "memo_items";
    //private static final int LAYOUT = R.layout.input_memo_dialog;

    private onSaveButtonClickListener mSaveListener;
    private TextInputEditText memoTitleDialog;
    private TextInputEditText memoContentDialog;
    private TextView saveButton;
    private TextView backButton;
    private int mMemoId;
    private MemoVO memoTemp;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.memoInputdialog_save:
                String memoTitle = memoTitleDialog.getText().toString();
                String memoContent = memoContentDialog.getText().toString();
                memoTemp.setMemoTitle(memoTitle);
                memoTemp.setMemoContent(memoContent);
                mSaveListener.onSaveButtonClick(memoTemp);
                dismiss();
                break;
            case R.id.memoInputdialog_cancle:
                mSaveListener.onCancelButtonClick();
                dismiss();
                break;
        }

    }

    public interface onSaveButtonClickListener{
        void onSaveButtonClick(MemoVO memoVO);
        void onCancelButtonClick();
    }
    public void setOnSaveButtonClickListener(onSaveButtonClickListener listener){
        mSaveListener = listener;
    }


    public static InputMemoDialog newInstance(MemoVO memoVO) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MEMO_DIALOG_ITEMS, (Serializable) memoVO);
//        bundle.putInt(MEMO_DIALOG_ITEM_ID, itemId);
//        bundle.putString(MEMO_DIALOG_ITEM_TITLE, title);
//        bundle.putString(MEMO_DIALOG_ITEM_CONTENT, content);

        InputMemoDialog fragment = new InputMemoDialog();
        fragment.setArguments(bundle);
        return fragment;
    }
//onCreateView 주석처리
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_memo_dialog, container, false);
         memoTitleDialog = (TextInputEditText)view.findViewById(R.id.memoInputdialog_Title);
         memoContentDialog = (TextInputEditText)view.findViewById(R.id.memoInputdialog_Content);

         saveButton = (TextView) view.findViewById(R.id.memoInputdialog_save);
         backButton = (TextView) view.findViewById(R.id.memoInputdialog_cancle);

        if (getArguments() != null){
            memoTemp= (MemoVO) getArguments().get(MEMO_DIALOG_ITEMS);
            memoTitleDialog.setText(memoTemp.getMemoTitle());
            memoContentDialog.setText(memoTemp.getMemoContent());
//            mMemoId = getArguments().getInt(MEMO_DIALOG_ITEM_ID,-1);
//            memoTitleDialog.setText(getArguments().getString(MEMO_DIALOG_ITEM_TITLE));
//            memoContentDialog.setText(getArguments().getString(MEMO_DIALOG_ITEM_CONTENT));
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

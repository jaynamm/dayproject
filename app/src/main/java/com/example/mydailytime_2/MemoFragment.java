package com.example.mydailytime_2;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mydailytime_2.dialog.InputMemoDialog;
import com.example.mydailytime_2.dummy.MemoItemContent.MemoItemVO;
import com.example.mydailytime_2.helper.MemoContract;
import com.example.mydailytime_2.helper.MemoDBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MemoFragment extends Fragment {

    private MyMemoRecyclerViewAdapter memoAdapter;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
//    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MemoFragment() {
    }

    // TODO: Customize parameter initialization
    public static MemoFragment newInstance(int columnCount) {
        MemoFragment fragment = new MemoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo_list, container, false);

        // Set the adapter
        FloatingActionButton addMemoBtn = (FloatingActionButton)view.findViewById(R.id.addMemoBtn);
        RecyclerView memoRecyclerView = view.findViewById(R.id.memoList);

        if (memoRecyclerView != null) {
            Context context = view.getContext();
            Cursor cursor = getMemoCursor();

            if (mColumnCount <= 1) {
                memoRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                memoRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
             memoAdapter = new MyMemoRecyclerViewAdapter(getActivity(), cursor);
            memoRecyclerView.setAdapter(memoAdapter);
        }

        addMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputMemoDialog(null,null,null);
            }
        });

        memoAdapter.setMyMemoItemClickedListener(new MyMemoRecyclerViewAdapter.memoItemClickedListener() {
            @Override
            public void memoItemClicked(MemoItemVO memoitemVO) {
                showInputMemoDialog(memoitemVO.getMemoId(),memoitemVO.getMemoTitle(),memoitemVO.getMemoContent());
            }
        });

        memoAdapter.setMyMemoItemLongClickedListener(new MyMemoRecyclerViewAdapter.memoItemLongClickedListener() {
            @Override
            public void memoItemLongClicked(MemoItemVO memoItemVO) {
                final String deleteId = memoItemVO.getMemoId();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("메모 삭제");
                builder.setMessage("메모를 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db =MemoDBHelper.getInstance(getActivity()).getWritableDatabase();
                        int deleteCount =db.delete(MemoContract.MemoEntry.MEMOTABLE_NAME,
                                    MemoContract.MemoEntry._ID+" = "+deleteId,null);
                        if (deleteCount==0){
                            Toast.makeText(getActivity(), "삭제에 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                        }else {
                            memoAdapter.swapCursor(getMemoCursor());
                            Toast.makeText(getActivity(), "삭제 완료", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("취소" ,null);
                builder.show();

            }
        });


        return view;
    }
    
    private void showInputMemoDialog(String id,String title,String content) {
        InputMemoDialog inputMemoDialog = InputMemoDialog.newInstance(id,title,content);
        inputMemoDialog.setOnSaveButtonClickListener(new InputMemoDialog.onSaveButtonClickListener() {
            @Override
            public void onSaveButtonClick(String mMemoId,String memoTitle, String memoContent) {

                Toast.makeText(getActivity(), "Title :"+memoTitle+"memo :"+memoContent, Toast.LENGTH_SHORT).show();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MemoContract.MemoEntry.COLUCMN_NAME_MEMOTITLE,memoTitle);
                contentValues.put(MemoContract.MemoEntry.COLUCMN_NAME_MEMOCONTENTS,memoContent);

                SQLiteDatabase db = MemoDBHelper.getInstance(getContext()).getWritableDatabase();

                if (mMemoId.equals("-1")){
                    long newRowId = db.insert(MemoContract.MemoEntry.MEMOTABLE_NAME,null,contentValues);
                    if (newRowId == -1) {
                        Toast.makeText(getContext(), "저장에 문제발생 ", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "저장완료", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    int upDateRowId=db.update(MemoContract.MemoEntry.MEMOTABLE_NAME,contentValues,MemoContract.MemoEntry._ID+" = "+mMemoId,null);
                    if (upDateRowId == 0) {
                        Toast.makeText(getContext(), "저장에 문제발생 ", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "저장완료", Toast.LENGTH_SHORT).show();
                    }
                }

                memoAdapter.swapCursor(getMemoCursor());
            }

            @Override
            public void onCancelButtonClick() {
                Toast.makeText(getActivity(), "cancel button", Toast.LENGTH_SHORT).show();
            }
        });


        assert getFragmentManager() != null;
        inputMemoDialog.show(getFragmentManager(), "fragment_dialog_new");
    }

    private Cursor getMemoCursor() {
        MemoDBHelper memoDBHelper = MemoDBHelper.getInstance(getActivity());
        return memoDBHelper.getReadableDatabase().
               query(MemoContract.MemoEntry.MEMOTABLE_NAME,
                       null,null,null,null,null,MemoContract.MemoEntry._ID+" DESC");
    }

    //OnListFragmentInteractionListener 를 상속받지 못했을때 발생하는 메소드
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contai n this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnListFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onListFragmentInteraction(MemoItemVO item);
//    }


}

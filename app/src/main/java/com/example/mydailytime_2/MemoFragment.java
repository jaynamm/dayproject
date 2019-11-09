package com.example.mydailytime_2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailytime_2.dialog.InputMemoDialog;
import com.example.mydailytime_2.helper.MemoVO;
import com.example.mydailytime_2.viewModel.MemoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MemoFragment extends Fragment {

    private MemoViewModel memoViewModel;
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
        memoViewModel = ViewModelProviders.of(this).get(MemoViewModel.class);
        memoViewModel.getAll().observe(this, memoVOS -> memoAdapter.setData(memoVOS));

        // Set the adapter
        FloatingActionButton addMemoBtn = (FloatingActionButton)view.findViewById(R.id.addMemoBtn);
        RecyclerView memoRecyclerView = view.findViewById(R.id.memoList);

        if (memoRecyclerView != null) {
            Context context = view.getContext();

            if (mColumnCount <= 1) {
                memoRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                memoRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
             memoAdapter = new MyMemoRecyclerViewAdapter(getActivity());
            memoRecyclerView.setAdapter(memoAdapter);
        }

        addMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputMemoDialog(new MemoVO());
            }
        });

        memoAdapter.setMyMemoItemClickedListener(new MyMemoRecyclerViewAdapter.memoItemClickedListener() {
            @Override
            public void memoItemClicked(MemoVO memoVO) {
                showInputMemoDialog(memoVO);

            }
        });

        memoAdapter.setMyMemoItemLongClickedListener(new MyMemoRecyclerViewAdapter.memoItemLongClickedListener() {
            @Override
            public void memoItemLongClicked(MemoVO memoVO) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("메모 삭제");
                builder.setMessage("메모를 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        memoViewModel.delete(memoVO);
                    }
                });
                builder.setNegativeButton("취소" ,null);
                builder.show();
            }

        });


        return view;
    }

    //다이얼로그를 호출하고 받은 데이터를 가져와서 DB에 저장
    private void showInputMemoDialog(MemoVO memoVO) {
        InputMemoDialog inputMemoDialog = InputMemoDialog.newInstance(memoVO);
        inputMemoDialog.setOnSaveButtonClickListener(new InputMemoDialog.onSaveButtonClickListener() {

            @Override
            public void onSaveButtonClick(MemoVO memoVO) {
                Toast.makeText(getActivity(), "Title :"+memoVO.getMemoTitle()+"memo :"+memoVO.getMemoContent(), Toast.LENGTH_SHORT).show();
                memoViewModel.insert(memoVO);
            }

            @Override
            public void onCancelButtonClick() {
                Toast.makeText(getActivity(), "cancel button", Toast.LENGTH_SHORT).show();
            }
        });


        assert getFragmentManager() != null;
        inputMemoDialog.show(getFragmentManager(), "fragment_dialog_new");
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
//        void onListFragmentInteraction(MemoItemVO item);
//    }


}

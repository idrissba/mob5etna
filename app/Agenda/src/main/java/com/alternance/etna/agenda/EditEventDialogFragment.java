package com.alternance.etna.agenda;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.WindowManager;
import android.support.annotation.Nullable;





public class EditEventDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";

    private EditText mEventNote;
    private EditText mEventStartDate;
    private EditText mEventStartTime;
    private EditText mEventEndDate;
    private EditText mEventEndTime;
    private Button   mEventSave;


    public EditEventDialogFragment() {
        // Required empty public constructor
    }

    public static EditEventDialogFragment newInstance(String pTitle) {
        EditEventDialogFragment fragment = new EditEventDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, pTitle);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_event_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEventNote = (EditText) view.findViewById(R.id.edit_event_note);
        mEventStartDate = (EditText) view.findViewById(R.id.edit_event_start_date);
        mEventStartTime = (EditText) view.findViewById(R.id.edit_event_start_time);
        mEventEndDate = (EditText) view.findViewById(R.id.edit_event_end_date);
        mEventEndTime = (EditText) view.findViewById(R.id.edit_event_end_time);
        mEventSave = (Button) view.findViewById(R.id.edit_event_save);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEventNote.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}

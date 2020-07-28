package com.codeunite.paymyrch.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.codeunite.paymyrch.AsyncTask.DisputeRepleyTask;
import com.codeunite.paymyrch.AsyncTask.DisputeReportChatTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.activity.ErrorActivity;
import com.codeunite.paymyrch.adapter.ChatHistoryAdapter;

public class DispteReportHistoryDialog extends DialogFragment implements OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ChatHistoryAdapter adapter;
    Dialog dialog;
    ListView listView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    String message;
    ProgressDialog progressDialog;
    EditText repley_message;
    Button send_repley;
    String tiketID;
    String transID;
    TextView txt_transId;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DispteReportHistoryDialog$1 */
    class C05581 implements OnClickListener {
        C05581() {
        }

        public void onClick(View v) {
            DispteReportHistoryDialog.this.message = DispteReportHistoryDialog.this.repley_message.getText().toString();
            if (DispteReportHistoryDialog.this.message.equalsIgnoreCase("")) {
                new SweetAlertDialog(DispteReportHistoryDialog.this.getContext(), 3).setTitleText("Something Wrong...").setContentText("Please enter message...").show();
            } else {
                new DisputeRepleyTask(DispteReportHistoryDialog.this.getContext(), DispteReportHistoryDialog.this.transID, DispteReportHistoryDialog.this.message, DispteReportHistoryDialog.this.progressDialog, DispteReportHistoryDialog.this, DispteReportHistoryDialog.this.repley_message).execute(new String[0]);
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DispteReportHistoryDialog$2 */
    class C05592 implements OnClickListener {
        C05592() {
        }

        public void onClick(View view) {
            DispteReportHistoryDialog.this.dialog.dismiss();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static DispteReportHistoryDialog newInstance(String param1, String param2) {
        DispteReportHistoryDialog fragment = new DispteReportHistoryDialog();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        this.dialog = new Dialog(getContext(), R.style.DialogFragmentTheme);
        this.dialog.setContentView(R.layout.fragment_dispte_report_history_dialog);
        this.tiketID = getArguments().getString("tiket_id");
        this.transID = getArguments().getString("trans_id");
        this.listView = (ListView) this.dialog.findViewById(R.id.list_dispute_history);
        this.repley_message = (EditText) this.dialog.findViewById(R.id.edt_repley);
        this.send_repley = (Button) this.dialog.findViewById(R.id.btn_repley);
        this.txt_transId = (TextView) this.dialog.findViewById(R.id.select_circle);
        this.txt_transId.setText(this.transID);
        this.progressDialog = new ProgressDialog(getActivity());
        ImageView right_arrow = (ImageView) this.dialog.findViewById(R.id.back);
        if (isNetworkAvailable()) {
            new DisputeReportChatTask(getContext(), this.progressDialog, this.adapter, this.listView, this.tiketID).execute(new String[0]);
        } else {
            startActivity(new Intent(getContext(), ErrorActivity.class));
        }
        this.send_repley.setOnClickListener(new C05581());
        right_arrow.setOnClickListener(new C05592());
        this.dialog.show();
        return this.dialog;
    }

    public void onButtonPressed(Uri uri) {
        if (this.mListener != null) {
            this.mListener.onFragmentInteraction(uri);
        }
    }

    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    public void onClick(View v) {
    }

    public void chat_history() {
        new DisputeReportChatTask(getActivity(), this.progressDialog, this.adapter, this.listView, this.tiketID).execute(new String[0]);
    }

    private boolean isNetworkAvailable() {
        return ((ConnectivityManager) getActivity().getSystemService("connectivity")).getActiveNetworkInfo() != null;
    }
}

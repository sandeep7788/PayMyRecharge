package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codeunite.paymyrch.AsyncTask.DealerOperatorStatusTask;
import com.codeunite.paymyrch.AsyncTask.OperatorActiveDeactiveTask;
import com.codeunite.paymyrch.AsyncTask.RetailerListTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.DistributerOPStatusAdapter;
import com.codeunite.paymyrch.adapter.StatusModel;
import com.codeunite.paymyrch.adapter.VolleySingleton;
import com.codeunite.paymyrch.utils.ServerUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DistributerOperatorStatus extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String[] retailer_Id;
    DistributerOPStatusAdapter adapter;
    Context context;
    ListView listView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    RequestQueue myRequestQueue;
    String opCode;
    String opName;
    String opStatus;
    String paramStatus;
    ProgressDialog progressDialog;
    String re;
    StringBuffer retailerDetails;
    ArrayAdapter<String> retailerid;
    Spinner spRetailerId;
    VolleySingleton volleySingleton;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DistributerOperatorStatus$1 */
    class C05601 implements OnItemSelectedListener {
        C05601() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int position, long arg3) {
            String retailerData = RetailerListTask.retailer_id[position];
            if (retailerData.length() >= 4) {
                String[] temp = retailerData.split(":");
                DistributerOperatorStatus.this.re = temp[0];
                Log.d("DAY1", DistributerOperatorStatus.this.re);
            }
            new DealerOperatorStatusTask(DistributerOperatorStatus.this.getActivity(), DistributerOperatorStatus.this.progressDialog, DistributerOperatorStatus.this.re, DistributerOperatorStatus.this.adapter, DistributerOperatorStatus.this.listView).execute(new String[0]);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DistributerOperatorStatus$2 */
    class C05632 implements OnItemClickListener {
        String message;

        /* renamed from: com.codeunite.PayMyRecharge.fragment.DistributerOperatorStatus$2$1 */
        class C05611 implements OnClickListener {
            C05611() {
            }

            public void onClick(DialogInterface dialog, int which) {
            }
        }

        /* renamed from: com.codeunite.PayMyRecharge.fragment.DistributerOperatorStatus$2$2 */
        class C05622 implements OnClickListener {
            C05622() {
            }

            public void onClick(DialogInterface dialog, int which) {
                new OperatorActiveDeactiveTask(DistributerOperatorStatus.this.getActivity(), DistributerOperatorStatus.this.re, DistributerOperatorStatus.this.opCode, DistributerOperatorStatus.this, DistributerOperatorStatus.this.opStatus).execute(new String[0]);
            }
        }

        C05632() {
        }

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("PRINT-INSIDE", String.valueOf(position));
            StatusModel dataHolder = (StatusModel) parent.getAdapter().getItem(position);
            DistributerOperatorStatus.this.opName = dataHolder.operatoeName2;
            DistributerOperatorStatus.this.opCode = dataHolder.operatorCode2;
            DistributerOperatorStatus.this.opStatus = dataHolder.operatorStatus2;
            if (DistributerOperatorStatus.this.opStatus.equalsIgnoreCase("Activated")) {
                this.message = "Do you want to deactivate this operator?";
                DistributerOperatorStatus.this.paramStatus = "true";
            } else if (DistributerOperatorStatus.this.opStatus.equalsIgnoreCase("Deactivated")) {
                DistributerOperatorStatus.this.paramStatus = "false";
                this.message = "Do you want to activate this operator?";
            }
            Log.d("PRINT-INSIDE", DistributerOperatorStatus.this.opName + " " + DistributerOperatorStatus.this.opCode + " " + DistributerOperatorStatus.this.opStatus);
            Builder alertDialog = new Builder(DistributerOperatorStatus.this.getActivity());
            alertDialog.setTitle((CharSequence) "Please Confirm");
            alertDialog.setIcon((int) R.mipmap.ic_launcher);
            alertDialog.setMessage(this.message);
            alertDialog.setPositiveButton((CharSequence) "Yes", new C05622()).setNegativeButton((CharSequence) "No", new C05611()).show();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DistributerOperatorStatus$3 */
    class C05643 implements Listener<String> {
        C05643() {
        }

        public void onResponse(String response) {
            Log.d("VOLLEY ACTIVE", response);
            DistributerOperatorStatus.this.UpdateOpStatusList();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.DistributerOperatorStatus$4 */
    class C05654 implements ErrorListener {
        C05654() {
        }

        public void onErrorResponse(VolleyError error) {
            if (DistributerOperatorStatus.this.progressDialog != null) {
                DistributerOperatorStatus.this.progressDialog.dismiss();
            }
            Toast.makeText(DistributerOperatorStatus.this.getActivity(), "Something went wrong: Please check your network connection", 0).show();
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static DistributerOperatorStatus newInstance(String param1, String param2) {
        DistributerOperatorStatus fragment = new DistributerOperatorStatus();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distributer_operator_status, container, false);
        this.listView = (ListView) view.findViewById(R.id.list_dealer_operator_status);
        this.progressDialog = new ProgressDialog(getActivity());
        this.volleySingleton = VolleySingleton.getInstance(getActivity().getApplicationContext());
        this.myRequestQueue = this.volleySingleton.getRequestQueue();
        this.spRetailerId = (Spinner) view.findViewById(R.id.spRetailer1);
        this.retailerDetails = new StringBuffer();
        new RetailerListTask(getActivity(), this.progressDialog, retailer_Id, this.retailerid, this.spRetailerId).execute(new String[0]);
        this.spRetailerId.setOnItemSelectedListener(new C05601());
        this.listView.setOnItemClickListener(new C05632());
        return view;
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

    public void UpdateOpStatusList() {
        new DealerOperatorStatusTask(getActivity(), this.progressDialog, this.re, this.adapter, this.listView).execute(new String[0]);
    }

    void changeOperatorStatus() {
        StringRequest stringRequest = new StringRequest(1, ServerUtils.OperatorActiveDeactiveStatusUrl, new C05643(), new C05654()) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put("MemberId", DistributerOperatorStatus.this.re);
                params.put("OperatorCodeID", DistributerOperatorStatus.this.opCode);
                params.put("Status", DistributerOperatorStatus.this.paramStatus);
                Log.d("VOLLEY PARAMS", DistributerOperatorStatus.this.paramStatus);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(20), 1, 1.0f));
        this.myRequestQueue.add(stringRequest);
    }
}

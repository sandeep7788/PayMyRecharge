package com.codeunite.paymyrch.adapter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.codeunite.paymyrch.AsyncTask.DisputeTask;
import com.codeunite.paymyrch.AsyncTask.RechargeTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.GroupReport;
import com.codeunite.paymyrch.utils.CommonUtils;

public class DealerExpandableListAdapter extends BaseExpandableListAdapter {
    String OperatorCode;
    String OperatorName;
    String[] SPINNERCODES = new String[]{"AC", "AT", "BR", "TD", "IC", "MT", "RG", "UN", "VF", "MTT", "VG", "VI", "RJIO"};
    String[] SPINNERVALUES = new String[]{"Aircel", "Airtel", "Bsnl", "Docomo", "Idea", "Mts", "Reliance", "Telenor", "Vodafone", "Mtnl", "Virgin", "Videocon", "JIO"};
    public Activity activity;
    String amount;
    EditText amount_retry;
    View change_Operator;
    Dialog dialog;
    Dialog dialog1;
    GroupReport group;
    private final SparseArray<GroupReport> groups;
    public LayoutInflater inflater;
    ListView lvSearchMobile;
    EditText mobileNumber_retry;
    String mobno;
    LinearLayout op;
    TextView op_change;
    String op_code;
    ImageView operatorImageView;
    int[] prepaidImageId = new int[]{R.drawable.aircel, R.drawable.airtel, R.drawable.bsnl, R.drawable.docomo, R.drawable.idea, R.drawable.mts, R.drawable.reliance, R.drawable.telenor, R.drawable.vodafone, R.drawable.mtnl, R.drawable.virgin, R.drawable.videocon_mobile, R.drawable.jio};
    ProgressDialog progressDialog;
    RadioButton rbOperatorOne;
    RadioButton rbOperatorTwo;
    RadioGroup rgOperatorCode;
    TextView selected_op;
    Button send_recharge_retry;
    TextView showPlan;
    String status;
    TextView tvBalance;
    TextView tv_operator;

    /* renamed from: com.codeunite.PayMyRecharge.adapter.DealerExpandableListAdapter$2 */
    class C05202 implements OnClickListener {
        C05202() {
        }

        public void onClick(View v) {
            DealerExpandableListAdapter.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.adapter.DealerExpandableListAdapter$3 */
    class C05213 implements OnClickListener {
        C05213() {
        }

        public void onClick(View v) {
            DealerExpandableListAdapter.this.selectOperator();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.adapter.DealerExpandableListAdapter$4 */
    class C05224 implements OnClickListener {
        C05224() {
        }

        public void onClick(View v) {
            DealerExpandableListAdapter.this.mobno = DealerExpandableListAdapter.this.mobileNumber_retry.getText().toString();
            DealerExpandableListAdapter.this.amount = DealerExpandableListAdapter.this.amount_retry.getText().toString();
            new RechargeTask(DealerExpandableListAdapter.this.activity, CommonUtils.userName, DealerExpandableListAdapter.this.tvBalance, DealerExpandableListAdapter.this.mobno, DealerExpandableListAdapter.this.amount.split("\\.")[0], DealerExpandableListAdapter.this.OperatorCode, DealerExpandableListAdapter.this.progressDialog).execute(new String[0]);
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.adapter.DealerExpandableListAdapter$5 */
    class C05235 implements OnItemClickListener {
        C05235() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            DealerExpandableListAdapter.this.OperatorName = DealerExpandableListAdapter.this.SPINNERVALUES[i];
            DealerExpandableListAdapter.this.OperatorCode = DealerExpandableListAdapter.this.SPINNERCODES[i];
            int img = DealerExpandableListAdapter.this.prepaidImageId[i];
            if (DealerExpandableListAdapter.this.OperatorCode.equalsIgnoreCase("BR")) {
                DealerExpandableListAdapter.this.rgOperatorCode.setVisibility(View.VISIBLE);
                DealerExpandableListAdapter.this.rbOperatorOne.setText("Topup");
                DealerExpandableListAdapter.this.rbOperatorTwo.setText("Special");
            } else if (DealerExpandableListAdapter.this.OperatorCode.equalsIgnoreCase("VI")) {
                DealerExpandableListAdapter.this.rgOperatorCode.setVisibility(View.VISIBLE);
                DealerExpandableListAdapter.this.rbOperatorOne.setText("Topup");
                DealerExpandableListAdapter.this.rbOperatorTwo.setText("Special");
            } else if (DealerExpandableListAdapter.this.OperatorCode.equalsIgnoreCase("UN")) {
                DealerExpandableListAdapter.this.rgOperatorCode.setVisibility(View.VISIBLE);
                DealerExpandableListAdapter.this.rbOperatorOne.setText("Topup");
                DealerExpandableListAdapter.this.rbOperatorTwo.setText("Special");
            } else {
                DealerExpandableListAdapter.this.rgOperatorCode.setVisibility(View.GONE);
            }
            DealerExpandableListAdapter.this.operatorImageView.setImageResource(img);
            DealerExpandableListAdapter.this.selected_op.setText(DealerExpandableListAdapter.this.OperatorName);
            DealerExpandableListAdapter.this.tv_operator.setText(DealerExpandableListAdapter.this.OperatorName);
            DealerExpandableListAdapter.this.dialog.dismiss();
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.adapter.DealerExpandableListAdapter$6 */
    class C05246 implements OnClickListener {
        C05246() {
        }

        public void onClick(View view) {
            DealerExpandableListAdapter.this.dialog.dismiss();
        }
    }

    public DealerExpandableListAdapter(Activity act, SparseArray<GroupReport> groups, TextView tvBalance, ProgressDialog progressDialog) {
        this.activity = act;
        this.groups = groups;
        this.tvBalance = tvBalance;
        this.progressDialog = progressDialog;
        this.inflater = act.getLayoutInflater();
    }

    public Object getChild(int groupPosition, int childPosition) {
        return ((GroupReport) this.groups.get(groupPosition)).children.get(childPosition);
    }

    public void clear() {
        this.groups.clear();
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long) childPosition;
    }

    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String children = (String) getChild(groupPosition, childPosition);
        final GroupReport group = (GroupReport) getGroup(groupPosition);
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.listrow_details, parent, false);
        }
        TextView text = (TextView) convertView.findViewById(R.id.textView2);
        text.setText(children);
        if (childPosition == 6) {
            text.setVisibility(View.GONE);
        }
        if (childPosition == 6 && this.status.equalsIgnoreCase("Success")) {
            text.setVisibility(View.VISIBLE);
            text.setTypeface(null, Typeface.BOLD);
            text.setTextSize(15.0f);
            text.setTextColor(Color.parseColor("#03A9F4"));
        } else {
            text.setTypeface(null, Typeface.NORMAL);
            text.setTextColor(Color.parseColor("#000000"));
        }
        text.setOnClickListener(new OnClickListener() {

            /* renamed from: com.codeunite.PayMyRecharge.adapter.DealerExpandableListAdapter$1$1 */
            class C05171 implements DialogInterface.OnClickListener {
                C05171() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    new DisputeTask(DealerExpandableListAdapter.this.activity, group.getRequestId()).execute(new String[0]);
                }
            }

            /* renamed from: com.codeunite.PayMyRecharge.adapter.DealerExpandableListAdapter$1$2 */
            class C05182 implements DialogInterface.OnClickListener {
                C05182() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }

            public void onClick(View v) {
                if (childPosition == 6) {
                    Builder builder = new Builder(DealerExpandableListAdapter.this.activity);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure to dispute?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Yes", new C05171());
                    builder.setNegativeButton("No", new C05182());
                    builder.create();
                    builder.show();
                }
            }
        });
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return ((GroupReport) this.groups.get(groupPosition)).children.size();
    }

    public Object getGroup(int groupPosition) {
        return this.groups.get(groupPosition);
    }

    public int getGroupCount() {
        return this.groups.size();
    }

    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    public long getGroupId(int groupPosition) {
        return 0;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.dealer_list_group_rech, parent, false);
        }
        this.group = (GroupReport) getGroup(groupPosition);
        ((TextView) convertView.findViewById(R.id.req1)).setText(this.group.getRequestId());
        ((TextView) convertView.findViewById(R.id.custno1)).setText(this.group.getCustomerNo());
        ((TextView) convertView.findViewById(R.id.amount1)).setText(this.group.getAmount());
        ((TextView) convertView.findViewById(R.id.status1)).setText(this.group.getStatus());
        this.status = this.group.getStatus();
        if (this.status.equalsIgnoreCase("Failed")) {
            ((TextView) convertView.findViewById(R.id.status1)).setTextColor(Color.parseColor("#D50000"));
        } else if (this.status.equalsIgnoreCase("Success")) {
            ((TextView) convertView.findViewById(R.id.status1)).setTextColor(Color.parseColor("#00C853"));
        }
        return convertView;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void recharge() {
        this.dialog1 = new Dialog(this.activity);
        this.dialog1.setContentView(R.layout.retry_recharge);
        this.dialog1.setTitle("Retry Recharge");
        this.mobileNumber_retry = (EditText) this.dialog1.findViewById(R.id.edtMobile);
        this.amount_retry = (EditText) this.dialog1.findViewById(R.id.edtAmount);
        this.operatorImageView = (ImageView) this.dialog1.findViewById(R.id.operatorimg);
        this.tv_operator = (TextView) this.dialog1.findViewById(R.id.operator);
        this.send_recharge_retry = (Button) this.dialog1.findViewById(R.id.btnRecharge);
        this.op = (LinearLayout) this.dialog1.findViewById(R.id.view_op);
        this.rgOperatorCode = (RadioGroup) this.dialog1.findViewById(R.id.rgOperatorCodes);
        this.rbOperatorOne = (RadioButton) this.dialog1.findViewById(R.id.rbOperatorOne);
        this.rbOperatorTwo = (RadioButton) this.dialog1.findViewById(R.id.rbOperatorTwo);
        this.mobileNumber_retry.setText(this.group.getCustomerNo());
        this.amount_retry.setText(this.group.getAmount());
        this.op.setOnClickListener(new C05202());
        this.tv_operator.setOnClickListener(new C05213());
        this.send_recharge_retry.setOnClickListener(new C05224());
        this.dialog1.show();
    }

    public void selectOperator() {
        this.dialog = new Dialog(this.activity, R.style.DialogFragmentTheme);
        this.dialog.setContentView(R.layout.fragment_prepaid_list);
        this.lvSearchMobile = (ListView) this.dialog.findViewById(R.id.listview134);
        ImageView right_arrow = (ImageView) this.dialog.findViewById(R.id.back);
        this.op_change = (TextView) this.dialog.findViewById(R.id.change_op);
        this.selected_op = (TextView) this.dialog.findViewById(R.id.select_op);
        this.change_Operator = this.dialog.findViewById(R.id.op_change_layout);
        this.lvSearchMobile.setAdapter(new OfflineImageSpinnerAdapter(this.activity, this.SPINNERVALUES, this.SPINNERCODES, this.prepaidImageId));
        this.lvSearchMobile.setOnItemClickListener(new C05235());
        right_arrow.setOnClickListener(new C05246());
        this.dialog.show();
    }
}

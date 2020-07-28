package com.codeunite.paymyrch.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.fragment.DispteReportHistoryDialog;
import com.codeunite.paymyrch.fragment.Dispute_Report;
import com.codeunite.paymyrch.model.GroupReport;

public class Dispute_Report_Adapter extends BaseExpandableListAdapter {
    public Activity activity;
    RetailerExpandableListAdapter adapter;
    Dispute_Report disputeReport;
    private SparseArray<GroupReport> groups;
    public LayoutInflater inflater;
    ExpandableListView listView;
    ProgressDialog progressDialog;
    public String retailerId;
    public String status;

    public Dispute_Report_Adapter(Activity act, SparseArray<GroupReport> groups, Dispute_Report activity) {
        this.activity = act;
        this.groups = groups;
        this.inflater = act.getLayoutInflater();
        this.disputeReport = activity;
    }

    public void clear() {
        this.groups.clear();
    }

    public Object getChild(int groupPosition, int childPosition) {
        return ((GroupReport) this.groups.get(groupPosition)).children.get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String children = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.listrow_details, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.textView2)).setText(children);
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
            convertView = this.inflater.inflate(R.layout.dispute_single_item_report, parent, false);
        }
        final GroupReport group = (GroupReport) getGroup(groupPosition);
        ((TextView) convertView.findViewById(R.id.retailerId)).setText(group.getRetailerId());
        ((TextView) convertView.findViewById(R.id.mobile)).setText(group.getMobile());
        ((TextView) convertView.findViewById(R.id.status_1)).setText(group.getStatus());
        TextView repley = (TextView) convertView.findViewById(R.id.reply);
        TextView txt_status = (TextView) convertView.findViewById(R.id.status_1);
        this.retailerId = group.getRetailerId();
        this.status = group.getStatus();
        if (this.status.equalsIgnoreCase("true")) {
            txt_status.setText("Close");
        } else if (this.status.equalsIgnoreCase("false")) {
            txt_status.setText("Pending");
        }
        convertView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                FragmentManager manager = Dispute_Report_Adapter.this.disputeReport.getChildFragmentManager();
                DispteReportHistoryDialog dispteReportHistoryDialog = new DispteReportHistoryDialog();
                Bundle args = new Bundle();
                args.putString("tiket_id", group.getRetailerId());
                args.putString("trans_id", group.getTalktime());
                dispteReportHistoryDialog.setArguments(args);
                dispteReportHistoryDialog.show(manager, "dialog");
            }
        });
        return convertView;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

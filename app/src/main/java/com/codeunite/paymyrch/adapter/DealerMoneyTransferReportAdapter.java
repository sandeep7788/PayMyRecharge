package com.codeunite.paymyrch.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.GroupReport;

public class DealerMoneyTransferReportAdapter extends BaseExpandableListAdapter {
    public Activity activity;
    private SparseArray<GroupReport> groups;
    public LayoutInflater inflater;

    public DealerMoneyTransferReportAdapter(Activity act, SparseArray<GroupReport> groups) {
        this.activity = act;
        this.groups = groups;
        this.inflater = act.getLayoutInflater();
    }

    public int getGroupCount() {
        return this.groups.size();
    }

    public int getChildrenCount(int groupPosition) {
        return ((GroupReport) this.groups.get(groupPosition)).children.size();
    }

    public Object getGroup(int groupPosition) {
        return this.groups.get(groupPosition);
    }

    public Object getChild(int groupPosition, int childPosition) {
        return ((GroupReport) this.groups.get(groupPosition)).children.get(childPosition);
    }

    public long getGroupId(int groupPosition) {
        return 0;
    }

    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    public boolean hasStableIds() {
        return false;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String children = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.listrow_details, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.textView2);
        textView.setText(children);
        if (((String) ((GroupReport) this.groups.get(groupPosition)).children.get(childPosition)).equalsIgnoreCase("Success")) {
            textView.setTypeface(null, Typeface.BOLD);
            textView.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            textView.setTypeface(null, Typeface.NORMAL);
            textView.setTextColor(Color.parseColor("#000000"));
        }
        return convertView;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.dealersinglerow_money_transfer_report, parent, false);
        }
        GroupReport group = (GroupReport) getGroup(groupPosition);
        ((TextView) convertView.findViewById(R.id.txt_operator_name_money)).setText(group.getSenderNumber());
        ((TextView) convertView.findViewById(R.id.txt_operator_code_money)).setText(group.getAccountNumber());
        ((TextView) convertView.findViewById(R.id.txt_commision_money)).setText(group.getAmount());
        return convertView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

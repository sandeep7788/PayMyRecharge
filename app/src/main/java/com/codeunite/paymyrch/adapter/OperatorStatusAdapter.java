package com.codeunite.paymyrch.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.GroupReport;

public class OperatorStatusAdapter extends BaseExpandableListAdapter {
    public Activity activity;
    private SparseArray<GroupReport> groups;
    public LayoutInflater inflater;

    public OperatorStatusAdapter(Activity act, SparseArray<GroupReport> groups) {
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
        ((TextView) convertView.findViewById(R.id.textView2)).setText(children);
        return convertView;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.operator_status_listrow_group, parent, false);
        }
        GroupReport group = (GroupReport) getGroup(groupPosition);
        TextView textView1 = (TextView) convertView.findViewById(R.id.txt_operator_status);
        ((TextView) convertView.findViewById(R.id.txt_operator_name)).setText(group.getOperatorName());
        ((TextView) convertView.findViewById(R.id.txt_operator_code)).setText(group.getOperatorCode());
        ((TextView) convertView.findViewById(R.id.txt_commision)).setText(group.getCommision());
        ((TextView) convertView.findViewById(R.id.txt_operator_status)).setText(group.getOperatorStatus());
        if (group.getOperatorStatus().equalsIgnoreCase("True")) {
            textView1.setText("Activated");
            textView1.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            textView1.setText("Deactivatd");
            textView1.setTextColor(Color.parseColor("#F44336"));
        }
        return convertView;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

package com.codeunite.paymyrch.adapter;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.GroupReport;

public class FundReceiveExpandableListAdapter extends BaseExpandableListAdapter {
    public Activity activity;
    private SparseArray<GroupReport> groups;
    public LayoutInflater inflater;

    public FundReceiveExpandableListAdapter(Activity act, SparseArray<GroupReport> groups) {
        this.activity = act;
        this.groups = groups;
        this.inflater = act.getLayoutInflater();
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
            convertView = this.inflater.inflate(R.layout.fund_receive_listrow_group, parent, false);
        }
        GroupReport group = (GroupReport) getGroup(groupPosition);
        ((TextView) convertView.findViewById(R.id.req)).setText(group.getReceiveFrom());
        ((TextView) convertView.findViewById(R.id.custno)).setText(group.getOldRemain());
        ((TextView) convertView.findViewById(R.id.amount)).setText(group.getAmount());
        ((TextView) convertView.findViewById(R.id.status)).setText(group.getNewRemain());
        return groupPosition % 2 == 0 ? convertView : convertView;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

package com.codeunite.paymyrch.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.fragment.RetailerListFragment;
import com.codeunite.paymyrch.model.GroupReport;

public class RetailerExpandableListAdapter extends BaseExpandableListAdapter {
    public Activity activity;
    RetailerExpandableListAdapter adapter;
    private SparseArray<GroupReport> groups;
    public LayoutInflater inflater;
    ExpandableListView listView;
    ProgressDialog progressDialog;
    public String retailerId;
    RetailerListFragment retailerListFragment;
    public String status;

    public RetailerExpandableListAdapter(Activity act, SparseArray<GroupReport> groups, RetailerListFragment activity) {
        this.activity = act;
        this.groups = groups;
        this.inflater = act.getLayoutInflater();
        this.retailerListFragment = activity;
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
            convertView = this.inflater.inflate(R.layout.retailer_listrow_group, parent, false);
        }
        GroupReport group = (GroupReport) getGroup(groupPosition);
        ((TextView) convertView.findViewById(R.id.retailerId)).setText(group.getRetailerId());
        ((TextView) convertView.findViewById(R.id.retailerName)).setText(group.getName());
        ((TextView) convertView.findViewById(R.id.mobile)).setText(group.getMobile());
        TextView textView = (TextView) convertView.findViewById(R.id.status_1);
        this.retailerId = group.getR_ret_id();
        System.out.println("vskjsefewf-" + this.retailerId);
        this.status = group.getStatus();
        return convertView;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

package com.codeunite.paymyrch.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.GroupReport;

import cc.cloudist.acplibrary.ACProgressFlower;

public class PlanAdapter extends BaseExpandableListAdapter {
    public Activity activity;
    PlanAdapter adapter;
    Activity detailsFragment;
    Dialog dialog;
    EditText edtAmount;
    private SparseArray<GroupReport> groups;
    private ImageView imageView1;
    private ImageView imageView2;
    public LayoutInflater inflater;
    ExpandableListView listView;
    String num;
    TextView number;
    ACProgressFlower progressDialog;
    public String retailerId;
    public String status;

    public PlanAdapter(Activity act, SparseArray<GroupReport> groups, Activity activity, EditText edtAmount, Dialog dialog) {
        this.activity = act;
        this.groups = groups;
        this.inflater = act.getLayoutInflater();
        this.detailsFragment = activity;
        this.edtAmount = edtAmount;
        this.dialog = dialog;
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
            ((TextView) convertView.findViewById(R.id.textView2)).setText(children);
        } else {
            ((TextView) convertView.findViewById(R.id.textView2)).setText(children);
        }
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
            convertView = this.inflater.inflate(R.layout.plansingleitem, parent, false);
        }
        final GroupReport group = (GroupReport) getGroup(groupPosition);
        ((TextView) convertView.findViewById(R.id.txt_jioaplan_amount)).setText(group.getAmount());
        ((TextView) convertView.findViewById(R.id.talktime)).setText(group.getTalktime());
        ((TextView) convertView.findViewById(R.id.discription)).setText(group.getDetails());
        convertView.findViewById(R.id.lay_details).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PlanAdapter.this.edtAmount.setText(group.getAmount());
                PlanAdapter.this.dialog.dismiss();
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

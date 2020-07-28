package com.codeunite.paymyrch.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.BeneficiaryHolder;
import java.util.ArrayList;

public class BeneficiaryListAdapter extends Adapter<BeneficiaryListAdapter.ViewHolder> {
    String benef_accNo;
    String benef_ifsc;
    String benef_name;
    private ArrayList beneficiaryDetails;
    LinearLayout cardlayout;
    Context context;
    private MyClickListener myClickListener;

    public interface MyClickListener {
        void oncardClicked(CardView cardView, int i, View view);
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder implements OnClickListener {
        TextView benef_acNotv;
        TextView benef_ifsctv;
        TextView benef_nametv;
        CardView card;
        ImageButton checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.card = (CardView) itemView.findViewById(R.id.mycard);
            this.benef_nametv = (TextView) itemView.findViewById(R.id.benef_name);
            this.benef_acNotv = (TextView) itemView.findViewById(R.id.benef_accountNumber);
            this.benef_ifsctv = (TextView) itemView.findViewById(R.id.benef_ifscCode);
            this.checkBox = (ImageButton) itemView.findViewById(R.id.checkbox);
            this.checkBox.setOnClickListener(this);
        }

        public void onClick(View v) {
            if (BeneficiaryListAdapter.this.myClickListener != null) {
                BeneficiaryListAdapter.this.myClickListener.oncardClicked(this.card, getPosition(), v);
            }
        }
    }

    public BeneficiaryListAdapter(Context context, ArrayList beneficiaryDetails) {
        this.context = context;
        this.beneficiaryDetails = beneficiaryDetails;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.single_row_beneficiary, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        BeneficiaryHolder tempobj = (BeneficiaryHolder) this.beneficiaryDetails.get(position);
        holder.benef_nametv.setText(tempobj.beneficiaryName);
        holder.benef_acNotv.setText(tempobj.accountNo);
        holder.benef_ifsctv.setText(tempobj.ifscCode);
    }

    public void setMyClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public LinearLayout getParentLayout() {
        return this.cardlayout;
    }

    public int getItemCount() {
        return this.beneficiaryDetails.size();
    }
}

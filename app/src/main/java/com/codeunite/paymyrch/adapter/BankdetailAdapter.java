package com.codeunite.paymyrch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.model.BankDetailHolder;
import java.util.ArrayList;

public class BankdetailAdapter extends BaseAdapter {
    ArrayList<BankDetailHolder> bankDetailHolders;
    int bankimage;
    Context context;
    private final int[] imageId = new int[]{R.drawable.airtel_payment_bank, R.drawable.allahbad_bank, R.drawable.andra_bank, R.drawable.axis_bank, R.drawable.bandhan_bank, R.drawable.bank_of_baroda, R.drawable.bank_of_india, R.drawable.bank_of_maharastra, R.drawable.bmb_bank, R.drawable.canara_bank, R.drawable.catholic_syrian_bank, R.drawable.central_bank, R.drawable.citi_bank, R.drawable.city_union_bank, R.drawable.corporation_bank, R.drawable.dcb_bank, R.drawable.dena_bank, R.drawable.dhanlaxmi_bank, R.drawable.federal_bank, R.drawable.hdfc_bank, R.drawable.icici_bank, R.drawable.idbi_bank, R.drawable.idfc_bank, R.drawable.indian_bank, R.drawable.indian_overseas_bank, R.drawable.induslnd_bank, R.drawable.j_and_k_bank, R.drawable.karnataka_bank, R.drawable.kvg_bank, R.drawable.karur_vaysya_bank, R.drawable.kerala_gramin_bank, R.drawable.kotak_bank, R.drawable.lakshmi_vilas_bank, R.drawable.maharashtra_gramin_bank, R.drawable.oriental_bank, R.drawable.pragathi_krishna_gramin_bank, R.drawable.prathama_bank, R.drawable.punjab_and_sind_bank, R.drawable.pnbe_bank, R.drawable.rbl_bank, R.drawable.south_indian_bank, R.drawable.standred_charted_bank, R.drawable.sbbj_bank, R.drawable.sbh_bank, R.drawable.state_bank_of_india, R.drawable.state_bank_of_mysore, R.drawable.state_bank_of_patiala, R.drawable.syndicate_bank, R.drawable.tamilnad_mercantile_bank, R.drawable.nainital_bank, R.drawable.uco_bank, R.drawable.union_bank, R.drawable.untitled_bank_of_india, R.drawable.vijaya_bank, R.drawable.yes_bank, R.drawable.other_bank_account};

    private class MyviewHolder {
        public ImageView bankImg;
        public TextView tvBankName;

        private MyviewHolder() {
        }
    }

    public BankdetailAdapter(Context context, ArrayList<BankDetailHolder> bankDetailHolders) {
        this.context = context;
        this.bankDetailHolders = bankDetailHolders;
    }

    public int getCount() {
        return this.bankDetailHolders.size();
    }

    public Object getItem(int position) {
        return this.bankDetailHolders.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MyviewHolder myviewHolder;
        View v = convertView;
        if (v == null) {
            myviewHolder = new MyviewHolder();
            v = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.single_bank_details, parent, false);
            myviewHolder.tvBankName = (TextView) v.findViewById(R.id.bankName);
            myviewHolder.bankImg = (ImageView) v.findViewById(R.id.bankImage);
            v.setTag(myviewHolder);
        } else {
            myviewHolder = (MyviewHolder) v.getTag();
        }
        String bankName = ((BankDetailHolder) this.bankDetailHolders.get(position)).BankName;
        myviewHolder.tvBankName.setText(bankName);
        setBankImage(bankName, myviewHolder.bankImg);
        return v;
    }

    void setBankImage(String bankName, ImageView imageView) {
        if (bankName.equalsIgnoreCase("AIRTEL PAYMENTS BANK LIMITED")) {
            this.bankimage = this.imageId[0];
        } else if (bankName.equalsIgnoreCase("ALLAHABAD BANK")) {
            this.bankimage = this.imageId[1];
        } else if (bankName.equalsIgnoreCase("ANDHRA BANK")) {
            this.bankimage = this.imageId[2];
        } else if (bankName.equalsIgnoreCase("AXIS BANK")) {
            this.bankimage = this.imageId[3];
        } else if (bankName.equalsIgnoreCase("BANDHAN BANK LIMITED")) {
            this.bankimage = this.imageId[4];
        } else if (bankName.equalsIgnoreCase("BANK OF BARODA")) {
            this.bankimage = this.imageId[5];
        } else if (bankName.equalsIgnoreCase("BANK OF INDIA")) {
            this.bankimage = this.imageId[6];
        } else if (bankName.equalsIgnoreCase("BANK OF MAHARASHTRA")) {
            this.bankimage = this.imageId[7];
        } else if (bankName.equalsIgnoreCase("BHARATIYA MAHILA BANK LIMITED")) {
            this.bankimage = this.imageId[8];
        } else if (bankName.equalsIgnoreCase("CANARA BANK")) {
            this.bankimage = this.imageId[9];
        } else if (bankName.equalsIgnoreCase("CATHOLIC SYRIAN BANK LIMITED")) {
            this.bankimage = this.imageId[10];
        } else if (bankName.equalsIgnoreCase("CENTRAL BANK OF INDIA")) {
            this.bankimage = this.imageId[11];
        } else if (bankName.equalsIgnoreCase("CITI BANK")) {
            this.bankimage = this.imageId[12];
        } else if (bankName.equalsIgnoreCase("CITY UNION BANK LIMITED")) {
            this.bankimage = this.imageId[13];
        } else if (bankName.equalsIgnoreCase("CORPORATION BANK")) {
            this.bankimage = this.imageId[14];
        } else if (bankName.equalsIgnoreCase("DCB BANK LIMITED")) {
            this.bankimage = this.imageId[15];
        } else if (bankName.equalsIgnoreCase("DENA BANK")) {
            this.bankimage = this.imageId[16];
        } else if (bankName.equalsIgnoreCase("DHANALAKSHMI BANK")) {
            this.bankimage = this.imageId[17];
        } else if (bankName.equalsIgnoreCase("FEDERAL BANK")) {
            this.bankimage = this.imageId[18];
        } else if (bankName.equalsIgnoreCase("HDFC BANK")) {
            this.bankimage = this.imageId[19];
        } else if (bankName.equalsIgnoreCase("ICICI BANK LIMITED")) {
            this.bankimage = this.imageId[20];
        } else if (bankName.equalsIgnoreCase("IDBI BANK")) {
            this.bankimage = this.imageId[21];
        } else if (bankName.equalsIgnoreCase("IDFC BANK LIMITED")) {
            this.bankimage = this.imageId[22];
        } else if (bankName.equalsIgnoreCase("INDIAN BANK")) {
            this.bankimage = this.imageId[23];
        } else if (bankName.equalsIgnoreCase("INDIAN OVERSEAS BANK")) {
            this.bankimage = this.imageId[24];
        } else if (bankName.equalsIgnoreCase("INDUSIND BANK")) {
            this.bankimage = this.imageId[25];
        } else if (bankName.equalsIgnoreCase("JAMMU AND KASHMIR BANK LIMITED")) {
            this.bankimage = this.imageId[26];
        } else if (bankName.equalsIgnoreCase("KARNATAKA BANK LIMITED")) {
            this.bankimage = this.imageId[27];
        } else if (bankName.equalsIgnoreCase("KARNATAKA VIKAS GRAMEENA BANK")) {
            this.bankimage = this.imageId[28];
        } else if (bankName.equalsIgnoreCase("KARUR VYSYA BANK")) {
            this.bankimage = this.imageId[29];
        } else if (bankName.equalsIgnoreCase("KERALA GRAMIN BANK")) {
            this.bankimage = this.imageId[30];
        } else if (bankName.equalsIgnoreCase("KOTAK MAHINDRA BANK LIMITED")) {
            this.bankimage = this.imageId[31];
        } else if (bankName.equalsIgnoreCase("LAXMI VILAS BANK")) {
            this.bankimage = this.imageId[32];
        } else if (bankName.equalsIgnoreCase("Maharashtra Gramin Bank")) {
            this.bankimage = this.imageId[33];
        } else if (bankName.equalsIgnoreCase("ORIENTAL BANK OF COMMERCE")) {
            this.bankimage = this.imageId[34];
        } else if (bankName.equalsIgnoreCase("PRAGATHI KRISHNA GRAMIN BANK")) {
            this.bankimage = this.imageId[35];
        } else if (bankName.equalsIgnoreCase("PRATHAMA BANK")) {
            this.bankimage = this.imageId[36];
        } else if (bankName.equalsIgnoreCase("PUNJAB AND SIND BANK")) {
            this.bankimage = this.imageId[37];
        } else if (bankName.equalsIgnoreCase("PUNJAB NATIONAL BANK")) {
            this.bankimage = this.imageId[38];
        } else if (bankName.equalsIgnoreCase("RBL Bank Limited")) {
            this.bankimage = this.imageId[39];
        } else if (bankName.equalsIgnoreCase("SOUTH INDIAN BANK")) {
            this.bankimage = this.imageId[40];
        } else if (bankName.equalsIgnoreCase("STANDARD CHARTERED BANK")) {
            this.bankimage = this.imageId[41];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF BIKANER AND JAIPUR")) {
            this.bankimage = this.imageId[42];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF HYDERABAD")) {
            this.bankimage = this.imageId[43];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF INDIA")) {
            this.bankimage = this.imageId[44];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF MYSORE")) {
            this.bankimage = this.imageId[45];
        } else if (bankName.equalsIgnoreCase("STATE BANK OF PATIALA")) {
            this.bankimage = this.imageId[46];
        } else if (bankName.equalsIgnoreCase("SYNDICATE BANK")) {
            this.bankimage = this.imageId[47];
        } else if (bankName.equalsIgnoreCase("TAMILNAD MERCANTILE BANK LIMITED")) {
            this.bankimage = this.imageId[48];
        } else if (bankName.equalsIgnoreCase("THE NAINITAL BANK LIMITED")) {
            this.bankimage = this.imageId[49];
        } else if (bankName.equalsIgnoreCase("UCO BANK")) {
            this.bankimage = this.imageId[50];
        } else if (bankName.equalsIgnoreCase("UNION BANK OF INDIA")) {
            this.bankimage = this.imageId[51];
        } else if (bankName.equalsIgnoreCase("UNITED BANK OF INDIA")) {
            this.bankimage = this.imageId[52];
        } else if (bankName.equalsIgnoreCase("VIJAYA BANK")) {
            this.bankimage = this.imageId[53];
        } else if (bankName.equalsIgnoreCase("YES BANK")) {
            this.bankimage = this.imageId[54];
        } else {
            this.bankimage = this.imageId[55];
        }
        imageView.setImageResource(this.bankimage);
    }
}

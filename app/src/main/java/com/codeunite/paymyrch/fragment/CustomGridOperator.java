package com.codeunite.paymyrch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import com.codeunite.paymyrch.AsyncTask.LoadOperatorTask;
import com.codeunite.paymyrch.AsyncTask.OPTask;
import com.codeunite.paymyrch.AsyncTask.OperatorListTask;
import com.codeunite.paymyrch.R;
import com.codeunite.paymyrch.adapter.CustomDataCardAdapter;
import com.codeunite.paymyrch.adapter.CustomDthAdapter;
import com.codeunite.paymyrch.adapter.CustomElectricityAdapter;
import com.codeunite.paymyrch.adapter.CustomGasAdapter;
import com.codeunite.paymyrch.adapter.CustomGridOPAdapter;
import com.codeunite.paymyrch.adapter.CustomInsuranceAdapter;
import com.codeunite.paymyrch.adapter.CustomLandLineAdapter;
import com.codeunite.paymyrch.adapter.CustomPostpaidAdapter;
import com.codeunite.paymyrch.model.GridOperatorModel;
import java.util.ArrayList;

public class CustomGridOperator extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String OperatorsData,name;
    CustomGridOPAdapter adapter;
    Context context;
    int currentposition;
    CustomDataCardAdapter customDataCardAdapter;
    CustomDthAdapter customDthAdapter;
    CustomElectricityAdapter customElectricityAdapter;
    CustomGasAdapter customGasAdapter;
    CustomGridOPAdapter customGridOPAdapter;
    CustomInsuranceAdapter customInsuranceAdapter;
    CustomLandLineAdapter customLandLineAdapter;
    CustomPostpaidAdapter customPostpaidAdapter;
    GridView gridView;
    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;
    OPTask myop;
    OperatorListTask operatorListTask;
    ArrayList oplist;
    ProgressDialog progressDialog;

    /* renamed from: com.codeunite.PayMyRecharge.fragment.CustomGridOperator$1 */
    class C05351 implements OnItemClickListener {
        C05351() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            int i;
            ElectricityFragment electricityFragment = new ElectricityFragment();
            FragmentTransaction fragmentTransaction = CustomGridOperator.this.getFragmentManager().beginTransaction();
            String tag = "PREPAID";
            Bundle bundle = new Bundle();
            if (CustomGridOperator.this.currentposition == 0) {
                PrepaidFragment prepaidFragment = new PrepaidFragment();
                try {
                    if (!CustomGridOperator.this.customGridOPAdapter.localarrayList.isEmpty()) {
                        for (i = 0; i < CustomGridOperator.this.customGridOPAdapter.localarrayList.size(); i++) {
                            if (i == position) {
                                CustomGridOperator.this.GoToPrepaidFrag(prepaidFragment, fragmentTransaction, CustomGridOperator.this.getBundle(CustomGridOperator.this.customGridOPAdapter, position), tag);
                                break;
                            }
                        }
                    }
                } catch (NullPointerException e) {
                    CustomGridOperator.this.loadobject();
                }
            }
            if (CustomGridOperator.this.currentposition == 1) {
                PostpaidFragment postpaidFragment = new PostpaidFragment();
                try {
                    if (!CustomGridOperator.this.customPostpaidAdapter.localPostpaidarrayList.isEmpty()) {
                        i = 0;
                        while (i < CustomGridOperator.this.customPostpaidAdapter.localPostpaidarrayList.size()) {
                            if (i == position) {
                                bundle = CustomGridOperator.this.getBundle(CustomGridOperator.this.customPostpaidAdapter, position);
                                if (bundle.containsKey("Opcode")) {
                                    CustomGridOperator.this.GoToPrepaidFrag(postpaidFragment, fragmentTransaction, bundle, tag);
                                } else {
                                    Toast.makeText(CustomGridOperator.this.getActivity(), "Please Try Again", 0).show();
                                }
                            } else {
                                i++;
                            }
                        }
                    }
                } catch (NullPointerException e2) {
                    CustomGridOperator.this.loadobject();
                }
            }
            if (CustomGridOperator.this.currentposition == 2) {
                DTHFragment dthFragment = new DTHFragment();
                try {
                    if (!CustomGridOperator.this.customDthAdapter.localDtharrayList.isEmpty()) {
                        i = 0;
                        while (i < CustomGridOperator.this.customDthAdapter.localDtharrayList.size()) {
                            if (i == position) {
                                bundle = CustomGridOperator.this.getBundle(CustomGridOperator.this.customDthAdapter, position);
                                if (bundle.containsKey("Opcode")) {
                                    CustomGridOperator.this.GoToPrepaidFrag(dthFragment, fragmentTransaction, bundle, tag);
                                } else {
                                    Toast.makeText(CustomGridOperator.this.getActivity(), "Please Try Again", 0).show();
                                }
                            } else {
                                i++;
                            }
                        }
                    }
                } catch (NullPointerException e3) {
                    CustomGridOperator.this.loadobject();
                }
            }
            if (CustomGridOperator.this.currentposition == 3) {
                LandLineFragment landLineFragment = new LandLineFragment();
                try {
                    if (!CustomGridOperator.this.customLandLineAdapter.locaLandLinelarrayList.isEmpty()) {
                        i = 0;
                        while (i < CustomGridOperator.this.customLandLineAdapter.locaLandLinelarrayList.size()) {
                            if (i == position) {
                                bundle = CustomGridOperator.this.getBundle(CustomGridOperator.this.customLandLineAdapter, position);
                                if (bundle.containsKey("Opcode")) {
                                    CustomGridOperator.this.GoToPrepaidFrag(landLineFragment, fragmentTransaction, bundle, tag);
                                } else {
                                    Toast.makeText(CustomGridOperator.this.getActivity(), "Please Try Again", 0).show();
                                }
                            } else {
                                i++;
                            }
                        }
                    }
                } catch (NullPointerException e4) {
                    CustomGridOperator.this.loadobject();
                }
            }
            if (CustomGridOperator.this.currentposition == 4) {
                DataCardFragment dataCardFragment = new DataCardFragment();
                try {
                    if (!CustomGridOperator.this.customDataCardAdapter.localDatACardarrayList.isEmpty()) {
                        i = 0;
                        while (i < CustomGridOperator.this.customDataCardAdapter.localDatACardarrayList.size()) {
                            if (i == position) {
                                bundle = CustomGridOperator.this.getBundle(CustomGridOperator.this.customDataCardAdapter, position);
                                if (bundle.containsKey("Opcode")) {
                                    CustomGridOperator.this.GoToPrepaidFrag(dataCardFragment, fragmentTransaction, bundle, tag);
                                } else {
                                    Toast.makeText(CustomGridOperator.this.getActivity(), "Please Try Again", 0).show();
                                }
                            } else {
                                i++;
                            }
                        }
                    }
                } catch (NullPointerException e5) {
                    CustomGridOperator.this.loadobject();
                    Toast.makeText(CustomGridOperator.this.getActivity(), "Once Again Select The Operator", 0).show();
                }
            }
            if (CustomGridOperator.this.currentposition == 5) {
                InsuranceFragment insuranceFragment = new InsuranceFragment();
                try {
                    if (!CustomGridOperator.this.customInsuranceAdapter.localInsurancearrayList.isEmpty()) {
                        i = 0;
                        while (i < CustomGridOperator.this.customInsuranceAdapter.localInsurancearrayList.size()) {
                            if (i == position) {
                                bundle = CustomGridOperator.this.getBundle(CustomGridOperator.this.customInsuranceAdapter, position);
                                if (bundle.containsKey("Opcode")) {
                                    CustomGridOperator.this.GoToPrepaidFrag(insuranceFragment, fragmentTransaction, bundle, tag);
                                } else {
                                    Toast.makeText(CustomGridOperator.this.getActivity(), "Please Try Again", 0).show();
                                }
                            } else {
                                i++;
                            }
                        }
                    }
                } catch (NullPointerException e6) {
                    CustomGridOperator.this.loadobject();
                    Toast.makeText(CustomGridOperator.this.getActivity(), "Once Again Select The Operator", 0).show();
                }
            }
            if (CustomGridOperator.this.currentposition == 6) {
                GasFragment gasFragment = new GasFragment();
                try {
                    if (!CustomGridOperator.this.customGasAdapter.localGasarrayList.isEmpty()) {
                        i = 0;
                        while (i < CustomGridOperator.this.customGasAdapter.localGasarrayList.size()) {
                            if (i == position) {
                                bundle = CustomGridOperator.this.getBundle(CustomGridOperator.this.customGasAdapter, position);
                                if (bundle.containsKey("Opcode")) {
                                    CustomGridOperator.this.GoToPrepaidFrag(gasFragment, fragmentTransaction, bundle, tag);
                                } else {
                                    Toast.makeText(CustomGridOperator.this.getActivity(), "Please Try Again", 0).show();
                                }
                            } else {
                                i++;
                            }
                        }
                    }
                } catch (NullPointerException e7) {
                    CustomGridOperator.this.loadobject();
                }
            }
            if (CustomGridOperator.this.currentposition == 7) {
                electricityFragment = new ElectricityFragment();
                try {
                    if (!CustomGridOperator.this.customElectricityAdapter.locaElectricitylarrayList.isEmpty()) {
                        for (i = 0; i < CustomGridOperator.this.customElectricityAdapter.locaElectricitylarrayList.size(); i++) {
                            if (i == position) {
                                bundle = CustomGridOperator.this.getBundle(CustomGridOperator.this.customElectricityAdapter, position);
                                if (bundle.containsKey("Opcode")) {
                                    CustomGridOperator.this.GoToPrepaidFrag(electricityFragment, fragmentTransaction, bundle, tag);
                                    return;
                                } else {
                                    Toast.makeText(CustomGridOperator.this.getActivity(), "Please Try Again", 0).show();
                                    return;
                                }
                            }
                        }
                    }
                } catch (NullPointerException e8) {
                    CustomGridOperator.this.loadobject();
                }
            }
        }
    }

    /* renamed from: com.codeunite.PayMyRecharge.fragment.CustomGridOperator$2 */
    class C05362 extends Thread {
        C05362() {
        }

        public void run() {
            try {
                Thread.sleep(900);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object object = CustomGridOperator.this.myop.getinstance();
            if (object instanceof CustomGridOPAdapter) {
                CustomGridOperator.this.customGridOPAdapter = (CustomGridOPAdapter) object;
                Log.d("SIZE OF PREPAID", String.valueOf(CustomGridOperator.this.customGridOPAdapter.Opcode.size()));
            } else if (object instanceof CustomPostpaidAdapter) {
                CustomGridOperator.this.customPostpaidAdapter = (CustomPostpaidAdapter) object;
                Log.d("SIZE OF POSTPAID", String.valueOf(CustomGridOperator.this.customPostpaidAdapter.PostpaidOpcode.size()));
            } else if (object instanceof CustomDthAdapter) {
                CustomGridOperator.this.customDthAdapter = (CustomDthAdapter) object;
                Log.d("SIZE OF LIST DTH", String.valueOf(CustomGridOperator.this.customDthAdapter.DthCodeList.size()));
            } else if (object instanceof CustomLandLineAdapter) {
                CustomGridOperator.this.customLandLineAdapter = (CustomLandLineAdapter) object;
                Log.d("SIZE OF LIST LANDLINE", String.valueOf(CustomGridOperator.this.customLandLineAdapter.LandlineOpCodeList.size()));
            } else if (object instanceof CustomDataCardAdapter) {
                CustomGridOperator.this.customDataCardAdapter = (CustomDataCardAdapter) object;
                Log.d("SIZE OF LIST datacard", String.valueOf(CustomGridOperator.this.customDataCardAdapter.DataCardOpcode.size()));
            } else if (object instanceof CustomInsuranceAdapter) {
                CustomGridOperator.this.customInsuranceAdapter = (CustomInsuranceAdapter) object;
                Log.d("SIZE OF LIST INSURANCE", String.valueOf(CustomGridOperator.this.customInsuranceAdapter.InsuranceOpCodeList.size()));
            } else if (object instanceof CustomGasAdapter) {
                CustomGridOperator.this.customGasAdapter = (CustomGasAdapter) object;
                Log.d("SIZE OF LIST GAS", String.valueOf(CustomGridOperator.this.customGasAdapter.GasOpCodeList.size()));
            } else if (object instanceof CustomElectricityAdapter) {
                CustomGridOperator.this.customElectricityAdapter = (CustomElectricityAdapter) object;
                Log.d("SIZE OF LIST ELECTICITY", String.valueOf(CustomGridOperator.this.customElectricityAdapter.ElecOpcodeList.size()));
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public static CustomGridOperator newInstance(String param1, String param2) {
        CustomGridOperator fragment = new CustomGridOperator();
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
        View view = inflater.inflate(R.layout.fragment_custom_grid_operator, container, false);
        this.gridView = (GridView) view.findViewById(R.id.gridView_op);
        this.progressDialog = new ProgressDialog(getActivity());
        Bundle b = getArguments();
        if (b != null) {
            this.currentposition = b.getInt("Position");
            Log.d("CURRENT POSITION", String.valueOf(this.currentposition));
        }
        this.OperatorsData = LoadOperatorTask.result;
        Log.d("OHH YES", this.OperatorsData);
        this.myop = (OPTask) new OPTask(getActivity(), this.progressDialog, this.gridView, this.currentposition).execute(new String[]{this.OperatorsData});
        loadobject();
        this.gridView.setOnItemClickListener(new C05351());
        return view;
    }

    public void onResume() {
        super.onResume();
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

    public void GoToPrepaidFrag(Fragment fragment, FragmentTransaction fragmentTransaction, Bundle bundle, String tag) {
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.swipe, fragment, tag).addToBackStack("operators");
        fragmentTransaction.commit();
    }

    public Bundle getBundle(Object object, int position) {
        Bundle bundle = new Bundle();
        if (object instanceof CustomGridOPAdapter) {
            String name = ((GridOperatorModel) this.customGridOPAdapter.localarrayList.get(position)).getOperatorName1();
            Log.d("NAME WILL BE", name);
            Log.d("CODE WILL BE", String.valueOf(this.customGridOPAdapter.Opcode.get(position)));
            bundle.putString("Name", name);
            bundle.putString("Opcode", (String) this.customGridOPAdapter.Opcode.get(position));
            bundle.putInt("Position", position);
        }
        if (object instanceof CustomPostpaidAdapter) {
            name = ((GridOperatorModel) this.customPostpaidAdapter.localPostpaidarrayList.get(position)).getOperatorName1();
            Log.d("NAME WILL BE", name);
            Log.d("CODE WILL BE", String.valueOf(this.customPostpaidAdapter.PostpaidOpcode.get(position)));
            bundle.putString("Name", name);
            bundle.putString("Opcode", (String) this.customPostpaidAdapter.PostpaidOpcode.get(position));
            bundle.putInt("Position", position);
        }
        if (object instanceof CustomDthAdapter) {
            name = ((GridOperatorModel) this.customDthAdapter.localDtharrayList.get(position)).getOperatorName1();
            Log.d("NAME WILL BE", name);
            Log.d("CODE WILL BE", String.valueOf(this.customDthAdapter.DthCodeList.get(position)));
            bundle.putString("Name", name);
            bundle.putString("Opcode", (String) this.customDthAdapter.DthCodeList.get(position));
            bundle.putInt("Position", position);
        }
        if (object instanceof CustomLandLineAdapter) {
            name = ((GridOperatorModel) this.customLandLineAdapter.locaLandLinelarrayList.get(position)).getOperatorName1();
            Log.d("NAME WILL BE", name);
            Log.d("CODE WILL BE", String.valueOf(this.customLandLineAdapter.LandlineOpCodeList.get(position)));
            bundle.putString("Name", name);
            bundle.putString("Opcode", (String) this.customLandLineAdapter.LandlineOpCodeList.get(position));
            bundle.putInt("Position", position);
        }
        if (object instanceof CustomDataCardAdapter) {
            name = ((GridOperatorModel) this.customDataCardAdapter.localDatACardarrayList.get(position)).getOperatorName1();
            Log.d("NAME WILL BE", name);
            Log.d("CODE WILL BE", String.valueOf(this.customDataCardAdapter.DataCardOpcode.get(position)));
            bundle.putString("Name", name);
            bundle.putString("Opcode", (String) this.customDataCardAdapter.DataCardOpcode.get(position));
            bundle.putInt("Position", position);
        }
        if (object instanceof CustomInsuranceAdapter) {
            name = ((GridOperatorModel) this.customInsuranceAdapter.localInsurancearrayList.get(position)).getOperatorName1();
            Log.d("NAME WILL BE", name);
            Log.d("CODE WILL BE", String.valueOf(this.customInsuranceAdapter.InsuranceOpCodeList.get(position)));
            bundle.putString("Name", name);
            bundle.putString("Opcode", (String) this.customInsuranceAdapter.InsuranceOpCodeList.get(position));
            bundle.putInt("Position", position);
        }
        if (object instanceof CustomElectricityAdapter) {
            name = ((GridOperatorModel) this.customElectricityAdapter.locaElectricitylarrayList.get(position)).getOperatorName1();
            Log.d("NAME WILL BE", name);
            Log.d("CODE WILL BE", String.valueOf(this.customElectricityAdapter.ElecOpcodeList.get(position)));
            bundle.putString("Name", name);
            bundle.putString("Opcode", (String) this.customElectricityAdapter.ElecOpcodeList.get(position));
            bundle.putInt("Position", position);
        }
        if (object instanceof CustomGasAdapter) {
            name = ((GridOperatorModel) this.customGasAdapter.localGasarrayList.get(position)).getOperatorName1();
            Log.d("NAME WILL BE", name);
            Log.d("CODE WILL BE", String.valueOf(this.customGasAdapter.GasOpCodeList.get(position)));
            bundle.putString("Name", name);
            bundle.putString("Opcode", (String) this.customGasAdapter.GasOpCodeList.get(position));
            bundle.putInt("Position", position);
        }
        return bundle;
    }

    void loadobject() {
        new C05362().start();
    }
}

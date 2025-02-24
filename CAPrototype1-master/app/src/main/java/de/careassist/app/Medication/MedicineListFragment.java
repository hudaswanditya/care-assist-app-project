package de.careassist.app.Medication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.careassist.app.Client;
import de.careassist.app.ClientViewActivity;
import de.careassist.app.R;
import de.careassist.app.dummy.DummyClientMedicine;

import java.util.HashMap;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class MedicineListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<Medicine> medicineList;
    //private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MedicineListFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MedicineListFragment newInstance(int columnCount) {
        MedicineListFragment fragment = new MedicineListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    public static MedicineListFragment newInstance(String medication){
        MedicineListFragment fragment = new MedicineListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        args.putString("category", medication);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Client c = ((ClientViewActivity)getActivity()).getClient();
        DummyClientMedicine m = c.getMedicineList();
        if(m!=null) {
            HashMap<String, List<Medicine>> medicines = m.CLIENTMEDICINE;

            if (getArguments() != null) {
                mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
                if (getArguments().get("category") != null) {
                    String medicine = getArguments().getString("category");
                    if (medicine.contains("prediscribed")) {
                        medicineList = medicines.get("PREDESCRIBED");
                    } else if (medicine.contains("temporary")) {
                        medicineList = medicines.get("TEMPORARY");
                    } else if (medicine.contains("self")) {
                        medicineList = medicines.get("SELF_ORDERED");
                    } else if (medicine.contains("demand")) {
                        medicineList = medicines.get("SELF_ORDERED");
                    }
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicinelist_list, container, false);



        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyMedicineListRecyclerViewAdapter(medicineList));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Medicine item);
    }*/
}

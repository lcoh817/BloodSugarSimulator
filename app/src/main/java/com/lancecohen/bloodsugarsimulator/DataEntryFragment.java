package com.lancecohen.bloodsugarsimulator;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DataEntryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DataEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataEntryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Reference to the fragment views
    View view;

    private LinearLayout mLinearListView;
    private ArrayList<Item> mArrayListData;

    // Define reference to foodList string array
    private String[] foodList;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataEntryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataEntryFragment newInstance(String param1, String param2) {
        DataEntryFragment fragment = new DataEntryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DataEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the views layout
        view = inflater.inflate(R.layout.fragment_data_entry, null);

        // Get a reference to the linear layout list view.
        mLinearListView = (LinearLayout) view.findViewById(R.id.linear_listview);

        foodList = getResources().getStringArray(R.array.FoodList);

        mArrayListData = new ArrayList<Item>();

        for (int i = 0; i < foodList.length; i++)
        {

            mArrayListData.add(new Item(foodList[i]));
        }

        /***
         * adding item into listview
         */
        for (int i = 0; i < mArrayListData.size(); i++) {
            /**
             * inflate items/ add items in linear layout instead of listview
             */
            //LayoutInflater inflater = null;
            inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView = inflater.inflate(R.layout.row, null);
            /**
             * getting id of row.xml
             */

            TextView fnameText = (TextView) mLinearView
                    .findViewById(R.id.foodName);

            /**
             * set item into row
             */
            final String foodName = mArrayListData.get(i).getItem();
             fnameText.setText(foodName);

            /**
             * add view in top linear
             */

            mLinearListView.addView(mLinearView);

            /**
             * get item row on click
             *
             */
            mLinearView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getActivity(), "Clicked item;" + foodName,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

package com.lancecohen.bloodsugarsimulator;

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
import android.widget.CheckBox;
import android.widget.Button;
import android.util.Log;
import java.util.ArrayList;
import java.util.Calendar;

import android.view.View.OnClickListener;
import android.content.SharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DataEntryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DataEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataEntryFragment extends Fragment implements OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Reference to the fragment views
    View view;

    // Reference to list view for food items
    private LinearLayout foodLinearListView;

    // Reference to list view for exercise items
    private LinearLayout exerciseLinearListView;

    // Reference to ArrayList for storing food items
    private ArrayList<Item> foodArrayListData;

    // Reference to ArrayList for storing exercise items
    private ArrayList<Item> exerciseArrayListData;

    // Define reference to foodList string array
    private String[] foodList;

    // Define reference to exerciseList string array
    private String[] exerciseList;

    // Define a reference to checkBox next to food item
    private CheckBox cb;

    // Define a reference to the submit Data button
    private Button submitBn;

    // Private variable for tracking of total Food GI, initialised to 0
    private int totalFoodGI = 0;

    // Private variable for tracking of total exercise index, initialised to 0
    private int totalExerciseIndex = 0;

    /* Define int EI constants for all exercises */

    private final int CRUNCHES_EI = 15;

    private final int WALKING_EI = 15;

    private final int RUNNING_EI = 40;

    private final int SPRINTING_EI = 60;

    private final int SQUATS_EI = 60;

    private final int BENCHPRESS_EI = 45;


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

        // Get the reference to the submitButton
        submitBn = (Button) view.findViewById(R.id.submitButton);

        // Listen for clicks of the button- see onClick()
        submitBn.setOnClickListener(this);

        // Get a reference to the food linear layout list view.
        foodLinearListView = (LinearLayout) view.findViewById(R.id.food_linear_listview);


        // Get a reference to the exercise linear layout list view.
        exerciseLinearListView = (LinearLayout) view.findViewById(R.id.exercise_linear_listview);

        // Extract the Food List data array into the foodList array
        foodList = getResources().getStringArray(R.array.FoodList);

        // Extract the Exercise List data array into the foodList array
        exerciseList = getResources().getStringArray(R.array.ExerciseList);

        // Instantiate the foodArrayList
        foodArrayListData = new ArrayList<Item>();

        // Instantiate the exerciseArrayList
        exerciseArrayListData = new ArrayList<Item>();

        // This loop adds to the foodList string items into the foodArrayList
        for (int i = 0; i < foodList.length; i++)
        {
            int j = i+1;
            // Add the food item string and the ID to the food list (the loop iterator is +1 of the item id)
            foodArrayListData.add(new Item(foodList[i],j));
        }

        // This loop adds to the exerciseList string items into the foodArrayList
        for (int i = 0; i < exerciseList.length; i++)
        {
            int j = i+1;

            // Add the food item string and the ID to the exercise list (the loop iterator is +1 of the item id)
            exerciseArrayListData.add(new Item(exerciseList[i],j));
        }


        /***
         * Adding item into the food listview
         */
        for (int i = 0; i < foodArrayListData.size(); i++) {
            /**
             * inflate items/ add items in linear layout instead of food listview
             */
            //LayoutInflater inflater = null;
            inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View foodLinearView = inflater.inflate(R.layout.row, null);

            cb = (CheckBox) foodLinearView.findViewById(R.id.itemSelectedCB);
            /**
             * getting id of row.xml
             */




            TextView fnameText = (TextView) foodLinearView
                    .findViewById(R.id.itemName);

            /**
             * set item into row
             */
            final String foodName = foodArrayListData.get(i).getItem();
            fnameText.setText(foodName);

            // Get the food item ID;
            final int foodID = foodArrayListData.get(i).getItemID();

            /**
             * add view in top linear
             */

            foodLinearListView.addView(foodLinearView);

            /**
             * get item row on click
             *
             */
            foodLinearView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(), "Clicked item:" + foodName,
                            Toast.LENGTH_SHORT).show();


                }
            });



           cb.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View v) {


                   Toast.makeText(getActivity(), "Food ID:" + foodID,
                           Toast.LENGTH_SHORT).show();

                   //
                   int foodGI = getSelectedFoodGI(foodName);

                   // Add selected foodGI to the totalFoodGI
                   totalFoodGI = totalFoodGI + foodGI;


               }
           });


            /* If a food item has been checked then update the current GI, make the currentGI
                a public instance variable that can be accessed by the BloodSugarFragment class

                Perhaps, create a model for a food item, which has the GI as an instance variable



             */



        }


        /***
         * Adding item into the exercise listview
         */
        for (int i = 0; i < exerciseArrayListData.size(); i++) {
            /**
             * inflate items/ add items in linear layout instead of exercise listview
             */
            //LayoutInflater inflater = null;
            inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View exerciseLinearView = inflater.inflate(R.layout.row, null);

            cb = (CheckBox) exerciseLinearView.findViewById(R.id.itemSelectedCB);
            /**
             * getting id of row.xml
             */

            TextView exnameText = (TextView) exerciseLinearView
                    .findViewById(R.id.itemName);

            /**
             * set item into row
             */
            final String exerciseName = exerciseArrayListData.get(i).getItem();
            exnameText.setText(exerciseName);

            // Get the food item ID;
            final int exerciseID = foodArrayListData.get(i).getItemID();

            /**
             * add view in top linear
             */

            exerciseLinearListView.addView(exerciseLinearView);

            /**
             * get item row on click
             *
             */
            exerciseLinearView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getActivity(), "Clicked item;" + exerciseName,
                            Toast.LENGTH_SHORT).show();
                }
            });

            cb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    Toast.makeText(getActivity(), "Exercise ID:" + exerciseID,
                            Toast.LENGTH_SHORT).show();

                    int exerciseIndex = getSelectedExerciseIndex(exerciseID);

                    // Add selected exerciseIndex to the totalExerciseIndex
                    totalExerciseIndex = totalExerciseIndex + exerciseIndex;

                    // Used for debugging
                    Log.i("LSC", "Exercise Index of item:" + exerciseIndex);
                    Log.i("LSC", "Total Exercise Index: " + totalExerciseIndex);

                }
            });

            // If an exercise item has been checked, update the currentGI
        }



        // Inflate the layout for this fragment
        return view;
    }

    // This method takes in a foodName and returns the food's GI
    private int getSelectedFoodGI(String foodName){

            int foodGI = 0;


            return foodGI;
    }

    // This method take in an exercise ID and returns the exercise Index
    private int  getSelectedExerciseIndex(int exerciseID)
    {

            int exerciseIndex = 0;

            switch(exerciseID){

                case 1:
                    exerciseIndex = CRUNCHES_EI;
                    break;
                case 2:
                    exerciseIndex = WALKING_EI;
                    break;
                case 3:
                    exerciseIndex = RUNNING_EI;
                    break;
                case 4:
                    exerciseIndex = SPRINTING_EI;
                    break;
                case 5:
                    exerciseIndex = SQUATS_EI;
                    break;
                case 6:
                    exerciseIndex = BENCHPRESS_EI;
                    break;

            }

            return exerciseIndex;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /* This method returns the current system time in Minutes */
    private int getTimeStampMinutes(){

        // Get an instance of the current system calendar
        final Calendar c = Calendar.getInstance();

        // Get the current hour of the day
        int hour = c.get(Calendar.HOUR_OF_DAY);

        // Get the current minute of the hour
        int minute = c.get(Calendar.MINUTE);

        // Set the timestamp to be the hour in minutes plus the minute
        int timeStampMinutes = hour*60 + minute;

        return timeStampMinutes;


    }

     /*  This method saves entry data: totalFoodGI, totalExerciseGI and the entry timeStamp in minutes*/
    private void saveEntryData(){

        /*
		 * Get access to the activities preference file object for storing
		 * key-value pairs
		 */
        SharedPreferences prefs = this.getActivity().getPreferences(
                Context.MODE_PRIVATE);

		/*
		 * Create an editor for preference object thru which can make
		 * modifications
		 */
        SharedPreferences.Editor editor = prefs.edit();

		/*
		 * Save the current total food GI
		 */
        editor.putInt("TOTAL_FOOD_GI", totalFoodGI);

        // Save total exercise GI in shared preference
        editor.putInt("TOTAL_EXERCISE_GI", totalExerciseIndex);

        // Save spf image count in shared preference
        editor.putInt("ENTRY_TIMESTAMP", getTimeStampMinutes());

		/*
		 * Commit preferences back from the editor to the SharedPreferences
		 * object
		 */
        editor.commit();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.submitButton:

                // Save the entry data
                saveEntryData();

         break;

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

package fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvw.wordpower.R;

import org.json.JSONObject;

import common.Common;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScoreFragment.OnScoreFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SCORE = "param_score";
    private static final String ARG_CATEGORY = "quiz_category";

    private int mScore;
    private String mQuizCategory;

    // UI Elements
    TextView mScoreTextView;

    private OnScoreFragmentInteractionListener mListener;

    public ScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param score Parameter 1.
     * @return A new instance of fragment ScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(int score,String category) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);
        args.putString(ARG_CATEGORY,category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mScore= getArguments().getInt(ARG_SCORE);
            mQuizCategory = getArguments().getString(ARG_CATEGORY);
            saveScore();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_score, container, false);
        initializeScoreTextView(view);
        return view;
    }

    /*
    Initializes TextView meant for displaying the score.
     */
    private void initializeScoreTextView(View view){

        mScoreTextView = (TextView)view.findViewById(R.id.score);
        mScoreTextView.setText(Integer.toString(mScore));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onScoreFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnScoreFragmentInteractionListener) {
            mListener = (OnScoreFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnScoreFragmentInteractionListener {
        // TODO: Update argument type and name
        void onScoreFragmentInteraction(Uri uri);
    }

    // saves score in SharedPreferences if it is a top 5 score.
    private void saveScore(){

        new Thread(new Runnable(){

            @Override
            public void run() {

                SharedPreferences sharedPref = getActivity().
                                               getSharedPreferences(getActivity().getResources().getString(R.string.preference_key)
                                                       ,Context.MODE_PRIVATE);

                JSONObject object = Common.setScore(mQuizCategory, mScore, sharedPref);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(mQuizCategory, object.toString());
                editor.commit();
            }
        }).start();
    }


}

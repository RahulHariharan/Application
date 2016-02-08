package fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ClearCacheRequest;
import com.mvw.wordpower.R;

import common.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuizFragment.OnQuizFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUESTION_COUNT = "question_count";

    private int mQuestionCount = -1;
    private boolean mIsCorrect = false;
    private String mSelectedOption;

    // UI elements
    TextView mProgressTextView;
    TextView mOptionATextView, mOptionBTextView, mOptionCTextView, mOptionDTextView;
    Button mNextButton,mGiveupButton;
    ProgressBar mProgressBar;
    ImageView mAnswerImageView;
    CountDownTimer mCountDownTimer;

    private OnQuizFragmentInteractionListener mListener;

    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionCount The number of questions remaining.
     * @return A new instance of fragment QuizFragment.
     */
    public static QuizFragment newInstance(int questionCount) {

        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_COUNT,questionCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionCount = getArguments().getInt(ARG_QUESTION_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        initializeImageView(view);
        initializeOptionsTextView(view);
        initializeProgressTextView(view);
        initializeNextButton(view);
        initializeGiveupButton(view);
        initializeProgressBar(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onQuizFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuizFragmentInteractionListener) {
            mListener = (OnQuizFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
        mCountDownTimer.cancel();
        mCountDownTimer = null;
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
    public interface OnQuizFragmentInteractionListener {
        // TODO: Update argument type and name
        void onQuizFragmentInteraction(Uri uri);
        void onQuizFragmentNextButton(boolean isCorrect);
        void onQuizFragmentGiveUpButton();
    }

    // initialize image view associated with answer graphic
    private void initializeImageView(View view){

        mAnswerImageView = (ImageView)view.findViewById(R.id.symbol);
    }

    // this is to restore all TextViews to Color.TRANSPARENT when a TextView is selecte
    private void clearAllOptions(){

        // TODO: need to change this color
        this.mOptionATextView.setTextColor(Color.GRAY);
        this.mOptionBTextView.setTextColor(Color.GRAY);
        this.mOptionCTextView.setTextColor(Color.GRAY);
        this.mOptionDTextView.setTextColor(Color.GRAY);
    }
    // intialize TextViews associated with answer options and attach click listeners
    private void initializeOptionsTextView(View view){

        this.mOptionATextView = (TextView)view.findViewById(R.id.answer_option_A);
        this.mOptionBTextView = (TextView)view.findViewById(R.id.answer_option_B);
        this.mOptionCTextView = (TextView)view.findViewById(R.id.answer_option_C);
        this.mOptionDTextView = (TextView)view.findViewById(R.id.answer_option_D);

        // attach click listener to mOptionATextView
        mOptionATextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView optionATextView = (TextView) v;
                clearAllOptions();
                mSelectedOption = optionATextView.getText().toString();
                optionATextView.setTextColor(Color.YELLOW);

            }
        });

        // attach click listener to mOptionBTextView
        mOptionBTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView optionBTextView = (TextView) v;
                clearAllOptions();
                mSelectedOption = optionBTextView.getText().toString();
                optionBTextView.setTextColor(Color.YELLOW);
            }
        });

        // attach click listener to mOptionCTextView
        mOptionCTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView optionCTextView = (TextView) v;
                clearAllOptions();
                mSelectedOption = optionCTextView.getText().toString();
                optionCTextView.setTextColor(Color.YELLOW);
            }
        });

        // attach click listener to mOptionDTextView
        mOptionDTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView optionDTextView = (TextView) v;
                clearAllOptions();
                mSelectedOption = optionDTextView.getText().toString();
                optionDTextView.setTextColor(Color.YELLOW);
            }
        });
    }

    // rotate the 'correct-image' when a correct answer is chosen
    private void rotateCorrectImage(){

        Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        mAnswerImageView.setImageResource(R.drawable.notification_done);
        mAnswerImageView.startAnimation(rotate);
    }

    // rotate the 'wrong-image' when a wrong answer is chosen
    private void shakeWrongImage(){

        Animation rotate = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mAnswerImageView.setImageResource(R.drawable.notification_error);
        mAnswerImageView.startAnimation(rotate);
    }

    private void initializeProgressTextView(View view){
        this.mProgressTextView = (TextView)view.findViewById(R.id.progress_text);
        this.mProgressTextView.setText(Integer.toString(mQuestionCount) + "/" + Integer.toString(Constants.TOTAL_QUESTION_COUNT));
    }

    // Initialize next button and attach click listener
    private void initializeNextButton(View view){

        this.mNextButton = (Button)view.findViewById(R.id.next_button);
        this.mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mQuestionCount == Constants.TOTAL_QUESTION_COUNT)
                    mListener.onQuizFragmentGiveUpButton();
                else {
                    mListener.onQuizFragmentNextButton(mIsCorrect);
                }

            }
        });

        if(mQuestionCount == Constants.TOTAL_QUESTION_COUNT)
            this.mNextButton.setText("Show score!");
    }

    // Initialize Give-Up button and attach click listener
    private void initializeGiveupButton(View view){

        this.mGiveupButton = (Button)view.findViewById(R.id.giveup_button);
        this.mGiveupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCountDownTimer != null)
                    mCountDownTimer.cancel();
                mListener.onQuizFragmentGiveUpButton();
            }
        });
    }

    private void initializeProgressBar(View view){

        this.mProgressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        this.mProgressBar.setMax(Constants.PROGRESS_BAR_MAX);
        this.mCountDownTimer = new CountDownTimer(Constants.PROGRESS_BAR_MAX*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                mProgressBar.setProgress((int)(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                mProgressBar.setProgress(0);
                if(mQuestionCount < Constants.TOTAL_QUESTION_COUNT)
                    mListener.onQuizFragmentNextButton(mIsCorrect);
                else
                    mListener.onQuizFragmentGiveUpButton();
            }
        }.start();
    }
}

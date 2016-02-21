package models;

import java.util.List;

/**
 * Created by rahul on 21-02-2016.
 */
public abstract class QuizSection {

    private List<Integer> mScores;

    public void setScores(List<Integer> scores){this.mScores = scores;}

    public List<Integer> getScores(){return this.mScores;}

}

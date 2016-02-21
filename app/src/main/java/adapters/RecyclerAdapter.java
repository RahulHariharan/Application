package adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mvw.wordpower.R;
import common.Constants;


/**
 * Created by rahul on 18-01-2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mOptionTextView;

        public ViewHolder(View view) {
            super(view);
            mOptionTextView = (TextView) view.findViewById(R.id.menu_option);

        }
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_element,parent,false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        String option = Constants.QUIZ_MENU_LIST[position];

        holder.mOptionTextView.setText(option);
    }

    @Override
    public int getItemCount() {
        return Constants.QUIZ_MENU_LIST.length;
    }
}

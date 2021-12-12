package edu.sjsu.android.myfitnessfriend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private List<String[]> values;
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtHeader;
        public TextView txtFooter;
        public View layout;

        public ViewHolder(View v)
        {
            super(v);
            layout=v;
            txtHeader=(TextView)v.findViewById(R.id.firstLine);
            txtFooter=(TextView)v.findViewById(R.id.secondLine);
        }
    }


    public MyAdapter(List<String[]>myDataset)
    {
        values=myDataset;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v= inflater.inflate(R.layout.row_layout,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override public void onBindViewHolder(ViewHolder holder,final int position)
    {

        String [] value = values.get(position);
        final String DATE=value[0];
        final String WEIGHT=value[1];
        holder.txtHeader.setText(DATE);
        holder.txtFooter.setText(WEIGHT);
    }

    @Override public int getItemCount(){return values.size();
    }
}

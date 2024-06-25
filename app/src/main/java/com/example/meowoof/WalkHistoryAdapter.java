package com.example.meowoof;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WalkHistoryAdapter extends RecyclerView.Adapter<WalkHistoryAdapter.WalkHistoryHolder> {
    private Context context;
    private ArrayList<WalkHistory> walkHistories;

    public WalkHistoryAdapter(Context context, ArrayList<WalkHistory> walkHistories) {
        this.context = context;
        this.walkHistories = walkHistories;
    }

    @NonNull
    @Override
    public WalkHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.walk_layout_item, parent, false);

        return new WalkHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalkHistoryAdapter.WalkHistoryHolder holder, int position) {
        WalkHistory walkHistory = walkHistories.get(position);
        holder.setDetails(walkHistory);

    }

    @Override
    public int getItemCount() {
        return walkHistories.size();
    }

    class WalkHistoryHolder extends RecyclerView.ViewHolder{
        private TextView txtStepCounter, txtDate, txtPet;

        public WalkHistoryHolder(@NonNull View itemView) {
            super(itemView);
            txtStepCounter = itemView.findViewById(R.id.txtStepCount);
            txtPet = itemView.findViewById(R.id.txtPet);
            txtDate = itemView.findViewById(R.id.txtDate);
        }

        void setDetails(WalkHistory walkHistory){
            txtStepCounter.setText(String.format("%d", walkHistory.getStepCount()));
            txtDate.setText(walkHistory.getSave_at().toString());
            txtPet.setText(walkHistory.getPet());
        }
    }
}

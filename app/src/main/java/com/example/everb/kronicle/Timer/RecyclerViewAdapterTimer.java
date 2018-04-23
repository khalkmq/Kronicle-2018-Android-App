package com.example.everb.kronicle.Timer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import com.example.everb.kronicle.R;

public class RecyclerViewAdapterTimer extends RecyclerView.Adapter<RecyclerViewAdapterTimer.MyViewHolder>{

    private List<TimerData> timerDataList;

    public RecyclerViewAdapterTimer(List<TimerData> listTimer) {
        this.timerDataList = listTimer;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.timer_row_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.timerTitle.setText(timerDataList.get(position).getTitle());
        holder.timerFocus.setText(timerDataList.get(position).getFocus_duration());
        holder.timerShort.setText(timerDataList.get(position).getShort_break_duration());
        holder.timerLong.setText(timerDataList.get(position).getLong_break_duration());
    }

    @Override
    public int getItemCount() {
        return timerDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView timerTitle;
        TextView timerFocus;
        TextView timerShort;
        TextView timerLong;

        public MyViewHolder(View itemView) {
            super(itemView);

            timerTitle = itemView.findViewById(R.id.title_tri);
            timerFocus = itemView.findViewById(R.id.focus_time_tri);
            timerShort = itemView.findViewById(R.id.short_break_time_tri);
            timerLong = itemView.findViewById(R.id.long_break_time_tri);
        }
    }
}

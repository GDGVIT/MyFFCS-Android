package com.dscvit.android.myffcs.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dscvit.android.myffcs.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CourseTabRecyclerviewAdapter extends RecyclerView.Adapter<CourseTabRecyclerviewAdapter.DayViewHolder> {
    private static final String TAG = "CourseTabRecyclerviewAd";
    private List<String> days, timings, venues;

    public CourseTabRecyclerviewAdapter(List<String> days, List<String> timings, List<String> venues) {
        this.days = days;
        this.timings = timings;
        this.venues = venues;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_recyclerview_item, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.dayText.setText(days.get(position));
        holder.timeText.setText(timings.get(position));
        holder.venueText.setText(venues.get(position));
//        Log.d(TAG, "onBindViewHolder: " + days.size());
//        Log.d(TAG, "onBindViewHolder: " + timings.size());
//        Log.d(TAG, "onBindViewHolder: " + venues.size());
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class DayViewHolder extends RecyclerView.ViewHolder {
        TextView dayText, timeText, venueText;

        DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.day_text);
            timeText = itemView.findViewById(R.id.time_text);
            venueText = itemView.findViewById(R.id.venue_text);

        }
    }
}

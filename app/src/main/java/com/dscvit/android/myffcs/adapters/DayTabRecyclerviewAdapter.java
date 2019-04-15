package com.dscvit.android.myffcs.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dscvit.android.myffcs.R;
import com.dscvit.android.myffcs.models.ClassroomResponse;
import com.dscvit.android.myffcs.utils.CourseRepository;
import com.dscvit.android.myffcs.utils.Utils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DayTabRecyclerviewAdapter extends RecyclerView.Adapter<DayTabRecyclerviewAdapter.DayCoursesViewHolder> {
    private List<ClassroomResponse> courseList;
    private ClassroomResponse recentlyDeletedItem;
    private int recentlyDeletedItemPosition;
    private String selectedDay;
    private Context context;
    private Activity activity;
    private Snackbar snackbar;
    private CourseRepository courseRepository;

    public DayTabRecyclerviewAdapter(List<ClassroomResponse> courseList, String selectedDay, Context context, Activity activity) {
        this.courseList = courseList;
        this.selectedDay = selectedDay;
        this.context = context;
        this.activity = activity;
        courseRepository = new CourseRepository(activity.getApplication());
        View view = activity.findViewById(R.id.coordinator_layout);
        snackbar = Snackbar.make(view, "Deleted course", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo delete", v -> undoDeletedItem());
        snackbar.addCallback(new Snackbar.Callback() {

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_SWIPE) {
                    courseRepository.deleteCourse(recentlyDeletedItem);
                }
            }

            @Override
            public void onShown(Snackbar snackbar) {
            }
        });
    }

    public Context getContext() {
        return context;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    @NonNull
    @Override
    public DayCoursesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_recyclerview_item, parent, false);
        return new DayCoursesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayCoursesViewHolder holder, int position) {
        holder.courseNameText.setText(courseList.get(position).getTitle());
        holder.courseCodeText.setText(courseList.get(position).getCode());
        holder.slotText.setText(courseList.get(position).getSlot());
        holder.venueText.setText(courseList.get(position).getVenue());
        holder.timingText.setText(Utils.getTimingFromCourseAndDay(courseList.get(position), selectedDay));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void deleteItem(int position) {
        recentlyDeletedItem = courseList.get(position);
        recentlyDeletedItemPosition = position;
        courseList.remove(position);
        notifyItemRemoved(position);
        snackbar.show();
    }

    private void undoDeletedItem() {
        courseList.add(recentlyDeletedItemPosition, recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }


    class DayCoursesViewHolder extends RecyclerView.ViewHolder {
        TextView courseNameText, courseCodeText, slotText, timingText, venueText;

        DayCoursesViewHolder(@NonNull View itemView) {
            super(itemView);
            courseNameText = itemView.findViewById(R.id.course_name_text_day);
            courseCodeText = itemView.findViewById(R.id.course_code_text_day);
            slotText = itemView.findViewById(R.id.slot_text_day);
            timingText = itemView.findViewById(R.id.timing_text_day);
            venueText = itemView.findViewById(R.id.venue_text_day);
        }
    }
}

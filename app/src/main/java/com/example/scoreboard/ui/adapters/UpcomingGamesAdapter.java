package com.example.scoreboard.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.scoreboard.R;
import com.example.scoreboard.net.data.RecentGames;
import com.example.scoreboard.net.data.TeamSchedule;
import com.example.scoreboard.ui.fragments.GameDetailsFragment;


public class UpcomingGamesAdapter extends RecyclerView.Adapter<UpcomingGamesAdapter.MyViewHolder> {
    private Context context;
    private TeamSchedule teamSchedule;
    private GameDetailsFragment gameDetailsFragment;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private GameDetailsFragment gameDetailsFragment;
        private TextView date;
        private TextView teamOne;
        private TextView teamTwo;
        private TextView postponed;

        private MyViewHolder(GameDetailsFragment gameDetailsFragment, View v) {
            super(v);
            this.gameDetailsFragment = gameDetailsFragment;
            date = v.findViewById(R.id.date);
            teamOne = v.findViewById(R.id.team1);
            teamTwo = v.findViewById(R.id.team2);
            postponed = v.findViewById(R.id.postponed);
        }
    }

    public UpcomingGamesAdapter(GameDetailsFragment gameDetailsFragment, TeamSchedule teamSchedule) {
        this.gameDetailsFragment = gameDetailsFragment;
        this.teamSchedule = teamSchedule;
    }

    @Override
    public UpcomingGamesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_games_list, parent, false);

        context = parent.getContext();
        return new MyViewHolder(gameDetailsFragment, view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(teamSchedule != null && teamSchedule.getEvents() != null) {
            holder.date.setText(teamSchedule.getEvents().get(position).getDateEvent());
            holder.teamOne.setText(teamSchedule.getEvents().get(position).getStrHomeTeam());
            holder.teamTwo.setText(teamSchedule.getEvents().get(position).getStrAwayTeam());
            if (teamSchedule.getEvents().get(position).getStrPostponed().equalsIgnoreCase("yes")) {
                holder.postponed.setVisibility(View.VISIBLE);
            } else {
                holder.postponed.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public int getItemCount() {
        if(teamSchedule != null && teamSchedule.getEvents() != null) {
            return teamSchedule.getEvents().size();
        }
        return 0;
    }

}


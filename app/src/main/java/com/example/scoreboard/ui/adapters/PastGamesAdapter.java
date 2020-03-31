package com.example.scoreboard.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.scoreboard.R;
import com.example.scoreboard.net.data.RecentGames;
import com.example.scoreboard.ui.fragments.GameDetailsFragment;




public class PastGamesAdapter extends RecyclerView.Adapter<PastGamesAdapter.MyViewHolder> {
    private Context context;
    private RecentGames recentGames;
    private GameDetailsFragment gameDetailsFragment;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private GameDetailsFragment gameDetailsFragment;
        private TextView date;
        private TextView teamOne;
        private TextView teamTwo;
        private TextView scoreTeamOne;
        private TextView scoreTeamTwo;
        private TextView postponed;

        private MyViewHolder(GameDetailsFragment gameDetailsFragment, View v) {
            super(v);
            this.gameDetailsFragment = gameDetailsFragment;
            date = v.findViewById(R.id.date);
            teamOne = v.findViewById(R.id.team1);
            teamTwo = v.findViewById(R.id.team2);
            scoreTeamOne = v.findViewById(R.id.score_team1);
            scoreTeamTwo = v.findViewById(R.id.score_team2);
            postponed = v.findViewById(R.id.postponed);
        }
    }

    public PastGamesAdapter(GameDetailsFragment gameDetailsFragment, RecentGames recentGames) {
        this.gameDetailsFragment = gameDetailsFragment;
        this.recentGames = recentGames;
    }

    @Override
    public PastGamesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_games_list, parent, false);

        context = parent.getContext();
        return new MyViewHolder(gameDetailsFragment, view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(recentGames != null && recentGames.getResults() != null) {
            holder.date.setText(recentGames.getResults().get(position).getDateEvent());
            holder.teamOne.setText(recentGames.getResults().get(position).getStrHomeTeam());
            holder.teamTwo.setText(recentGames.getResults().get(position).getStrAwayTeam());
            holder.scoreTeamOne.setText(recentGames.getResults().get(position).getIntHomeScore());
            holder.scoreTeamTwo.setText(recentGames.getResults().get(position).getIntAwayScore());
            if (recentGames.getResults().get(position).getStrPostponed().equalsIgnoreCase("yes")) {
                holder.postponed.setVisibility(View.VISIBLE);
            } else {
                holder.postponed.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public int getItemCount() {
        if(recentGames != null && recentGames.getResults() != null) {
            return recentGames.getResults().size();
        }
        return 0;
    }

}


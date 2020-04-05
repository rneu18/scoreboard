package com.example.scoreboard.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.scoreboard.R;
import com.example.scoreboard.net.data.RecentGames;
import com.example.scoreboard.net.data.TeamDetail;
import com.example.scoreboard.ui.fragments.GameDetailsFragment;
import com.example.scoreboard.ui.fragments.SearchFragment;


public class TeamListAdapter extends RecyclerView.Adapter<TeamListAdapter.MyViewHolder> {
    private Context context;
    private TeamDetail teamDetail;
    private SearchFragment searchFragment;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private SearchFragment searchFragment;
        private TextView teamName;
        private TextView leauge;
        private TextView gameType;
        private Button selectTeam;


        private MyViewHolder(SearchFragment searchFragment, View v) {
            super(v);
            this.searchFragment = searchFragment;
            teamName = v.findViewById(R.id.team_name);
            leauge = v.findViewById(R.id.league_name);
            gameType = v.findViewById(R.id.team_game);
            selectTeam = v.findViewById(R.id.select_team);
        }
    }

    public TeamListAdapter(SearchFragment searchFragment, TeamDetail teamDetail) {
        this.searchFragment = searchFragment;
        this.teamDetail = teamDetail;
    }

    @Override
    public TeamListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.team_list, parent, false);

        context = parent.getContext();
        return new MyViewHolder(searchFragment, view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        if(teamDetail != null) {
            holder.teamName.setText(teamDetail.getTeams().get(position).getStrTeam());
            holder.leauge.setText(teamDetail.getTeams().get(position).getStrLeague());
            holder.gameType.setText(teamDetail.getTeams().get(position).getStrSport());

            holder.selectTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchFragment.setTeam(position);

                }
            });

        }

    }


    @Override
    public int getItemCount() {
        if(teamDetail != null) {
            return teamDetail.getTeams().size();
        }
        return 0;
    }

}


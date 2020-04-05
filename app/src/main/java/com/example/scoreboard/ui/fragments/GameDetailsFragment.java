package com.example.scoreboard.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scoreboard.R;
import com.example.scoreboard.net.data.RecentGames;
import com.example.scoreboard.net.data.TeamDetail;
import com.example.scoreboard.net.data.TeamSchedule;
import com.example.scoreboard.ui.MainActivity;
import com.example.scoreboard.ui.adapters.PastGamesAdapter;
import com.example.scoreboard.ui.adapters.UpcomingGamesAdapter;
import com.example.scoreboard.utility.SharedPeferencesUtility;
import com.example.scoreboard.viewModel.ScoreDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class GameDetailsFragment extends Fragment {

    private ScoreDetailsViewModel scoreDetailsViewModel;
    private RecyclerView pastGamesRecyclerView;
    private ImageView teamLogo;
    private RecyclerView upComingGamesRecyclerView;
    private PastGamesAdapter pastGamesAdapter;
    private UpcomingGamesAdapter upcomingGamesAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView pastGamesTitle;
    private TextView upComingTitle;
    private TextView teamCountry;
    private TextView teamLeague;
    private TextView teamGame;
    private TextView teamName;
    private Boolean openedFirst = true;
    private TextView searchMsg;
    private ConstraintLayout detailHolder;

    public GameDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_details, container, false);
        pastGamesRecyclerView = view.findViewById(R.id.past_game_rv);
        upComingGamesRecyclerView = view.findViewById(R.id.upcoming_game_rv);
        teamLogo = view.findViewById(R.id.team_logo);
        pastGamesTitle = view.findViewById(R.id.past_game_title);
        teamName = view.findViewById(R.id.team_name);
        teamCountry = view.findViewById(R.id.set_country);
        teamLeague = view.findViewById(R.id.set_league);
        teamGame = view.findViewById(R.id.set_game);
        upComingTitle = view.findViewById(R.id.upcoming_game_title);
        searchMsg = view.findViewById(R.id.ask_search);
        detailHolder = view.findViewById(R.id.details_holder);
        pastGamesRecyclerView.setHasFixedSize(true);
        upComingGamesRecyclerView.setHasFixedSize(true);
        scoreDetailsViewModel = ViewModelProviders.of(getActivity()).get(ScoreDetailsViewModel.class);
        scoreDetailsViewModel.getTeamHistory().observe(this, new Observer<RecentGames>() {

            @Override
            public void onChanged(RecentGames recentGames) {
                populatePastGamesReyclerView(recentGames);
            }
        });

        scoreDetailsViewModel.getTeamSchedule().observe(this, new Observer<TeamSchedule>() {

            @Override
            public void onChanged(TeamSchedule teamSchedule) {
                populateUpcomingGamesReyclerView(teamSchedule);
            }
        });

        scoreDetailsViewModel.getTeamDetail().observe(this, new Observer<TeamDetail.Team>() {

            @Override
            public void onChanged(TeamDetail.Team teamDetail) {
                populateHomeFragmentUi(teamDetail);
            }
        });
        String savedTeamName = SharedPeferencesUtility.getSavedTeam(Objects.requireNonNull(this.getActivity()));
        int savedTeamIndex = SharedPeferencesUtility.getSavedIndex(getActivity());
        if (savedTeamName != null && openedFirst) {
            openedFirst = false;
            scoreDetailsViewModel.getTeam(savedTeamName, savedTeamIndex);
        }
        if (savedTeamName == null) {
            searchMsg.setVisibility(View.VISIBLE);
            detailHolder.setVisibility(View.GONE);
            if (getContext() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.switchToHome(1);
            }
        }

        pastGamesTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pastGamesTitle.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                upComingTitle.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
                pastGamesRecyclerView.setVisibility(View.VISIBLE);
                upComingGamesRecyclerView.setVisibility(View.GONE);


            }
        });

        upComingTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pastGamesTitle.setBackgroundColor(getResources().getColor(R.color.cardview_dark_background));
                upComingTitle.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                pastGamesRecyclerView.setVisibility(View.GONE);
                upComingGamesRecyclerView.setVisibility(View.VISIBLE);
            }
        });



        return view;
    }

    private  void populateHomeFragmentUi(TeamDetail.Team teamDetail) {
        if (teamDetail != null && teamDetail.getStrTeamLogo() !=null) {
            String imageUrl = teamDetail.getStrTeamLogo();
            Picasso.get().load(imageUrl).into(teamLogo);
            teamLogo.setVisibility(View.VISIBLE);
        } else {
            teamLogo.setVisibility(View.GONE);
        }
        if (teamDetail != null) {
            teamName.setText(teamDetail.getStrTeam());
            teamLeague.setText(teamDetail.getStrLeague());
            teamCountry.setText(teamDetail.getStrCountry());
            teamGame.setText(teamDetail.getStrSport());
            String teamID = teamDetail.getIdTeam();
            scoreDetailsViewModel.getRecentGames(teamID);
            scoreDetailsViewModel.getTeamSchedule(teamID);
        }

        searchMsg.setVisibility(View.GONE);
        detailHolder.setVisibility(View.VISIBLE);


    }

    private void populatePastGamesReyclerView(RecentGames recentGames) {
        if (recentGames !=null && recentGames.getResults() != null) {
            pastGamesTitle.setText(R.string.past_games);
        } else {
            pastGamesTitle.setText(R.string.history_na);
        }
        layoutManager = new LinearLayoutManager(getContext());
        pastGamesRecyclerView.setLayoutManager(layoutManager);
        pastGamesAdapter = new PastGamesAdapter(this, recentGames);
        pastGamesRecyclerView.setAdapter(pastGamesAdapter);

    }

    private void populateUpcomingGamesReyclerView(TeamSchedule teamSchedule) {
        if (teamSchedule != null && teamSchedule.getEvents() != null) {

            upComingTitle.setText(R.string.upcoming_games);
        } else {
            upComingTitle.setText(R.string.schedule_na);
        }
        layoutManager = new LinearLayoutManager(getContext());
        upComingGamesRecyclerView.setLayoutManager(layoutManager);
        upcomingGamesAdapter = new UpcomingGamesAdapter(this, teamSchedule);
        upComingGamesRecyclerView.setAdapter(upcomingGamesAdapter);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}

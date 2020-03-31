package com.example.scoreboard.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.scoreboard.R;
import com.example.scoreboard.net.data.TeamDetail;
import com.example.scoreboard.ui.MainActivity;
import com.example.scoreboard.utility.SharedPeferencesUtility;
import com.example.scoreboard.viewModel.ScoreDetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class HomeFragment extends Fragment {

    private ScoreDetailsViewModel scoreDetailsViewModel;
    private ImageView teamLogo;
    private String savedTeamName = null;
    private Boolean openedFirst = true;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        savedTeamName = SharedPeferencesUtility.getSavedTeam(Objects.requireNonNull(this.getActivity()));
        teamLogo = view.findViewById(R.id.team_logo);
        scoreDetailsViewModel = ViewModelProviders.of(getActivity()).get(ScoreDetailsViewModel.class);
        scoreDetailsViewModel.getTeamDetail().observe(this, new Observer<TeamDetail>() {

            @Override
            public void onChanged(TeamDetail teamDetail) {
               populateHomeFragmentUi(teamDetail);
            }
        });
        if (savedTeamName != null && openedFirst) {
            openedFirst = false;
            scoreDetailsViewModel.getTeamDetails(savedTeamName, this.getActivity());
        }
        return view;
    }

    private  void populateHomeFragmentUi(TeamDetail teamDetail) {
        if (teamDetail != null && teamDetail.getTeams() != null && teamDetail.getTeams().get(0).getStrTeamLogo() !=null) {
            String imageUrl = teamDetail.getTeams().get(0).getStrTeamLogo();
            Picasso.get().load(imageUrl).into(teamLogo);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

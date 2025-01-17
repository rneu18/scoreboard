package com.example.scoreboard.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scoreboard.R;
import com.example.scoreboard.net.data.TeamDetail;
import com.example.scoreboard.ui.MainActivity;
import com.example.scoreboard.ui.adapters.TeamListAdapter;
import com.example.scoreboard.utility.HideSoftKeyUtility;
import com.example.scoreboard.viewModel.ScoreDetailsViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SearchFragment extends Fragment {
    private EditText searchTeam;
    private Button search;
    private ScoreDetailsViewModel scoreDetailsViewModel;
    private boolean isSearchClicked = false;
    private TextView seachTeamErro;
    private TextView seachNetworkErro;
    private FirebaseAnalytics mFirebaseAnalytics;

    private TeamListAdapter teamListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;


    public SearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchTeam = view.findViewById(R.id.search_input);
        search = view.findViewById(R.id.search_btn);
        seachNetworkErro = view.findViewById(R.id.search_error2);
        seachTeamErro = view.findViewById(R.id.search_error);
        recyclerView = view.findViewById(R.id.teams_rv);

        scoreDetailsViewModel = ViewModelProviders.of(getActivity()).get(ScoreDetailsViewModel.class);
        scoreDetailsViewModel.setTeamToNull();
        scoreDetailsViewModel.getSwitchTOHomeFragment().observe(this, new Observer<Boolean>() {

            @Override
            public void onChanged(Boolean switchFragment) {
               if(switchFragment && isSearchClicked) {
                   isSearchClicked = false;
                   scoreDetailsViewModel.setSwitchToHomeFragment(false);
                   if (getContext() instanceof MainActivity) {
                      MainActivity mainActivity = (MainActivity) getContext();
                      mainActivity.switchToHome(0);
                   }
               }
            }
        });

        scoreDetailsViewModel.setErrorMeassageVisibilty(2);

        scoreDetailsViewModel.getErrorMeassageVisibilty().observe(this, new Observer<Integer>() {

            @Override
            public void onChanged(Integer code) {
                switch(code) {
                    case 0:
                        seachNetworkErro.setVisibility(View.GONE);
                        seachTeamErro.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        seachNetworkErro.setVisibility(View.VISIBLE);
                        seachTeamErro.setVisibility(View.GONE);
                        break;
                    default:
                        seachNetworkErro.setVisibility(View.GONE);
                        seachTeamErro.setVisibility(View.GONE);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideSoftKeyUtility.hideSoftKeyboard(getActivity());
                scoreDetailsViewModel.setTeamToNull();
                String teamForSearch = searchTeam.getText().toString().trim();
                searchTeam.setText("");
                if (teamForSearch.length() > 0) {
                    scoreDetailsViewModel.setErrorMeassageVisibilty(2);
                    isSearchClicked = true;
                    searchTeam(teamForSearch);
                }
            }
        });
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());

        scoreDetailsViewModel.getTeamList().observe(this, new Observer<TeamDetail>() {

            @Override
            public void onChanged(TeamDetail teamDetail) {
                populatePastGamesReyclerView(teamDetail);
            }
        });

        return view;
    }

    private void populatePastGamesReyclerView(TeamDetail teamDetail) {

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        teamListAdapter = new TeamListAdapter(this, teamDetail);
        recyclerView.setAdapter(teamListAdapter);

    }


    public void searchTeam(String team) {

        Bundle bundle = new Bundle();
        bundle.putString("Event", team);
        mFirebaseAnalytics.logEvent("action", bundle);
        scoreDetailsViewModel.getTeamDetails(team, this.getActivity());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void setTeam(int postion) {
        scoreDetailsViewModel.setTeam(postion, getContext());

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}

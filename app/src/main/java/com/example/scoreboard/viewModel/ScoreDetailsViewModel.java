package com.example.scoreboard.viewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.scoreboard.net.data.RecentGames;
import com.example.scoreboard.net.data.TeamDetail;
import com.example.scoreboard.net.NetworkCommunicator;
import com.example.scoreboard.net.data.TeamSchedule;
import com.example.scoreboard.utility.SharedPeferencesUtility;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class ScoreDetailsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> switchTOHomeFragment = new MutableLiveData<>();
    private final MutableLiveData<TeamDetail.Team> teamDetail = new MutableLiveData<>();
    private final MutableLiveData<TeamDetail> teamList = new MutableLiveData<>();
    private final MutableLiveData<TeamSchedule> teamSchedule = new MutableLiveData<>();
    private final MutableLiveData<RecentGames> teamHistory = new MutableLiveData<>();
    private final MutableLiveData<Integer> errorMeassageVisibilty = new MutableLiveData<>();

    private NetworkCommunicator networkCommunicator = new NetworkCommunicator();


    public void getTeamDetails(final String teamName, final Context context) {

        networkCommunicator.getTeamDetail(teamName, new Subscriber<TeamDetail>() {

            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(TeamDetail responseData) {
                //TODO
                if (responseData != null && responseData.getTeams() != null) {
                    setTeamToNull();
                    teamList.setValue(responseData);
                    errorMeassageVisibilty.setValue(2);

                } else {
                    errorMeassageVisibilty.setValue(0);
                }

            }

            @Override
            public void onError(Throwable t) {
                errorMeassageVisibilty.setValue(null);
                errorMeassageVisibilty.setValue(1);

            }

            @Override
            public void onComplete() {
                //TODO

            }
        });
    }

    public void getTeam(final String teamName, final int savedIndex) {

        networkCommunicator.getTeam(teamName, new Subscriber<TeamDetail>() {

            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(TeamDetail responseData) {
                //TODO
                if (responseData != null && responseData.getTeams() != null) {
                    setTeamToNull();
                    teamDetail.setValue(responseData.getTeams().get(savedIndex));
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                //TODO

            }
        });
    }

    public void getRecentGames(final String id) {
        networkCommunicator.getTeamHistory(id, new Subscriber<RecentGames>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(RecentGames responseData) {
                if(responseData != null && responseData.getResults() != null) {
                    teamHistory.setValue(responseData);
                } else {

                }


            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void getTeamSchedule(final String id) {
        networkCommunicator.getTeamSchedule(id, new Subscriber<TeamSchedule>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(TeamSchedule responseData) {

                if (responseData != null && responseData.getEvents() != null) {
                    teamSchedule.setValue(responseData);
                } else {

                }

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public MutableLiveData<TeamDetail.Team> getTeamDetail() {
        return teamDetail;
    }
    public MutableLiveData<TeamDetail> getTeamList() {
        return teamList;
    }

    public void setTeamToNull() {
        teamList.setValue(null);
    }
    public MutableLiveData<TeamSchedule> getTeamSchedule() {
        return teamSchedule;
    }
    public MutableLiveData<RecentGames> getTeamHistory() {
        return teamHistory;
    }
    public MutableLiveData<Boolean> getSwitchTOHomeFragment() {
        return switchTOHomeFragment;
    }
    public void setSwitchToHomeFragment(Boolean switchFragment) {
        switchTOHomeFragment.setValue(switchFragment);
    }

    public MutableLiveData<Integer> getErrorMeassageVisibilty(){
        return errorMeassageVisibilty;
    }

    public void setErrorMeassageVisibilty(int code) {
        errorMeassageVisibilty.setValue(code);
    }

    public void setTeam(int position,Context context) {
        teamHistory.setValue(null);
        teamSchedule.setValue(null);
        teamDetail.setValue(null);
        switchTOHomeFragment.setValue(true);
        teamDetail.setValue(teamList.getValue().getTeams().get(position));
        //SharedPeferencesUtility.setSavedTeam(teamList.getTeams().get(0).getStrTeam(), context);
        SharedPeferencesUtility.setSavedTeam(teamList.getValue().getTeams().get(position).getStrTeam(), context);
    }

}

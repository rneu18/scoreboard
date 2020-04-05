package com.example.scoreboard.net;


import com.example.scoreboard.net.data.RecentGames;
import com.example.scoreboard.net.data.TeamDetail;
import com.example.scoreboard.net.data.TeamSchedule;

import org.reactivestreams.Subscriber;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkCommunicator {

    String trackTeamBaseUrl = "https://www.thesportsdb.com/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(trackTeamBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    //This methods calls for team detail using retrofit
    public void getTeamDetail(String team, final Subscriber<TeamDetail> subscriber) {

        TrackScoreApiServicesInterface trackScoreApiServicesInterface = retrofit.create(TrackScoreApiServicesInterface.class);
        Call<TeamDetail> userTeam = trackScoreApiServicesInterface.searchTeam(team);
        userTeam.enqueue(new Callback<TeamDetail>() {
            @Override
            public void onResponse(Call<TeamDetail> call, Response<TeamDetail> response) {
                subscriber.onNext(response.body());

            }

            @Override
            public void onFailure(Call<TeamDetail> call, Throwable t) {
                subscriber.onError(t);

            }
        });
    }

    public void getTeamSchedule(String id, final Subscriber<TeamSchedule> subscriber) {

        TrackScoreApiServicesInterface trackScoreApiServicesInterface = retrofit.create(TrackScoreApiServicesInterface.class);
        Call<TeamSchedule> userTeam = trackScoreApiServicesInterface.getUpcomingGames(id);
        userTeam.enqueue(new Callback<TeamSchedule>() {
            @Override
            public void onResponse(Call<TeamSchedule> call, Response<TeamSchedule> response) {
                subscriber.onNext(response.body());

            }

            @Override
            public void onFailure(Call<TeamSchedule> call, Throwable t) {
                subscriber.onError(t);

            }
        });
    }

    public void getTeamHistory(String id, final Subscriber<RecentGames> subscriber) {

        TrackScoreApiServicesInterface trackScoreApiServicesInterface = retrofit.create(TrackScoreApiServicesInterface.class);
        Call<RecentGames> userTeam = trackScoreApiServicesInterface.getRecentGames(id);
        userTeam.enqueue(new Callback<RecentGames>() {
            @Override
            public void onResponse(Call<RecentGames> call, Response<RecentGames> response) {
                subscriber.onNext(response.body());

            }

            @Override
            public void onFailure(Call<RecentGames> call, Throwable t) {
                subscriber.onError(t);

            }
        });
    }

    public void getTeam(String id, final Subscriber<TeamDetail> subscriber) {

        TrackScoreApiServicesInterface trackScoreApiServicesInterface = retrofit.create(TrackScoreApiServicesInterface.class);
        Call<TeamDetail> userTeam = trackScoreApiServicesInterface.searchTeam(id);
        userTeam.enqueue(new Callback<TeamDetail>() {
            @Override
            public void onResponse(Call<TeamDetail> call, Response<TeamDetail> response) {
                subscriber.onNext(response.body());

            }

            @Override
            public void onFailure(Call<TeamDetail> call, Throwable t) {
                subscriber.onError(t);

            }
        });
    }


}

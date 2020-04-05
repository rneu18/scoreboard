package com.example.scoreboard.net;

import com.example.scoreboard.net.data.RecentGames;
import com.example.scoreboard.net.data.TeamDetail;
import com.example.scoreboard.net.data.TeamSchedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrackScoreApiServicesInterface {

    //Get teams based on search query
    //https://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=Arsenal
    @GET("api/v1/json/1/searchteams.php")
    Call<TeamDetail> searchTeam(@Query("t") String team);

    //Get recebt games based on search query
    @GET("api/v1/json/1/eventslast.php")
    Call<RecentGames> getRecentGames(@Query("id") String id);

    //Get schedule based on search query
    @GET("api/v1/json/1/eventsnext.php")
    Call<TeamSchedule> getUpcomingGames(@Query("id") String id);

    //Get team based on search query
    //lookupteam.php?id=133604
    @GET("api/v1/json/1/lookupteam.php")
    Call<TeamDetail.Team> getTeam(@Query("id") String id);

}

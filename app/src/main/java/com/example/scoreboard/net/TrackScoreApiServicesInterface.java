package com.example.scoreboard.net;

import com.example.scoreboard.net.data.RecentGames;
import com.example.scoreboard.net.data.TeamDetail;
import com.example.scoreboard.net.data.TeamSchedule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrackScoreApiServicesInterface {

    //Get teams based on search query
    @GET("searchteams.php")
    Call<TeamDetail> searchTeam(@Query("t") String team);

    //Get teams based on search query
    @GET("eventslast.php")
    Call<RecentGames> getRecentGames(@Query("id") String id);

    //Get teams based on search query
    @GET("eventsnext.php")
    Call<TeamSchedule> getUpcomingGames(@Query("id") String id);

}

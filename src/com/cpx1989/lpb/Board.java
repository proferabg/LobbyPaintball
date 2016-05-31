package com.cpx1989.lpb;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Board {
	
	private Scoreboard scoreboard;
	private Objective objective;
	
	public Board(Paintball p){
		scoreboard = p.getServer().getScoreboardManager().getNewScoreboard();
	    objective = scoreboard.registerNewObjective("Lobby Paintball", "dummy");
	    objective.setDisplayName("§eLobby Paintball");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}
	
	@SuppressWarnings("deprecation")
	public void addPlayer(Player p) {
	    Team team = scoreboard.getPlayerTeam(p);
	    if (team == null) {
	    	team = scoreboard.registerNewTeam(p.getName());
		    team.addPlayer(p);
		    Score score = objective.getScore(p.getName());
		    score.setScore(0);
	    }
	}
	
	@SuppressWarnings("deprecation")
	public void addShot(Player p, int num){
		Team team = scoreboard.getPlayerTeam(p);
	    if (team == null) {
	    	addPlayer(p);
	    } else {
	    	Score score = objective.getScore(p.getName());
		    score.setScore(score.getScore()+1);
	    }
	}
	
	@SuppressWarnings("deprecation")
	public void removePlayer(Player p){
	    Team team = scoreboard.getPlayerTeam(p);
	    if (team != null){
	    	team.removePlayer(p);
	    	team.unregister();
	    }
	}
	
	public Scoreboard getScoreboard(){
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		return scoreboard;
	}

	@SuppressWarnings("deprecation")
	public void setZero(Player p) {
		Team team = scoreboard.getPlayerTeam(p);
	    if (team == null) {
	    	addPlayer(p);
	    } else {
	    	Score score = objective.getScore(p.getName());
		    score.setScore(0);
	    }
	}
	
	@SuppressWarnings("deprecation")
	public int getScore(Player p){
		Team team = scoreboard.getPlayerTeam(p);
	    if (team == null) {
	    	addPlayer(p);
	    	return 0;
	    } else {
	    	Score score = objective.getScore(p.getName());
		    return score.getScore();
	    }
	}

	@SuppressWarnings("deprecation")
	public void resetAllScores() {
		for (Player p : Bukkit.getOnlinePlayers()){
			Team team = scoreboard.getPlayerTeam(p);
		    if (team == null) {
		    	addPlayer(p);
		    } else {
		    	Score score = objective.getScore(p.getName());
			    score.setScore(0);
		    }
		}
		
	}

}

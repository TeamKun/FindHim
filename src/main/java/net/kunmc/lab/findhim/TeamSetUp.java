package net.kunmc.lab.findhim;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

public class TeamSetUp {

    private static final String TEAM_FINDER_NAME = "Seek";
    private static final String TEAM_TARGET_NAME = "Escape";
    public static Team teamFinder;
    public static Team teamTarget;

    public static void setupFinder(){
        teamFinder = FindHim.board.getTeam(TEAM_FINDER_NAME);
        if(teamFinder == null) {
            teamFinder = FindHim.board.registerNewTeam(TEAM_FINDER_NAME);
            teamFinder.setPrefix(ChatColor.BLUE+"[Seek]");
            teamFinder.setSuffix(ChatColor.RESET.toString());
            teamFinder.setDisplayName("Seek");
            teamFinder.setAllowFriendlyFire(false);
            //teamFinder.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
        }
    }

    public static void setupTarget(){
        teamTarget = FindHim.board.getTeam(TEAM_TARGET_NAME);
        if(teamTarget == null) {
            teamTarget = FindHim.board.registerNewTeam(TEAM_TARGET_NAME);
            teamTarget.setPrefix(ChatColor.RED + "Escape");
            teamTarget.setSuffix(ChatColor.RESET.toString());
            teamTarget.setDisplayName("Escape");
            teamTarget.setAllowFriendlyFire(false);
            teamTarget.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        }
    }
}

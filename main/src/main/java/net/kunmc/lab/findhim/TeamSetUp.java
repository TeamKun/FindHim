package net.kunmc.lab.findhim;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

public class TeamSetUp {

    private static final String TEAM_FINDER_NAME = "Finder";
    private static final String TEAM_TARGET_NAME = "Target";
    public static Team teamFinder;
    public static Team teamTarget;

    public static void setupFinder(){
        teamFinder = FindHim.board.getTeam(TEAM_FINDER_NAME);
        if(teamFinder == null) {
            teamFinder = FindHim.board.registerNewTeam(TEAM_FINDER_NAME);
            teamFinder.setPrefix(ChatColor.BLUE+"[Finder]");
            teamFinder.setSuffix(ChatColor.RESET.toString());
            teamFinder.setDisplayName("Finder");
            teamFinder.setAllowFriendlyFire(false);
            teamFinder.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        }
    }

    public static void setupTarget(){
        teamTarget = FindHim.board.getTeam(TEAM_TARGET_NAME);
        if(teamTarget == null) {
            teamTarget = FindHim.board.registerNewTeam(TEAM_TARGET_NAME);
            teamTarget.setPrefix(ChatColor.RED + "[Target]");
            teamTarget.setSuffix(ChatColor.RESET.toString());
            teamTarget.setDisplayName("Target");
            teamTarget.setAllowFriendlyFire(false);
            teamTarget.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        }
    }
}

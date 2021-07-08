package net.kunmc.lab.findhim;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public final class FindHim extends JavaPlugin {
    //チーム関連
    public static Scoreboard board;
    ScoreboardManager sbManager;

    //起動関連
    public static boolean running = false;
    public static FindHim plugin;


    @Override
    public void onEnable() {
        System.out.println("[FindHim] Plugin ON^.＿.^");
        plugin = this;

        sbManager = Bukkit.getScoreboardManager();
        board = sbManager.getMainScoreboard();

        //各チームを設定
        TeamSetUp.setupFinder();
        TeamSetUp.setupTarget();

        getServer().getPluginManager().registerEvents(new TouchEventListener(), this);
        this.getCommand("findhim").setExecutor(new CommandManager());
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        GameManager.setGameMode();
        GameManager.gameManager();
    }

    public static void timer(){
        new SchedulerTask().runTaskTimer(plugin,0L,20L);
    }

    public static void stop() {
        GameManager.playing = false;
        GameManager.chaos = false;
        running = false;
        GameManager.kaisuued = 0;
        GameManager.clear = 0;
        TouchEventListener.captured = false;
        return;
    }
}

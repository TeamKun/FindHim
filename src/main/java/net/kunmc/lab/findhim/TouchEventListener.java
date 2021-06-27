package net.kunmc.lab.findhim;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class TouchEventListener implements Listener {
    public static boolean captured = false;
    public static Player finder;

    @EventHandler
    public static void touch(PlayerInteractEntityEvent e) {
        if(!GameManager.playing)
            return;

        if (TeamSetUp.teamFinder.hasEntry(e.getPlayer().getName())){
            if (e.getRightClicked().getName().equals(GameManager.wanted.getName())){
                finder = e.getPlayer();
                GameManager.time = GameManager.intT+5;
                captured = true;//その後ScheduleTask側で確認
            }
        }

    }




}

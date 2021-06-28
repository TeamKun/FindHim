package net.kunmc.lab.findhim;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameManager {

    private static FindHim plugin = FindHim.plugin;
    static ArrayList<Player> fPlayers = new ArrayList<Player>();
    static ArrayList<Player> tPlayers = new ArrayList<Player>();
    static List<EntityType> entities = Arrays.asList(
            EntityType.BAT,
            EntityType.BEE,
            EntityType.BLAZE,
            EntityType.CAT,
            EntityType.CAVE_SPIDER,
            EntityType.CHICKEN,
            EntityType.COD,
            EntityType.COW,
            EntityType.DOLPHIN,
            EntityType.DONKEY,
            EntityType.DROWNED,
            EntityType.ELDER_GUARDIAN,
            EntityType.ENDERMAN,
            EntityType.ENDERMITE,
            EntityType.EVOKER,
            EntityType.FOX,
            EntityType.GIANT,
            EntityType.GHAST,
            EntityType.GUARDIAN,
            EntityType.HUSK,
            EntityType.LLAMA,
            EntityType.MAGMA_CUBE,
            EntityType.MULE,
            EntityType.MUSHROOM_COW,
            EntityType.OCELOT,
            EntityType.PANDA,
            EntityType.PARROT,
            EntityType.PHANTOM,
            EntityType.PIG,
            //EntityType.PIG_ZOMBIE,
            EntityType.POLAR_BEAR,
            EntityType.PUFFERFISH,
            EntityType.RABBIT,
            EntityType.SHEEP,
            EntityType.SHULKER,
            EntityType.SILVERFISH,
            EntityType.SKELETON,
            EntityType.SLIME,
            EntityType.SPIDER,
            EntityType.SQUID,
            EntityType.STRAY,
            EntityType.VILLAGER,
            EntityType.WITCH,
            EntityType.WITHER,
            EntityType.WITHER_SKELETON,
            EntityType.WOLF,
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_HORSE,
            EntityType.ZOMBIE_VILLAGER
    );
   
    private static int tPlayerNum = 0;
    public static Player wanted;
    public static int intT = 40;
    public static int time;
    public static int kaisuu;
    public static  int kaisuued = 0;
    public static boolean playing = false;
    public static int i;
    public static int escapeMode;

    //mode用の定数
    final static int modeCountDown0 = 100;
    final static int modeShowWanted = 1;
    final static int modeGamePlaying = 2;
    final static int modeCaptureWanted = 3;
    final static int modeGameSet = 4;




    //ゲームモード設定
    public static void setGameMode() {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (!player.isOnline()) continue;

            if (TeamSetUp.teamFinder.hasEntry(player.getName())) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendRawMessage("あなたはクリエイティブモードになりました！！");
                fPlayers.add(player);
            } else if (TeamSetUp.teamTarget.hasEntry(player.getName())) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendRawMessage("あなたはサバイバルモードになりました！！");
                tPlayers.add(player);
                tPlayerNum++;
            }
        }
    }

    public static void gameManager() {
         if(fPlayers.size()==0) {
            FindHim.plugin.getServer().broadcastMessage("Finderがいません");
            forPlayers(modeGameSet);
        }
        if(tPlayers.size()==0) {
            FindHim.plugin.getServer().broadcastMessage("Targetがいません");
            forPlayers(modeGameSet);
        }
        playing = false;
        i = 5;
        if(kaisuued==0)
            FindHim.timer();
        kaisuued++;
        wanted = chooseWanted();
        escapeMode = new java.util.Random().nextInt(3);
    }

    //全プレイヤーの繰り返し
    public static void forPlayers(int mode) {
        for (Player player : fPlayers) {
            doPlayer(player, mode);
        }
        for (Player player : tPlayers) {
            doPlayer(player, mode);
        }
    }

    //各実行部分
    public static void doPlayer(Player player, int mode) {
        switch (mode) {
            case modeCountDown0:
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2f, 0.5f);
                player.sendTitle("" , ""+i, 5, 20, 8);
                break;
            case modeShowWanted:
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2f, 0.5f);
                player.sendTitle(ChatColor.RED + wanted.getName() + "をさがせ!", escapeMode+" "+time, 5, 20, 8);
                break;
            case modeGamePlaying:
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2f, 0.5f);
                player.sendTitle("" , ""+time, 5, 20, 8);
                if (TeamSetUp.teamTarget.hasEntry(player.getName())) {
                    switch (escapeMode){
                        case 0:
                            break;//running away
                        case 1:
                            player.teleport(new Location(player.getWorld(), wanted.getLocation().getX(),wanted.getLocation().getY(),wanted.getLocation().getZ()));
                            break;//tp to target everytime
                        case 2:
                            Location loc = player.getLocation();
                            Collections.shuffle(entities);
                            loc.getWorld().spawnEntity(loc, entities.get(0));
                            System.out.println("333");
                            break;//random mob spawn
                    }
                }
                break;
            case modeCaptureWanted:
                player.sendTitle(ChatColor.BLUE + TouchEventListener.finder.getName() + "が見つけた!", "終了", 5, 20, 8);
                break;
            case modeGameSet:
                player.sendTitle("end", ""+time, 5, 20, 8);
                player.setGameMode(GameMode.SURVIVAL);
                player.sendRawMessage("あなたはサバイバルモードになりました.");
                //FindHim.stop();
                break;
        }

    }

    public static Player chooseWanted() {
        int r = new java.util.Random().nextInt(tPlayerNum);
        return tPlayers.get(r);
    }

    public static boolean setTime(String t){
        try {
            intT = Integer.parseInt(t);
            if (intT>0) {
                time = intT;
                return true;
            }
        } catch (Exception ignore) {
            // do nothing
        }
        return false;
    }

    public static boolean setKaisuu(String k){
        try {
            int intK = Integer.parseInt(k);
            if (intK>0) {
                kaisuu = intK;
                return true;
            }
        } catch (Exception ignore) {
            // do nothing
        }
        return false;
    }

}




package net.kunmc.lab.findhim;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class GameManager{

    private static FindHim plugin = FindHim.plugin;
    static ArrayList<Player> fPlayers = new ArrayList<Player>();
    static ArrayList<Player> tPlayers = new ArrayList<Player>();

   
    private static int tPlayerNum = 0;
    public static Location loc;
    public static Player wanted;
    public static int intT = 40;
    public static int time;
    public static int kaisuu;
    public static  int kaisuued = 0;
    public static int clear = 0;
    public static int mobSpawnInterval = 5;
    public static boolean playing = false;
    public static boolean chaos = false;
    public static int i;

    //mode用の定数
    final static int modeCountDown0 = 100;
    final static int modeShowWanted = 1;
    final static int modeGamePlaying = 2;
    final static int modeCaptureWanted = 3;
    final static int modeGameSet = 4;
    final static int modeGameOver = 5;

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

    //ゲームモード設定
    public static void setGameMode() {
        //fPlayers.clear();
        //tPlayers.clear();
        tPlayerNum=0;
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (!player.isOnline()) continue;

            if (TeamSetUp.teamFinder.hasEntry(player.getName())) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendRawMessage("あなたはクリエイティブモードになりました！！");
                fPlayers.add(player);
            } else if (TeamSetUp.teamTarget.hasEntry(player.getName())) {
                if(chaos){
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendRawMessage("あなたはクリエイティブモードになりました！！");
                }else {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendRawMessage("あなたはサバイバルモードになりました！！");
                }
                tPlayers.add(player);
                tPlayerNum++;
            }
        }
    }

    public static void gameManager() {

        kaisuued++;
         if(fPlayers.size()==0) {
            FindHim.plugin.getServer().broadcastMessage("[Seek]がいません");
            forPlayers(modeGameSet);
            FindHim.stop();
            return;
        }
        if(tPlayers.size()==0) {
            FindHim.plugin.getServer().broadcastMessage("[Escape]がいません");
            forPlayers(modeGameSet);
            FindHim.stop();
            return;
        }
        playing = false;
        i = 5;
        if(kaisuued==1)
            FindHim.timer();
        wanted = chooseWanted();
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
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2f, 0.5f);
                player.sendTitle("" , ""+i, 5, 20, 8);
                break;
            case modeShowWanted:
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2f, 0.5f);
                player.sendTitle(ChatColor.RED + wanted.getName() + ChatColor.WHITE+"をさがせ!", "残り"+time+"秒", 5, 20, 8);
                if (TeamSetUp.teamTarget.hasEntry(player.getName())) {
                    player.spawnParticle(Particle.CLOUD,(new Location(player.getWorld(), wanted.getLocation().getX(),wanted.getLocation().getY(),wanted.getLocation().getZ())),10);
                }

                /*chaosモード ランダムmobスポーン*/
                if(chaos) {
                    if (TeamSetUp.teamTarget.hasEntry(player.getName())) {
                        if (time % mobSpawnInterval == 0) {
                            Location locMobSpawn = player.getLocation();
                            Collections.shuffle(entities);
                            locMobSpawn.getWorld().spawnEntity(locMobSpawn, entities.get(0));
                        }
                    }
                }
                break;
            case modeGamePlaying:
                player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 2f, 0.5f);
                player.sendTitle("" , "残り"+time+"秒", 5, 20, 8);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + wanted.getName() + ChatColor.WHITE+"をさがせ!"));
                if (TeamSetUp.teamTarget.hasEntry(player.getName())) {
                    player.spawnParticle(Particle.CLOUD,(new Location(player.getWorld(), wanted.getLocation().getX(),wanted.getLocation().getY(),wanted.getLocation().getZ())),10);
                }

                /*chaosモード ランダムmobスポーン*/
                if(chaos) {
                    if (TeamSetUp.teamTarget.hasEntry(player.getName())) {
                        if (time % mobSpawnInterval == 0) {
                            Location locMobSpawn = player.getLocation();
                            Collections.shuffle(entities);
                            locMobSpawn.getWorld().spawnEntity(locMobSpawn, entities.get(0));
                        }
                    }
                }

                break;
            case modeCaptureWanted:
                player.sendTitle(ChatColor.BLUE + TouchEventListener.finder.getName() + ChatColor.WHITE+"が見つけた!", "", 5, 20, 8);
                {//花火　コピペ
                    loc = new Location(player.getWorld(), wanted.getLocation().getX(),wanted.getLocation().getY(),wanted.getLocation().getZ());
                    Firework firework = loc.getWorld().spawn(loc, Firework.class);
                    FireworkMeta meta=firework.getFireworkMeta();
                    FireworkEffect.Builder effect=FireworkEffect.builder();
                    effect.withColor(Color.YELLOW);
                    meta.addEffect(effect.build());
                    meta.setPower(1);
                    firework.setFireworkMeta(meta);
                }
                if(player != wanted) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 5));
                }
                break;
            case modeGameSet:
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.sendTitle("end", "クリア回数: "+(clear), 5, 100, 8);
                player.setGameMode(GameMode.SURVIVAL);
                player.sendRawMessage("あなたはサバイバルモードになりました.");
                break;
            case modeGameOver:
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.sendTitle("GAME OVER", "クリア回数: "+(clear), 5, 100, 8);
                player.setGameMode(GameMode.SURVIVAL);
                player.sendRawMessage("あなたはサバイバルモードになりました.");
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

    public static boolean setInterval(String i){
        try {
            int intI = Integer.parseInt(i);
            if (intI>0) {
                mobSpawnInterval = intI;
                return true;
            }
        } catch (Exception ignore) {
            // do nothing
        }
        return false;
    }

}




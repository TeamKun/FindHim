package net.kunmc.lab.findhim;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.stream.Stream;
import org.bukkit.command.TabCompleter;
import java.util.List;
import java.util.stream.Collectors;



public class CommandManager implements CommandExecutor,TabCompleter {

    private boolean same(String a, String b) {
        return a.equals(b);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 0) {
            sender.sendMessage("エラー：引数が足りない！");
            return false;
        }

        if(args.length == 1) {
            // help
            if (same(args[0], "help")) {
                final String[] HELP_MESSAGE = {
                        "-------------- [ " + ChatColor.GREEN + "FindHim Plugin" + ChatColor.RESET + " ] ---------------",
                        "/findhim help : ヘルプ表示",
                        "/findhim start <int 制限時間> <int 回数>: プラグインを有効化",
                        "/findhim chaos <int 制限時間> <int 回数> <int スポーン間隔>: カオスモードを有効化",
                        "/findhim stop : プラグインを無効化",
                        "--------------------------------------------",
                };
                Stream.of(HELP_MESSAGE).forEach(sender::sendMessage);
                return true;
            }

            // start
            if (same(args[0], "start")) {
                GameManager.time = 40;
                GameManager.kaisuu = 3;
                sender.sendMessage("[FindHim] Plugin is started.");
                FindHim.plugin.start();
                return true;
            }

            // chaos
            if (same(args[0], "chaos")) {
                GameManager.time = 40;
                GameManager.kaisuu = 3;
                GameManager.chaos = true;
                GameManager.mobSpawnInterval = 5;
                sender.sendMessage("[FindHim] Plugin is started.");
                FindHim.plugin.start();
                return true;
            }

            // stop
            if (same(args[0], "stop")) {
                FindHim.plugin.stop();
                sender.sendMessage("Plugin is stopped.");
                return true;
            }
        }

        // timeの設定
        //(start)
        if (args.length == 2 && same(args[0], "start")) {
            if(GameManager.setTime(args[1])){
                sender.sendMessage(String.format("time が %s に設定されました。", args[1]));
                GameManager.kaisuu = 3;
                sender.sendMessage("[FindHim] Plugin is started.");
                FindHim.plugin.start();
                return true;
            }
            sender.sendMessage(" 不正な引数です。");
            return false;
        }
        //(chaos)
        if (args.length == 2 && same(args[0], "chaos")) {
            if(GameManager.setTime(args[1])){
                sender.sendMessage(String.format("time が %s に設定されました。", args[1]));
                GameManager.kaisuu = 3;
                GameManager.mobSpawnInterval = 5;
                GameManager.chaos = true;
                sender.sendMessage("[FindHim] Plugin is started.");
                FindHim.plugin.start();
                return true;
            }
            sender.sendMessage(" 不正な引数です。");
            return false;
        }

        // kaisuuの設定
        //(start)
        if (args.length == 3 && same(args[0], "start")) {
            if(GameManager.setTime(args[1])){
                sender.sendMessage(String.format("制限時間 が %s に設定されました。", args[1]));
            }else {
                sender.sendMessage(" 不正な引数です。");
                return false;
            }

            if(GameManager.setKaisuu(args[2])){
                sender.sendMessage(String.format("回数 が %s に設定されました。", args[2]));
                sender.sendMessage("[FindHim] Plugin is started.");
                FindHim.plugin.start();
                return true;
            }else{
                sender.sendMessage(" 不正な引数です。");
                return false;
            }
        }
        //(chaos)
        if (args.length == 3 && same(args[0], "chaos")) {
            if(GameManager.setTime(args[1])){
                sender.sendMessage(String.format("制限時間 が %s に設定されました。", args[1]));
            }else {
                sender.sendMessage(" 不正な引数です。");
                return false;
            }

            if(GameManager.setKaisuu(args[2])){
                sender.sendMessage(String.format("回数 が %s に設定されました。", args[2]));
                sender.sendMessage("[FindHim] Plugin is started.");
                GameManager.mobSpawnInterval = 5;
                GameManager.chaos = true;
                FindHim.plugin.start();
                return true;
            }else{
                sender.sendMessage(" 不正な引数です。");
                return false;
            }
        }

        //mobSpawnIntervalの設定(chaosのみ)
        if (args.length == 4 && same(args[0], "chaos")) {
            if(GameManager.setTime(args[1])){
                sender.sendMessage(String.format("制限時間 が %s に設定されました。", args[1]));
            }else {
                sender.sendMessage(" 不正な引数です。");
                return false;
            }

            if(GameManager.setKaisuu(args[2])){
                sender.sendMessage(String.format("回数 が %s に設定されました。", args[2]));
                sender.sendMessage("[FindHim] Plugin is started.");
            }else{
                sender.sendMessage(" 不正な引数です。");
                return false;
            }

            if(GameManager.setInterval(args[3])){
                sender.sendMessage(String.format("スポーン間隔 が %s に設定されました。", args[3]));
                sender.sendMessage("[FindHim] Plugin is started.");
                GameManager.chaos = true;
                FindHim.plugin.start();
                return true;
            }else{
                sender.sendMessage(" 不正な引数です。");
                return false;
            }
        }

        sender.sendMessage(" 不正な引数です。");
        return false;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Stream.of("help",  "start","chaos", "stop")
                    .filter(e -> e.startsWith(args[0]))
                    .collect(Collectors.toList());
        }

        // time
        if (args.length == 2 && same(args[0], "start")) {
            if (args[1].length() == 0) {
                return Collections.singletonList("40");
            }
        }
        if (args.length == 2 && same(args[0], "chaos")) {
            if (args[1].length() == 0) {
                return Collections.singletonList("40");
            }
        }

        // kaisuu
        if (args.length == 3 && same(args[0], "start")) {
            if (args[2].length() == 0) {
                return Collections.singletonList("3");
            }
        }
        if (args.length == 3 && same(args[0], "chaos")) {
            if (args[2].length() == 0) {
                return Collections.singletonList("3");
            }
        }

        // mobSpawnInterval
        if (args.length == 4 && same(args[0], "chaos")) {
            if (args[3].length() == 0) {
                return Collections.singletonList("5");
            }
        }

        return null;
    }

}


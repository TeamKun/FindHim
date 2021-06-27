package net.kunmc.lab.findhim;

import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerTask extends BukkitRunnable {
    @Override
    public void run() {// スケジューラ側で実行する処理の内容


        if(!FindHim.running){
            this.cancel();
            GameManager.forPlayers(GameManager.modeGameSet);
            return;
        }

        //スタート前
        if(!GameManager.playing){
            GameManager.forPlayers(GameManager.modeCountDown0);
            if(GameManager.i-- <= 0){
                this.cancel();
                GameManager.playing = true;
                FindHim.timer();//スケジューラでカウントダウン
            }
            return;
        }

        //スタート後
        GameManager.time--;
        if(TouchEventListener.captured){
            //System.out.println("aaa");
            if(GameManager.time>GameManager.intT){
              //  System.out.println("bbb");
                GameManager.forPlayers(GameManager.modeCaptureWanted);
            }else if(GameManager.kaisuued<GameManager.kaisuu) {
                //System.out.println("ccc");
                TouchEventListener.captured = false;
                GameManager.gameManager();
            }else {
                //System.out.println("kkk");
                TouchEventListener.captured = false;
                GameManager.kaisuued = 0;
                this.cancel();
                GameManager.forPlayers(GameManager.modeGameSet);
            }
        }
        else
        {
            if (GameManager.time >= GameManager.intT-5) {
                GameManager.forPlayers(GameManager.modeShowWanted);
            }
            if (GameManager.time <= GameManager.intT-6) {
                GameManager.forPlayers(GameManager.modeGamePlaying);
            }
            //debug
            if (GameManager.time == 10) {
                GameManager.time = GameManager.intT+5;
                TouchEventListener.captured = true;
            }
            if (GameManager.time <= 0) {
                this.cancel();
                GameManager.forPlayers(GameManager.modeGameSet);
            }
        }
    }


}

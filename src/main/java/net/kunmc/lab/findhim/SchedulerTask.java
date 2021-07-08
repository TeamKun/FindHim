package net.kunmc.lab.findhim;

import org.bukkit.GameMode;
import org.bukkit.scheduler.BukkitRunnable;

public class SchedulerTask extends BukkitRunnable {
    @Override
    public void run() {// スケジューラ側で実行する処理の内容


        if(!FindHim.running){
            this.cancel();
            GameManager.forPlayers(GameManager.modeGameSet);
            FindHim.stop();
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
        if(TouchEventListener.captured){                   /*■wantedが捕まった時*/
            if(GameManager.time>GameManager.intT){                  //●TouchEventListenerで秒数が追加された後の5秒間
                GameManager.forPlayers(GameManager.modeCaptureWanted);
                //GameManager.wanted.setGameMode(GameMode.SURVIVAL);
            }else if(GameManager.kaisuued<GameManager.kaisuu) {     //●規定の回数に達していなければ
                TouchEventListener.captured = false;
                GameManager.clear++;
                GameManager.setGameMode();                                  //▲ゲーム続行
                GameManager.gameManager();
            }else {                                                 //●規定の回数に達したら
                TouchEventListener.captured = false;
                GameManager.clear++;
                this.cancel();
                GameManager.forPlayers(GameManager.modeGameSet);            //▲ゲームセット
                FindHim.stop();
            }
        }
        else                                                /*捕まっていない時*/
        {
            if (GameManager.time >= GameManager.intT-5) {
                GameManager.forPlayers(GameManager.modeShowWanted);
            }
            if (GameManager.time <= GameManager.intT-6) {
                GameManager.forPlayers(GameManager.modeGamePlaying);
            }
            if (GameManager.time <= 0) {
                this.cancel();
                GameManager.forPlayers(GameManager.modeGameOver);
                FindHim.stop();
            }
        }
    }


}

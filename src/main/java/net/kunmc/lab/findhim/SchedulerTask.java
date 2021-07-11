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
            if(GameManager.time==GameManager.intT+4){                  //●TouchEventListenerで秒数が追加された後
                GameManager.forPlayers(GameManager.modeCaptureWanted);
                GameManager.forPlayers(222);
            }else if(GameManager.time>GameManager.intT){                  //●TouchEventListenerで秒数が追加された後の5秒間
                GameManager.forPlayers(222);
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
            if(GameManager.time==GameManager.intT+4){
                GameManager.forPlayers(GameManager.modeGameOver);
                GameManager.forPlayers(226);
            } else if(GameManager.time>GameManager.intT){
                //TouchEventListener.captured = true;
                GameManager.forPlayers(226);
            }else if(GameManager.time==GameManager.intT){
                TouchEventListener.captured = false;
                if(GameManager.kaisuued<GameManager.kaisuu) {     //●規定の回数に達していなければ
                    TouchEventListener.captured = false;
                    GameManager.setGameMode();                                  //▲ゲーム続行
                    GameManager.gameManager();
                }else {                                                 //●規定の回数に達したら
                    TouchEventListener.captured = false;
                    this.cancel();
                    GameManager.forPlayers(GameManager.modeGameSet);            //▲ゲームセット
                    FindHim.stop();
                }
            } else if (GameManager.time == GameManager.intT-1) {
                GameManager.forPlayers(GameManager.modeShowWanted);
            }else if (GameManager.time <= GameManager.intT) {
                GameManager.forPlayers(GameManager.modeGamePlaying);
            }
            if (GameManager.time <= 0) {
                GameManager.time = GameManager.intT+5;
                //if(GameManager.time>GameManager.intT){

                //}else
            }
        }
    }


}

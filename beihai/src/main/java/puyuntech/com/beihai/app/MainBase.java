package puyuntech.com.beihai.app;

import android.view.KeyEvent;

import com.nicodelee.utils.IsExit;


/**
 * Created by alee on 2015/7/4.
 */
public abstract class MainBase extends BaseAct {

    // 按返回退出App
    private IsExit exit = new IsExit();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            pressAgainExit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void pressAgainExit() {
        if (exit.isExit()) {
            finish();
        } else {
            showInfo("喵，再按一次离开^~^");
            exit.doExitInThreeSecond();
        }
    }


}


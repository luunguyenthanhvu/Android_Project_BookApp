package nlu.hmuaf.android_bookapp.sync;

import java.util.TimerTask;

public class SyncCartTask extends TimerTask {
    private HomeViewModel viewModel;

    public SyncCartTask(HomeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void run() {
//        viewModel.syncCartToServer();
    }
}

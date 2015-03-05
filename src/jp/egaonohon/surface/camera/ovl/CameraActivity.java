package jp.egaonohon.surface.camera.ovl;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

public class CameraActivity extends Activity {
    private CameraView cameraView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraView = new CameraView(this);
        setContentView(cameraView);//カメラビューをsetContentView
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //addContentViewした時点で、CameraOvlViewクラスのonSizeChanged()メソッドが動いてwとhが取得される。
        addContentView(new CameraOvlView(this), params);//プレビューの上にオーバーレイさせる。
    }

    @Override
    protected void onResume() {
        super.onResume();
//        cameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        cameraView.onPause();
    }
}
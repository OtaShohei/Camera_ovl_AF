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
        setContentView(cameraView);//�J�����r���[��setContentView
        LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //addContentView�������_�ŁACameraOvlView�N���X��onSizeChanged()���\�b�h��������w��h���擾�����B
        addContentView(new CameraOvlView(this), params);//�v���r���[�̏�ɃI�[�o�[���C������B
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
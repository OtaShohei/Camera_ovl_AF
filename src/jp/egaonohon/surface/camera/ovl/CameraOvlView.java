package jp.egaonohon.surface.camera.ovl;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class CameraOvlView extends View {

	//�R���X�g���N�^�͂��̂܂܁B
	public CameraOvlView(Context context) {
		super(context);
	}

	private int width, height;

	//CameraOvlView�N���X��new���ꂽ���_��onSizeChanged���\�b�h��������w��h���擾�����B
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		width= w;
		height= h;
	}


	/**
	 *
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// �w�i�F���g�����X�y�A�����g�ɐݒ�
		canvas.drawColor(Color.TRANSPARENT);

		// �`�悷�邽�߂̐�
		Paint paint = new Paint();
		paint.setARGB(255, 255, 0, 0);
		paint.setStyle(Paint.Style.STROKE);


		// �\��
		int radial = height / 10;
		canvas.drawCircle(width/2, height/2, radial*5, paint);
		canvas.drawCircle(width/2, height/2, radial*4, paint);
		canvas.drawCircle(width/2, height/2, radial*3, paint);
		canvas.drawCircle(width/2, height/2, radial*2, paint);
		canvas.drawCircle(width/2, height/2, radial*1, paint);
		//�c����
		canvas.drawLine((float)width/2, (float)0.0, (float)width/2, (float)height, paint);
		//������
		canvas.drawLine((float)0.0, (float)height/2, (float)width,(float)height/2, paint);
		//�����\��
		paint.setARGB(255, 255, 255, 255);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawText("���܂��B���Ă�", 0, height-30, paint);
		//�G�A�[�^�O�Ɏ��Ĕ�Ȃ����
		canvas.drawText("�G�A�[�^�O?", width-75, 17, paint);
		paint.setARGB(125, 100, 100, 255);
		RectF rect=new RectF(width-80, 0, width-5, 30);
		canvas.drawRoundRect(rect, 10, 10, paint);

	}
}


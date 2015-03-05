package jp.egaonohon.surface.camera.ovl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * CameraView�́ACameraBasic�قځA���̂܂܁B �����AAF���g���悤�ɂ��Ă���̂��Ⴄ�B
 * 
 * @author 1107AND
 *
 */
public class CameraView extends SurfaceView {
	private Camera c;
	private static ContentResolver contentResolver = null;
	private boolean afStart = false;

	public CameraView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public CameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CameraView(Context context) {
		super(context);
		init(context);
	}

	public void init(Context context) {
		contentResolver = context.getContentResolver();

		SurfaceHolder holder = getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			public void surfaceCreated(SurfaceHolder holder) {
				c = Camera.open(0);
				try {
					c.setPreviewDisplay(holder);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				c.stopPreview();
				Parameters params = c.getParameters();

				Size sz = params.getSupportedPreviewSizes().get(0);
				params.setPreviewSize(sz.width, sz.height);

				c.setParameters(params);
				setCameraParameters(c);
				c.startPreview();
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
				c.release();
				c = null;
			}
		});
		// holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// �J�����ɑ΂��ĉ摜�̃T�C�Y�̎w������Ă���B
	private void setCameraParameters(Camera camera) {
		// ���̐ݒ��ǂ�ł���
		Parameters parameters = camera.getParameters();
		// �ύX����Ƃ��낾���ύX����B
		parameters.setPictureSize(480, 320); // Default:2048x1536
		// �ύX���e��ۑ�����B
		camera.setParameters(parameters);
	}

	/**
	 * onAutoFocus()���\�b�h���Ăяo�����Ƃ� onTouch�̃^�C�~���O�Ŏ��͍s���Ă���B ���������āA��⎞�Ԃ��������Ă���B
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (e.getAction() == MotionEvent.ACTION_DOWN) {
			Log.v("CAMERA", "ontouch");
			// AutoFoucus����v���𔭍s�I
			if (afStart == false)
				onAutoFocus();
		}
		return true;
	}

	public void onAutoFocus() {
		afStart = true;
		c.autoFocus(new AutoFocusCallback() {// c.autoFocus�ŃI�[�g�t�H�[�J�X�̎x�����o���B
			@Override
			public void onAutoFocus(boolean success, Camera camera) {
				camera.takePicture(new ShutterCallback() {
					public void onShutter() {
					}
				}, null, new Camera.PictureCallback() {
					@Override
					public void onPictureTaken(byte[] data, Camera camera) {
						try {
							String dataName = "photo_"
									+ String.valueOf(Calendar.getInstance()
											.getTimeInMillis()) + ".jpg";
							/**
							 * saveDataToURI�̓M�������[�ɒ��ڏ������ރ��\�b�h�B
							 * SD�J�[�h�o�R�̎��ɗp�����AEnvironment
							 * .getExternalStorageState()���g�����ǂ����͂ǂ���ł������B
							 */
							saveDataToURI(data, dataName);
							camera.startPreview();// �v���r���[���ēx�\���J�n�B
							afStart = false;// �I�[�g�t�H�[�J�X���I�t&���̓���܂ōĂюʐ^�B�e�������Ȃ��B
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}

	// �R���e���c�v���o�C�_�o�R�ŕۑ����郁�\�b�h(�M�������[�ɓo�^�����)
	private void saveDataToURI(byte[] data, String dataName) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
		ContentValues values = new ContentValues();
		values.put(Media.DISPLAY_NAME, dataName);
		values.put(Media.DESCRIPTION, "taken with G1");
		values.put(Media.MIME_TYPE, "image/jpeg");
		values.put(Media.DATE_TAKEN, System.currentTimeMillis());
		Uri uri = contentResolver.insert(Media.EXTERNAL_CONTENT_URI, values);
		try {
			OutputStream outStream = contentResolver.openOutputStream(uri);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
			outStream.close();
		} catch (Exception e) {
		}
	}
}

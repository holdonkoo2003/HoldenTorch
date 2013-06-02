package com.example.holdentorch;

import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {


	Camera cam;

	Camera.Parameters p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		ImageButton flash_button = (ImageButton) findViewById(R.id.flash_button);


		flash_button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {


				if (p.getFlashMode().equals(android.hardware.Camera.Parameters.FLASH_MODE_OFF)) {


					Toast.makeText(getApplicationContext(), p.getFlashMode().toString(), Toast.LENGTH_SHORT).show();
					p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
					cam.setParameters(p);
					cam.startPreview();

					Toast.makeText(getApplicationContext(), p.getFlashMode().toString(), Toast.LENGTH_SHORT).show();


				} else if (p.getFlashMode().equals(android.hardware.Camera.Parameters.FLASH_MODE_TORCH)) {
					p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
					cam.setParameters(p);
					cam.startPreview();

				}
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (cam != null) {
			cam.stopPreview();
			cam.release();
			cam = null;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		getCamera();
		Toast.makeText(getApplicationContext(), p.getFlashMode().toString() + " on resume", Toast.LENGTH_SHORT).show();
	}

	private void getCamera() {
		if (cam == null) {
			try {
				cam = Camera.open();
				p = cam.getParameters();
			} catch (RuntimeException e) {
				Log.e("Camera Error. Failed to Open. Error: ", e.getMessage());
			}
		}
	}


}

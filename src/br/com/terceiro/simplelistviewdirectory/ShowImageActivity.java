package br.com.terceiro.simplelistviewdirectory;

import java.io.File;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowImageActivity extends Activity {
	
	ImageView largeImage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_show_image);
		this.setImageBitmap(
			getIntent().getExtras().getString(MainActivity.IMAGE_PATH_KEY)
		);
	}
	
	private void setImageBitmap(String imagePath) {
		ImageView largeImage =  (ImageView) this.findViewById(R.id.large_image);
		largeImage.setImageBitmap(
				BitmapFactory.decodeFile(imagePath)
		);
	}
}

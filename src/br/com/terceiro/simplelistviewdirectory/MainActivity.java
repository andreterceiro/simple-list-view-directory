package br.com.terceiro.simplelistviewdirectory;

import java.io.File;
import java.util.ArrayList;

import br.com.terceiro.simplelistviewdirectory.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	static final String THUMBS_PREFIX = "THUMB_";
	
	static final String IMAGE_PATH_KEY = "imagePath";
	
	private ListView lista;
	private ImageAndPathAdapter adapter;
	private ArrayList<File> thumbsPaths; 
	
	private File imagesDirectory; 
	
	BaseAlbumDirFactory mAlbumStorageDirFactory;
	
	ImagesFinder imagesFinder;
	
	ImageAndPathAdapter listAdapter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.imagesFinder = new ImagesFinder();
		this.thumbsPaths = this.imagesFinder.listImages(this.getAlbumDir(), THUMBS_PREFIX);
		
		//adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, this.itens);
		listAdapter = new ImageAndPathAdapter(this, R.layout.list_item, this.thumbsPaths);
		
		lista = (ListView) findViewById(R.id.lista);
	
		lista.setAdapter(listAdapter);
		
	    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, View view,
	                                int position1, long id) {

	        	/*Bundle bundle = new Bundle();
	        	bundle.putString(
        			"imagePath", 
        			MainActivity.this.extractLargeImagePath(
    					MainActivity.this.listAdapter.getImagePathAtPosition(position1)
					)
				);*/
	        	
	        	Intent intent = new Intent(MainActivity.this, ShowImageActivity.class);
	        	intent.putExtra(
	           			IMAGE_PATH_KEY, 
	        			MainActivity.this.extractLargeImagePath(
	    					MainActivity.this.listAdapter.getImagePathAtPosition(position1)
						)
				);
	        	
	        	startActivity(intent);
	         }
	    });

		
		//mAlbumStorageDirFactory = new BaseAlbumDirFactory();
	}
	
	private String extractLargeImagePath(File thumb) {
		String absolutePath = thumb.getAbsolutePath();
		String directoryPath = absolutePath.substring(
				0, 
				absolutePath.length() - thumb.getName().length()
		);
		
		String imageName = thumb.getName().substring(THUMBS_PREFIX.length()); 
		
		return directoryPath  + imageName;
	}
	
	private File getAlbumDir() {
		File storageDir = null;

		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			
			String albumName = getAlbumName();
			
			//storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(albumName);
			storageDir = new File (Environment.getExternalStorageDirectory()
					+ "/DCIM/DailySelfie/"); 

			if (storageDir != null) {
				if (! storageDir.mkdirs()) {
					if (! storageDir.exists()){
						Log.d("Andre", "failed to create directory");
						return null;
					}
				}
			}
			
		} else {
			Toast.makeText(this, "Sorry. External storage is not mounted READ/WRITE.", Toast.LENGTH_LONG).show();
			Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
		}
		
		return storageDir;
	}
	
	/* Photo album for this application */
	private String getAlbumName() {
		return getString(R.string.album_name);
	}
	
}

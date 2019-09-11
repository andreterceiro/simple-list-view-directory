package br.com.terceiro.simplelistviewdirectory;

import java.io.File;

import android.os.Environment;

public final class BaseAlbumDirFactory extends AlbumStorageDirFactory {

	// Standard storage location for digital camera files
	private static final String CAMERA_DIR = "/dcim/";

	@Override
	public File getAlbumStorageDir(String albumName) {
		File externalStorageDirectory = Environment.getExternalStorageDirectory();
		
		return new File (
				externalStorageDirectory
				+ CAMERA_DIR
				+ albumName
		);
	}
}

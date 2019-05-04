/**
 * 
 * Copyright 2009,2010,2011,2012 RickCeeNet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package net.rickcee.swingxs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import javax.swing.ImageIcon;

/**
 * @author RickCeeNet
 * 
 */
public abstract class Utils {

	/**
	 * Reads an image file from the classpath and returns the appropriate
	 * ImageIcon object
	 * 
	 * @param path
	 *            The path of the image to read
	 * @return
	 */
	public static ImageIcon getClassPathImage(String path) {
		ImageIcon icon = null;
		try {
			URL url = Utils.class.getClassLoader().getResource(path);
			if (url != null) {
				icon = new ImageIcon(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return icon;
	}
	
	public static synchronized String getFileCheckSum(File in) throws IOException {
		FileInputStream fis = new FileInputStream(in);
		CheckedInputStream cis = null;
		cis = new CheckedInputStream(fis, new CRC32());
		byte[] tempBuf = new byte[2048];
		while (cis.read(tempBuf) != -1) {
			// Nothing. Generate CheckSum
		}

		Long chkSum = new Long(cis.getChecksum().getValue());
		String sourceChkSum = chkSum.toString();
		return sourceChkSum;
	}

}

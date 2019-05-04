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
 * Created on Jan 24, 2012
 * 
 */
package net.rickcee.swingxs.utils;

/**
 * @author RickCeeNet
 * 
 */
public class Constants {
	public static final String HOME = "HOME";
	public static final String USERPROFILE = "USERPROFILE";
	public static String CONFIG_FILE_EXTENSION = ".cfg";

	public static final int TYPE_GETTER = 0;
	public static final int TYPE_SETTER = 1;
	
	public static final String OPTIONS = "Options";
	public static final String EXPORT_ALL = "Export All";
	public static final String EXPORT_SELECTED = "Export Selected";
	public static final String EXPORT_FILTERED = "Export Filtered";
	public static final String EMAIL_ALL = "Email All";
	public static final String EMAIL_SELECTED = "Email Selected";
	public static final String EMAIL_FILTERED = "Email Filtered";
	public static final String CONFIG_SAVE = "Save Table Configuration";
	public static final String CONFIG_RESTORE_LOCAL = "Restore Local Configuration";
	public static final String CONFIG_RESTORE_GLOBAL = "Restore Global Configuration";
	
	/**
	 * Generates the names of Getters and Setters for a particular attribute
	 * following the Java convention.
	 * 
	 * @param field
	 *            The name of the attribute of the class
	 * @param type
	 *            The type of method name to be generated
	 * @return The generated method name
	 */
	public static String getMethodName(String field, int type) {
		String output = "";
		switch (type) {
		case TYPE_GETTER:
			output = output.concat("get");
			break;
		case TYPE_SETTER:
			output = output.concat("set");
			break;
		}
		output = output.concat(field.substring(0, 1).toUpperCase());
		output = output.concat(field.substring(1, field.length()));
		return output;
	}
}

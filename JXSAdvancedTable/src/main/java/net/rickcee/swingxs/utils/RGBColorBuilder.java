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
 * Created on Feb 14, 2012
 * 
 */
package net.rickcee.swingxs.utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RickCeeNet
 * 
 */
public class RGBColorBuilder {
	public static final String BLUE_DODGER = "BLUE_DODGER";
	public static final String BLUE_CORNFLOWER = "BLUE_CORNFLOWER";
	public static final String GREEN_LIME = "GREEN_LIME";
	public static final String GOLD = "GOLD";
	public static final String ORANGE = "ORANGE";

	
	private static Map<String, Color> colors = new HashMap<String, Color>();
	private static Map<String, Integer[]> colorConfig = new HashMap<String, Integer[]>();

	static {
		colorConfig.put("BLUE_DODGER", build(30, 144, 255));
		colorConfig.put("BLUE_CORNFLOWER", build(100, 149, 237));
		colorConfig.put("GREEN_LIME", build(50, 205, 50));
		colorConfig.put("GOLD", build(255, 215, 0));
		colorConfig.put("ORANGE", build(255, 165, 0));
	}

	public static Color get(String key) {
		if(colors.containsKey(key)) {
			return colors.get(key);
		}
		Integer[] codes = colorConfig.get(key);
		Color color = new Color(codes[0], codes[1], codes[2]);
		colors.put(key, color);
		return color;
	}

	private static Integer[] build(Integer r, Integer g, Integer b) {
		Integer[] output = { r, g, b };
		return output;
	}
}

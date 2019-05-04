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
 * Created on Feb 19, 2012
 * 
 */
package net.rickcee.swingxs.model.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author RickCeeNet
 * 
 */
public abstract class JXSRowFilterConstants {

	// public static final Integer CONTAINS = 0;
	public static final Integer EQUALS = 1;
	public static final Integer NOT_EQUALS = 2;
	public static final Integer GREATER_THAN = 3;
	public static final Integer GREATER_OR_EQUALS_THAN = 4;
	public static final Integer LESSER_THAN = 5;
	public static final Integer LESSER_OR_EQUALS_THAN = 6;
	public static final Integer BETWEEN = 7;
	public static final Integer BETWEEN_INCLUSIVE = 8;

	// public static final String LBL_CONTAINS = "Contains";
	public static final String LBL_EQUALS = "Equals";
	public static final String LBL_NOT_EQUALS = "Not Equals";
	public static final String LBL_GREATER_THAN = "Greater than";
	public static final String LBL_GREATER_OR_EQUALS_THAN = "Greater of Equals than";
	public static final String LBL_LESSER_THAN = "Lesser than";
	public static final String LBL_LESSER_OR_EQUALS_THAN = "Lesser of Equals than";
	public static final String LBL_BETWEEN = "Between";
	public static final String LBL_BETWEEN_INCLUSIVE = "Between (Inclusive)";

	protected static Set<String> filterTypes = new TreeSet<String>();
	protected static Set<String> filterTypesForStrings = new TreeSet<String>();
	protected static Map<Integer, String> filterTypeLabels = new HashMap<Integer, String>();
	protected static Map<String, Integer> filterTypeReverse = new HashMap<String, Integer>();

	static {
		filterTypesForStrings.add(LBL_EQUALS);
		filterTypesForStrings.add(LBL_NOT_EQUALS);

		// filterTypes.add(LBL_CONTAINS);
		filterTypes.add(LBL_EQUALS);
		filterTypes.add(LBL_NOT_EQUALS);
		filterTypes.add(LBL_GREATER_THAN);
		filterTypes.add(LBL_GREATER_OR_EQUALS_THAN);
		filterTypes.add(LBL_LESSER_THAN);
		filterTypes.add(LBL_LESSER_OR_EQUALS_THAN);
		filterTypes.add(LBL_BETWEEN);
		filterTypes.add(LBL_BETWEEN_INCLUSIVE);

		// filterTypeLabels.put(CONTAINS, LBL_CONTAINS);
		filterTypeLabels.put(EQUALS, LBL_EQUALS);
		filterTypeLabels.put(NOT_EQUALS, LBL_NOT_EQUALS);
		filterTypeLabels.put(GREATER_THAN, LBL_GREATER_THAN);
		filterTypeLabels.put(GREATER_OR_EQUALS_THAN, LBL_GREATER_OR_EQUALS_THAN);
		filterTypeLabels.put(LESSER_THAN, LBL_LESSER_THAN);
		filterTypeLabels.put(LESSER_OR_EQUALS_THAN, LBL_LESSER_OR_EQUALS_THAN);
		filterTypeLabels.put(BETWEEN, LBL_BETWEEN);
		filterTypeLabels.put(BETWEEN_INCLUSIVE, LBL_BETWEEN_INCLUSIVE);

		// filterTypeReverse.put(LBL_CONTAINS, CONTAINS);
		filterTypeReverse.put(LBL_EQUALS, EQUALS);
		filterTypeReverse.put(LBL_NOT_EQUALS, NOT_EQUALS);
		filterTypeReverse.put(LBL_GREATER_THAN, GREATER_THAN);
		filterTypeReverse.put(LBL_GREATER_OR_EQUALS_THAN, GREATER_OR_EQUALS_THAN);
		filterTypeReverse.put(LBL_LESSER_THAN, LESSER_THAN);
		filterTypeReverse.put(LBL_LESSER_OR_EQUALS_THAN, LESSER_OR_EQUALS_THAN);
		filterTypeReverse.put(LBL_BETWEEN, BETWEEN);
		filterTypeReverse.put(LBL_BETWEEN_INCLUSIVE, BETWEEN_INCLUSIVE);
	}

	/**
	 * @return the filterTypes
	 */
	public static Set<String> getFilterTypes() {
		return filterTypes;
	}

	/**
	 * @return the filterTypeLabels
	 */
	public static Map<Integer, String> getFilterTypeLabels() {
		return filterTypeLabels;
	}

	/**
	 * @return the filterTypeReverse
	 */
	public static Map<String, Integer> getFilterTypeReverse() {
		return filterTypeReverse;
	}

	/**
	 * @return the filterTypesForStrings
	 */
	public static Set<String> getFilterTypesForStrings() {
		return filterTypesForStrings;
	}

}

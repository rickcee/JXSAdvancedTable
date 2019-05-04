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
package net.rickcee.swingxs.config;

import java.awt.Color;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rickcee.swingxs.ITableFilter;
import net.rickcee.swingxs.model.DateFormatConfig;
import net.rickcee.swingxs.model.NumberFormatConfig;
import net.rickcee.swingxs.model.SortConfig;

/**
 * @author RickCeeNet
 * 
 */
public class BeanAttributeContainer {

	// Column Type
	private Map<String, Class<?>> fieldTypes = new HashMap<String, Class<?>>();
	// Column Visibility
	private Map<String, Boolean> fieldVisibility = new HashMap<String, Boolean>();
	// Getters corresponding to class Attributes
	private Map<String, Method> fieldGetters = new HashMap<String, Method>();
	// Setters corresponding to class Attributes
	private Map<String, Method> fieldSetters = new HashMap<String, Method>();
	// Column Background Colors
	private Map<String, Color> fieldBackgroundColor = new HashMap<String, Color>();
	// Column Foreground Colors
	private Map<String, Color> fieldForegroundColor = new HashMap<String, Color>();
	// Field Names (attribute - NOT column name!)
	private Map<String, String> fieldName = new HashMap<String, String>();
	// Column Names (attribute - column name)
	private Map<String, String> fieldLabel = new HashMap<String, String>();
	// Reverse Column Names (column name - attribute)
	private Map<String, String> fieldReverseLabel = new HashMap<String, String>();
	// Column Position
	private Map<Integer, String> fieldPosition = new HashMap<Integer, String>();
	// Column Position
	private Map<String, Integer> fieldReversePosition = new HashMap<String, Integer>();
	// Column Alignment
	private Map<String, Integer> fieldAlignment = new HashMap<String, Integer>();
	// Column Frozen
	private Map<String, Boolean> fieldFixed = new HashMap<String, Boolean>();
	// Column Width
	private Map<String, Integer> fieldWidth = new HashMap<String, Integer>();
	// Column Format
	private Map<String, String> fieldFormat = new HashMap<String, String>();
	// Number Format
	private Map<String, NumberFormatConfig> fieldNumberFormat = new HashMap<String, NumberFormatConfig>();
	private Map<String, DecimalFormat> fieldNumberFormatter = new HashMap<String, DecimalFormat>();
	// Date Format
	private Map<String, DateFormatConfig> fieldDateFormat = new HashMap<String, DateFormatConfig>();
	private Map<String, DateFormat> fieldDateFormatter = new HashMap<String, DateFormat>();
	// Sort Criteria
	private Map<String, SortConfig> sortCriteria = new HashMap<String, SortConfig>();

	private Class<?> clazz;
	private List<ITableFilter> filters;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 */
	public BeanAttributeContainer(Class<?> clazz) {
		super();
		this.clazz = clazz;
		this.filters = new ArrayList<ITableFilter>();
	}

	/**
	 * @return the fieldTypes
	 */
	public Map<String, Class<?>> getFieldTypes() {
		return fieldTypes;
	}

	/**
	 * @return the fieldVisibility
	 */
	public Map<String, Boolean> getFieldVisibility() {
		return fieldVisibility;
	}

	/**
	 * @return the fieldGetters
	 */
	public Map<String, Method> getFieldGetters() {
		return fieldGetters;
	}

	/**
	 * @return the fieldSetters
	 */
	public Map<String, Method> getFieldSetters() {
		return fieldSetters;
	}

	/**
	 * @return the fieldBackgroundColor
	 */
	public Map<String, Color> getFieldBackgroundColor() {
		return fieldBackgroundColor;
	}

	/**
	 * @return the fieldForegroundColor
	 */
	public Map<String, Color> getFieldForegroundColor() {
		return fieldForegroundColor;
	}

	/**
	 * @return the fieldName
	 */
	public Map<String, String> getFieldName() {
		return fieldName;
	}

	/**
	 * @return the fieldPosition
	 */
	public Map<Integer, String> getFieldPosition() {
		return fieldPosition;
	}

	/**
	 * @return the fieldAlignment
	 */
	public Map<String, Integer> getFieldAlignment() {
		return fieldAlignment;
	}

	/**
	 * @return the fieldFixed
	 */
	public Map<String, Boolean> getFieldFixed() {
		return fieldFixed;
	}

	/**
	 * @return the fieldWitdh
	 */
	public Map<String, Integer> getFieldWidth() {
		return fieldWidth;
	}

	/**
	 * @return the fieldReverseName
	 */
	public Map<String, String> getFieldReverseLabel() {
		return fieldReverseLabel;
	}

	/**
	 * @return the filters
	 */
	public List<ITableFilter> getFilters() {
		return filters;
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @return the fieldLabel
	 */
	public Map<String, String> getFieldLabel() {
		return fieldLabel;
	}

	/**
	 * @return the fieldReversePosition
	 */
	public Map<String, Integer> getFieldReversePosition() {
		return fieldReversePosition;
	}

	/**
	 * @return the fieldFormat
	 */
	public Map<String, String> getFieldFormat() {
		return fieldFormat;
	}

	/**
	 * @return the fieldNumberFormat
	 */
	public Map<String, NumberFormatConfig> getFieldNumberFormat() {
		return fieldNumberFormat;
	}

	/**
	 * @return the fieldNumberFormatter
	 */
	public Map<String, DecimalFormat> getFieldNumberFormatter() {
		return fieldNumberFormatter;
	}

	/**
	 * @return the fieldDateFormat
	 */
	public Map<String, DateFormatConfig> getFieldDateFormat() {
		return fieldDateFormat;
	}

	/**
	 * @return the fieldDateFormatter
	 */
	public Map<String, DateFormat> getFieldDateFormatter() {
		return fieldDateFormatter;
	}

	/**
	 * @return the sortCriteria
	 */
	public Map<String, SortConfig> getSortCriteria() {
		return sortCriteria;
	}

}

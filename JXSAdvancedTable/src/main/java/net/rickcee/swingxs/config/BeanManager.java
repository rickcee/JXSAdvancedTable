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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.rickcee.swingxs.model.meta.IFilterable;
import net.rickcee.swingxs.ui.JXSAdvancedTable;
import net.rickcee.swingxs.ui.JXSTable;

/**
 * @author RickCeeNet
 * 
 */
public class BeanManager {
	public static final Integer FIELD_TYPE = 1;

	protected static Map<Class<?>, URL> masterConfigFiles = new HashMap<Class<?>, URL>();
	protected static Map<Class<?>, URL> userConfigFiles = new HashMap<Class<?>, URL>();

	protected static Map<Class<?>, BeanAttributeContainer> container = new HashMap<Class<?>, BeanAttributeContainer>();
	protected static Map<Class<?>, JXSTable<?>> tables = new HashMap<Class<?>, JXSTable<?>>();
	protected static Map<Class<?>, JXSTable<?>> frozenTables = new HashMap<Class<?>, JXSTable<?>>();
	protected static Map<Class<?>, JXSAdvancedTable<?>> advancedTables = new HashMap<Class<?>, JXSAdvancedTable<?>>();
	protected static Map<Class<?>, IFilterable> filters = new HashMap<Class<?>, IFilterable>();

	/**
	 * @return the container
	 */
	public static Map<Class<?>, BeanAttributeContainer> getContainer() {
		return container;
	}

	public static BeanAttributeContainer get(Class<?> clazz) {
		return container.get(clazz);
	}

	public static URL getMasterFileLocation(Class<?> clazz) {
		return masterConfigFiles.get(clazz);
	}

	public static void setMasterFileLocation(Class<?> clazz, URL fileLocation) {
		masterConfigFiles.put(clazz, fileLocation);
	}

	public static URL getUserFileLocation(Class<?> clazz) {
		return userConfigFiles.get(clazz);
	}

	public static void setUserFileLocation(Class<?> clazz, URL fileLocation) {
		userConfigFiles.put(clazz, fileLocation);
	}
	
	/**
	 * @return the tables
	 */
	public static Map<Class<?>, JXSTable<?>> getTables() {
		return tables;
	}

	/**
	 * @return the frozenTables
	 */
	public static Map<Class<?>, JXSTable<?>> getFrozenTables() {
		return frozenTables;
	}

	/**
	 * @return the advancedTables
	 */
	public static Map<Class<?>, JXSAdvancedTable<?>> getAdvancedTables() {
		return advancedTables;
	}

	/**
	 * @return the filters
	 */
	public static Map<Class<?>, IFilterable> getFilters() {
		return filters;
	}

}

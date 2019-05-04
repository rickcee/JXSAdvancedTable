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
 * Created on Feb 18, 2012
 * 
 */
package net.rickcee.swingxs.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.SortOrder;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableSorterModel {
	protected List<String> colNames = new ArrayList<String>();
	protected Set<JXSTableSorterObject> currentOrder = new TreeSet<JXSTableSorterObject>();
	protected BeanAttributeContainer container;
	protected Class<?> clazz;

	public static List<String> orderValues = new ArrayList<String>();

	static {
		orderValues.add(SortOrder.ASCENDING.name());
		orderValues.add(SortOrder.DESCENDING.name());
	}

	/**
	 * Constructor
	 * 
	 * @param clazz
	 */
	public JXSTableSorterModel(Class<?> clazz) {
		this.container = BeanManager.get(clazz);
		this.clazz = clazz;
		for(String key : container.getFieldLabel().values()) {
			this.colNames.add(key);
		}
	}
	
	public Integer getColumnIndex(String colName) {
		String attName = container.getFieldReverseLabel().get(colName);
		Integer attPosition = container.getFieldReversePosition().get(attName);
		return attPosition;
	}

	/**
	 * @return the colNames
	 */
	public List<String> getColNames() {
		return colNames;
	}

	/**
	 * @param colNames
	 *            the colNames to set
	 */
	public void setColNames(List<String> colNames) {
		this.colNames = colNames;
	}

	/**
	 * @return the currentOrder
	 */
	public Set<JXSTableSorterObject> getCurrentOrder() {
		return currentOrder;
	}

	/**
	 * @param currentOrder
	 *            the currentOrder to set
	 */
	public void setCurrentOrder(Set<JXSTableSorterObject> currentOrder) {
		this.currentOrder = currentOrder;
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

}

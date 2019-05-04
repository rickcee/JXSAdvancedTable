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

import java.util.Set;
import java.util.TreeSet;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableFilterModel {
	protected Set<String> colNames = new TreeSet<String>();
	protected Set<JXSTableFilterObject> currentFilters = new TreeSet<JXSTableFilterObject>();
	protected BeanAttributeContainer container;
	protected Boolean andSelected = Boolean.FALSE;
	protected Boolean orSelected = Boolean.FALSE;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 */
	public JXSTableFilterModel(Class<?> clazz) {
		this.container = BeanManager.get(clazz);
		for (String key : container.getFieldLabel().values()) {
			this.colNames.add(key);
		}
	}

	/**
	 * @param colName
	 * @return
	 */
	public Integer getColumnIndex(String colName) {
		String attName = container.getFieldReverseLabel().get(colName);
		Integer attPosition = container.getFieldReversePosition().get(attName);
		return attPosition;
	}

	/**
	 * @return the colNames
	 */
	public Set<String> getColNames() {
		return colNames;
	}

	/**
	 * @param colNames
	 *            the colNames to set
	 */
	public void setColNames(Set<String> colNames) {
		this.colNames = colNames;
	}

	/**
	 * @return the currentFilters
	 */
	public Set<JXSTableFilterObject> getCurrentFilters() {
		return currentFilters;
	}

	/**
	 * @param currentFilters
	 *            the currentFilters to set
	 */
	public void setCurrentFilters(Set<JXSTableFilterObject> currentFilters) {
		this.currentFilters = currentFilters;
	}

	/**
	 * @return the andSelected
	 */
	public Boolean getAndSelected() {
		return andSelected;
	}

	/**
	 * @param andSelected
	 *            the andSelected to set
	 */
	public void setAndSelected(Boolean andSelected) {
		this.andSelected = andSelected;
	}

	/**
	 * @return the orSelected
	 */
	public Boolean getOrSelected() {
		return orSelected;
	}

	/**
	 * @param orSelected
	 *            the orSelected to set
	 */
	public void setOrSelected(Boolean orSelected) {
		this.orSelected = orSelected;
	}

}

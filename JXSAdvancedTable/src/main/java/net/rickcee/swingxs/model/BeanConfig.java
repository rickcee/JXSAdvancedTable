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
package net.rickcee.swingxs.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author RickCeeNet
 * 
 */
public class BeanConfig {
	private Class<?> clazz;
	private List<BeanAttributeConfig> attributes = new ArrayList<BeanAttributeConfig>();
	private Collection<SortConfig> sorters = new ArrayList<SortConfig>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Config Name: [" + clazz + "] / " + super.toString();
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the attributes
	 */
	public List<BeanAttributeConfig> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the attributes to set
	 */
	public void setAttributes(List<BeanAttributeConfig> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the sorters
	 */
	public Collection<SortConfig> getSorters() {
		return sorters;
	}

	/**
	 * @param sorters
	 *            the sorters to set
	 */
	public void setSorters(Collection<SortConfig> sorters) {
		this.sorters = sorters;
	}

}

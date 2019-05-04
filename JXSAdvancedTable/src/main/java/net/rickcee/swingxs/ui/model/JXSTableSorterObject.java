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
package net.rickcee.swingxs.ui.model;

import javax.swing.SortOrder;

public class JXSTableSorterObject implements Comparable<JXSTableSorterObject> {
	protected Integer listIndex = 0;
	protected String column;
	protected Integer columnIndex;
	protected SortOrder order;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return (getListIndex() + 1) + " / " + getColumn() + " - " + getOrder().name();
	}

	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return the order
	 */
	public SortOrder getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(SortOrder order) {
		this.order = order;
	}

	/**
	 * @return the index
	 */
	public Integer getListIndex() {
		return listIndex;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setListIndex(Integer index) {
		this.listIndex = index;
	}

	/**
	 * @return the columnIndex
	 */
	public Integer getColumnIndex() {
		return columnIndex;
	}

	/**
	 * @param columnIndex
	 *            the columnIndex to set
	 */
	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof JXSTableSorterObject)) {
			return false;
		}
		JXSTableSorterObject _obj = (JXSTableSorterObject) obj;
		return _obj.getColumn().equals(getColumn());// && _obj.getOrder().equals(getOrder())
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 16 * getColumn().length() + getColumn().hashCode();
	}

	@Override
	public int compareTo(JXSTableSorterObject arg0) {
		if (arg0 != null && arg0.equals(this) && arg0.hashCode() == hashCode()) {
			return 0;
		}
		return -1;
	}

}

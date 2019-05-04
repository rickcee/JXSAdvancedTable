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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.model.filter.JXSRowFilter;
import net.rickcee.swingxs.model.filter.JXSRowFilterConstants;

public class JXSTableFilterObject implements Comparable<JXSTableFilterObject> {
	private static final Logger logger = LoggerFactory.getLogger(JXSTableFilterObject.class);

	protected Integer listIndex = 0;
	protected String column;
	protected String attName;
	protected Integer columnIndex;

	protected Class<?> attType;
	protected Class<?> clazz;

	protected Integer filterType;
	protected Boolean matchCase = Boolean.FALSE;
	protected Boolean matchWholeWord = Boolean.FALSE;
	protected String filterValue1;
	protected String filterValue2;

	protected BeanAttributeContainer container;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 * @param colName
	 */
	public JXSTableFilterObject(Class<?> clazz, String colName) {
		this.container = BeanManager.get(clazz);
		this.clazz = clazz;
		this.column = colName;
		this.attName = container.getFieldReverseLabel().get(colName);
		this.attType = container.getFieldTypes().get(attName);
	}

	public RowFilter<TableModel, Integer> generateRowFilter() {
		RowFilter<TableModel, Integer> output = null;
		if (Number.class.isAssignableFrom(attType)) {
			// JXSRowFilterNumber rfn;
			if (!"".equals(filterValue2)) {
				output = JXSRowFilter.generateNumberFilter(clazz, attName, filterType,
						Double.parseDouble(filterValue1), Double.parseDouble(filterValue2));
			} else {
				output = JXSRowFilter
						.generateNumberFilter(clazz, attName, filterType, Double.parseDouble(filterValue1));
			}
			return output;
		}
		if (Date.class.isAssignableFrom(attType)) {
			Date d1 = null, d2 = null;
			try {
				d1 = DateFormat.getInstance().parse(filterValue1);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			try {
				d2 = DateFormat.getInstance().parse(filterValue2);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			output = JXSRowFilter.generateDateFilter(clazz, attName, filterType, d1, d2);
			return output;
		}
		if (String.class.isAssignableFrom(attType)) {
			output = JXSRowFilter.generateStringFilter(clazz, attName, filterType, matchCase, matchWholeWord,
					filterValue1, filterValue1);
			return output;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append((getListIndex() + 1) + ")   [" + getColumn());
		sb.append("] " + JXSRowFilterConstants.getFilterTypeLabels().get(filterType));
		sb.append(" [" + getFilterValue1() + "]");
		if (getFilterValue2() != null && !"".equals(getFilterValue2().trim())) {
			sb.append(" and [" + getFilterValue2() + "]");
		}

		// Display these only if I'm filtering strings...
		if (attType.equals(String.class)) {
			if (matchCase) {
				sb.append(" - (Match Case)");
			}
			if (matchWholeWord) {
				sb.append(" - (Match Whole Word)");
			}
		}
		return sb.toString();
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

	/**
	 * @return the matchCase
	 */
	public Boolean getMatchCase() {
		return matchCase;
	}

	/**
	 * @param matchCase
	 *            the matchCase to set
	 */
	public void setMatchCase(Boolean matchCase) {
		this.matchCase = matchCase;
	}

	/**
	 * @return the matchWholeWord
	 */
	public Boolean getMatchWholeWord() {
		return matchWholeWord;
	}

	/**
	 * @param matchWholeWord
	 *            the matchWholeWord to set
	 */
	public void setMatchWholeWord(Boolean matchWholeWord) {
		this.matchWholeWord = matchWholeWord;
	}

	/**
	 * @return the filterValue1
	 */
	public String getFilterValue1() {
		return filterValue1;
	}

	/**
	 * @param filterValue1
	 *            the filterValue1 to set
	 */
	public void setFilterValue1(String filterValue1) {
		this.filterValue1 = filterValue1;
	}

	/**
	 * @return the filterValue2
	 */
	public String getFilterValue2() {
		return filterValue2;
	}

	/**
	 * @param filterValue2
	 *            the filterValue2 to set
	 */
	public void setFilterValue2(String filterValue2) {
		this.filterValue2 = filterValue2;
	}

	/**
	 * @return the filterType
	 */
	public Integer getFilterType() {
		return filterType;
	}

	/**
	 * @param filterType
	 *            the filterType to set
	 */
	public void setFilterType(Integer filterType) {
		this.filterType = filterType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof JXSTableFilterObject)) {
			return false;
		}
		JXSTableFilterObject _obj = (JXSTableFilterObject) obj;
		return _obj.getColumn().equals(getColumn()) && _obj.getFilterValue1().equals(getFilterValue1())
				&& _obj.getFilterValue2().equals(getFilterValue2()) && _obj.getMatchCase().equals(getMatchCase())
				&& _obj.getMatchWholeWord().equals(getMatchWholeWord()) && _obj.getFilterType().equals(getFilterType());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 32 * getColumn().length() + getColumn().hashCode() + getFilterValue1().hashCode()
				+ getFilterValue2().hashCode() + getMatchCase().hashCode() + getMatchCase().hashCode()
				+ getFilterType().hashCode();
	}

	@Override
	public int compareTo(JXSTableFilterObject arg0) {
		if (arg0 != null && arg0.equals(this) && arg0.hashCode() == hashCode()) {
			return 0;
		}
		return -1;
	}

}

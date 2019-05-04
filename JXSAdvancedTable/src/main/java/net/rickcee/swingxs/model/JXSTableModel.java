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
package net.rickcee.swingxs.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.ui.JXSTable;
import net.rickcee.swingxs.ui.JXSTableCellRenderer;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 4392974322455323824L;
	private static final Logger logger = LoggerFactory.getLogger(JXSTableModel.class);

	private List<T> result = new ArrayList<T>();
	private Class<?> clazz;
	private BeanAttributeContainer container;
	private JXSTable<T> table;	
	
	/**
	 * Default Constructor
	 */
	public JXSTableModel(Class<?> clazz, JXSTable<T> table) throws Exception {
		super();
		this.clazz = clazz;
		this.container = BeanManager.get(clazz);
		this.table = table;
	}

	public void refreshContent() {
		fireTableDataChanged();
	}

	/**
	 * @param mapping
	 */
	public void removeObject(Object mapping) {
		result.remove(mapping);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	public int getColumnCount() {
		// If no labels defined, use default column names
		if (container.getFieldLabel().keySet().isEmpty()) {
			return container.getFieldName().keySet().size();
		}
		return container.getFieldLabel().keySet().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		String attName = container.getFieldPosition().get(column);
		String colName = container.getFieldLabel().get(attName);
		// If no label defined, then use default column name
		if (colName == null) {
			colName = container.getFieldName().get(attName);
		}
		return colName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		String attName = container.getFieldPosition().get(column);
		Class<?> colclass = container.getFieldTypes().get(attName);
		return colclass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	public int getRowCount() {
		return result.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object mapping = result.get(rowIndex);
		String attName = container.getFieldPosition().get(columnIndex);
		Method getter = container.getFieldGetters().get(attName);
		
		Object _do = null;
		try {
			_do = getter.invoke(mapping);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (_do == null) {
			return "";
		}
		return _do;
	}

	/**
	 * @param row
	 * @return
	 */
	public Object getModelObjectAt(int row) {
		if (row >= result.size() || row < 0) {
			return null;
		} else {
			return result.get(row);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#fireTableRowsInserted(int, int)
	 */
	@Override
	public void fireTableRowsInserted(int firstRow, int lastRow) {
		super.fireTableRowsInserted(firstRow, lastRow);
		// Blink the newly inserted row
		JXSTableCellRenderer ca = ((JXSTableModelListener) getListeners(TableModelListener.class)[0]).getRenderer();
		flashRow(firstRow, ca);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#fireTableRowsInserted(int, int)
	 */
	@Override
	public void fireTableRowsUpdated(int firstRow, int lastRow) {
		super.fireTableRowsInserted(firstRow, lastRow);
		// Blink the newly inserted row
		JXSTableCellRenderer ca = ((JXSTableModelListener) getListeners(TableModelListener.class)[0]).getRenderer();
		flashRow(firstRow, ca);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#fireTableRowsInserted(int, int)
	 */
	@Override
	public void fireTableRowsDeleted(int firstRow, int lastRow) {
		super.fireTableRowsInserted(firstRow, lastRow);
		// Blink the newly inserted row
		JXSTableCellRenderer ca = ((JXSTableModelListener) getListeners(TableModelListener.class)[0]).getRenderer();
		flashRow(firstRow, ca);
	}

	/**
	 * @param firstRow
	 * @param ca
	 */
	private void flashRow(int firstRow, JXSTableCellRenderer ca) {
		if (table != null) {
			// TODO: Review this public property
			ca.flashRowSelection = table.convertRowIndexToView(firstRow);
			ca.flashRow();
		}
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @return the result
	 */
	public List<T> getResult() {
		return result;
	}

	/**
	 * @return the table
	 */
	public JXSTable<T> getTable() {
		return table;
	}

}

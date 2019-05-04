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
package net.rickcee.swingxs.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.lang.model.type.NullType;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.TableColumnExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.model.JXSTableModel;
import net.rickcee.swingxs.model.JXSTableModelListener;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTable<T> extends JXTable {

	private static final long serialVersionUID = -4601911673703353538L;
	private static final Logger logger = LoggerFactory.getLogger(JXSTable.class);

	protected Boolean columnControlEnabled = Boolean.TRUE;
	protected JXSColumHeaderPopup popup;
	protected Class<?> clazz;
	protected BeanAttributeContainer container;
	protected JXSTableModelListener modelListener;
	protected JXSTableCellRenderer renderer;
	protected JXSTable<T> myself;
	protected T clazzObj;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 * @throws Exception
	 */
	public JXSTable(Class<?> clazz) throws Exception {
		super();
		this.clazz = clazz;
		this.container = BeanManager.get(clazz);
		this.myself = this;
		initializeColumnHandler();
	}

	/**
	 * Constructor
	 * 
	 * @param model
	 * @param clazz
	 * @throws Exception
	 */
	public JXSTable(JXSTableModel<T> model, Class<?> clazz) throws Exception {
		super(model);
		this.clazz = clazz;
		this.container = BeanManager.get(clazz);
		initializeColumnHandler();
		adjustColumnsVisibilityAndWidth();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jdesktop.swingx.JXTable#setModel(javax.swing.table.TableModel)
	 */
	@Override
	public void setModel(TableModel dataModel) {
		if (dataModel instanceof JXSTableModel) {
			super.setModel(dataModel);
			// JTable calls this on startup so the container is NULL
			if (this.container != null) {
				adjustColumnsVisibilityAndWidth();
			}
		} else {
			logger.warn("Table model is NOT an instance of "
					+ JXSTableModel.class.getSimpleName() + " !!!");
			//super.setModel(dataModel);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JTable#getModel()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JXSTableModel<T> getModel() {
		return (JXSTableModel<T>) super.getModel();
	}

	/**
	 * 
	 */
	protected void adjustColumnsVisibilityAndWidth() {
		logger.debug("adjustColumnsVisibilityAndWidth");
		// setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		if (columnControlEnabled) {
			setColumnControl(new JXSColumnControl(this));
		}
		setHorizontalScrollEnabled(true);
		setFillsViewportHeight(true);
		setColumnControlVisible(true);
		setSortable(false);

		getModel().addTableModelListener(modelListener);

		// Adjust Column visibility and width
		int colCount = getColumnCount();
		for (int i = 0; i < colCount ; i++) {
			String attName = container.getFieldPosition().get(i);
			// String colName = container.getFieldLabel().get(attName);
			String colName = getModel().getColumnName(i);
			Boolean visible = container.getFieldVisibility().get(attName);
			Boolean frozen = container.getFieldFixed().get(attName);
			Integer width = container.getFieldWidth().get(attName);
			if (logger.isDebugEnabled()) {
				logger.debug("Table Column [" + i + "] [" + attName + "] [" + colName + "] : [Visible: " + visible
						+ "] - [Width: " + width + "] - [Frozen: " + frozen + "]");
			}
			TableColumnExt col = (TableColumnExt) getColumn(colName);
			col.setVisible(visible);
			col.setPreferredWidth(width);

			col.setHeaderRenderer(new JXSTableHeaderRenderer());
		}

	}

	/**
	 * @throws Exception
	 */
	protected void initializeColumnHandler() throws Exception {
		getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				final JTable table = ((JTableHeader) e.getSource()).getTable();
				TableColumnModel colModel = table.getColumnModel();

				// The index of the column whose header was clicked
				int vColIndex = colModel.getColumnIndexAtX(e.getX());
				int mColIndex = table.convertColumnIndexToModel(vColIndex);

				/* Handle RIGHT CLICK */
				if (SwingUtilities.isRightMouseButton(e)) {
					final String colName = table.getModel().getColumnName(mColIndex);
					// logger.info(table.getModel().getColumnName(mColIndex));
					popup = new JXSColumHeaderPopup(clazz, colName);
					popup.show(e.getComponent(), e.getX(), e.getY());
				} else if (SwingUtilities.isLeftMouseButton(e)) {
					/* Handle LEFT CLICK */
					processMouseSorting2(mColIndex, e.isControlDown());
					//super.mouseClicked(e);
				}
			}

			
		});

		this.renderer = new JXSTableCellRenderer(getDefaultRenderer(Object.class), clazz, this);
		this.setDefaultRenderer(Object.class, renderer);
		this.setDefaultRenderer(Number.class, renderer);
		this.setDefaultRenderer(Date.class, renderer);
		this.setDefaultRenderer(String.class, renderer);
		this.setDefaultRenderer(NullType.class, renderer);

		// TODO: Review, this is needed for row blink!
		this.modelListener = new JXSTableModelListener(this, renderer);

	}

	public void processMouseSorting2(final int mColIndex, boolean controlPressed) {
		//JXSTable table = BeanManager.getTables().get(clazz);
		JXSTable<T> table = this;
		JXSTable<?> tableFrozen = BeanManager.getFrozenTables().get(clazz);
		
		// Original SortKeys
		List<? extends SortKey> rs = getRowSorter().getSortKeys();
		// New SortKeys
		List<SortKey> sortKeys = new ArrayList<SortKey>();

		Boolean rowSortAdded = Boolean.FALSE;
		if (controlPressed) {
			// Add all original sort keys
			sortKeys.addAll(rs);
			for (int i = 0; i < rs.size(); i++) {
				SortKey sk = rs.get(i);
				if (mColIndex == sk.getColumn()) {
					// If we had ASCENDING order in the original
					if (sk.getSortOrder().equals(SortOrder.ASCENDING)) {
						// Set to DESCENDING
						sortKeys.add(new SortKey(mColIndex, SortOrder.DESCENDING));
					} else {
						// Otherwise set to ASCENDING
						sortKeys.add(new SortKey(mColIndex, SortOrder.ASCENDING));
					}
					rowSortAdded = Boolean.TRUE;
					// Remove old Sort Key element
					sortKeys.remove(sk);
				}
			}
			if (!rowSortAdded) {
				sortKeys.add(new SortKey(mColIndex, SortOrder.ASCENDING));
			}
		} else {
			// If we already have an item in the original sortkey
			if (rs.size() == 1) {
				// and the column index is the same we clicked
				if (rs.get(0).getColumn() == mColIndex) {
					// If we had ASCENDING order in the original
					if (rs.get(0).getSortOrder().equals(SortOrder.ASCENDING)) {
						// Set to DESCENDING
						sortKeys.add(new SortKey(mColIndex, SortOrder.DESCENDING));
					} else {
						// Otherwise set to ASCENDING
						sortKeys.add(new SortKey(mColIndex, SortOrder.ASCENDING));
					}
					//table.getRowSorter().setSortKeys(sortKeys);
					//if (tableFrozen != null) {
					//	tableFrozen.getRowSorter().setSortKeys(sortKeys);
					//}
					//return;
				}
			} else {
				sortKeys.add(new SortKey(mColIndex, SortOrder.ASCENDING));
			}
		}
		
		table.getRowSorter().setSortKeys(sortKeys);
		if (tableFrozen != null) {
			tableFrozen.getRowSorter().setSortKeys(sortKeys);
		}
	}
	
	/**
	 * Process sorting triggered by a mouse click in the table header.
	 * 
	 * @param table
	 *            The JTable object.
	 * @param mColIndex
	 *            The index of the clicked column
	 */
	public void processMouseSorting(final int mColIndex) {
		//JXSTable table = BeanManager.getTables().get(clazz);
		JXSTable<T> table = this;
		JXSTable<?> tableFrozen = BeanManager.getFrozenTables().get(clazz);
		
		// Original SortKeys
		List<? extends SortKey> rs = getRowSorter().getSortKeys();
		// New SortKeys
		List<SortKey> sortKeys = new ArrayList<SortKey>();
		// logger.info("SortKeys: " + sortKeys);

		// If we already have an item in the original sortkey
		if (rs.size() == 1) {
			// and the column index is the same we clicked
			if (rs.get(0).getColumn() == mColIndex) {
				// If we had ASCENDING order in the original
				if (rs.get(0).getSortOrder().equals(SortOrder.ASCENDING)) {
					// Set to DESCENDING
					sortKeys.add(new SortKey(mColIndex, SortOrder.DESCENDING));
				} else {
					// Otherwise set to ASCENDING
					sortKeys.add(new SortKey(mColIndex, SortOrder.ASCENDING));
				}
				table.getRowSorter().setSortKeys(sortKeys);
				if (tableFrozen != null) {
					tableFrozen.getRowSorter().setSortKeys(sortKeys);
				}
			} else {
				// If the column index is not the same, then unset
				// the sortkeys
				table.getRowSorter().setSortKeys(null);
				if (tableFrozen != null) {
					tableFrozen.getRowSorter().setSortKeys(null);
				}
			}
		} else if (rs.size() > 1) {
			// If we currently have more than one, unset the
			// sortkeys
			table.getRowSorter().setSortKeys(null);
			if (tableFrozen != null) {
				tableFrozen.getRowSorter().setSortKeys(null);
			}
		} else {
			// If we don't have anything set, set it initially as
			// ASCENDING
			sortKeys.add(new SortKey(mColIndex, SortOrder.ASCENDING));
			table.getRowSorter().setSortKeys(sortKeys);
			if (tableFrozen != null) {
				tableFrozen.getRowSorter().setSortKeys(sortKeys);
			}
		}
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}
	
	public void setColumnControlEnabled(Boolean value) {
		columnControlEnabled = value;
	}
	
	public Boolean isColumnControlEnabled() {
		return columnControlEnabled;
	}
	
	public T getSelectedObject() {
		Integer selected = getSelectedRow();
		if(selected >= 0) {
			Integer selectedModel = convertRowIndexToModel(selected);
			JXSTableModel<T> model = (JXSTableModel<T>) getModel();
			T obj = model.getResult().get(selectedModel);
			return obj;
		}
		return null;
	}
	
	public List<T> getSelectedObjects() {
		List<T> result = new ArrayList<T>();
		int[] rowNumbers = getSelectedRows();
		for (int rowNumber : rowNumbers) {
			Integer selectdModel = convertRowIndexToModel(rowNumber);
			JXSTableModel<T> model = (JXSTableModel<T>) getModel();
			T obj = model.getResult().get(selectdModel);
			result.add(obj);
		}
		return result;
	}

//	public Boolean contentEquals(String[][] strings) {
//		// TODO Auto-generated method stub
//		return Boolean.TRUE;
//	}

}

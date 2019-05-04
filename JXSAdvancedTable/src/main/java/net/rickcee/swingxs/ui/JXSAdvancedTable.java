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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;
import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanConfigUtils;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.mail.IMailConfig;
import net.rickcee.swingxs.model.JXSTableModel;
import net.rickcee.swingxs.model.meta.IFilterable;
import net.rickcee.swingxs.utils.Utils;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.sort.RowFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author RickCeeNet
 * 
 */
public class JXSAdvancedTable<T> extends JXPanel {

	private static final long serialVersionUID = -7615801463029922788L;
	private static final Logger logger = LoggerFactory.getLogger(JXSAdvancedTable.class);

	public static final String ACTION_SEARCH_ICON = "images/16/mapping-search-16.png";

	protected JXSTable<T> table;
	protected JXSTable<T> tableFrozen;
	protected Class<?> clazz;

	protected final JXStatusBar statusBar = new JXStatusBar();
	protected final JLabel statusBarComponent = new JLabel();
	protected JXSColumnSelectionDialog columnSelector;

	protected JXPanel searchBarContainer;
	protected JTextField text;
	protected JXLabel label;
	protected JXButton searchButton;

	private JXSTableModel<T> model;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 *            The clazz of the objects to display
	 * @throws Exception
	 *             In case of any errors
	 */
	public JXSAdvancedTable(Class<?> clazz) throws Exception {
		this(clazz, new ArrayList<T>());
	}

	/**
	 * Constructor
	 * 
	 * @param clazz
	 *            The clazz of the objects to display
	 * @param tableObjects
	 *            The Initial population of objects
	 * @throws Exception
	 *             In case of any errors
	 */
	public JXSAdvancedTable(Class<?> clazz, List<T> tableObjects) throws Exception {
		this.clazz = clazz;
		BeanConfigUtils.loadUserConfiguration(clazz);
		this.table = new JXSTable<T>(clazz);
		this.model = new JXSTableModel<T>(clazz, table);
		this.model.setResult(tableObjects);
		this.table.setModel(model);
		this.statusBarComponent.setText("Total Elements: " + tableObjects.size());
		this.columnSelector = new JXSColumnSelectionDialog(clazz);
		this.tableFrozen = new JXSTable<T>(clazz);

		BeanManager.getTables().put(clazz, table);
		BeanManager.getFrozenTables().put(clazz, tableFrozen);
		BeanManager.getAdvancedTables().put(clazz, this);

		processFrozenTable();

		createComponents();
	}

	protected void processFrozenTable() {
		table.setFillsViewportHeight(true);

		tableFrozen.setColumnControlEnabled(false);
		tableFrozen.setAutoCreateColumnsFromModel(false);
		tableFrozen.setModel(model);
		tableFrozen.setSelectionModel(table.getSelectionModel());
		tableFrozen.setFillsViewportHeight(true);

		JTableHeader nonScrollingHeader = tableFrozen.getTableHeader();
		nonScrollingHeader.setResizingAllowed(false);
		nonScrollingHeader.setReorderingAllowed(false);

		BeanAttributeContainer bac = BeanManager.get(clazz);
		for (String fieldName : bac.getFieldName().keySet()) {
			Boolean fixed = bac.getFieldFixed().get(fieldName);
			if (fixed) {
				if (logger.isDebugEnabled()) {
					logger.debug("Processing Frozen Field [" + fieldName + "]");
				}
				String colName = bac.getFieldLabel().get(fieldName);
				TableColumn frozenColumn = table.getColumn(colName);
				table.removeColumn(frozenColumn);
				tableFrozen.addColumn(frozenColumn);
			}
		}

		tableFrozen.setPreferredScrollableViewportSize(tableFrozen.getPreferredSize());
	}

	/**
	 * 
	 */
	public void reProcessFrozenColumns() {
		BeanAttributeContainer bac = BeanManager.get(clazz);
		Integer viewPortWidth = 0;
		for (String fieldName : bac.getFieldName().keySet()) {
			Boolean fixed = bac.getFieldFixed().get(fieldName);
			String colName = bac.getFieldLabel().get(fieldName);

			TableColumn frozenColumn = table.getColumnExt(colName);
			// If it's null then the column is already frozen
			if (frozenColumn == null) {
				frozenColumn = tableFrozen.getColumnExt(colName);
				// If it's in the frozen table and is no longer marked as
				// frozen, then process
				if (!fixed) {
					tableFrozen.removeColumn(frozenColumn);
					table.addColumn(frozenColumn);
				} else {
					viewPortWidth = viewPortWidth + frozenColumn.getWidth();
				}
			} else {
				// If it's in the original table and it's marked as frozen, then
				// process
				if (fixed) {
					table.removeColumn(frozenColumn);
					tableFrozen.addColumn(frozenColumn);
					viewPortWidth = viewPortWidth + frozenColumn.getWidth();
				}
			}
		}
		Dimension dim = tableFrozen.getPreferredSize();
		dim.width = viewPortWidth;
		tableFrozen.setPreferredScrollableViewportSize(dim);
	}

	/**
	 * Creates the UI Components
	 */
	private void createComponents() throws Exception {
		// This component's layout
		setLayout(new MigLayout("insets 5 5 5 5", "[grow]","[][grow][]"));

		JTableHeader nonScrollingHeader = tableFrozen.getTableHeader();
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setRowHeaderView(tableFrozen);
		scrollPane.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, nonScrollingHeader);

		searchBarContainer = generateSearchContainer();
		statusBar.add(statusBarComponent);

		add(searchBarContainer, "growx, wrap");
		add(scrollPane, "grow, wrap");
		add(statusBar, "growx, wrap");
	}

	/**
	 * @return
	 */
	private JXPanel generateSearchContainer() {
		JXPanel searchBarContainer = new JXPanel();
		searchBarContainer.setLayout(new MigLayout("insets 5 5 5 5", "[][grow][][]"));

		label = new JXLabel("Quick Search: ");

		text = new JTextField(25);
		text.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				// Only if the user press enter in the text box
				if (key == KeyEvent.VK_ENTER) {
					applySearchFilter();
				}
			}

		});

		/* FIND BUTTON */
		searchButton = new JXButton("Find", Utils.getClassPathImage(ACTION_SEARCH_ICON));
		searchButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				applySearchFilter();
			}
		});

		JButton jb = new JButton("Select Columns...", Utils.getClassPathImage("images/columns-icon.jpg"));
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				columnSelector.setVisible(true);
			}
		});
		columnSelector.setLocationRelativeTo(jb);
		
		searchBarContainer.add(label);
		searchBarContainer.add(text, "grow");
		searchBarContainer.add(searchButton);
		searchBarContainer.add(jb);
		return searchBarContainer;
	}

	/**
	 * Sets the object that will create the MultiPartEmail object used as a
	 * template to enable the email functionality
	 * 
	 * @param config
	 */
	public void setEmailFactory(IMailConfig config) {
		JXSColumnControl ccontrol = (JXSColumnControl) getTable().getColumnControl();
		ccontrol.setEmailTemplate(config);
	}

	public void addMouseListener(MouseListener listener) {
		table.addMouseListener(listener);
		tableFrozen.addMouseListener(listener);
	}

	/**
	 * @return the table
	 */
	public JXSTable<T> getTable() {
		return table;
	}

	/**
	 * @return the tableFrozen
	 */
	public JXSTable<T> getTableFrozen() {
		return tableFrozen;
	}

	/**
	 * @return the statusBar
	 */
	public JXStatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * @return the statusBarComponent
	 */
	public JLabel getStatusBarComponent() {
		return statusBarComponent;
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	public void addColorFilter(IFilterable filter) {
		BeanManager.getFilters().put(clazz, filter);
	}

	public void removeColorFilter(Class<?> clazz) {
		BeanManager.getFilters().remove(clazz);
	}
	
	public void setRows(List<T> newRows) {
		getModel().setResult(newRows);
		refreshTableContent();
	}
	
	public void addRows(List<T> newRows) {
		getModel().getResult().addAll(newRows);
		refreshTableContent();
	}
	
	public void addRow(T newRow) {
		getModel().getResult().add(newRow);
		refreshTableContent();
	}
	
	public void removeRow(T newRow) {
		getModel().getResult().remove(newRow);
		refreshTableContent();
	}
	
	public void removeRow(Integer index) {
		getModel().getResult().remove(index);
		refreshTableContent();
	}
	
	public void removeAllRows() {
		getModel().getResult().clear();
		refreshTableContent();
	}
	
	public T getSelectedObject() {
		return table.getSelectedObject();
	}
	
	public List<T> getSelectedObjects() {
		return table.getSelectedObjects();
	}
	
	public int getSelectedRowCount() {
		return table.getSelectedRowCount();
	}
	
	/**
	 * 
	 */
	public void applySearchFilter(String searchPattern) {
		int[] cols = columnSelector.getSelected();
		// (?i) will make the search case insensitive
		RowFilter<? super TableModel, ? super Integer> filter = RowFilters.GeneralFilter.regexFilter("(?i)" + searchPattern,
				cols);
		table.setRowFilter(filter);
		tableFrozen.setRowFilter(filter);
		logger.info("Searching for [" + searchPattern + "], size: " + table.getRowCount());
		refreshRowCount();
	}

	/**
	 * Refresh the count of rows in the status bar
	 */
	public void refreshRowCount() {
		statusBarComponent.setText("Total Elements: " + table.getRowCount());
	}
	
	/**
	 * Refresh the content of the table + the status bar
	 */
	public void refreshTableContent() {
		getModel().fireTableDataChanged();
		refreshRowCount();
	}

	protected void applySearchFilter() {
		String searchPattern = text.getText();
		applySearchFilter(searchPattern);
	}

	/**
	 * @return the model
	 */
	public JXSTableModel<T> getModel() {
		return model;
	}

	/**
	 * @return the searchBarContainer
	 */
	public JXPanel getSearchBarContainer() {
		return searchBarContainer;
	}

}

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
package net.rickcee.swingxs.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;

import net.miginfocom.swing.MigLayout;
import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanConfigUtils;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.model.BeanConfig;
import net.rickcee.swingxs.model.SortConfig;
import net.rickcee.swingxs.ui.model.JXSTableSorterListModel;
import net.rickcee.swingxs.ui.model.JXSTableSorterModel;
import net.rickcee.swingxs.ui.model.JXSTableSorterObject;

import org.jdesktop.swingx.JXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableSorterDialog extends JDialog {

	private static final long serialVersionUID = 1460415261507293047L;
	private static final Logger logger = LoggerFactory.getLogger(JXSTableSorterDialog.class);

	protected Class<?> clazz;
	protected JXSTableSorterModel model;
	protected JXSTable<?> table;
	protected JComboBox columnCombo = new JComboBox();
	protected JLabel columnLabel = new JLabel("Select Column:");
	protected JComboBox orderCombo = new JComboBox();
	protected JLabel orderLabel = new JLabel("Select Sort Order:");
	protected JLabel currentCriteriaLabel = new JLabel("<html><font color=blue><b>Saved Criteria:</font></b></html>");

	protected JButton _btnAdd = new JButton("Add Criterion");
	protected JButton _btnRemove = new JButton("Remove Selected Criterion");
	protected JButton _btnApply = new JButton("Apply");
	protected JButton _btnSaveNew = new JButton("Save New");
	protected JButton _btnCancel = new JButton("Cancel");
	protected JButton _btnNew = new JButton("New");
	protected JButton _btnLoad = new JButton("Load");
	protected JButton _btnDelete = new JButton("Delete");
	
	protected JList selectionContainer = new JList();
	protected JList currentCriteria = new JList();
	
	// TODO: I think this Should NOT be here!
	protected List<JXSTableSorterObject> entries = new ArrayList<JXSTableSorterObject>();
	
	// Model for the list of saved Criteria Items
	protected JXSTableSorterListModel entryModel = new JXSTableSorterListModel();
	// Model for the list of saved Sort Criterias
	protected DefaultListModel criteriaModel = new DefaultListModel();

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JXSTableSorterDialog(JXSTableSorterModel model) {
		super();
		this.model = model;
		this.clazz = model.getClazz();
		initialize();
	}

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JXSTableSorterDialog(JXSTable<?> table) {
		super();
		this.table = table;
		this.model = new JXSTableSorterModel(table.getClazz());
		this.clazz = table.getClazz();
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		populateCombo(columnCombo, model.getColNames());
		populateCombo(orderCombo, JXSTableSorterModel.orderValues);

		createGUI();

		createActions();
		
		setTitle("Table Sorter");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * @param cb
	 * @param values
	 */
	protected void populateCombo(JComboBox cb, List<String> values) {
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		for (String value : new TreeSet<String>(values)) {
			model.addElement(value);
		}
		cb.setModel(model);
	}

	/**
	 * 
	 */
	protected void createActions() {
		_btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JXSTableSorterObject object = new JXSTableSorterObject();
				object.setColumn((String) columnCombo.getSelectedItem());
				object.setColumnIndex(model.getColumnIndex(object.getColumn()));
				String order = (String) orderCombo.getSelectedItem();
				if (SortOrder.ASCENDING.name().equals(order)) {
					object.setOrder(SortOrder.ASCENDING);
				} else {
					object.setOrder(SortOrder.DESCENDING);
				}

				if (!entryModel.contains(object)) {
					entries.add(object);
					entryModel.addElement(object);
				} else {
					JOptionPane.showMessageDialog(null, "The specified combination already exists in the list.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				logger.info("Adding SorterObject: " + object);
				//selectionContainer.revalidate();
			}
		});

		_btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		_btnApply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<SortKey> sortKeys = new ArrayList<SortKey>();
				for (int i = 0; i < entryModel.getSize(); i++) {
					JXSTableSorterObject obj = (JXSTableSorterObject) entryModel.get(i);
					if(logger.isDebugEnabled()) {
						logger.debug("Adding Sort Criteria Item: " + obj);
					}
					sortKeys.add(new SortKey(obj.getColumnIndex(), obj.getOrder()));
				}
				// System.out.println("SortKeys: " + sortKeys);
				if (table != null) {
					table.getRowSorter().setSortKeys(sortKeys);
					
					JXSTable<?> tableFrozen = BeanManager.getFrozenTables().get(clazz);
					if (tableFrozen != null) {
						tableFrozen.getRowSorter().setSortKeys(sortKeys);
					}

				}
				setVisible(false);
				
				try {
					//BeanConfig bc = BeanConfigUtils.getConfigurationFromClass(table.getClazz());
					//BeanAttributeContainer bac = BeanManager.get(table.getClazz());
					JXSAdvancedTable<?> advTable = BeanManager.getAdvancedTables().get(clazz);
					BeanConfig config = BeanConfigUtils.getCurrentConfig(advTable);

					logger.info(BeanConfigUtils.getXs().toXML(config));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		_btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selectionContainer.getSelectedValue() != null) {
					criteriaModel.removeElement(selectionContainer.getModel().getElementAt(
							selectionContainer.getSelectedIndex()));
				} else {
					JOptionPane.showMessageDialog(null, "You must select an item from the list.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		_btnSaveNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String value = JOptionPane.showInputDialog("Enter Name of the Sort Criteria");
				SortConfig cfg = new SortConfig();
				cfg.setSortName(value);
				cfg.setEntries(entries);
				//XStream xs = new XStream();
				//System.out.println(xs.toXML(cfg));
				
				BeanAttributeContainer bac = BeanManager.get(clazz);
				bac.getSortCriteria().put(value, cfg);
				
				// TODO: Make this more optimal - Quick workaround
				Boolean flag = Boolean.FALSE;
				for (Object obj : criteriaModel.toArray()) {
					SortConfig sc = (SortConfig) obj;
					if (sc.equals(cfg)) {
						flag = Boolean.TRUE;
					}
				}
				if (!flag) {
					criteriaModel.addElement(cfg);
				}
				
			}
		});
		
		_btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object obj = currentCriteria.getSelectedValue();
				String selected = null;
				if (obj != null) {
					selected = obj.toString();
				}
				
				BeanAttributeContainer bac = BeanManager.get(clazz);
				bac.getSortCriteria().remove(selected);
				criteriaModel.removeElement(obj);
				
			}
		});
		
		_btnLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				processSelectedCriteria();
			}
		});
	}

	/**
	 * 
	 */
	public void createGUI() {
		setSize(700, 400);

		//selectionContainer.setSize(new Dimension(270, 250));
		selectionContainer.setPreferredSize(new Dimension(200, 250));
		selectionContainer.setBackground(Color.GRAY);
		selectionContainer.setModel(entryModel);
		
		// Add Existing Sort Criterias
		for (SortConfig sc : BeanManager.get(clazz).getSortCriteria().values()) {
			criteriaModel.addElement(sc);
		}

		//currentCriteria.setSize(new Dimension(250, 200));
		currentCriteria.setPreferredSize(new Dimension(200, 200));
		currentCriteria.setBackground(Color.GRAY);
		currentCriteria.setModel(criteriaModel);

		JXPanel left = new JXPanel();
		JXPanel right = new JXPanel();
		
		left.setLayout(new MigLayout("wrap 15", "[grow][grow]"));
		left.add(columnLabel, "grow");
		left.add(columnCombo, "wrap, grow");
		left.add(orderLabel, "grow");
		left.add(orderCombo, "wrap, grow");
		left.add(_btnAdd, "span, grow, wrap");
		left.add(_btnRemove, "span, grow");
		left.add(selectionContainer, "span, grow");
		left.add(_btnSaveNew, "align left, grow");
		left.add(_btnApply, "align left, grow");
		left.add(_btnCancel, "align right, grow");
		
		right.setLayout(new MigLayout("wrap 15", "[grow]","[][grow][]"));
		right.add(currentCriteriaLabel, "wrap");
		right.add(currentCriteria, "grow, wrap");
		right.add(_btnNew, "split 3");
		right.add(_btnLoad);
		right.add(_btnDelete, "wrap");
		
		setLayout(new MigLayout("wrap 15", "[grow][grow]", "[grow][grow]"));
		add(right, "grow");
		add(left, "grow");
	}
	
	public void processSelectedCriteria() {
		SortConfig sc = (SortConfig) currentCriteria.getSelectedValue();
		if (sc != null) {
			// Clear the current values
			entryModel.clear();
			// Load the selected criteria
			for (JXSTableSorterObject so : sc.getEntries()) {
				entryModel.addElement(so);
			}
		} else {
			JOptionPane.showMessageDialog(null, "You must select an item from the criteria list.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * @return the entries
	 */
	public List<JXSTableSorterObject> getEntries() {
		return entries;
	}

	/**
	 * @return the table
	 */
	public JXSTable<?> getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(JXSTable<?> table) {
		this.table = table;
	}

}

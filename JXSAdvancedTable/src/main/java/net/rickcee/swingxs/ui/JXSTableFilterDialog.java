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
 * Created on Feb 19, 2012
 * 
 */
package net.rickcee.swingxs.ui;

import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.getFilterTypes;
import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.getFilterTypesForStrings;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;
import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.model.filter.JXSRowFilterConstants;
import net.rickcee.swingxs.ui.model.JXSTableFilterListModel;
import net.rickcee.swingxs.ui.model.JXSTableFilterModel;
import net.rickcee.swingxs.ui.model.JXSTableFilterObject;

import org.jdesktop.swingx.JXLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableFilterDialog extends JDialog {

	private static final long serialVersionUID = 1460415261507293047L;
	private static final Logger logger = LoggerFactory.getLogger(JXSTableFilterDialog.class);

	protected JXSTableFilterModel model;
	protected JXSTable<?> table;
	//protected JXSTable tableFrozen;

	protected JXLabel filterTypeLabel = new JXLabel("Select Filter Type:");
	protected JXLabel columnLabel = new JXLabel("Select Column:");
	protected JXLabel filterLabel = new JXLabel("Search for:");
	protected JXLabel filtersRelationLabel = new JXLabel("Filters Relation:");

	protected JComboBox filterTypeCombo = new JComboBox();
	protected JComboBox columnCombo = new JComboBox();

	// protected JTextField filterText = new JTextField(15);
	protected JTextField filterValueOneText = new JTextField(10);
	protected JTextField filterValueTwoText = new JTextField(10);

	protected JButton _btnAdd = new JButton("Add Filter");
	protected JButton _btnRemove = new JButton("Remove Selected");
	protected JButton _btnOk = new JButton("OK");
	protected JButton _btnCancel = new JButton("Cancel");

	protected JCheckBox _chkMatchCase = new JCheckBox("Match Case");
	protected JCheckBox _chkMatchWholeWord = new JCheckBox("Match Whole Word");

	protected JList selectionContainer = new JList();

	protected JRadioButton radioAnd = new JRadioButton("AND");
	protected JRadioButton radioOr = new JRadioButton("OR");
	protected ButtonGroup bg = new ButtonGroup();

	// Should NOT be here!
	protected List<JXSTableFilterObject> entries = new ArrayList<JXSTableFilterObject>();

	protected JXSTableFilterListModel entryModel = new JXSTableFilterListModel();

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JXSTableFilterDialog(JXSTableFilterModel model) {
		super();
		this.model = model;
		initialize();
	}

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JXSTableFilterDialog(JXSTable<?> table) {
		super();
		this.table = table;
		//this.tableFrozen = frozenTable;
		this.model = new JXSTableFilterModel(table.getClazz());
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		populateCombo(columnCombo, model.getColNames());

		updateFilterTypeCombo();

		createGUI();

		createActions();

		setTitle("Table Filter");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * @param cb
	 * @param values
	 */
	protected void populateCombo(JComboBox cb, Collection<String> values) {
		DefaultComboBoxModel model = (DefaultComboBoxModel) cb.getModel();
		model.removeAllElements();
		for (String value : values) {
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
				Class<?> clazz = table.getClazz();
				String colName = (String) columnCombo.getSelectedItem();
				JXSTableFilterObject object = new JXSTableFilterObject(clazz, colName);

				object.setColumnIndex(model.getColumnIndex(object.getColumn()));

				object.setFilterType(JXSRowFilterConstants.getFilterTypeReverse().get(
						filterTypeCombo.getSelectedItem().toString()));

				object.setFilterValue1(filterValueOneText.getText());
				object.setFilterValue2(filterValueTwoText.getText());
				object.setMatchCase(_chkMatchCase.isSelected());
				object.setMatchWholeWord(_chkMatchWholeWord.isSelected());

				entries.add(object);
				if (!entryModel.contains(object)) {
					entryModel.addElement(object);
				} else {
					JOptionPane.showMessageDialog(null, "The element already exists", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
				logger.info("Adding FilterObject: " + object);
			}
		});

		_btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		_btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<RowFilter<? super TableModel, ? super Integer>> filters = new ArrayList<RowFilter<? super TableModel, ? super Integer>>();

				for (int i = 0; i < entryModel.getSize(); i++) {
					JXSTableFilterObject obj = (JXSTableFilterObject) entryModel.get(i);
					filters.add(obj.generateRowFilter());
				}

				// System.out.println("Filters: " + filters);
				if (table != null) {
					RowFilter<TableModel, Integer> compoundRowFilter;
					if (radioAnd.isSelected()) {
						compoundRowFilter = RowFilter.andFilter(filters);
					} else {
						compoundRowFilter = RowFilter.orFilter(filters);
					}
					table.setRowFilter(compoundRowFilter);
					
					JXSTable<?> tableFrozen = BeanManager.getFrozenTables().get(table.getClazz());
					if (tableFrozen != null) {
						tableFrozen.setRowFilter(compoundRowFilter);
					}
				}

				setVisible(false);
			}
		});

		_btnRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (selectionContainer.getSelectedValue() != null) {
					((DefaultListModel) selectionContainer.getModel()).removeElement(selectionContainer.getModel()
							.getElementAt(selectionContainer.getSelectedIndex()));
				} else {
					JOptionPane.showMessageDialog(null, "You must select an item on the list", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		columnCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateFilterTypeCombo();
			}
		});

		filterTypeCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
	}

	protected void updateFilterTypeCombo() {
		String colName = (String) columnCombo.getSelectedItem();
		BeanAttributeContainer container = BeanManager.get(table.getClazz());
		String attName = container.getFieldReverseLabel().get(colName);
		Class<?> attType = container.getFieldTypes().get(attName);

		// TODO: Implement CustomerListCellRenderer to disable items
		if (attType.equals(String.class)) {
			populateCombo(filterTypeCombo, getFilterTypesForStrings());
		} else {
			populateCombo(filterTypeCombo, getFilterTypes());
		}
		
		String selected = (String) filterTypeCombo.getSelectedItem();
		if (JXSRowFilterConstants.LBL_EQUALS.equals(selected)
				|| JXSRowFilterConstants.LBL_NOT_EQUALS.equals(selected)) {
			filterValueTwoText.setEditable(false);
		} else {
			filterValueTwoText.setEditable(true);
		}

		this.pack();
	}

	/**
	 * 
	 */
	public void createGUI() {
		setSize(575, 300);

		// filterText.setSize(columnCombo.getSize());
		filterTypeCombo.setMinimumSize(new Dimension(125,25));
		filterValueOneText.setMinimumSize(new Dimension(150,25));
		filterValueOneText.setMinimumSize(new Dimension(150,25));

		bg.add(radioAnd);
		bg.add(radioOr);

		radioAnd.setSelected(true);

		selectionContainer.setSize(new Dimension(270, 200));
		selectionContainer.setPreferredSize(new Dimension(270, 200));
		selectionContainer.setBackground(Color.GRAY);
		selectionContainer.setModel(entryModel);

		setLayout(new MigLayout("wrap 15", "[][][][][grow]"));

		add(columnLabel, "grow");
		add(columnCombo, "span, grow");

		// add(filterLabel, "grow");
		// add(filterText, "span, grow");

		add(filterTypeLabel, "grow");
		add(filterTypeCombo, "grow");

		add(filterValueOneText, "grow");
		add(filterValueTwoText, "grow, wrap, span");

		add(filtersRelationLabel);
		add(radioAnd, "split 2");
		add(radioOr, "grow");

		add(_btnAdd, "grow");
		add(_btnRemove, "grow, wrap, span");

		add(_chkMatchCase, "");
		add(_chkMatchWholeWord, "span");

		add(selectionContainer, "span, grow");

		add(_btnOk, "cell 1 5, align right");
		add(_btnCancel, "cell 2 5, align left");
	}

}

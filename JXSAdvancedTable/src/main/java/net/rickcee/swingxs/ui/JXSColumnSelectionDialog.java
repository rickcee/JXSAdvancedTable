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
 * Created on Feb 23, 2012
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.miginfocom.swing.MigLayout;
import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;

/**
 * @author RickCeeNet
 * 
 */
public class JXSColumnSelectionDialog extends JDialog {

	private static final long serialVersionUID = -667873172256494344L;
	private static final Logger logger = LoggerFactory.getLogger(JXSColumnSelectionDialog.class);

	protected JXSCheckList checkList = new JXSCheckList();
	protected Class<?> clazz;
	protected JButton _btnSelect = new JButton("Select All");
	protected JButton _btnDeselect = new JButton("Deselect All");
	protected JButton _btnOk = new JButton("OK");
	protected JButton _btnCancel = new JButton("Cancel");

	protected BeanAttributeContainer container;

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JXSColumnSelectionDialog(Class<?> clazz) {
		super();
		this.clazz = clazz;
		initialize();
	}

	/**
	 * Initialize
	 */
	private void initialize() {
		container = BeanManager.get(clazz);

		DefaultListModel dfl = new DefaultListModel();
		TreeSet<String> keys = new TreeSet<String>(container.getFieldLabel().keySet());
		for (String key : keys) {
			dfl.addElement(new JXSCheckListItem(container.getFieldLabel().get(key)));
		}
		checkList.setModel(dfl);

		createGUI();

		createActions();

		setTitle("Search on Column(s)");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// By default, all columns selected
		applySelection(true);
	}

	/**
	 * 
	 */
	protected void createActions() {
		_btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				applySelection(true);
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
				setVisible(false);
			}
		});

		_btnDeselect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				applySelection(false);
			}
		});
	}

	/**
	 * 
	 */
	protected void applySelection(Boolean value) {
		for (int i = 0; i < checkList.getModel().getSize(); i++) {
			JXSCheckListItem item = (JXSCheckListItem) checkList.getModel().getElementAt(i);
			item.setSelected(value);
			checkList.repaint(checkList.getCellBounds(i, i));
		}
	}
	
	/**
	 * Creates the GUI
	 */
	public void createGUI() {
		setSize(250, 280);
		setUndecorated(true);

		checkList.setSize(new Dimension(190, checkList.getModel().getSize() * 23));
		checkList.setPreferredSize(checkList.getSize());
		checkList.setBackground(Color.LIGHT_GRAY);

		setLayout(new MigLayout("wrap 5"));
		add(_btnSelect, "grow");
		add(_btnDeselect, "wrap, grow");
		add(new JScrollPane(checkList), "span, grow");
		add(_btnOk, "align left, grow");
		add(_btnCancel, "align right, grow");

		setModal(true);
		pack();
	}
	
	public int[] getSelected() {
		List<Integer> selected = new ArrayList<Integer>();
		for (int i = 0; i < checkList.getModel().getSize(); i++) {
			JXSCheckListItem item = (JXSCheckListItem) checkList.getModel().getElementAt(i);
			if (item.isSelected()) {
				selected.add(container.getFieldReversePosition().get(
						container.getFieldReverseLabel().get(item.toString())));
			}
		}
		String _cols = "   ";
		int[] output = new int[selected.size()];
		for (int i = 0; i < selected.size(); i++) {
			output[i] = selected.get(i);
			_cols = _cols + output[i] + ", ";
		}
		logger.debug("Columns Selected: " + _cols.substring(0, _cols.length() - 2));
		return output;
	}

}

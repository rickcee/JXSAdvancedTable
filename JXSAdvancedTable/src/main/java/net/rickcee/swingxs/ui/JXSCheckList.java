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

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class JXSCheckList extends JList {

	private static final long serialVersionUID = -4632502444861562797L;

	/**
	 * Constructor
	 * 
	 * @param dataModel
	 */
	public JXSCheckList(ListModel dataModel) {
		this();
		super.setModel(dataModel);
	}

	/**
	 * Constructor
	 * 
	 * @param listData
	 */
	public JXSCheckList(Object[] listData) {
		this();
		super.setListData(listData);
	}

	/**
	 * Constructor
	 * 
	 */
	public JXSCheckList() {
		setCellRenderer(new CheckListRenderer());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Add a mouse listener to handle changing selection
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				JList list = (JList) event.getSource();

				// Get index of item clicked
				int index = list.locationToIndex(event.getPoint());
				JXSCheckListItem item = (JXSCheckListItem) list.getModel().getElementAt(index);

				// Toggle selected state
				item.setSelected(!item.isSelected());

				// Repaint cell
				list.repaint(list.getCellBounds(index, index));
			}
		});
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create a list containing CheckListItem's
		JXSCheckList list = new JXSCheckList(new JXSCheckListItem[] { new JXSCheckListItem("apple"), new JXSCheckListItem("orange"),
				new JXSCheckListItem("mango"), new JXSCheckListItem("paw paw"), new JXSCheckListItem("banana") });
		JScrollPane pane = new JScrollPane(list);
		pane.setBorder(BorderFactory.createTitledBorder("Select Columns:"));

		frame.getContentPane().add(pane);
		frame.pack();
		frame.setVisible(true);
	}
}

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
 * Created on Mar 17, 2012
 *
 */
package net.rickcee.swingxs.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import net.rickcee.swingxs.ui.meta.JXSComponent;

import org.jdesktop.swingx.JXButton;

/**
 * @author RickCeeNet
 * 
 */
public class JXSExportTypeSelectionView extends JDialog implements JXSComponent {

	private static final long serialVersionUID = 1692718898759526849L;

	protected JRadioButton exportAll = new JRadioButton("Export All");
	protected JRadioButton exportSelected = new JRadioButton("Export Selected");
	protected JRadioButton exportFiltered = new JRadioButton("Export Filtered");
	protected ButtonGroup bg = new ButtonGroup();

	protected JXButton btnOk = new JXButton("Ok");
	protected JXButton btnCancel = new JXButton("Cancel");

	/**
	 * Constructor
	 * 
	 */
	public JXSExportTypeSelectionView() {
		super();
		createActions();
		createGUI();
	}

	public void createActions() {
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exportAll.setSelected(true);
				setVisible(false);
			}
		});
	}

	public void createGUI() {
		setLayout(new MigLayout("wrap 15", "[grow]"));

		bg.add(exportAll);
		bg.add(exportFiltered);
		bg.add(exportSelected);

		exportAll.setSelected(true);

		add(exportAll, "wrap");
		add(exportFiltered, "wrap");
		add(exportSelected, "wrap");

		add(btnCancel, "split 2, align right");
		add(btnOk, "align left");

		pack();
	}

}

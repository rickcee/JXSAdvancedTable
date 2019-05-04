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
 * Created on Jan 25, 2012
 * 
 */
package net.rickcee.swingxs.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.table.TableColumnExt;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.model.DateFormatConfig;
import net.rickcee.swingxs.model.NumberFormatConfig;
import net.rickcee.swingxs.utils.Formatter;
import net.rickcee.swingxs.utils.Utils;

/**
 * @author RickCeeNet
 * 
 */
public class JXSColumHeaderPopup extends JPopupMenu {

	private static final long serialVersionUID = 6677115963497342539L;

	protected String colName;
	protected Class<?> clazz;
	protected BeanAttributeContainer container;
	protected JMenuItem item;
	protected JXSDateFormatDialog dateFormatDialog;
	protected JXSNumberFormatDialog numberFormatDialog;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 * @param colName
	 */
	public JXSColumHeaderPopup(Class<?> clazz, String colName) {
		super();
		this.colName = colName;
		this.clazz = clazz;
		this.container = BeanManager.getContainer().get(clazz);
		this.dateFormatDialog = new JXSDateFormatDialog();
		this.numberFormatDialog = new JXSNumberFormatDialog();
		
		this.numberFormatDialog.setLocationRelativeTo(null);
		this.dateFormatDialog.setLocationRelativeTo(null);
		this.numberFormatDialog.setModal(true);
		this.dateFormatDialog.setModal(true);

		this.initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		item = new JMenuItem("Column: " + colName);
		
		final JComponent myself = this;
		final String attName = container.getFieldReverseLabel().get(colName);
		final Class<?> attClass = container.getFieldTypes().get(attName);

		item.setEnabled(false);
		add(item);
		add(new JSeparator());

		JMenuItem back = new JMenuItem("Background Color");
		// back.setIcon(Utils.getClassPathImage("background-icon.jpg"));
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Color color = JColorChooser.showDialog(myself, "Choose Color", Color.WHITE);
				if (color != null) {
					container.getFieldBackgroundColor().put(attName, color);
				}
			}
		});

		JMenuItem fore = new JMenuItem("Foreground Color");
		// fore.setIcon(Utils.getClassPathImage("foreground-icon.jpg"));
		fore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Color color = JColorChooser.showDialog(myself, "Choose Color", Color.WHITE);
				if (color != null) {
					container.getFieldForegroundColor().put(attName, color);
				}
			}
		});

		add(back);
		add(fore);

		JMenu alignMenu = new JMenu("Alignment");
		JMenuItem alignLeft = new JMenuItem("Left");
		alignLeft.setIcon(Utils.getClassPathImage("images/align-left.jpg"));
		alignLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				container.getFieldAlignment().put(attName, SwingConstants.LEFT);
			}
		});

		JMenuItem alignCenter = new JMenuItem("Center");
		alignCenter.setIcon(Utils.getClassPathImage("images/align-center.jpg"));
		alignCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				container.getFieldAlignment().put(attName, SwingConstants.CENTER);
			}
		});

		JMenuItem alignRight = new JMenuItem("Right");
		alignRight.setIcon(Utils.getClassPathImage("images/align-right.jpg"));
		alignRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				container.getFieldAlignment().put(attName, SwingConstants.RIGHT);
			}
		});

		alignMenu.add(alignLeft);
		alignMenu.add(alignCenter);
		alignMenu.add(alignRight);
		add(alignMenu);

		if (Number.class.isAssignableFrom(attClass) || attClass.isAssignableFrom(Date.class)) {
			final JMenuItem format = new JMenuItem("Format Column");
			format.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					// NUMBERS
					if (Number.class.isAssignableFrom(attClass)) {
						// Set the old configuration
						numberFormatDialog.getColumnName().setText("<html><b>" + colName + "</b></html>");
						NumberFormatConfig cfgOld = container.getFieldNumberFormat().get(attName);
						numberFormatDialog.setConfig(cfgOld);
						// Display dialog and wait for the result
						numberFormatDialog.setVisible(true);
						// Set the new configuration
						NumberFormatConfig cfgNew = numberFormatDialog.getConfig();
						container.getFieldNumberFormat().put(attName, cfgNew);
						container.getFieldNumberFormatter().put(attName, Formatter.getFormatter(cfgNew));
					}
					// DATES
					if (attClass.isAssignableFrom(Date.class)) {
						// Set the old configuration
						dateFormatDialog.getColumnName().setText("<html><b>" + colName + "</b></html>");
						DateFormatConfig cfgOld = container.getFieldDateFormat().get(attName);
						dateFormatDialog.setConfig(cfgOld);
						// Display dialog and wait for the result
						dateFormatDialog.setVisible(true);
						// Set the new configuration
						DateFormatConfig cfgNew = dateFormatDialog.getConfig();
						container.getFieldDateFormat().put(attName, cfgNew);
						container.getFieldDateFormatter().put(attName, Formatter.getFormatter(cfgNew));
					}
				}
			});
			add(format);
		}
		
		final JMenuItem show = new JMenuItem("Hide Column");
		// show.setIcon(Utils.getClassPathImage("hide-icon.jpg"));
		show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JXSTable<?> table = BeanManager.getTables().get(clazz);
				TableColumnExt tce = (TableColumnExt) table.getColumn(colName);
				tce.setVisible(Boolean.FALSE);
			}
		});

		final JMenuItem freeze = new JMenuItem("Freeze / Unfreeze");
		// freeze.setIcon(Utils.getClassPathImage("freeze-icon.jpg"));
		freeze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BeanAttributeContainer bac = BeanManager.get(clazz);
				Boolean fixed = bac.getFieldFixed().get(attName);
				bac.getFieldFixed().put(attName, !fixed);
				
				BeanManager.getAdvancedTables().get(clazz).reProcessFrozenColumns();
			}
		});

		add(show);
		add(freeze);

	}

	/**
	 * @param colName
	 *            the colName to set
	 */
	public void setColName(String colName) {
		this.colName = colName;
		this.item.setText("Column: " + colName);
	}

}

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
 * Created on Mar 10, 2012
 *
 */
package net.rickcee.swingxs.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import net.rickcee.swingxs.model.DateFormatConfig;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author RickCeeNet
 * 
 */
public class JXSDateFormatDialog extends JDialog {

	private static final long serialVersionUID = 2248995103549766073L;
	private static final Logger logger = LoggerFactory.getLogger(JXSDateFormatDialog.class);

	protected JXLabel columnLabel = new JXLabel("Column Name:");
	protected JXLabel columnName = new JXLabel();
	protected JXLabel monthDaysLabel = new JXLabel("Month/Days:");
	protected JXLabel yearLabel = new JXLabel("Year:");
	
	protected JRadioButton mmddRadio = new JRadioButton("mm/dd");
	protected JRadioButton ddmmRadio = new JRadioButton("dd/mm");
	protected JRadioButton year2dRadio = new JRadioButton("2 Digit Year");
	protected JRadioButton year4dRadio = new JRadioButton("4 Digit Year");
	
	protected ButtonGroup dateGroup = new ButtonGroup();
	protected ButtonGroup yearGroup = new ButtonGroup();

	protected JCheckBox showDayCheck = new JCheckBox("Show Day of the Week");
	protected JCheckBox showYearCheck = new JCheckBox("Show Year");

	protected JXButton okButton = new JXButton("OK");
	protected JXButton cancelButton = new JXButton("Cancel");

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JXSDateFormatDialog() {
		super();
		initialize();
		if (logger.isDebugEnabled()) {
			logger.debug(getClass().getSimpleName() + " Constructor");
		}
	}

	private void initialize() {
		createGUI();
		createActions();
		setTitle("Format Date Column");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void createActions() {
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	private void createGUI() {
		setSize(400, 200);
		setMinimumSize(new Dimension(getSize()));
		setLayout(new MigLayout("wrap 15", "[][grow][grow]", "[][][][grow][grow][grow]"));

		dateGroup.add(mmddRadio);
		dateGroup.add(ddmmRadio);
		yearGroup.add(year2dRadio);
		yearGroup.add(year4dRadio);
		
		add(columnLabel);
		add(columnName, "span 2, grow, wrap");
		add(monthDaysLabel);
		add(mmddRadio);
		add(ddmmRadio, "wrap");
		add(yearLabel);
		add(year2dRadio);
		add(year4dRadio, "wrap");
		add(showDayCheck, "span 3, wrap");
		add(showYearCheck, "span 3, wrap");
		add(cancelButton, "split 2, align right");
		add(okButton, "align left");
	}

	/**
	 * @return the columnName
	 */
	public JXLabel getColumnName() {
		return columnName;
	}
	
	/**
	 * Set the screen attributes based on the values specified by the
	 * configuration object
	 * 
	 * @param config
	 */
	public void setConfig(DateFormatConfig config) {
		if (config.getMmdd()) {
			mmddRadio.setSelected(true);
		} else {
			ddmmRadio.setSelected(true);
		}
		if (config.getYear2d()) {
			year2dRadio.setSelected(true);
		} else {
			year4dRadio.setSelected(true);
		}
		if (config.getShowDay()) {
			showDayCheck.setSelected(true);
		}
		if (config.getShowYear()) {
			showYearCheck.setSelected(true);
		}
	}
	
	
	/**
	 * Returns an object representing the desired configuration
	 * 
	 * @return
	 */
	public DateFormatConfig getConfig() {
		DateFormatConfig config = new DateFormatConfig();
		config.setMmdd(mmddRadio.isSelected());
		config.setYear2d(year2dRadio.isSelected());
		config.setShowDay(showDayCheck.isSelected());
		config.setShowYear(showYearCheck.isSelected());
		return config;
	}
	
	public static void main(String args[]) throws Exception {
		JXSDateFormatDialog dialog = new JXSDateFormatDialog();
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.columnName.setText("Trade Date");
	}

}

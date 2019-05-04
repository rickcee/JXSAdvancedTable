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

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import net.miginfocom.swing.MigLayout;
import net.rickcee.swingxs.model.NumberFormatConfig;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author RickCeeNet
 * 
 */
public class JXSNumberFormatDialog extends JDialog {
	private static final long serialVersionUID = -6448845413694064804L;
	private static final Logger logger = LoggerFactory.getLogger(JXSNumberFormatDialog.class);

	protected JXLabel columnLabel = new JXLabel("Column Name:");
	protected JXLabel columnName = new JXLabel();
	protected JXLabel precisionLabel = new JXLabel("Precision:");
	protected JXLabel factorLabel = new JXLabel("Factor:");
	protected JXLabel commasLabel = new JXLabel("Commas:");
	protected JXLabel parensLabel = new JXLabel("Parens:");
	protected JXLabel leadingLabel = new JXLabel("Leading Text:");
	protected JXLabel trailingLabel = new JXLabel("Trailing Text:");

	protected JXTextField precisionTextField = new JXTextField();
	protected JXTextField factorTextField = new JXTextField();
	protected JXTextField leadingTextField = new JXTextField();
	protected JXTextField trailingTextField = new JXTextField();
	
	protected JCheckBox commasCheck = new JCheckBox();
	protected JCheckBox parensCheck = new JCheckBox();

	protected JXButton okButton = new JXButton("OK");
	protected JXButton cancelButton = new JXButton("Cancel");

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JXSNumberFormatDialog() {
		super();
		initialize();
		if (logger.isDebugEnabled()) {
			logger.debug(getClass().getSimpleName() + " Constructor");
		}
	}

	private void initialize() {
		createGUI();
		createActions();
		setTitle("Format Number Column");
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
		setSize(300, 280);
		setMinimumSize(new Dimension(getSize()));
		setLayout(new MigLayout("wrap 15", "[][grow]", "[][][][][][][][grow]"));

		//cancelButton.setIcon(Utils.getClassPathImage("images/24/mail_delete.png"));
		
		add(columnLabel);
		add(columnName, "grow, wrap");
		add(precisionLabel);
		add(precisionTextField, "grow, wrap");
		add(factorLabel);
		add(factorTextField, "grow, wrap");
		add(commasLabel);
		add(commasCheck, "grow, wrap");
		add(parensLabel);
		add(parensCheck, "grow, wrap");
		add(leadingLabel);
		add(leadingTextField, "grow, wrap");
		add(trailingLabel);
		add(trailingTextField, "grow, wrap");
		add(Box.createGlue());
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
	public void setConfig(NumberFormatConfig config) {
		commasCheck.setSelected(config.getCommas());
		parensCheck.setSelected(config.getParens());
		leadingTextField.setText(config.getLeadingText());
		trailingTextField.setText(config.getTrailingText());
		precisionTextField.setText(config.getPrecision().toString());
		factorTextField.setText(config.getFactor().toString());
	}
	
	/**
	 * Returns an object representing the desired configuration
	 * 
	 * @return
	 */
	public NumberFormatConfig getConfig() {
		NumberFormatConfig config = new NumberFormatConfig();

		// Commas
		if(commasCheck.isSelected()) {
			config.setCommas(true);
		} else {
			config.setCommas(false);
		}
		
		// Parenthesis
		if(parensCheck.isSelected()) {
			config.setParens(true);
		} else {
			config.setParens(false);
		}
		
		// Factor
		String factor = factorTextField.getText();
		if (!"".equals(factor)) {
			try {
				Double newFactor = Double.parseDouble(factor);
				config.setFactor(newFactor);
			} catch (NumberFormatException e) {
				logger.warn("Unable to parse Factor: " + factor);
			}
		}
		
		// Precision
		String precision = precisionTextField.getText();
		if (!"".equals(precision)) {
			try {
				Integer newPrecision = Integer.parseInt(precision);
				config.setPrecision(newPrecision);
			} catch (NumberFormatException e) {
				logger.warn("Unable to parse Precision: " + precision);
			}
		}
		
		config.setLeadingText(leadingTextField.getText());
		config.setTrailingText(trailingTextField.getText());

		return config;
	}

	public static void main(String args[]) throws Exception {
		JXSNumberFormatDialog dialog = new JXSNumberFormatDialog();
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.columnName.setText("Original Face");
	}

}

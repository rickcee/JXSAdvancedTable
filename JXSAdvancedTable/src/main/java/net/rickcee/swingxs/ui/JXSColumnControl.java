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
 * Created on Feb 21, 2012
 * 
 */
package net.rickcee.swingxs.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.jdesktop.swingx.icon.ColumnControlIcon;
import org.jdesktop.swingx.table.TableColumnExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanConfigUtils;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.export.MailExporter;
import net.rickcee.swingxs.export.XLSExporter;
import net.rickcee.swingxs.mail.IMailConfig;
import net.rickcee.swingxs.model.BeanConfig;
import net.rickcee.swingxs.model.JXSTableModel;
import net.rickcee.swingxs.utils.Utils;

/**
 * @author RickCeeNet
 * 
 */
public class JXSColumnControl extends JButton {

	private static final long serialVersionUID = 5751525709209474101L;
	private static final Logger logger = LoggerFactory.getLogger(JXSColumnControl.class);

	public static String LBL_VISIBILITY = "Column Visibility";
	public static String LBL_FILTER_MANAGER = "Filter Manager";
	public static String LBL_SORT_MANAGER = "Sort Manager";
	public static String LBL_EXPORT = "Export";
	public static String LBL_EXPORT_ALL = "Export All";
	public static String LBL_EXPORT_SELECTED = "Export Selected";
	public static String LBL_EXPORT_FILTERED = "Export Filtered";
	//public static String LBL_FORMAT_COLUMN = "Format Column";
	public static String LBL_EMAIL = "Email";
	public static String LBL_EMAIL_ALL = "Email All";
	public static String LBL_EMAIL_SELECTED = "Email Selected";
	public static String LBL_EMAIL_FILTERED = "Email Filtered";
	public static String LBL_SAVE_CONFIG = "Save Current Config";
	public static String LBL_RESTORE_LOCAL_CONFIG = "Restore Local Config";
	public static String LBL_RESTORE_GLOBAL_CONFIG = "Restore Global Config";

	protected static final String[] LBL_EXPORT_LIST = { LBL_EXPORT_ALL, LBL_EXPORT_FILTERED, LBL_EMAIL_SELECTED };
	protected static final String[] LBL_EMAIL_LIST = { LBL_EMAIL_ALL, LBL_EMAIL_FILTERED, LBL_EMAIL_SELECTED };

	protected JXSTable<?> table;
	protected JXSTableModel<?> model;
	protected JXSTableSorterDialog sortManager = null;
	protected JXSTableFilterDialog filterManager = null;
	protected JFileChooser fileChooser;
	protected JPopupMenu popUpMenu;
	protected JXSColumnControl mySelf;
	//protected MultiPartEmail emailTemplate;
	protected IMailConfig emailTemplateFactory;
	protected JXSEmailDialog emailDialog;
	protected JXSProgressDialog progressDialog;
	protected BeanAttributeContainer attributeConfig;
	
	protected XLSExporter xlsExporter;
	protected MailExporter mailExporter;

	/**
	 * Constructor
	 * 
	 * @param table
	 */
	public JXSColumnControl(JXSTable<?> table) {
		this.table = table;
		this.model = (JXSTableModel<?>) table.getModel();
		this.sortManager = new JXSTableSorterDialog(table);
		this.filterManager = new JXSTableFilterDialog(table);
		this.popUpMenu = new JPopupMenu();
		this.setIcon(new ColumnControlIcon());
		this.mySelf = this;
		this.emailDialog = new JXSEmailDialog();
		this.progressDialog = new JXSProgressDialog();
		this.attributeConfig = BeanManager.get(table.getClazz());

		// Set Undecorated
		//filterManager.setUndecorated(true);
		//sortManager.setUndecorated(true);
		//emailDialog.setUndecorated(true);
		
		// Location relative to the table
		filterManager.setLocationRelativeTo(table);
		sortManager.setLocationRelativeTo(table);
		emailDialog.setLocationRelativeTo(table);
		
		// Set Modal dialog
		filterManager.setModal(true);
		sortManager.setModal(true);
		emailDialog.setModal(true);

		generateMenuItems();

		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				popUpMenu.show(mySelf, -new Double(popUpMenu.getPreferredSize().width).intValue() + 16, getHeight());
			}
		});
		
		xlsExporter = new XLSExporter(table);
		mailExporter = new MailExporter(table);
	}

	protected void generateMenuItems() {
		JMenu exportMenu = new JMenu(LBL_EXPORT);
		exportMenu.setIcon(Utils.getClassPathImage("images/16/export-icon.jpg"));

		JMenuItem item;

		/* Export ALL */
		item = new JMenuItem(LBL_EXPORT_ALL);
		item.setIcon(Utils.getClassPathImage("images/16/mapping-excel-16.png"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				logger.info("Selected Rows: " + table.getModel().getRowCount());
				xlsExporter.showFileChooser(table, model.getResult());
			}
		});
		exportMenu.add(item);

		/* Export SELECTED */
		item = new JMenuItem(LBL_EXPORT_SELECTED);
		item.setIcon(Utils.getClassPathImage("images/16/mapping-excel-16.png"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Object> toExport = new ArrayList<Object>();
				for (int i : table.getSelectedRows()) {
					logger.info("Export Selected Rows: " + table.getValueAt(i, 0) + " / Model Index: "
							+ table.convertRowIndexToModel(i));
					toExport.add(model.getResult().get(table.convertRowIndexToModel(i)));
				}
				xlsExporter.showFileChooser(table, toExport);
			}
		});
		exportMenu.add(item);

		/* Export FILTERED */
		item = new JMenuItem(LBL_EXPORT_FILTERED);
		item.setIcon(Utils.getClassPathImage("images/16/mapping-excel-16.png"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				List<Object> toExport = new ArrayList<Object>();
				for (int i = 0; i < table.getRowCount(); i++) {
					logger.info("Export Filtered Rows: " + table.getValueAt(i, 0) + " / Model Index: "
							+ table.convertRowIndexToModel(i));
					toExport.add(model.getResult().get(table.convertRowIndexToModel(i)));
				}
				xlsExporter.showFileChooser(table, toExport);
			}
		});
		exportMenu.add(item);

		JMenu mailMenu = new JMenu(LBL_EMAIL);
		mailMenu.setIcon(Utils.getClassPathImage("images/12/email-icon.jpg"));

		/* Email ALL */
		item = new JMenuItem(LBL_EMAIL_ALL);
		item.setIcon(Utils.getClassPathImage("images/16/email.jpg"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// JOptionPane.showMessageDialog(null, "Not Implemented!",
				// "Message", JOptionPane.INFORMATION_MESSAGE);
				mailExporter.emailObject(model.getResult());
			}
		});
		mailMenu.add(item);

		/* Email SELECTED */
		item = new JMenuItem(LBL_EMAIL_SELECTED);
		item.setIcon(Utils.getClassPathImage("images/16/email.jpg"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// JOptionPane.showMessageDialog(null, "Not Implemented!",
				// "Message", JOptionPane.INFORMATION_MESSAGE);
				List<Object> toExport = new ArrayList<Object>();
				for (int i : table.getSelectedRows()) {
					logger.info("Email Selected Rows: " + table.getValueAt(i, 0) + " / Model Index: "
							+ table.convertRowIndexToModel(i));
					toExport.add(model.getResult().get(table.convertRowIndexToModel(i)));
				}
				mailExporter.emailObject(toExport);
			}
		});
		mailMenu.add(item);

		/* Email FILTERED */
		item = new JMenuItem(LBL_EMAIL_FILTERED);
		item.setIcon(Utils.getClassPathImage("images/email.jpg"));
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// JOptionPane.showMessageDialog(null, "Not Implemented!",
				// "Message", JOptionPane.INFORMATION_MESSAGE);
				List<Object> toExport = new ArrayList<Object>();
				for (int i = 0; i < table.getRowCount(); i++) {
					logger.info("Export Filtered Rows: " + table.getValueAt(i, 0) + " / Model Index: "
							+ table.convertRowIndexToModel(i));
					toExport.add(model.getResult().get(table.convertRowIndexToModel(i)));
				}
				mailExporter.emailObject(toExport);
			}
		});
		mailMenu.add(item);

		/* Column Visibility Manager */
		JMenu visibilityItem = new JMenu(LBL_VISIBILITY);
		visibilityItem.setIcon(Utils.getClassPathImage("images/column-visibility.jpg"));
		Integer colCount = model.getColumnCount();
		for (int i = 0; i < colCount; i++) {
			final String colName = model.getColumnName(i);
			final String attName = attributeConfig.getFieldReverseLabel().get(colName);
			
			// logger.info("JXSColumnControl - Col.Name: " + colName);
			final JCheckBoxMenuItem cbItem = new JCheckBoxMenuItem(colName);
			final TableColumnExt col = (TableColumnExt) table.getColumnExt(colName);
			
			// Property change listener for [visible]
			col.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if ("visible".equals(arg0.getPropertyName())) {
						cbItem.setSelected(col.isVisible());
						attributeConfig.getFieldVisibility().put(attName, col.isVisible());
					}
				}
			});
			
			// Property change listener for [width]
			col.addPropertyChangeListener(new PropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent arg0) {
					if ("width".equals(arg0.getPropertyName())) {
						attributeConfig.getFieldWidth().put(attName, col.getWidth());
					}
				}
			});
			
			cbItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					col.setVisible(cbItem.isSelected());
				}
			});
			if (col.isVisible()) {
				cbItem.setSelected(true);
			} else {
				cbItem.setSelected(false);
			}
			visibilityItem.add(cbItem);
		}

		/* Sort Manager */
		JMenuItem sortManagerItem = new JMenuItem(LBL_SORT_MANAGER);
		sortManagerItem.setIcon(Utils.getClassPathImage("images/16/sort-manager.png"));
		sortManagerItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sortManager.setVisible(true);
			}
		});

		/* Filter Manager */
		JMenuItem filterManagerItem = new JMenuItem(LBL_FILTER_MANAGER);
		filterManagerItem.setIcon(Utils.getClassPathImage("images/16/filter-manager.png"));
		filterManagerItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				filterManager.setVisible(true);
			}
		});

		/* Filter Manager */
		JMenu filters = new JMenu("User Filters");
		filters.setIcon(Utils.getClassPathImage("images/16/filter-manager.png"));
		JMenuItem filter1 = new JMenuItem("Filter 1");
		filter1.setIcon(Utils.getClassPathImage("images/16/filter-manager.png"));
		filters.add(filter1);
		
		JMenuItem saveConfig = new JMenuItem(LBL_SAVE_CONFIG);
		saveConfig.setIcon(Utils.getClassPathImage("images/12/save-config-icon.jpg"));
		saveConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JXSAdvancedTable<?> advTable = BeanManager.getAdvancedTables().get(table.getClazz());
				BeanConfig config = BeanConfigUtils.getCurrentConfig(advTable);
				try {
					BeanConfigUtils.saveConfig(config, false);
					JOptionPane.showMessageDialog(null, "Configuration Saved.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					logger.error(e1.getMessage(), e1);
				}
			}
		});

		JMenuItem restoreLocalConfig = new JMenuItem(LBL_RESTORE_LOCAL_CONFIG);
		restoreLocalConfig.setIcon(Utils.getClassPathImage("images/12/restore-local-icon.jpg"));
		restoreLocalConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Not Implemented!", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem restoreGlobalConfig = new JMenuItem(LBL_RESTORE_GLOBAL_CONFIG);
		restoreGlobalConfig.setIcon(Utils.getClassPathImage("images/12/restore-global-icon.jpg"));
		restoreGlobalConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Not Implemented!", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		popUpMenu.add(visibilityItem);
		popUpMenu.addSeparator();
		popUpMenu.add(sortManagerItem);
		popUpMenu.add(filterManagerItem);
		popUpMenu.add(filters);
		popUpMenu.addSeparator();
		popUpMenu.add(exportMenu);
		popUpMenu.add(mailMenu);
		popUpMenu.addSeparator();
		popUpMenu.add(saveConfig);
		popUpMenu.add(restoreLocalConfig);
		popUpMenu.add(restoreGlobalConfig);
	}

	/**
	 * @return the sortManager
	 */
	public JXSTableSorterDialog getSortManager() {
		return sortManager;
	}

	/**
	 * @return the filterManager
	 */
	public JXSTableFilterDialog getFilterManager() {
		return filterManager;
	}

	/**
	 * @param emailTemplate
	 *            the emailTemplate to set
	 */
	public void setEmailTemplate(IMailConfig emailTemplate) {
		this.emailTemplateFactory = emailTemplate;
	}

	/**
	 * @return the emailTemplate
	 */
	public IMailConfig getEmailTemplate() {
		return emailTemplateFactory;
	}

}

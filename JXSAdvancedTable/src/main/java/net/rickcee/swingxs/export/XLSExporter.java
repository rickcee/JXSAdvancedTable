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
 * Created on Mar 18, 2012
 *
 */
package net.rickcee.swingxs.export;

import java.awt.Desktop;
import java.io.File;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.ui.JXSProgressDialog;
import net.rickcee.swingxs.ui.JXSTable;
import net.rickcee.swingxs.utils.ExportUtilities;

/**
 * @author RickCeeNet
 * 
 */
public class XLSExporter extends AbstractExporter {
	private static final Logger logger = LoggerFactory.getLogger(XLSExporter.class);
	protected JXSProgressDialog progressDialog;
	protected JXSTable<?> table;
	protected JFileChooser fileChooser;

	protected static int currentExcelFormat = ExportUtilities.EXCEL_2000;

	public XLSExporter(JXSTable<?> table) {
		super(table);
		this.progressDialog = new JXSProgressDialog();
		this.table = table;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rickcee.swingxs.model.meta.IExportable#exportAll()
	 */
	@Override
	public void exportAll() {
		showFileChooser(table, getAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rickcee.swingxs.model.meta.IExportable#exportFiltered()
	 */
	@Override
	public void exportFiltered() {
		showFileChooser(table, getFiltered());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rickcee.swingxs.model.meta.IExportable#exportSelected()
	 */
	@Override
	public void exportSelected() {
		showFileChooser(table, getSelected());
	}

	/**
	 * Displays a file chooser control
	 * 
	 * @throws Exception
	 */
	public void showFileChooser(final JComponent parent, final List<?> objectsToExport) {
		final String fileName = getFileName();
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				fileChooser = new JFileChooser();
				fileChooser.setSelectedFile(new File(fileName));

				int returnVal = fileChooser.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					try {
						progressDialog.startProgress("Generating File...");
						File file = fileChooser.getSelectedFile();
						exportToExcel(file, objectsToExport);
						progressDialog.stopProgress("File Generated.");
						if (Desktop.isDesktopSupported()) {
							Integer option = JOptionPane.showConfirmDialog(null, "The file " + file.getName()
									+ " has been saved. Do you wish to open it ?", "Export", JOptionPane.YES_NO_OPTION);
							if (option.equals(JOptionPane.YES_OPTION)) {
								Desktop.getDesktop().open(file);
							}
						} else {
							JOptionPane.showMessageDialog(null, "The file " + file.getName() + " has been saved.",
									"Export", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						progressDialog.stopProgress();
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					logger.debug("Save command cancelled by user.");
				}
			}
		});
		t.start();
	}

	/**
	 * @param file
	 *            The file to be used
	 * @param clazz
	 *            The class of the objects we are processing
	 * @param objectsToExport
	 *            List of objects to export
	 * @return true if success, false otherwise
	 * @throws Exception
	 *             In case of any error.
	 */
	public boolean exportToExcel(File file, List<?> objectsToExport) throws Exception {
		ExportUtilities.exportToExcel(file, table.getClazz(), objectsToExport, ExportUtilities.EXCEL_2000);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rickcee.swingxs.export.AbstractExporter#getFileName()
	 */
	@Override
	protected String getFileName() {
		String fileName = super.getFileName();
		if (currentExcelFormat == ExportUtilities.EXCEL_2007) {
			fileName = fileName + ".xlsx";
		} else {
			fileName = fileName + ".xls";
		}
		return fileName;
	}

}

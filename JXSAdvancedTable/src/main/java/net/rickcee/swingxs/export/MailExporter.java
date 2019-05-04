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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.swing.JOptionPane;

import org.apache.commons.mail.MultiPartEmail;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.mail.IMailConfig;
import net.rickcee.swingxs.ui.JXSEmailDialog;
import net.rickcee.swingxs.ui.JXSProgressDialog;
import net.rickcee.swingxs.ui.JXSTable;
import net.rickcee.swingxs.utils.ExportUtilities;

/**
 * @author RickCeeNet
 * 
 */
public class MailExporter extends AbstractExporter {
	private static final Logger logger = LoggerFactory.getLogger(XLSExporter.class);
	protected JXSProgressDialog progressDialog;
	protected JXSTable<?> table;
	protected IMailConfig emailTemplateFactory;
	protected JXSEmailDialog emailDialog;

	/**
	 * Constructor
	 * 
	 * @param table
	 */
	public MailExporter(JXSTable<?> table) {
		super(table);
		this.table = table;
		this.emailDialog = new JXSEmailDialog();
		this.progressDialog = new JXSProgressDialog();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rickcee.swingxs.model.meta.IExportable#exportAll()
	 */
	@Override
	public void exportAll() {
		emailObject(getAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rickcee.swingxs.model.meta.IExportable#exportFiltered()
	 */
	@Override
	public void exportFiltered() {
		emailObject(getFiltered());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rickcee.swingxs.model.meta.IExportable#exportSelected()
	 */
	@Override
	public void exportSelected() {
		emailObject(getSelected());
	}

	public void emailObject(final List<?> objectsToEmail) {
		if (emailTemplateFactory == null) {
			JOptionPane.showMessageDialog(null, "E-Mail functionallity is not supported on this table.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		progressDialog.startProgress("Generating File...");
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					MultiPartEmail emailInstance = emailTemplateFactory.getMailTemplate();
					Workbook workbook;
					String fileName = getFileName();
					if (XLSExporter.currentExcelFormat == ExportUtilities.EXCEL_2007) {
						workbook = ExportUtilities.getExcelWorkbook(table.getClazz(), objectsToEmail,
								ExportUtilities.EXCEL_2007);
						fileName = fileName + ".xlsx";
					} else {
						workbook = ExportUtilities.getExcelWorkbook(table.getClazz(), objectsToEmail,
								ExportUtilities.EXCEL_2000);
						fileName = fileName + ".xls";
					}
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					workbook.write(out);
					ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
					DataSource ds = new ByteArrayDataSource(in, "application/vnd.ms-excel");
					emailInstance.attach(ds, fileName, "The exported excel file");

					// Set the Email Template we got from the Factory
					emailDialog.setMultipartEmail(emailInstance);
					progressDialog.stopProgress("File Generated.");
					emailDialog.setVisible(true);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					progressDialog.stopProgress();
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		t.start();
	}
}

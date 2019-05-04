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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.model.JXSTableModel;
import net.rickcee.swingxs.model.meta.IExportable;
import net.rickcee.swingxs.ui.JXSTable;
import net.rickcee.swingxs.utils.Formatter;

/**
 * @author RickCeeNet
 * 
 */
public abstract class AbstractExporter implements IExportable {
	private static final Logger logger = LoggerFactory.getLogger(AbstractExporter.class);

	protected JXSTable<?> sourceTable;
	protected JXSTableModel<?> model;
	
	/**
	 * Constructor
	 * 
	 * @param table
	 */
	public AbstractExporter(JXSTable<?> table) {
		super();
		this.sourceTable = table;
		this.model = (JXSTableModel<?>) table.getModel();
	}

	public List<Object> getFiltered() {
		List<Object> toExport = new ArrayList<Object>();
		for (int i = 0; i < sourceTable.getRowCount(); i++) {
			logger.info("Export Filtered Rows: " + sourceTable.getValueAt(i, 0) + " / Model Index: "
					+ sourceTable.convertRowIndexToModel(i));
			toExport.add(model.getResult().get(sourceTable.convertRowIndexToModel(i)));
		}
		return toExport;
	}

	public List<?> getAll() {
		return model.getResult();
	}

	public List<Object> getSelected() {
		List<Object> toExport = new ArrayList<Object>();
		for (int i : sourceTable.getSelectedRows()) {
			logger.info("Export Selected Rows: " + sourceTable.getValueAt(i, 0) + " / Model Index: "
					+ sourceTable.convertRowIndexToModel(i));
			toExport.add(model.getResult().get(sourceTable.convertRowIndexToModel(i)));
		}
		return toExport;
	}
	
	protected String getFileName() {
		Calendar cal = Calendar.getInstance();
		String fileName = "Export_" + cal.get(Calendar.YEAR)
				+ Formatter.formatDayMonthNumber(cal.get(Calendar.MONTH) + 1)
				+ Formatter.formatDayMonthNumber(cal.get(Calendar.DAY_OF_MONTH)) + "_"
				+ Formatter.formatDayMonthNumber(cal.get(Calendar.HOUR_OF_DAY))
				+ Formatter.formatDayMonthNumber(cal.get(Calendar.MINUTE))
				+ Formatter.formatDayMonthNumber(cal.get(Calendar.SECOND));
		return fileName;
	}

}

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
 * Created on Feb 10, 2012
 * 
 */
package net.rickcee.swingxs.model;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.ui.JXSTable;
import net.rickcee.swingxs.ui.JXSTableCellRenderer;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableModelListener implements TableModelListener {

	protected JXSTableCellRenderer renderer;
	protected JXSTable<?> table;
	private static final Logger logger = LoggerFactory.getLogger(JXSTableModelListener.class);

	/**
	 * Constructor
	 *
	 * @param renderer
	 */
	public JXSTableModelListener(JXSTable<?> table, JXSTableCellRenderer renderer) {
		this.table = table;
		this.renderer = renderer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.TableModelListener#tableChanged(javax.swing.event.
	 * TableModelEvent)
	 */
	public void tableChanged(TableModelEvent e) {
		// TODO: REVIEW!!! 
		
//		TableModel atm = (TableModel) e.getSource();
////		int firstRow = e.getFirstRow();
////		int lastRow = e.getLastRow();
////		int mColIndex = e.getColumn();
//		int firstRow = e.getFirstRow();
//		int lastRow = atm.getRowCount();
//		int mColIndex = -1;
//
//		switch (e.getType()) {
//		case TableModelEvent.INSERT:
//			//logger.debug("Row Inserted");
//			//Integer index = firstRow!=0?table.convertRowIndexToView(firstRow):firstRow;
//			renderer.flashRowSelection = firstRow;
//			renderer.flashRow();
//			// The inserted rows are in the range [firstRow, lastRow]
//			for (int r = firstRow; r <= lastRow; r++) {
//				logger.debug("Row Inserted: " + r);
//				// Row r was inserted
//			}
//			break;
//		case TableModelEvent.UPDATE:
//			logger.debug("Row Updated");
//			//renderer.flashRowSelection = firstRow;
//			//renderer.flashRow();
//			if (firstRow == TableModelEvent.HEADER_ROW) {
//				if (mColIndex == TableModelEvent.ALL_COLUMNS) {
//					// A column was added
//				} else {
//					// Column mColIndex in header changed
//				}
//			} else {
//				// The rows in the range [firstRow, lastRow] changed
//				for (int r = firstRow; r <= lastRow; r++) {
//					// Row r was changed
//					//renderer.flashRowSelection = r;
//					//renderer.flashRow();
//					//renderer.actionPerformed(null);
//					if (mColIndex == TableModelEvent.ALL_COLUMNS) {
//						// All columns in the range of rows have changed
//					} else {
//						// Column mColIndex changed
//					}
//				}
//			}
//			break;
//		case TableModelEvent.DELETE:
//			logger.debug("Row Deleted");
//			// The rows in the range [firstRow, lastRow] changed
//			for (int r = firstRow; r <= lastRow; r++) {
//				// Row r was deleted
//			}
//			break;
//		}
	}

	/**
	 * @return the renderer
	 */
	public JXSTableCellRenderer getRenderer() {
		return renderer;
	}

}

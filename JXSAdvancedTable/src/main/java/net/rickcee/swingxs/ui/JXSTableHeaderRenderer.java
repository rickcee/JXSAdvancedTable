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
 * Created on Feb 22, 2012
 * 
 */
package net.rickcee.swingxs.ui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class JXSTableHeaderRenderer implements TableCellRenderer {

	// This method is called each time a column header
	// using this renderer needs to be rendered.
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int rowIndex, int vColIndex) {
		TableCellRenderer tcr = table.getTableHeader().getDefaultRenderer();
		Component c = tcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, rowIndex, vColIndex);
		DefaultTableCellRenderer r = (DefaultTableCellRenderer) c;

		Integer sortIndex = 1;
		for (RowSorter.SortKey sk : table.getRowSorter().getSortKeys()) {
			if (sk.getColumn() == table.convertColumnIndexToModel(vColIndex)) {
				// System.out.println("SortColumn: " + sk.getColumn() + " / " +
				// sortIndex);
				if (sk.getSortOrder().equals(SortOrder.ASCENDING)) {
					r.setIcon(new JXSColumnControlIcon(sortIndex, SortOrder.ASCENDING));
				} else {
					r.setIcon(new JXSColumnControlIcon(sortIndex, SortOrder.DESCENDING));
				}
			}
			sortIndex++;
		}

		// Set tool tip if desired
		r.setToolTipText((String) value);

		// Since the renderer is a component, return itself
		return r;
	}

}

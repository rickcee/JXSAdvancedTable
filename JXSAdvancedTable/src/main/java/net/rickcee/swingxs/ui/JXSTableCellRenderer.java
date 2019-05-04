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
 * Created on Jan 24, 2012
 * 
 */
package net.rickcee.swingxs.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.model.DateFormatConfig;
import net.rickcee.swingxs.model.JXSTableContants;
import net.rickcee.swingxs.model.JXSTableModel;
import net.rickcee.swingxs.model.NumberFormatConfig;
import net.rickcee.swingxs.model.meta.IFilterable;
import net.rickcee.swingxs.utils.Formatter;
import net.rickcee.swingxs.utils.RGBColorBuilder;

public class JXSTableCellRenderer implements TableCellRenderer, ActionListener {

	private static final Logger logger = LoggerFactory.getLogger(JXSTableCellRenderer.class);
	
	protected BeanAttributeContainer container;
	protected TableCellRenderer mWrappedRenderer;
	private Class<?> clazz;
	// static Boolean FLAG = Boolean.TRUE;
	protected JXSTable<?> table;
	protected Color selectionColor;
	protected Timer flashTimer;
	
	public int flashRowSelection;

	/**
	 * Constructor
	 * 
	 * @param pWrappedRenderer
	 * @param clazz
	 * @throws Exception
	 */
	public JXSTableCellRenderer(TableCellRenderer pWrappedRenderer, Class<?> clazz, JXSTable<?> table) throws Exception {
		this.mWrappedRenderer = pWrappedRenderer;
		this.clazz = clazz;
		this.container = BeanManager.get(clazz);
		this.table = table;

		flashRowSelection = -1;
		selectionColor = RGBColorBuilder.get(RGBColorBuilder.BLUE_CORNFLOWER);
		flashTimer = new Timer(500, this);
		flashTimer.setRepeats(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax
	 * .swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable pTable, Object pValue, boolean pIsSelected,
			boolean pHasFocus, int pRow, int pColumn) {

		// The index of the column whose header was clicked
		int mColIndex = pTable.convertColumnIndexToModel(pColumn);
		int mRowIndex = pTable.convertRowIndexToModel(pRow);
		
		// Get the column name
		String colName = pTable.getModel().getColumnName(mColIndex);
		// Get the clazz attribute name
		String attName = container.getFieldReverseLabel().get(colName);

		// If it's a date, then convert it to a String w/ particular
		if (pValue instanceof Date) {
			try {
				DateFormatConfig cfg = container.getFieldDateFormat().get(attName);
				DateFormat formatter = container.getFieldDateFormatter().get(attName);
				if (formatter == null) {
					formatter = Formatter.getDefaultDateFormat();
				}
				pValue = Formatter.formatDate((Date) pValue, cfg, formatter);
				// _do = Formatter.formatDate((Date) _do, format);
			} catch (Exception e) {
				logger.error("Error formatting Date: " + e.getMessage(), e);
			}
		}
		
		// Same for numbers
		if (pValue instanceof Number) {
			try {
				NumberFormatConfig cfg = container.getFieldNumberFormat().get(attName);
				DecimalFormat formatter = container.getFieldNumberFormatter().get(attName);
				if (formatter == null) {
					formatter = Formatter.getDefaultNumberformat();
				}
				pValue = Formatter.formatNumber((Number) pValue, cfg, formatter);
			} catch (Exception e) {
				logger.error("Error formatting Number: " + e.getMessage(), e);
			}
		}
		
		// Use the wrapped renderer
		Component renderedComponent = mWrappedRenderer.getTableCellRendererComponent(pTable, pValue, pIsSelected,
				pHasFocus, mRowIndex, mColIndex);

		// TableColumnModel colModel = pTable.getColumnModel();

		// Set the cell alignment
		// -------------------------------------------------------------
		Integer alignment = container.getFieldAlignment().get(attName);
		if (alignment != null) {
			((JLabel) renderedComponent).setHorizontalAlignment(alignment);
		} else {
			((JLabel) renderedComponent).setHorizontalAlignment(JXSTableContants.DEFAULT_COLUMN_ALIGNMENT);
		}

		// Color Filter
		// -------------------------------------------------------------
		IFilterable filter = BeanManager.getFilters().get(clazz);
		if (filter != null) {
			JXSTableModel<?> model = (JXSTableModel<?>) pTable.getModel();
			Color c = filter.getColor(model.getResult().get(pRow));
			if (c != null) {
				renderedComponent.setBackground(c);
			}
		} else {
			// Set the cell background color
			// -------------------------------------------------------------
			Color bg = container.getFieldBackgroundColor().get(attName);
			if (bg != null && !pIsSelected && !bg.equals(renderedComponent.getBackground())) {
				renderedComponent.setBackground(bg);
				pTable.repaint();
			}

			// Set the cell foreground color
			// -------------------------------------------------------------
			Color fg = container.getFieldForegroundColor().get(attName);
			if (fg != null && !fg.equals(renderedComponent.getForeground())) {
				renderedComponent.setForeground(fg);
				pTable.repaint();
			}
		}

		Integer viewIndex = table.convertRowIndexToView(pRow);
		
		// Flashing row
		// -------------------------------------------------------------
		if (viewIndex == flashRowSelection && flashTimer.isRunning()) {
			renderedComponent.setBackground(selectionColor);
		}

		return renderedComponent;
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("ActionPerformed! ");
		flashTimer.stop();
		table.repaint();
	}

	public void setFlashRowSelection(int frs) {
		flashRowSelection = frs;
	}

	public void flashRow() {
		System.out.println("FlashRow: " + flashRowSelection);
		if (!flashTimer.isRunning()) {
			flashTimer.start();
		}
		Rectangle r = table.getCellRect(flashRowSelection, 0, true);
		table.scrollRectToVisible(r);
		table.repaint();
	}

}

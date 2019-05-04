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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SortOrder;
import javax.swing.plaf.UIResource;

/**
 * Icon class for rendering icon which indicates user control of column
 * visibility.
 * 
 * @author Amy Fowler
 * @version 1.0
 */
public class JXSColumnControlIcon implements Icon, UIResource {
	private int width = 14;
	private int height = 14;
	private String colNumber;
	private SortOrder order;

	/** TODO: need to support small, medium, large */
	public JXSColumnControlIcon(Integer colNumber, SortOrder order) {
		this.colNumber = colNumber != null ? colNumber.toString() : "";
		this.order = order;
	}

	/**
	 * @return the colNumber
	 */
	public String getColNumber() {
		return colNumber;
	}

	/**
	 * @return the order
	 */
	public SortOrder getOrder() {
		return order;
	}

	/**
	 * @param order
	 *            the order to set
	 */
	public void setOrder(SortOrder order) {
		this.order = order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconWidth()
	 */
	public int getIconWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#getIconHeight()
	 */
	public int getIconHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.Icon#paintIcon(java.awt.Component, java.awt.Graphics,
	 * int, int)
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		Color color = c.getForeground();
		g.setColor(color);

		if (SortOrder.ASCENDING.equals(order)) {
			g.drawLine(x + 3, y + 6, x + 9, y + 6);
			g.drawLine(x + 4, y + 5, x + 8, y + 5);
			g.drawLine(x + 5, y + 4, x + 7, y + 4);
			g.drawLine(x + 6, y + 3, x + 6, y + 3);
		} else {
			g.drawLine(x + 3, y + 6, x + 9, y + 6);
			g.drawLine(x + 4, y + 7, x + 8, y + 7);
			g.drawLine(x + 5, y + 8, x + 7, y + 8);
			g.drawLine(x + 6, y + 9, x + 6, y + 9);
		}

		g.drawString(colNumber, x + 10, y + 12);

	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel label = new JLabel(new JXSColumnControlIcon(3, SortOrder.DESCENDING));
		frame.getContentPane().add(BorderLayout.CENTER, label);
		frame.pack();
		frame.setVisible(true);
	}

}

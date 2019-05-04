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
package net.rickcee.swingxs.model;

import java.awt.Color;


/**
 * @author RickCeeNet
 * 
 */
public class BeanAttributeConfig {
	private String name;
	private String label;
	private Boolean visible;
	private Color colorBackground;
	private Color colorForeground;
	private Boolean fixed;
	private String format;
	private DateFormatConfig dateFormat;
	private NumberFormatConfig numberFormat;
	private Integer width;
	private Integer alignment;
	private Integer position;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Attribute Name: [" + name + "] / Label: [" + label + "] - " + super.toString();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the visible
	 */
	public Boolean getVisible() {
		return visible;
	}

	/**
	 * @param visible
	 *            the visible to set
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the colorBackground
	 */
	public Color getColorBackground() {
		return colorBackground;
	}

	/**
	 * @param colorBackground
	 *            the colorBackground to set
	 */
	public void setColorBackground(Color colorBackground) {
		this.colorBackground = colorBackground;
	}

	/**
	 * @return the colorForeground
	 */
	public Color getColorForeground() {
		return colorForeground;
	}

	/**
	 * @param colorForeground
	 *            the colorForeground to set
	 */
	public void setColorForeground(Color colorForeground) {
		this.colorForeground = colorForeground;
	}

	/**
	 * @return the fixed
	 */
	public Boolean getFixed() {
		return fixed;
	}

	/**
	 * @param fixed
	 *            the fixed to set
	 */
	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @return the alignment
	 */
	public Integer getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment
	 *            the alignment to set
	 */
	public void setAlignment(Integer alignment) {
		this.alignment = alignment;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @return the dateFormat
	 */
	public DateFormatConfig getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(DateFormatConfig dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @return the numberFormat
	 */
	public NumberFormatConfig getNumberFormat() {
		return numberFormat;
	}

	/**
	 * @param numberFormat the numberFormat to set
	 */
	public void setNumberFormat(NumberFormatConfig numberFormat) {
		this.numberFormat = numberFormat;
	}

}

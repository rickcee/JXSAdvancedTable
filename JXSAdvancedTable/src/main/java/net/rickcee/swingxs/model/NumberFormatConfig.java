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
 * Created on Apr 18, 2013
 *
 */
package net.rickcee.swingxs.model;

/**
 * @author RickCeeNet
 * 
 */
public class NumberFormatConfig {
	private Number precision = 3;
	private Number factor = 1.0E-6;
	private Boolean commas = Boolean.TRUE;
	private Boolean parens = Boolean.FALSE;
	private String leadingText = "USD ";
	private String trailingText = "M";

	/**
	 * @return the precision
	 */
	public Number getPrecision() {
		return precision;
	}

	/**
	 * @param precision
	 *            the precision to set
	 */
	public void setPrecision(Number precision) {
		this.precision = precision;
	}

	/**
	 * @return the factor
	 */
	public Number getFactor() {
		return factor;
	}

	/**
	 * @param factor
	 *            the factor to set
	 */
	public void setFactor(Number factor) {
		this.factor = factor;
	}

	/**
	 * @return the commas
	 */
	public Boolean getCommas() {
		return commas;
	}

	/**
	 * @param commas
	 *            the commas to set
	 */
	public void setCommas(Boolean commas) {
		this.commas = commas;
	}

	/**
	 * @return the parens
	 */
	public Boolean getParens() {
		return parens;
	}

	/**
	 * @param parens
	 *            the parens to set
	 */
	public void setParens(Boolean parens) {
		this.parens = parens;
	}

	/**
	 * @return the leadingText
	 */
	public String getLeadingText() {
		return leadingText;
	}

	/**
	 * @param leadingText
	 *            the leadingText to set
	 */
	public void setLeadingText(String leadingText) {
		this.leadingText = leadingText;
	}

	/**
	 * @return the trailingText
	 */
	public String getTrailingText() {
		return trailingText;
	}

	/**
	 * @param trailingText
	 *            the trailingText to set
	 */
	public void setTrailingText(String trailingText) {
		this.trailingText = trailingText;
	}

}

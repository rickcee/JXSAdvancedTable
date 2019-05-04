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
 */
package net.rickcee.swingxs.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.rickcee.swingxs.model.DateFormatConfig;
import net.rickcee.swingxs.model.NumberFormatConfig;

/**
 * @author RickCeeNet
 * 
 */
public class Formatter {
	private static final NumberFormat nf = NumberFormat.getInstance();
	// private static final DecimalFormat dcf = new DecimalFormat();
	private static final DateFormat df = DateFormat.getInstance();

	//private DecimalFormat myFormatter = new DecimalFormat(DEFAULT_NUMBER_FORMAT);
	
	private static String DEFAULT_DATE_FORMAT = "MM/dd/yyyy"; // H:mm:ss
	private static String DEFAULT_NUMBER_FORMAT = "###,###,###,###.##";
	
	private static final DecimalFormat defaultNumberFormat = new DecimalFormat(DEFAULT_NUMBER_FORMAT);
	private static final DateFormat defaultDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

	/**
	 * @return the defaultNumberFormat
	 */
	public static DecimalFormat getDefaultNumberformat() {
		return defaultNumberFormat;
	}
	
	/**
	 * @return the defaultDateFormat
	 */
	public static DateFormat getDefaultDateFormat() {
		return defaultDateFormat;
	}

	public static synchronized DecimalFormat getFormatter(NumberFormatConfig config) {
		String format = "###,###,###,###";
		if(!config.getCommas()) {
			format = format.replaceAll(",", "");
		}
		format = format + ".";
		for(int i = 0; i<config.getPrecision().intValue();i++) {
			format = format + "#";
		}
		
		DecimalFormat myFormatter = new DecimalFormat(format);
		return myFormatter;
	}

	public static synchronized DateFormat getFormatter(DateFormatConfig config) {
		String format = "";
		if (config.getMmdd()) {
			format = "MM/dd";
		} else {
			format = "dd/MM";
		}
		if (config.getShowYear()) {
			if (config.getYear2d()) {
				format = format + "/yy";
			} else {
				format = format + "/yyyy";
			}
		}
		if (config.getShowDay()) {
			format = format + "EEE";
		}

		DateFormat myFormatter = new SimpleDateFormat(format);
		return myFormatter;
	}
	
	public static synchronized String formatNumber(Number number, NumberFormatConfig config, DecimalFormat myFormatter) {
		Number newNumber = number.doubleValue() * config.getFactor().doubleValue();

		if (myFormatter == null) {
			myFormatter = defaultNumberFormat;
		}
		String output = "";
		output = myFormatter.format(newNumber);

		// Add Leading and Trailing
		output = config.getLeadingText() + output + config.getTrailingText();
		return output;
	}
	
	public static synchronized String formatDate(Date date, DateFormatConfig config, DateFormat myFormatter) {
		if (myFormatter == null) {
			myFormatter = defaultDateFormat;
		}
		String output = "";
		output = myFormatter.format(date).toUpperCase();

		return output;
	}
	
	public static synchronized String formatNumber(Number number) {
		return nf.format(number);
	}

	public static synchronized String formatDecimal(Number number, String format) {
		NumberFormat nf = new DecimalFormat(format);
		return nf.format(number);
	}

	public static synchronized String formatNumber(Number number, String format) {
		return nf.format(number);
	}

	public static synchronized String formatDate(Date date) {
		return df.format(date);
	}

	public static synchronized String formatDate(Date date, String format) {
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}

	public static synchronized String formatDayMonthNumber(int number) {
		if (number < 10) {
			return "0" + number;
		} else {
			return "" + number;
		}
	}
}

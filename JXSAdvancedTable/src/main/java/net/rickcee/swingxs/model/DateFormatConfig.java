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
public class DateFormatConfig {
	private Boolean mmdd = Boolean.TRUE;
	private Boolean year2d = Boolean.FALSE;
	private Boolean showDay = Boolean.FALSE;
	private Boolean showYear = Boolean.TRUE;

	/**
	 * @return the mmdd
	 */
	public Boolean getMmdd() {
		return mmdd;
	}

	/**
	 * @param mmdd
	 *            the mmdd to set
	 */
	public void setMmdd(Boolean mmdd) {
		this.mmdd = mmdd;
	}

	/**
	 * @return the year2d
	 */
	public Boolean getYear2d() {
		return year2d;
	}

	/**
	 * @param year2d
	 *            the year2d to set
	 */
	public void setYear2d(Boolean year2d) {
		this.year2d = year2d;
	}

	/**
	 * @return the showDay
	 */
	public Boolean getShowDay() {
		return showDay;
	}

	/**
	 * @param showDay
	 *            the showDay to set
	 */
	public void setShowDay(Boolean showDay) {
		this.showDay = showDay;
	}

	/**
	 * @return the showYear
	 */
	public Boolean getShowYear() {
		return showYear;
	}

	/**
	 * @param showYear
	 *            the showYear to set
	 */
	public void setShowYear(Boolean showYear) {
		this.showYear = showYear;
	}

}

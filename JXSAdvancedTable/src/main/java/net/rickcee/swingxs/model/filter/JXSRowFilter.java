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
 * Created on Feb 20, 2012
 * 
 */
package net.rickcee.swingxs.model.filter;

import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.BETWEEN;
import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.BETWEEN_INCLUSIVE;
import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.EQUALS;
import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.GREATER_OR_EQUALS_THAN;
import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.GREATER_THAN;
import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.LESSER_OR_EQUALS_THAN;
import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.LESSER_THAN;
import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.NOT_EQUALS;

import java.lang.reflect.Method;
import java.util.Date;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.model.JXSTableModel;

/**
 * @author RickCeeNet
 * 
 */
public abstract class JXSRowFilter {
	private static final Logger logger = LoggerFactory.getLogger(JXSRowFilter.class);

	public static RowFilter<TableModel, Integer> generateDateFilter(Class<?> clazz, final String attName,
			final Integer type, final Date... value) {
		BeanAttributeContainer container = BeanManager.get(clazz);
		final Method getter = container.getFieldGetters().get(attName);
		return new RowFilter<TableModel, Integer>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
			 */
			@Override
			public boolean include(Entry<? extends TableModel, ? extends Integer> object) {
				Date objValue = null;
				JXSTableModel<?> model = (JXSTableModel<?>) object.getModel();
				Integer id = object.getIdentifier().intValue();
				try {
					objValue = (Date) getter.invoke(model.getResult().get(id));
				} catch (ClassCastException e) {
					logger.error("JXSRowFilterDate - Cannot Cast [" + e.getMessage() + "]");
					return false;
				} catch (Exception e) {
					logger.error("JXSRowFilterDate - Exception trying to get value for attribute [" + attName + "]: "
							+ e.getMessage());
					return false;
				}

				if (objValue == null || value == null || value.length < 1) {
					return false;
				}

				if (EQUALS.equals(type) && objValue.equals(value[0])) {
					return true;
				}

				if (NOT_EQUALS.equals(type) && !objValue.equals(value[0])) {
					return true;
				}

				if (GREATER_THAN.equals(type) && objValue.after(value[0])) {
					return true;
				}

				if (GREATER_OR_EQUALS_THAN.equals(type) && (objValue.after(value[0]) || objValue.equals(value[0]))) {
					return true;
				}

				if (LESSER_THAN.equals(type) && objValue.before(value[0])) {
					return true;
				}

				if (LESSER_OR_EQUALS_THAN.equals(type) && (objValue.before(value[0]) || objValue.equals(value[0]))) {
					return true;
				}

				/*
				 * BETWEEN & BETWEEN INCLUSIVE
				 */
				if (value.length > 1 && (BETWEEN.equals(type) || BETWEEN_INCLUSIVE.equals(type))) {
					if (BETWEEN.equals(type) && objValue.after(value[0]) && objValue.before(value[1])) {
						return true;
					}

					if (BETWEEN_INCLUSIVE.equals(type) && (objValue.after(value[0]) || objValue.equals(value[0]))
							&& (objValue.before(value[1]) || objValue.equals(value[1]))) {
						return true;
					}
				} else if (value.length < 2 && (BETWEEN.equals(type) || BETWEEN_INCLUSIVE.equals(type))) {
					logger.warn("Not enough parameters to compare attribute [" + attName + "]");
					return false;
				}

				logger.warn("JXSRowFilterDate - Cannot compare attribute [" + attName + "] / [" + type + "] / ["
						+ value + "]");
				return false;
			}

		};
	}

	public static RowFilter<TableModel, Integer> generateStringFilter(Class<?> clazz, final String attName,
			final Integer type, final Boolean matchCase, final Boolean matchWholeWord, final String... value) {
		BeanAttributeContainer container = BeanManager.get(clazz);
		final Method getter = container.getFieldGetters().get(attName);
		return new RowFilter<TableModel, Integer>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
			 */
			@Override
			public boolean include(javax.swing.RowFilter.Entry<? extends TableModel, ? extends Integer> object) {
				String objValue = null;
				JXSTableModel<?> model = (JXSTableModel<?>) object.getModel();
				Integer id = object.getIdentifier().intValue();
				try {
					objValue = (String) getter.invoke(model.getResult().get(id));
				} catch (ClassCastException e) {
					logger.error("JXSRowFilterString - Cannot Cast [" + e.getMessage() + "]");
					return false;
				} catch (Exception e) {
					logger.error("JXSRowFilterString - Exception trying to get value for attribute [" + attName + "]: "
							+ e.getMessage());
					return false;
				}

				if (objValue == null || value == null || value.length < 1) {
					return false;
				}

				if (EQUALS.equals(type) && matchCase && matchWholeWord && objValue.equals(value[0])) {
					return true;
				}

				if (EQUALS.equals(type) && !matchCase && matchWholeWord && objValue.equalsIgnoreCase(value[0])) {
					return true;
				}

				if (EQUALS.equals(type) && matchCase && !matchWholeWord && objValue.equals(value[0])) {
					return true;
				}

				if (EQUALS.equals(type) && !matchCase && !matchWholeWord
						&& objValue.toLowerCase().contains(value[0].toLowerCase())) {
					return true;
				}

				if (NOT_EQUALS.equals(type) && matchCase && matchWholeWord && !objValue.equals(value[0])) {
					return true;
				}

				if (NOT_EQUALS.equals(type) && !matchCase && matchWholeWord && !objValue.equalsIgnoreCase(value[0])) {
					return true;
				}

				if (NOT_EQUALS.equals(type) && matchCase && !matchWholeWord && !objValue.equals(value[0])) {
					return true;
				}

				if (NOT_EQUALS.equals(type) && !matchCase && !matchWholeWord
						&& !objValue.toLowerCase().contains(value[0].toLowerCase())) {
					return true;
				}

				logger.warn("JXSRowFilterString - Cannot compare attribute [" + attName + "] / [" + type + "] / ["
						+ value[0] + "]");
				return false;
			}

		};
	}

	public static RowFilter<TableModel, Integer> generateNumberFilter(Class<?> clazz, final String attName,
			final Integer type, final Number... value) {
		BeanAttributeContainer container = BeanManager.get(clazz);
		final Method getter = container.getFieldGetters().get(attName);

		return new RowFilter<TableModel, Integer>() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see javax.swing.RowFilter#include(javax.swing.RowFilter.Entry)
			 */
			@Override
			public boolean include(javax.swing.RowFilter.Entry<? extends TableModel, ? extends Integer> object) {
				Number objValue = null;
				JXSTableModel<?> model = (JXSTableModel<?>) object.getModel();
				Integer id = object.getIdentifier().intValue();
				try {
					objValue = (Number) getter.invoke(model.getResult().get(id));
				} catch (ClassCastException e) {
					logger.error("JXSRowFilterNumber - Cannot Cast [" + e.getMessage() + "]");
					return false;
				} catch (Exception e) {
					logger.error("JXSRowFilterNumber - Exception trying to get value for attribute [" + attName + "]: "
							+ e.getMessage());
					return false;
				}

				if (objValue == null || value == null || value.length < 1) {
					return false;
				}
				
				
				Class<?> fieldClass = BeanManager.get(model.getClazz()).getFieldTypes().get(attName);
				Number firstNumber;
				if (fieldClass.equals(Integer.class)) {
					firstNumber = value[0].intValue();
				} else if (fieldClass.equals(Float.class)) {
					firstNumber = value[0].floatValue();
				} else if (fieldClass.equals(Long.class)) {
					firstNumber = value[0].longValue();
				} else if (fieldClass.equals(Short.class)) {
					firstNumber = value[0].shortValue();
				} else {
					firstNumber = value[0];
				}
				
				if (EQUALS.equals(type) && objValue.equals(firstNumber)) {
					return true;
				}

				if (NOT_EQUALS.equals(type) && !objValue.equals(firstNumber)) {
					return true;
				}

				if (GREATER_THAN.equals(type) && objValue.doubleValue() > firstNumber.doubleValue()) {
					return true;
				}

				if (GREATER_OR_EQUALS_THAN.equals(type) && objValue.doubleValue() >= firstNumber.doubleValue()) {
					return true;
				}

				if (LESSER_THAN.equals(type) && objValue.doubleValue() < firstNumber.doubleValue()) {
					return true;
				}

				if (LESSER_OR_EQUALS_THAN.equals(type) && objValue.doubleValue() <= firstNumber.doubleValue()) {
					return true;
				}

				/*
				 * BETWEEN & BETWEEN INCLUSIVE
				 */
				if (value.length > 1 && (BETWEEN.equals(type) || BETWEEN_INCLUSIVE.equals(type))) {
					Number secondNumber;
					if (fieldClass.equals(Integer.class)) {
						secondNumber = value[1].intValue();
					} else if (fieldClass.equals(Float.class)) {
						secondNumber = value[1].floatValue();
					} else if (fieldClass.equals(Long.class)) {
						secondNumber = value[1].longValue();
					} else if (fieldClass.equals(Short.class)) {
						secondNumber = value[1].shortValue();
					} else {
						secondNumber = value[1];
					}
					
					if (BETWEEN.equals(type) && objValue.doubleValue() > firstNumber.doubleValue()
							&& objValue.doubleValue() < secondNumber.doubleValue()) {
						return true;
					}

					if (BETWEEN_INCLUSIVE.equals(type) && objValue.doubleValue() >= firstNumber.doubleValue()
							&& objValue.doubleValue() <= secondNumber.doubleValue()) {
						return true;
					}
				} else if (value.length < 2 && (BETWEEN.equals(type) || BETWEEN_INCLUSIVE.equals(type))) {
					logger.warn("Not enough parameters to compare attribute [" + attName + "]");
					return false;
				}

//				logger.warn("JXSRowFilterNumber - Cannot compare attribute [" + attName + "] / [" + type + "] / ["
//						+ value + "]");
				return false;
			}

		};
	}
}

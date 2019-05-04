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
 * Created on Feb 19, 2012
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

import javax.swing.RowFilter;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.model.JXSTableModel;

/**
 * @author RickCeeNet
 * 
 */
@Deprecated
public class JXSRowFilterNumber extends RowFilter<JXSTableModel, Number> {

	protected BeanAttributeContainer container;
	protected String attName;
	protected Method getter;
	protected Number[] value;
	protected Integer type;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 * @param attName
	 * @param type
	 * @param value
	 */
	public JXSRowFilterNumber(Class<?> clazz, String attName, Integer type, Number... value) {
		this.container = BeanManager.get(clazz);
		this.getter = container.getFieldGetters().get(attName);
		this.value = value;
		this.type = type;
		this.attName = attName;
	}

	@Override
	public boolean include(Entry<? extends JXSTableModel, ? extends Number> object) {
		Number objValue = null;
		JXSTableModel model = object.getModel();
		Integer id = object.getIdentifier().intValue();
		try {
			objValue = (Number) getter.invoke(model.getResult().get(id));
		} catch (ClassCastException e) {
			System.out.println("JXSRowFilterNumber - Cannot Cast [" + e.getMessage() + "]");
			return false;
		} catch (Exception e) {
			System.out.println("JXSRowFilterNumber - Exception trying to get value for attribute [" + attName + "]: " + e.getMessage());
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

		if (GREATER_THAN.equals(type) && objValue.doubleValue() > value[0].doubleValue()) {
			return true;
		}

		if (GREATER_OR_EQUALS_THAN.equals(type) && objValue.doubleValue() >= value[0].doubleValue()) {
			return true;
		}

		if (LESSER_THAN.equals(type) && objValue.doubleValue() < value[0].doubleValue()) {
			return true;
		}

		if (LESSER_OR_EQUALS_THAN.equals(type) && objValue.doubleValue() <= value[0].doubleValue()) {
			return true;
		}

		/*
		 * BETWEEN & BETWEEN INCLUSIVE
		 */
		if (value.length > 1 && (BETWEEN.equals(type) || BETWEEN_INCLUSIVE.equals(type))) {
			if (BETWEEN.equals(type) && objValue.doubleValue() > value[0].doubleValue()
					&& objValue.doubleValue() < value[1].doubleValue()) {
				return true;
			}

			if (BETWEEN_INCLUSIVE.equals(type) && objValue.doubleValue() >= value[0].doubleValue()
					&& objValue.doubleValue() <= value[1].doubleValue()) {
				return true;
			}
		} else if (value.length < 2 && (BETWEEN.equals(type) || BETWEEN_INCLUSIVE.equals(type))) {
			System.out.println("Not enough parameters to compare attribute [" + attName + "]");
			return false;
		}

		System.out.println("JXSRowFilterNumber - Cannot compare attribute [" + attName + "] / [" + type + "] / [" + value
				+ "]");
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}

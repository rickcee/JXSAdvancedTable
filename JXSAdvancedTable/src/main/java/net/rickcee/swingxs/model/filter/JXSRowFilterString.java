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

import static net.rickcee.swingxs.model.filter.JXSRowFilterConstants.EQUALS;
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
public class JXSRowFilterString extends RowFilter<JXSTableModel, Number> {

	protected BeanAttributeContainer container;
	protected String attName;
	protected Method getter;
	protected String[] value;
	protected Integer type;
	protected Boolean matchCase;
	protected Boolean matchWholeWord;

	/**
	 * Constructor
	 * 
	 * @param clazz
	 * @param attName
	 * @param type
	 * @param value
	 */
	public JXSRowFilterString(Class<?> clazz, String attName, Integer type, Boolean matchCase, Boolean matchWholeWord, String... value) {
		this.container = BeanManager.get(clazz);
		this.getter = container.getFieldGetters().get(attName);
		this.value = value;
		this.type = type;
		this.attName = attName;
		this.matchCase = matchCase;
		this.matchWholeWord = matchWholeWord;
	}

	@Override
	public boolean include(Entry<? extends JXSTableModel, ? extends Number> object) {
		String objValue = null;
		JXSTableModel model = object.getModel();
		Integer id = object.getIdentifier().intValue();
		try {
			objValue = (String) getter.invoke(model.getResult().get(id));
		} catch (ClassCastException e) {
			System.out.println("JXSRowFilterString - Cannot Cast [" + e.getMessage() + "]");
			return false;
		} catch (Exception e) {
			System.out.println("JXSRowFilterString - Exception trying to get value for attribute [" + attName + "]: " + e.getMessage());
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

		System.out.println("JXSRowFilterString - Cannot compare attribute [" + attName + "] / [" + type + "] / [" + value[0]
				+ "]");
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}

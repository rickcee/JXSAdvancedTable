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
package net.rickcee.swingxs.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.exceptions.JXSException;
import net.rickcee.swingxs.model.DateFormatConfig;
import net.rickcee.swingxs.model.JXSTableContants;
import net.rickcee.swingxs.model.NumberFormatConfig;
import net.rickcee.swingxs.utils.Constants;

/**
 * @author RickCeeNet
 * 
 */
public abstract class BeanConfigProcessor {
	private static final Logger logger = LoggerFactory.getLogger(BeanConfigProcessor.class);

	/**
	 * Utility method that is intended to get the list of attributes for the
	 * specified class. This method is recursive and will iterate over the
	 * specified class and it's super classes.
	 * 
	 * @param fields
	 *            The list that contains the current set of Field objects we're
	 *            processing.
	 * @param clazz
	 *            The class to process.
	 * @return The List of Field objects representing the attributes of the
	 *         specified class.
	 */
	public static List<Field> getAllFields(List<Field> fields, Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			fields.add(field);
		}
		if (clazz.getSuperclass() != null) {
			fields = getAllFields(fields, clazz.getSuperclass());
		}
		return fields;
	}

	/**
	 * Utility method to process the specified class and translate it's
	 * configuration to the framework's configuration schema.
	 * 
	 * @param clazz
	 *            The class to process.
	 * @throws Exception
	 *             In case of any error processing the class
	 */
	public static void processClass(Class<?> clazz) throws Exception {
		if (BeanManager.get(clazz) != null) {
			// Already configured
			return;
		}
		List<Field> fields = new ArrayList<Field>();
		fields = getAllFields(fields, clazz);
		BeanAttributeContainer container = new BeanAttributeContainer(clazz);

		Boolean processFieldPosition = Boolean.FALSE;
		if (container.getFieldPosition().isEmpty()) {
			processFieldPosition = Boolean.TRUE;
		}
		Integer position = 0;
		for (Field field : fields) {
			// If STATIC or FINAL, ignore
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
				continue;
			}
			String _field = field.getName();
			// System.out.println(_field);
			String getterName = Constants.getMethodName(_field, Constants.TYPE_GETTER);
			String setterName = Constants.getMethodName(_field, Constants.TYPE_SETTER);
			Method _getter = null, _setter = null;

			try {
				_getter = clazz.getMethod(getterName);
			} catch (SecurityException e) {
				logger.error(e.getMessage(), e);
				throw new JXSException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				logger.error(e.getMessage(), e);
				throw new JXSException("Method [" + getterName + "] does not exist!", e);
			}

			try {
				_setter = clazz.getMethod(setterName, field.getType());
			} catch (SecurityException e) {
				logger.error(e.getMessage(), e);
				throw new JXSException(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				logger.error(e.getMessage(), e);
				throw new JXSException("Method [" + setterName + "] does not exist!", e);
			}

			container.getFieldTypes().put(_field, field.getType());
			container.getFieldGetters().put(_field, _getter);
			container.getFieldSetters().put(_field, _setter);

			// Handle Field Names
			container.getFieldName().put(_field, _field);
			// Handle scenario where labels are missing
			if(container.getFieldLabel().get(_field)==null) {
				container.getFieldLabel().put(_field, _field);
				container.getFieldReverseLabel().put(_field, _field);
			}
			
			container.getFieldNumberFormat().put(_field, new NumberFormatConfig());
			container.getFieldDateFormat().put(_field, new DateFormatConfig());
			
			// Handle Default values for Width
			if (container.getFieldWidth().get(_field) == null) {
				container.getFieldWidth().put(_field, JXSTableContants.DEFAULT_COLUMN_WIDTH);
			}
			// Handle Default values for Visibility			
			if (container.getFieldVisibility().get(_field) == null) {
				container.getFieldVisibility().put(_field, JXSTableContants.DEFAULT_COLUMN_VISIBILITY);
			}
			// Handle Default values for Frozen column			
			if (container.getFieldFixed().get(_field) == null) {
				container.getFieldFixed().put(_field, Boolean.FALSE);
			}
			// Handle Default values for column format			
			if (container.getFieldFormat().get(_field) == null) {
				container.getFieldFormat().put(_field, null);
			}
			// Handle Default values for Position
			if (processFieldPosition) {
				container.getFieldPosition().put(position, _field);
			}
			position++;

		}
		BeanManager.getContainer().put(clazz, container);
	}

}

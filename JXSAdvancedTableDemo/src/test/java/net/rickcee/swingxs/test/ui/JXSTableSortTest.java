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
 * Created on Mar 16, 2012
 *
 */
package net.rickcee.swingxs.test.ui;

import org.uispec4j.Table;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.interception.MainClassAdapter;

import net.rickcee.swingxs.config.BeanConfigProcessor;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.demo.model.BOPerson;
import net.rickcee.swingxs.demo.util.TestSorter1PersonGenerator;
import net.rickcee.swingxs.model.JXSTableModel;
import net.rickcee.swingxs.ui.JXSTable;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableSortTest extends UISpecTestCase {
	protected static Class<?> clazz = BOPerson.class;
	protected static JXSTable table;
	protected static JXSTableModel model;
	protected static TestSorter1PersonGenerator gen;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.uispec4j.UISpecTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		//super.setUp();
		setAdapter(new MainClassAdapter(JXSTableSortTest.class));
	}

	/**
	 * Test the content of the table with DESCENDING sorting applied to column 1
	 * 
	 * @throws Exception
	 *             In case of any error
	 */
	public void testOrderAscending() throws Exception {
		// Simulate click on column 1 (ASCENDING)
		table.processMouseSorting(1);

		Table t = new Table(table);
		assertTrue(t.contentEquals(new String[][] {
				{ "4", "Ana Belen", "Massa Castillo", "24", "Tue Oct 08 00:00:00 EST 9", "86550.25" },
				{ "3", "Gaston", "Arcuri", "26", "Fri Dec 06 00:00:00 EST 15", "45000.5" },
				{ "1", "Ricardo", "Catalfo", "30", "Tue Feb 02 00:00:00 EST 12", "11000.0" },
				{ "2", "Ryan", "Lobner", "24", "Thu Oct 08 00:00:00 EST 11", "65000.99" } }));

	}

	/**
	 * Test the content of the table with ASCENDING sorting applied to column 1
	 * 
	 * @throws Exception
	 *             In case of any error
	 */
	public void testOrderDescending() throws Exception {
		// Simulate click on column 1 (ASCENDING)
		table.processMouseSorting(1);
		// Simulate another click on column 1 (DESCENDING)
		table.processMouseSorting(1);

		Table t = new Table(table);
		assertTrue(t.contentEquals(new String[][] {
				{ "2", "Ryan", "Lobner", "24", "Thu Oct 08 00:00:00 EST 11", "65000.99" },
				{ "1", "Ricardo", "Catalfo", "30", "Tue Feb 02 00:00:00 EST 12", "11000.0" },
				{ "3", "Gaston", "Arcuri", "26", "Fri Dec 06 00:00:00 EST 15", "45000.5" },
				{ "4", "Ana Belen", "Massa Castillo", "24", "Tue Oct 08 00:00:00 EST 9", "86550.25" } }));

	}

	/**
	 * Test the content of the table without any sorting applied
	 * 
	 * @throws Exception
	 *             In case of any error
	 */
	public void testOrderOriginal() throws Exception {
		Table t = new Table(table);
		assertTrue(t.contentEquals(new String[][] {
				{ "1", "Ricardo", "Catalfo", "30", "Tue Feb 02 00:00:00 EST 12", "11000.0" },
				{ "2", "Ryan", "Lobner", "24", "Thu Oct 08 00:00:00 EST 11", "65000.99" },
				{ "3", "Gaston", "Arcuri", "26", "Fri Dec 06 00:00:00 EST 15", "45000.5" },
				{ "4", "Ana Belen", "Massa Castillo", "24", "Tue Oct 08 00:00:00 EST 9", "86550.25" } }));

	}
	
	public static void main(String args[]) throws Exception {
		
		BeanConfigProcessor.processClass(clazz);

		table = new JXSTable(clazz);
		model = new JXSTableModel(clazz, table);

		BeanManager.getTables().put(clazz, table);
		// BeanManager.getFrozenTables().put(clazz, tableFrozen);

		gen = new TestSorter1PersonGenerator();
		model.getResult().addAll(gen.getListOfPersons());

		table.setModel(model);
		table.setSortable(true);

	}

}

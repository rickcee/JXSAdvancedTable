package net.rickcee.swingxs.demo.util;

import net.rickcee.swingxs.demo.model.AbstractPersonGenerator;

/**
 * Created on Feb 21, 2012
 */

/**
 * @author RickCeeNet
 * 
 */
public class TestSorter1PersonGenerator extends AbstractPersonGenerator {

	private static Long[] ids = {1L, 2L, 3L, 4L};
	private static String[] names = { "Ricardo", "Ryan", "Gaston", "Ana Belen" };
	private static String[] surnames = { "Catalfo", "Lobner", "Arcuri", "Massa Castillo" };
	private static Integer[] ages = { 30, 24, 26, 24 };
	private static Integer[][] birthDates = { { 06, 20, 1981 }, { 05, 28, 1987 }, { 11, 06, 1985 }, { 03, 28, 1987 } };
	private static Double[] incomes = {11000.00, 65000.99, 45000.50, 86550.25};

	/**
	 * Constructor
	 *
	 */
	public TestSorter1PersonGenerator() {
		super();
		super.ids = ids;
		super.names = names;
		super.surnames = surnames;
		super.ages = ages;
		super.birthDates = birthDates;
		super.incomes = incomes;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestSorter1PersonGenerator gen = new TestSorter1PersonGenerator();
		System.out.println(gen.getListOfPersons());
	}

}

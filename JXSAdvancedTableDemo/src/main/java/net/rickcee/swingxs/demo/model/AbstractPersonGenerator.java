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
 * Created on Mar 11, 2012
 *
 */
package net.rickcee.swingxs.demo.model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author RickCeeNet
 * 
 */
public abstract class AbstractPersonGenerator {
	protected Long[] ids;
	protected String[] names;
	protected String[] surnames;
	protected Integer[] ages;
	protected Integer[][] birthDates;
	protected Double[] incomes;

	public BOPerson buildPerson(Integer index) {
		BOPerson person = new BOPerson();
		person.setId(ids[index]);
		person.setAge(ages[index]);
		person.setName(names[index]);
		person.setSurname(surnames[index]);
		person.setBirthDate(new GregorianCalendar(birthDates[index][0] - 1, birthDates[index][1], birthDates[index][2])
				.getTime());
		person.setAnnualIncome(incomes[index]);
		return person;
	}

	public List<BOPerson> getListOfPersons() {
		List<BOPerson> output = new ArrayList<BOPerson>();
		for (int i = 0; i < names.length; i++) {
			output.add(buildPerson(i));
		}
		return output;
	}
}

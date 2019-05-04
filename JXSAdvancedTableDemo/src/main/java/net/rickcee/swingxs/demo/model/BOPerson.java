package net.rickcee.swingxs.demo.model;

import java.util.Date;

/**
 * Created on Feb 21, 2012
 */

/**
 * @author RickCeeNet
 * 
 */
public class BOPerson {
	private Long id;
	private String name;
	private String surname;
	private Integer age;
	private Date birthDate;
	private Double annualIncome;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "ID: " + id + " / Name: " + name + " / Surname: " + surname + " / Age: " + age + " / BirthDate: "
				+ birthDate + " / Annual Income: " + annualIncome;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the annualIncome
	 */
	public Double getAnnualIncome() {
		return annualIncome;
	}

	/**
	 * @param annualIncome
	 *            the annualIncome to set
	 */
	public void setAnnualIncome(Double annualIncome) {
		this.annualIncome = annualIncome;
	}

}

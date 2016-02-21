package md.utm.simple_jdbc;

import java.util.Date;

public class Person {
	private String name;
	private String surname;
	private Date yearOfBirth;
	private boolean student;

	public Person() {

	}

	public Person(String name, String surname, Date date, boolean isStudent) {
		super();
		this.name = name;
		this.surname = surname;
		this.yearOfBirth = date;
		this.student = isStudent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(Date yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public boolean isStudent() {
		return student;
	}

	public void setStudent(boolean isStudent) {
		this.student = isStudent;
	}

}

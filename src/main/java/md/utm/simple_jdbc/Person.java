package md.utm.simple_jdbc;

public class Person {
	private String name;
	private String surname;
	private int yearOfBirth;
	private boolean student;

	public Person() {

	}

	public Person(String name, String surname, int yearOfBirth,
			boolean isStudent) {
		super();
		this.name = name;
		this.surname = surname;
		this.yearOfBirth = yearOfBirth;
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

	public int getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public boolean isStudent() {
		return student;
	}

	public void setStudent(boolean isStudent) {
		this.student = isStudent;
	}

}
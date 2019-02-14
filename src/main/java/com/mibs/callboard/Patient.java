package com.mibs.callboard;

public class Patient  implements java.io.Serializable {

	private static final long serialVersionUID = 2L;
	private String name;
	private int age;
	public Patient(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	@Override
	public String toString() {
		return "Patient [name=" + name + ", age=" + age + "]";
	}
	
}

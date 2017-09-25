/**
 * @author Ciara Power
 */
package familyTrees_CPower;

import java.util.ArrayList;

public class Person {

	private String name;
	private String gender;
	private String dob;
	private Person mother;
	private Person father;
	private Person spouse;
	private ArrayList<Person> children;
	
	public Person(String name, String gender, String dob, Person mother, Person father,Person spouse) {
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		this.mother = mother;
		this.father = father;
		this.spouse=spouse;
		children=new ArrayList<Person>();   //empty when person is created
		
	}
	
	/*
	 * Getters and Setters
	 */
	public Person getSpouse() {
		return spouse;
	}

	public void setSpouse(Person spouse) {
		this.spouse = spouse;
	}

	public ArrayList<Person> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Person> children) {
		this.children = children;
	}

	public Person getMother() {
		return mother;
	}
	
	public void setMother(Person mother) {
		this.mother = mother;
	}
	
	public Person getFather() {
		return father;
	}
	
	public void setFather(Person father) {
		this.father = father;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		if(gender.equals("M") || gender.equals("F")){  //must be F or M
		this.gender = gender;
		}
	}
	
	public String getDob() {
		return dob;
	}
	
	public void setDob(String dob) {
		if(dob.trim().length()==4){  // year so must be 4 in length  e.g 1999   (not including any spaces)
		this.dob = dob.trim();
		}
	}
	
	
	@Override
	public String toString() {
		if(mother!=null && father!=null) return "Person [name=" + name + ", gender=" + gender + ", dob=" + dob + ", mother=" + mother.getName() + ", father="
				+ father.getName() + "]";  // if mother and father exist
		else if(mother!=null && father==null) return "Person [name=" + name + ", gender=" + gender + ", dob=" + dob + ", mother=" + mother.getName() + ", father=?]"; //only mother
		else if(mother==null && father!=null) return "Person [name=" + name + ", gender=" + gender + ", dob=" + dob + ", mother=?, father="+ father.getName() + "]";	//only father
	else {
		return "Person [name=" + name + ", gender=" + gender + ", dob=" + dob + ", mother=?, father=?]";  //orphan 
	}
}
	/*
	 * Method to add child to list of children
	 */
	public void addChild(Person child){
		if(child!=null)  //cant add null child
		children.add(child);
	}
}

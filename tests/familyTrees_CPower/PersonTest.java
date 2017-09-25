/**
 * @author Ciara Power
 */
package familyTrees_CPower;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testConstructor() {
		Person person=new Person("John","M","1999",null,null,null);
		
		assertEquals("John",person.getName());
		assertEquals("M",person.getGender());
		assertEquals("1999",person.getDob());
		assertEquals(null,person.getFather());
		assertEquals(null,person.getMother());
		assertEquals(0,person.getChildren().size());
	}
	
	@Test
	public void testSettersGetters() {
     Person person=new Person("John","M","1999",null,null,null);
		
		assertEquals("John",person.getName());
		person.setName("JohnNew");
		assertEquals("JohnNew",person.getName());
		
		assertEquals("M",person.getGender());
		person.setGender("F");
		assertEquals("F",person.getGender());
		person.setGender("Invalid");  //should not change gender
		assertEquals("F",person.getGender());
		
		assertEquals("1999",person.getDob());
		person.setDob("2000");
		assertEquals("2000",person.getDob());
		person.setDob("6545678765");  //should not change dob
		assertEquals("2000",person.getDob());
		
		assertEquals(null,person.getFather());
		Person father=new Person("Father","M","1999",null,null,null);
		person.setFather(father);
		assertEquals(father,person.getFather());
		
		assertEquals(null,person.getMother());
		Person mother=new Person("Mother","F","1999",null,null,null);
		person.setMother(mother);
		assertEquals(mother,person.getMother());
		
		assertEquals(0,person.getChildren().size());
		ArrayList<Person> children=new ArrayList<Person>();
		children.add(new Person("Child","M","2002",null,null,null));
		person.setChildren(children);
		assertEquals(1,person.getChildren().size());
	}
	
	@Test 
	public void testAddChild(){
		  Person person=new Person("John","M","1999",null,null,null);
		  assertEquals(0,person.getChildren().size());
		  Person child=new Person("Child","M","2002",null,null,null);
		 
		  person.addChild(child);   //valid
		  assertEquals(1,person.getChildren().size());
		  assertEquals(child,person.getChildren().get(0));
		  
		  person.addChild(null);   //invalid
		  assertEquals(1,person.getChildren().size());
		  assertEquals(child,person.getChildren().get(0));
		  
	}
	
	@Test
	public void testToString(){
		 Person noParents=new Person("John","M","1999",null,null,null);
		 Person justMother=new Person("John","M","1999",new Person("Mother","F","1000",null,null,null),null,null);
		 Person justFather=new Person("John","M","1999",null,new Person("Father","M","1000",null,null,null),null);
		 Person bothParents=new Person("John","M","1999",new Person("Mother","F","1000",null,null,null),new Person("Father","M","1000",null,null,null),null);
		 
		 assertEquals("Person [name=John, gender=M, dob=1999, mother=?, father=?]",noParents.toString());
		 assertEquals("Person [name=John, gender=M, dob=1999, mother=Mother, father=?]",justMother.toString());
		 assertEquals("Person [name=John, gender=M, dob=1999, mother=?, father=Father]",justFather.toString());
		 assertEquals("Person [name=John, gender=M, dob=1999, mother=Mother, father=Father]",bothParents.toString());
		 }

}

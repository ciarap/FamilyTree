/**
 * @author Ciara Power
 */
package familyTrees_CPower;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserInterfaceTest {

	FamilyTree tree=new FamilyTree();
	UserInterface inter=new UserInterface(tree);
	FileReader reader=new FileReader();
	
	@Test
	public void testDeletePerson() throws Exception {
		tree.getFamilyHeads().clear(); 
		reader.readFile("small.txt");
		assertEquals(2,reader.getTree().getFamilyHeads().size());  // family trees stored from oldest ancestor root
		
		assertEquals("Allyssa",tree.searchForPerson("Allyssa").getName());  // exists in trees
		assertEquals("Sheldon",tree.searchForPerson("Allyssa").getChildren().get(0).getName());  // Has children
		assertEquals("Sheldon",tree.searchForPerson("Sheldon").getName()); //child exists in tree
		assertEquals(0,tree.searchForPerson("Sheldon").getChildren().size());   // no children
		inter.deletePerson("Sheldon");  //delete child with no children
		assertEquals(0,tree.searchForPerson("Allyssa").getChildren().size());  // mother has no children now
		assertNull(tree.searchForPerson("Sheldon"));  // null because deleted from trees
		
		tree.getFamilyHeads().clear(); 
		reader.readFile("small.txt");  //repopulate data
		assertEquals("Sheldon",tree.searchForPerson("Allyssa").getChildren().get(0).getName()); // Allyssa has child Sheldon
		assertEquals("Allyssa",tree.searchForPerson("Dillan").getSpouse().getName());  // she has spouse dillan
		assertEquals("Sheldon",tree.searchForPerson("Sheldon").getName());  //sheldon found in tree
		inter.deletePerson("Allyssa");   // delete the mother
		assertNull(tree.searchForPerson("Allyssa"));  // she is no longer in trees
		assertNull(tree.searchForPerson("Dillan").getSpouse());  // dillans spouse is now null 
		assertEquals("Sheldon",tree.searchForPerson("Dillan").getChildren().get(0).getName()); // dillan still has the child allyssa had
		
		
		tree.getFamilyHeads().clear(); 
		reader.readFile("small.txt");  //repopulate data
		assertEquals(2,reader.getTree().getFamilyHeads().size());
		assertEquals("Emmett",tree.searchForPerson("Emmett").getName()); // Emmett exists
		assertEquals("Allyssa",tree.searchForPerson("Emmett").getChildren().get(0).getName()); //emmett has child allyssa
		assertTrue(tree.getFamilyHeads().contains(tree.searchForPerson("Emmett")));  //Emmett is a family root
		assertNull(tree.searchForPerson("Emmett").getMother());  //emmett is root
		assertNull(tree.searchForPerson("Emmett").getFather());
		inter.deletePerson("Emmett");   // delete the family head
		assertNull(tree.searchForPerson("Emmett"));  // he is no longer in trees
		assertEquals("Allyssa",tree.searchForPerson("Allyssa").getName());  // child is still longer in trees because included in spouse dillan's family tree
		assertEquals(1,reader.getTree().getFamilyHeads().size());
		
		
	}
	
	@Test
	public void testGetDetailsAndAppendDetails() throws Exception{
		tree.getFamilyHeads().clear(); 
		reader.readFile("small.txt");  // populate data
		inter.getDetails("Allyssa");
		assertEquals("~~~  DETAILS  ~~~\nName: Allyssa\nGender: F\nDOB: 1931"
				+ "\nSpouse: Dillan\nMother: ?\nFather: Emmett\nChildren: \n   1 : Sheldon\n",inter.getVisualDisplay().getText());
	}

}

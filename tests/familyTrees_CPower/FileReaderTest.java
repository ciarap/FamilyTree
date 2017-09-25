/**
 *@author Ciara Power
 */
 package familyTrees_CPower;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class FileReaderTest {

	FileReader reader;
	
	@After
	public void teardown(){
		reader.getTree().getFamilyHeads().clear();
	}
	
	@Test
	public void testRead() throws Exception {
		reader=new FileReader();
		assertEquals(0,reader.getTree().getFamilyHeads().size());
		
		reader.readFile("small.txt");
		
		assertEquals(2,reader.getTree().getFamilyHeads().size());  // family trees stored from oldest ancestor root
		
		//check connections made
		assertEquals("Allyssa",reader.getChild().getName());  // last child read in name
		assertEquals("Sheldon",reader.getChild().getChildren().get(0).getName());  // last person read in's child
		assertNull(reader.getChild().getMother()); // last child read in null mother because ? in file
		assertEquals("Emmett",reader.getChild().getFather().getName()); // last child's father name
		assertEquals("Dillan",reader.getChild().getSpouse().getName());  // spouse name
		assertEquals(reader.getChild().getChildren().get(0),reader.getChild().getSpouse().getChildren().get(0)); // spouse children must match persons children
		assertEquals("Tyshawn",reader.getChild().getSpouse().getFather().getName()); // spouse's father name
	}
	
	@Test
	public void testcheckTokens() throws Exception  {
		reader=new FileReader();
		
		reader.checkTokens("Jo","?","?");  // all should be null, parents not found in tree and also ? so not created
		assertNull(reader.getChild());
		assertNull(reader.getMother());
		assertNull(reader.getFather());
		assertEquals(0,reader.getTree().getFamilyHeads().size()); //noone addded yet
		
		reader.checkTokens("Jo","Mom","Dad");  // new parents created 
		assertNull(reader.getChild());
		assertEquals("Mom",reader.getMother().getName());
		assertEquals("Dad",reader.getFather().getName());
		assertEquals(1,reader.getTree().getFamilyHeads().size()); //mother of family added as root
		
		reader.readFile("small.txt");
		assertEquals(3,reader.getTree().getFamilyHeads().size());  // family trees stored from oldest ancestor root
		
		reader.checkTokens("Jo","Allyssa","Dillan");  // parents exist already 
		assertNull(reader.getChild());
		assertEquals("Allyssa",reader.getMother().getName());
		assertEquals("Dillan",reader.getFather().getName());
		assertEquals(3,reader.getTree().getFamilyHeads().size()); //no addittions here as mother is already in tree, no need to add root to family
		
	}

}

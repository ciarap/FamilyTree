/**
 * @author Ciara Power
 */
package familyTrees_CPower;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Test;

public class FamilyTreeTest {

	
	FamilyTree tree=new FamilyTree();
	FileReader reader=new FileReader();
	File testFile=null;
	
	@After
	public void teardown(){
		if(testFile!=null){
			if(testFile.exists()) 
				testFile.delete();
		}
		tree.getFamilyHeads().clear();
	}
	
	
	@Test
	public void test() throws Exception {
		reader.readFile("small.txt");
		
		// test display of tree with correct indents
		assertEquals("     ~~~~~~~    FAMILY NUMBER: 1    ~~~~~~~\n? -- ?\n Tyshawn -- ?"+
						    "\n    Dillan -- Allyssa\n       Sheldon -- ?\n     ~~~~~~~    FAMILY NUMBER: 2    ~~~~~~~\n"+
						"? -- ?\n Emmett -- ?\n    Allyssa -- Dillan\n       Sheldon -- ?\n",tree.displayTrees());

	}
	
	@Test 
	public void testWrite() throws Exception{
		testFile=new File("test.xml");
		tree.setFamilyTreeXML(testFile);
		tree.getSerializer().setFile(testFile);  //test file 
		
		assertFalse(testFile.exists());  //doesnt exist
		tree.write();
		assertTrue(testFile.exists());  //exists
	}
	
	@Test 
	public void testLoad() throws Exception{
		testFile=new File("test.xml");
		tree.setFamilyTreeXML(testFile);
		tree.getSerializer().setFile(testFile);
		
		assertFalse(testFile.exists()); //doesnt exist
		assertEquals(0,tree.getFamilyHeads().size()); //empty forest
		
		tree.load("small.txt"); //reads txt file - no xml
		assertEquals(2,tree.getFamilyHeads().size()); //populated forest
		tree.getFamilyHeads().remove(1); //remove an entry
		assertEquals(1,tree.getFamilyHeads().size()); //one less
		tree.write(); // writes details
		assertTrue(testFile.exists()); //exists 
		
		tree.getFamilyHeads().clear(); //reset any forests stored
		tree.load("small.txt"); //read from xml 
		assertEquals(1,tree.getFamilyHeads().size()); //one less - xml modified forest loaded
	}

}

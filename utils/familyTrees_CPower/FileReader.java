/**
 * @author Ciara Power
 */
package familyTrees_CPower;

import java.io.File;
import java.util.Scanner;

public class FileReader {

	private boolean debug=true;
    private FamilyTree tree;
	private Person mother=null;
	private Person father=null;
	private Person child=null;
    
	/*
	 * Constructor
	 */
	public FileReader(){
		tree=new FamilyTree();
	}
	
	/*
	 * Getters and Setters
	 */
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


	public Person getChild() {
		return child;
	}


	public void setChild(Person child) {
		this.child = child;
	}

	public FamilyTree getTree() {
		return tree;
	}

	/*
	 *   method to read the txt file of dictionary words, takes filename in as parameter
	 */
	public  void readFile(String fileName) throws Exception{ 
		int peopleCount=0;  // variable used to count how many people were read in for debug purposes
		File in=new File(fileName);
		Scanner inTerm = new Scanner(in);
		String delims = "[ ]";//each field in the file is separated(delimited) by a space.
		while (inTerm.hasNextLine()) {     //while the txt file has not reached an end
			mother=null;
			father=null;
			String termDetails = inTerm.nextLine();
			// parse term details string
			String[] termTokens = termDetails.split(delims);// split the line of data into a String[] by the divider
			if (termTokens.length == 5) {     // each token should have 5 tokens
			if (debug){
				System.out.println("Name: "+termTokens[0]+", Gender: "+termTokens[1]+", DOB: "+termTokens[2]+", Mother: "+termTokens[3]+", Father: "+termTokens[4]);
			}
			checkTokens(termTokens[0],termTokens[3],termTokens[4]);  // calls method which deals with if the person,mother or father are already in forest of trees
			if(child==null){  //child wasnt already in forest
			if(debug)System.out.println("This child does not exist already");
			child= new Person(termTokens[0],termTokens[1],termTokens[2],mother,father,null);  //create the new child with the mother/father created/found from checkTokens method
			if(debug)System.out.println("Child Created");
			if(mother==null && father==null) {   // child has no parents so must be family head 
				tree.addFamilyHead(child);
			}
			}
			else{
				if(debug)System.out.println(child.getName()+" : "+child.getFather()+" : "+child.getMother()+"\nFound Child already exists: "+child.getName());
				child.setMother(mother);  //set the child to have correct mother
				child.setFather(father);   //set child to have correct father
				if(debug)System.out.println(child.getName()+" : "+child.getFather()+" : "+child.getMother());
				child.setDob(termTokens[2]);   //set the dob 
				if(tree.getFamilyHeads().contains(child) && (child.getMother()!=null || child.getFather()!=null))  { // if the person read in is currently a family head, but they have mother or father
					tree.getFamilyHeads().remove(child);    // the mother or father will act as a family head (added elsewhere) so remove child
					if(debug) System.out.println("Remove - new family head");
				}
			}
			if(mother!=null){   // has mother
				mother.setSpouse(father);    //create  marriage
				mother.addChild(child);   //add child 
				if(debug){
					for(Person c:mother.getChildren()){   // print each child to see if child added 
					System.out.println("Mother: "+mother.getName()+",Spouse: "+mother.getSpouse()+",Child: "+c.toString());
				}
				}
			}
			if(father!=null){ //has father
				father.setSpouse(mother);  //create marriage
				father.addChild(child);  //add child
				if(debug){
					for(Person c:father.getChildren()){ // print each child to see if child added 
					System.out.println("Father: "+father.getName()+",Spouse: "+father.getSpouse()+",Child: "+c.toString());
				    }
				}
			}
			peopleCount++;  // increment as person added
			}
		}
		if (debug){
			System.out.println("---------\n People read in: "+peopleCount+"\n---------");
		}
		inTerm.close();
	}

	/*
	 *  Method to check the person name and parents names for the person read in 
	 */
	public void checkTokens(String termTokens0,String termTokens3,String termTokens4) {
		mother=null;   //each start off null
		father=null;
		child=null;
		child=tree.searchForPerson(termTokens0);   //will return person if found
		mother=tree.searchForPerson(termTokens3);
		father=tree.searchForPerson(termTokens4);
		if(mother!=null){  // mother found
			if(debug) System.out.println(mother.getName()+" Found");
		}
		else if(!termTokens3.equals("?")){ // mother is null so not found , and the name passed though is not ? so must create mother
			mother=new Person(termTokens3,"F",null,null,null,null);  //create the new mother
			tree.addFamilyHead(mother); // add as familyHead
			if(debug) System.out.println("Mother: "+mother.getName()+" new mother");   
		}
		if(father!=null) {   // father was found 
			if(debug) System.out.println(father.getName()+" Found");

		}
		else if(!termTokens4.equals("?")){   // father is null so not found , but not ? read in so must create father
			father=new Person(termTokens4,"M",null,null,null,null);  //create father
			if(mother==null){   // if mother is null , the father must act as family head (because no mother was added as family head above)
				tree.addFamilyHead(father);
				if(debug)System.out.println("Father: "+father.getName()+" new parent");
			}
		}
	}
}

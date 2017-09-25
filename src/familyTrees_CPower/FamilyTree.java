/**
 * @author Ciara Power
 */
package familyTrees_CPower;

import java.io.File;
import java.util.ArrayList;

public class FamilyTree {

	private static FileReader fileRead=new FileReader();  // fileReader object used to read the txt file 
	private static ArrayList<Person> familyHeads=new ArrayList<Person>();  // the head of each family is stored here 
    private Person personFound;  // used for searching for a person in trees
    private static UserInterface userInt;
	private static FamilyTree tree=new FamilyTree();    
	private static boolean debug=false;
	private static BinaryTreeIndentPrint printer=new BinaryTreeIndentPrint(); // object used to print trees
	private  File  familyTreeXML = new File("familyTree.xml");   // xml file for saved trees
	private   Serializer serializer = new XMLSerializer(familyTreeXML);   //to load and save xml 

	/*
	 * Getters and Setters for some global variables
	 */
	public  File getFamilyTreeXML() {
		return familyTreeXML;
	}

	public void setFamilyTreeXML(File familyTreeXML) {
		this.familyTreeXML = familyTreeXML;
	}

	public Serializer getSerializer() {
		return serializer;
	}

	public  void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}
	
	public ArrayList<Person> getFamilyHeads() {
		return familyHeads;
	}
	

	/*
	 * Main method to run, sets up the user interface and loads data
	 */
	public static void main(String[] args) throws Exception {
		tree.load("large-database.txt");    // load this text file of data
		userInt=new UserInterface(tree);
		if(debug)System.out.println("Parent Families: "+familyHeads.size());
		
	}
		
	/*
	 * Method to load data from xml if it exists, in other words a txt file
	 */
	public  void load (String fileName) throws Exception{    //method to load from txt or xml file
		if (debug){
        	System.out.println("---------\n LOAD \n---------");
        }
		if (familyTreeXML.isFile())   // if xml file exists
		{
			serializer.read();   //read xml file
			familyHeads=(ArrayList<Person>) serializer.pop();   //pop the list stored in xml to global list variable
			 if (debug){
		        	System.out.println("---------\n XML File Loaded \n---------");
		        }
		}
		else{
			fileRead.readFile(fileName);  // if no xml file, read the txt file 
			 if (debug){
		        	System.out.println("---------\n TXT File Loaded \n---------");
		       }
		}
		
	}
	
	/*
	 * writes the arraylist of family roots to an xml file
	 */
	public  void write() throws Exception {    // saves forest to xml
		if (debug){
        	System.out.println("---------\n WRITE \n---------");
        }
		serializer.push(familyHeads);
		serializer.write(); 

	}
	
	/*
	 * Method to display the forest of trees with indents for generation gaps
	 */
	public String displayTrees() {
		int num=1; // for visual aid  - numbers families
		String display="";    // starts as empty string
		for(Person p:familyHeads){
			if(debug)System.out.println(p.getName());  
			display+="     ~~~~~~~    FAMILY NUMBER: "+num+"    ~~~~~~~\n";   // each family tree has this division between them
			display+="? -- ?\n";   // all family heads are the root, so we know they do not have parents accounted for, if they did they wouldnt be a family head
			printer.printPreOrder(p," ");  // calls printer pre order for each family head person 
			display+=printer.getDisplay();  // gets the printer's display that was just created with one family tree string
			printer.setDisplay("");  // reset the printers display for next family
			num++;  //increase number for next family 
			}
		return display;    //return the full display string for all families together
	}

	/*
	 * Add a family head to the list (acts as root for that family tree)
	 */
	public void addFamilyHead(Person familyHead) {
		if(debug)System.out.println("Family Head added: "+familyHead.getName());
		familyHeads.add(familyHead);
		
	}

	/*
	 * Search for a certain name in the forest of family trees, returns the person , or null if the person does not exist 
	 */
	public Person searchForPerson(String parentChoice) {
		personFound=null;  // start with none found
		for(Person p:familyHeads){   //go through each root of family
			if(personFound==null){   // if person hasnt been found yet
				if(debug)System.out.println("Search to familyHead: "+p.getName()+", Children: "+p.getChildren());
				searchThroughFamily(p,parentChoice);   // search through root p's family tree
			}
		}
		return personFound;
	}
	
	/*
	 * Method which searches down through one family tree starting from the family head passed as parameter
	 */
	public Person searchThroughFamily(Person parent,String parentChoice){
		if (parent.getName().equals(parentChoice))   // if the name searched for is found 
		{
			if(debug)System.out.println("Person Found : PERSON : "+parent.getName());
			personFound=parent;   // set the personFound variable to that person 
		}
		else if (parent.getSpouse()!=null){  // if the current person has a spouse (spouse may be the name we searched for)
			if(parent.getSpouse().getName().equals(parentChoice)){   // same name
				if(debug)System.out.println("Person Found : SPOUSE : "+parent.getSpouse().getName());
				personFound=parent.getSpouse();   //set personFound to be the spouse of current person
			}
		}
		if (!parent.getChildren().isEmpty()){  // if the current has children search them ( may be searching for a person that is a great great grandchild)
			for(Person child:parent.getChildren()){  // go through each child
				if(personFound==null){  // if person hasnt been found yet
			    if(debug)System.out.println("Search to child: "+child.getName());
				searchThroughFamily(child,parentChoice);  // take the search down a generation and start at the child node next in a recursive manner
				}
			}
		}
		return personFound;  //return person found (may be null if person doesnt exist)
	}
}

/**
 * @author Ciara Power
 */
package familyTrees_CPower;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;


/**
 * A graphical user interface for the translator. No translation is being
 * done here. This class is responsible just for putting up the display on 
 * screen. It then refers to the "Dictionary" to do all the real work.
 * 
 * @author Ciara Power
 */
public class UserInterface
implements ActionListener    // interface which listens for key presses by user
{
	private FamilyTree tree;

	private JFrame frame;       // frame of window
	private JTextArea visualDisplay;  // the text area for all text display
	private boolean debug=true;


	/**
	 * Create a user interface for a given family tree program.
	 */
	public UserInterface(FamilyTree tree)
	{
		this.tree =tree;
		makeFrame();    //call to method which creates the window
		frame.setVisible(true);    //display window created

	}
	/**
	 * Make this interface visible again. (Has no effect if it is already
	 * visible.)
	 */
	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}

	/*
	 * Getters and Setters
	 */
	public JTextArea getVisualDisplay() {
		return visualDisplay;
	}
	public void setVisualDisplay(JTextArea visualDisplay) {
		this.visualDisplay = visualDisplay;
	}
	
	/**
	 * Make the frame for the user interface.
	 */
	private void makeFrame()
	{
		frame = new JFrame("Family Tree");    // initialise frame 

		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(800, 750));    //set sizes
		contentPane.setLayout(new BorderLayout(10, 10));
		contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));
		contentPane.setBackground(Color.WHITE);   // white background behind button panel 

		Font font1 = new Font("Comic Sans MS", Font.BOLD, 24);    // font for titles 
		Font font2 = new Font("Comic Sans MS", Font.PLAIN, 20);    // font for display text

		//PANEL 1
		JPanel panel1 = new JPanel();    // display panel
		Border blackline = BorderFactory.createLineBorder(Color.black);   // black border for panel
		TitledBorder title = BorderFactory.createTitledBorder(blackline, "FAMILY TREE DISPLAY"); //titled border created  with the black line border
		title.setTitleFont(font1);  //border title font set 
		title.setTitleJustification(TitledBorder.CENTER);  //title in center of border
		panel1.setBorder(title);  //set this border on the panel

		visualDisplay = new JTextArea();    // text area for text display
		visualDisplay.setEditable(false);  //cannot type here
		visualDisplay.setFont(font2);
		JScrollPane scrollPane=new JScrollPane(visualDisplay);  //add scroll function to area if text is long
		scrollPane.setPreferredSize( new Dimension( 750, 400 ) );   // text field size
		panel1.add(scrollPane);
		contentPane.add(panel1, BorderLayout.NORTH);

		//PANEL 2
		JPanel panel2 = new JPanel(new GridLayout(2,3));    // button options for family tree displays
		TitledBorder title2 = BorderFactory.createTitledBorder(blackline, "FAMILY TREE OPTIONS"); //titled border created  with the black line border
		title2.setTitleFont(font1);  //border title font set 
		title2.setTitleJustification(TitledBorder.CENTER);  //title in center of border
		panel2.setBorder(title2);  //set this border on the translation panel

		///P2 ROW 1 COL 1
		addButton(panel2,"Display Trees");

		///P2 ROW 1 COL 2
		addButton(panel2,"Add Child");
		
		//P2 ROW 1 COL 3
		addButton(panel2,"Add Spouse");

		///P2 ROW 2 COL 1
		addButton(panel2,"Get Details");

		///P2 ROW 2 COL 2
		addButton(panel2,"Modify Details");

		///P2 ROW 2 COL 3
		addButton(panel2,"Delete Person");

		contentPane.add(panel2, BorderLayout.CENTER);

		//PANEL 3
		JPanel panel3 = new JPanel(new GridLayout(1,3));    // program options panel
		TitledBorder title3 = BorderFactory.createTitledBorder(blackline, "PROGRAM OPTIONS"); //titled border created  with the black line border
		title3.setTitleFont(font1);  //border title font set 
		title3.setTitleJustification(TitledBorder.CENTER);  //title in center of border
		panel3.setBorder(title3);  //set this border on the panel
		
		///P3 R1 C1
		addButton(panel3,"Reload");
		
		///P3 R1 C2
		addButton(panel3,"Save");
		
		///P3 R1 C3
		addButton(panel3,"Exit");
		
		contentPane.add(panel3, BorderLayout.SOUTH);
		
		frame.pack();

	}

	/**
	 * Add a button to the button panel.
	 */
	private void addButton(Container panel, String buttonText)
	{
		JButton button = new JButton(buttonText);
		button.addActionListener(this);
		button.setBackground(new Color(214,135,253));    //color set to a purple tone
		button.setFont(new Font("Comic Sans MS", Font.BOLD, 26));   //font changed to comic sans ms , and size changed
		button.setForeground(Color.WHITE);  // the text appearing on each button set to white
		panel.add(button);
	}

	/**
	 * An interface action has been performed. Find out what it was and
	 * handle it.
	 */
	public void actionPerformed(ActionEvent event) 
	{
		String command = event.getActionCommand();
		
		if(command=="Display Trees"){
			visualDisplay.setText(tree.displayTrees());   //set the display to show trees
		}
		
		if(command=="Save"){
			try {
				tree.write();  //save to xml
				visualDisplay.setText("~~~  SAVED  ~~~");  // display shows saved 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(command=="Reload"){  // reload from xml if exists, or txt (discards unsaved changes)
			try {
				tree.load("large-database.txt");
				visualDisplay.setText("~~~  RELOADED  ~~~");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(command=="Exit"){
			System.exit(0);  //exits whole program
		}
		
		if(command=="Add Child"){
		 addChild();
		}
		
		if(command=="Get Details"){
			String personDetails= JOptionPane.showInputDialog(frame,"Please enter the name of the person to get details of: ");
			getDetails(personDetails);
		}
		
		if(command=="Modify Details"){
			modifyDetails();
		}
		
		if(command=="Add Spouse"){
			addSpouse();
		}
		
		if(command=="Delete Person"){
			deletePerson(JOptionPane.showInputDialog(frame,"Please enter the person to delete:"));
		}
		
}
	
	/*
	 * Method to delete a person with name as parameter
	 */
	public void deletePerson(String personChoice) {
		Person person=tree.searchForPerson(personChoice);  //search for the person
		if(person==null){   // no such person
			JOptionPane.showMessageDialog(frame, "This person does not exist!", "Error in Deletion", 0);
		}
		else{
			Person mother=person.getMother();  
			Person father=person.getFather();
			Person spouse=person.getSpouse();
			if(mother==null && father==null){   // person is a family head 
				if (debug) System.out.println("FAMILY HEAD DELETION: "+person.getName());
				tree.getFamilyHeads().remove(person);  //remove from family head list
			}
			if(mother!=null){  // if person has a mother
				if (debug) System.out.println("Person's Mother: "+mother.getName());
				mother.getChildren().remove(person);  //remove person from mothers children
			}
			if(father!=null){  //if person has father
				if (debug) System.out.println("Person's Father: "+father.getName());
				father.getChildren().remove(person);  //remove person from fathers children
			}
			if(spouse!=null){  // if person has spouse
				if (debug) System.out.println("Person's Spouse: "+spouse.getName());
				spouse.setSpouse(null);  // set the spouse to be null
			}
			if(!person.getChildren().isEmpty()){ // if person has children
				for(Person child:person.getChildren()){
					if(person.getGender().equals("M")){  // if person is a Male, set the childs father to null
						child.setFather(null);
					}
					else if(person.getGender().equals("F")){ // if person is a female, set the childs mother to null
						child.setMother(null);
					}
				}
			}
			visualDisplay.setText("~~~  DELETION  ~~~\nPerson Deleted: "+person.getName());   //update display to show deletion
		}	
	}
	
	/*
	 * Method to add a spouse to a person
	 */
	private void addSpouse() {
		String personChoice=JOptionPane.showInputDialog(frame,"Please enter the person to add new spouse to:");
		Person person=tree.searchForPerson(personChoice);
		if(person==null){  // cannot ad a spouse to null
			JOptionPane.showMessageDialog(frame, "This person does not exist!", "Error in Spouse Addition", 0);
		}
		else{
			if (person.getSpouse()!=null){  // if person already has a spouse , cannot overwrite
				JOptionPane.showMessageDialog(frame, "This person already has a spouse!","Error",0);
			}
			else{
				String newSpouseName= JOptionPane.showInputDialog(frame,"Please enter  new spouse's name: ");
				Person newSpouse=tree.searchForPerson(newSpouseName);  //check if spouse is already in a family
				if(newSpouse==null){   //doesnt exist already
					String newSpouseGender= JOptionPane.showInputDialog(frame,"Please enter  new spouse's gender (F/M) : ");
					String newSpouseDOB= JOptionPane.showInputDialog(frame,"Please enter  new spouse's date of birth : ");
					newSpouse=new Person(newSpouseName,newSpouseGender,newSpouseDOB,null,null,person);
					person.setSpouse(newSpouse);  //set the marriage 
					visualDisplay.setText("~~~  NEW SPOUSE CREATED  ~~~\n");
					appendDetails(newSpouse);  // show details of new spouse created 
				}
				else{   //spouse is already a person
					if (newSpouse.getSpouse()==null){  // doesnt already have a spouse
						person.setSpouse(newSpouse);
						newSpouse.setSpouse(person);
						if(person.getChildren().size()!=0){  //each of the persons children must be added to the spouse's list of children
							for(Person child:person.getChildren()){
								if(!newSpouse.getChildren().contains(child))
									newSpouse.addChild(child);
							}
						}
						if(newSpouse.getChildren().size()!=0){  //each of the new spouse's children must be added to the person's list of children
							for(Person child:newSpouse.getChildren()){
								if(!person.getChildren().contains(child))
									person.addChild(child);
							}
						}
						visualDisplay.setText("~~~  SPOUSE ADDED  ~~~\n"+person.getName()+" is now married to "+person.getSpouse().getName());
					}
					else{
						JOptionPane.showMessageDialog(frame, "This person already has a spouse!","Error",0);
					}
				}
			}
		}
		
	}
	
	/*
	 * Method to modify details of a person
	 */
	private void modifyDetails() {
		String personDetails= JOptionPane.showInputDialog(frame,"Please enter the name of the person to modify: ");
		Person found=tree.searchForPerson(personDetails);   // find person
		if(found==null){
			JOptionPane.showMessageDialog(frame, "This person does not exist!","Error in Detail Modification",0);
		}
		else{
			visualDisplay.setText("~~~  MODIFICATION  ~~~\nChanging details for: "+found.getName());
			String detailToChange="";
			while(!detailToChange.equals("Exit")){  //until exit is entered (allows many details to be changed )
				detailToChange=JOptionPane.showInputDialog(frame,"Please choose which detail to modify (or \"Exit\" to finish modications) :\n\t- Name\n\t- DOB\n\t- Gender\n\t- Exit");
				if(detailToChange!=null){ //cant be null entry
					if(detailToChange.equals("Gender")){
						String newGender=JOptionPane.showInputDialog(frame,"Please enter the new gender (M/F) :");
						if(!newGender.equals("M") && !newGender.equals("F")){
							JOptionPane.showMessageDialog(frame,"Please Enter a Valid Option!","Error",0);
						}
						else{
							found.setGender(newGender);
							JOptionPane.showMessageDialog(frame,"Gender Changed!\n"+found.getName()+"'s Gender : "+found.getGender(),"Gender Changed",1);
						}
					}
					else if(detailToChange.equals("Name")){
						String newName=JOptionPane.showInputDialog(frame,"Please enter the new name:");
						if(newName.trim().equals("") ){  //cant be blank entry
							JOptionPane.showMessageDialog(frame,"Please Enter a Valid Option!","Error",0);
						}
						else{
							found.setName(newName);
							JOptionPane.showMessageDialog(frame,"Name Changed!\nNew Name : "+found.getName(),"Name Changed",1);
						}
					}
					else if(detailToChange.equals("DOB")){  
						String newDOB=JOptionPane.showInputDialog(frame,"Please enter the new DOB (e.g 1998) : ");
						if(newDOB.trim().equals("") || newDOB.length()!=4){  //must be 4 in length and not blank
							JOptionPane.showMessageDialog(frame,"Please Enter a Valid Option!","Error",0);
						}
						else{
							found.setDob(newDOB);
							JOptionPane.showMessageDialog(frame,"DOB Changed!\n"+found.getName()+"'s new DOB : "+found.getDob(),"Date Of Birth Changed",1);
						}
					}
					else if(!detailToChange.equals("Exit")){   // if none of the above options were entered, and exit wasnt entered
						JOptionPane.showMessageDialog(frame,"Please Enter a Valid Option!","Error",0);
					}
				}
				else{  //null entry
					JOptionPane.showMessageDialog(frame,"Please Enter a Valid Option!","Error",0);
				}
			}
			visualDisplay.setText("~~~  UPDATED DETAILS  ~~~\n");   // show the updated person details
			appendDetails(found);

		}
		
	}
	
	/*
	 * Method to get a persons details
	 */
	public void getDetails(String personDetails) {
		Person found=tree.searchForPerson(personDetails);
		if(found==null){
			JOptionPane.showMessageDialog(frame, "This person does not exist!","Error in Details Retrieval",0);
		}
		else{
			visualDisplay.setText("~~~  DETAILS  ~~~\n");
			appendDetails(found);
			
		}
		
	}
	
	/*
	 * Method to add child
	 */
	private void addChild() {
		String parentChoice=JOptionPane.showInputDialog(frame,"Please enter the parent of the new child entry:");
		Person parent=tree.searchForPerson(parentChoice);  //find the existing person (to be parent)
		Person newChildMother=null;
		Person newChildFather=null;
		if(parent==null){ //cant add child to null
			JOptionPane.showMessageDialog(frame, "This person does not exist!", "Error in New Child Addition", 0);
		}
		else{
			if(parent.getGender().equals("F")) { //if parent searched for is female 
				newChildMother=parent;
				newChildFather=parent.getSpouse();
			}
			else{
				newChildFather=parent;
				newChildMother=parent.getSpouse();
			}
			String newChildName= JOptionPane.showInputDialog(frame,"Please enter  new child's name: ");
			String newChildGender= JOptionPane.showInputDialog(frame,"Please enter  new child's gender (F/M) : ");
			String newChildDOB= JOptionPane.showInputDialog(frame,"Please enter  new child's date of birth : ");
			Person child=new Person(newChildName,newChildGender,newChildDOB,newChildMother,newChildFather,null); //create the new child with details
			parent.addChild(child);  // add the child to the parent
			if(parent.getSpouse()!=null){ // if parent has spouse
				parent.getSpouse().addChild(child);  //add child
			}
			visualDisplay.setText("~~~  NEW CHILD CREATED  ~~~\n");
			appendDetails(child);
			if(debug) System.out.println("Parent's Children: "+parent.getChildren());
			if(debug && parent.getSpouse()!=null) System.out.println("Parent's Spouse Children: "+parent.getSpouse().getChildren());  
			
		}
		
	}
	
	/*
	 * Method to display person's details
	 */
	public void appendDetails(Person p){
		visualDisplay.append("Name: "+p.getName()+"\nGender: "+p.getGender()+"\nDOB: "+p.getDob());
		if(p.getSpouse()==null){
			visualDisplay.append("\nSpouse: None");
		}
		else{
			visualDisplay.append("\nSpouse: "+p.getSpouse().getName());
		}
		if(p.getMother()==null){
			visualDisplay.append("\nMother: ?");
		}
		else{
			visualDisplay.append("\nMother: "+p.getMother().getName());
		}
		if(p.getFather()==null){
			visualDisplay.append("\nFather: ?\n");
		}
		else{
			visualDisplay.append("\nFather: "+p.getFather().getName()+"\n");
		}
		if(!p.getChildren().isEmpty()){
			int num=1;
			visualDisplay.append("Children: \n");
			for (Person child:p.getChildren()){
				visualDisplay.append("   "+num+" : "+child.getName()+"\n");
				num++;
			}
		}
	}
	
}
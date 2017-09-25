/**
 * @author Ciara Power
 */
package familyTrees_CPower;


class BinaryTreeIndentPrint

{

    // Root of the Binary Tree
    Person root;
    String display;

    /*
     * Getters and Setters 
     */
    public String getDisplay() {
    	return display;
    }

    public void setDisplay(String display) {
    	this.display = display;
    }

    /*
     * Constructor
     */
    public BinaryTreeIndentPrint()
    {
        display="";
        root = null;
    }

    /*
    * prints out a tree from the root in a readable structure
    */
    public void printPreOrder(Person root, String indent)
    {
    	display+=indent+root.getName();
    	if(root.getSpouse()!=null)
    		display+=" -- "+root.getSpouse().getName()+"\n";  // if spouse isnt null, include the name
    	else
    		display+=" -- ?\n"; // null spouse so ?
    	if(root.getChildren().size()!=0){  // if the person has children
    		for(Person p: root.getChildren()){
    			printPreOrder(p,indent+"   ");  // recurse down through child as root
    		}
    	}
    }
}

     


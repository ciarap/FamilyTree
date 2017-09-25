/**
 * @author Ciara Power
 */
package familyTrees_CPower;

import java.io.File;

/**
 * An Interface for the XMLSerializer class
 * 
 * @author  Ciara Power
 */
public interface Serializer
{
	void push(Object o);
	Object pop();
	void write() throws Exception;
	void read() throws Exception;
	void setFile(File file);
}
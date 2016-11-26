package mips_sim;

import java.util.Vector;
import java.util.Iterator;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;

public class InstructionLoader {
	
	private Vector<MipsInstruction> instructions = new Vector<MipsInstruction>(25); // Holds Instructions
	private HashMap<String, Integer> data = new HashMap<String, Integer>(); // Holds Data
	private Iterator it;

	public InstructionLoader(String file) {
		instructions.listIterator();
		loadFileInstructions(file);
	}

	private void loadFileInstructions(String file) {
    	String temp;
    	String[] tempArray;
    	String key;
    	Integer value;
    	int i;
	
    	try {

    		// Access File
    		BufferedReader br = new BufferedReader(new FileReader(file));

    		// Read Instructions
    		i = 0;
    		temp = br.readLine();
    		while (temp != null 
				&& temp.contains("DATA SEGMENT") == Boolean.FALSE) {

				this.instructions.add(MipsInstruction.getInstruction(Integer.toString(i),temp)); // Load Line into Instructions
				//MipsInstruction tempa = MipsInstruction.getInstruction("1",this.instructions.lastElement());
				//System.out.println(tempa);
				temp = br.readLine();
				i += 4;
			} 


			// Read Data Section
			temp = br.readLine();
			while (temp != null){
				tempArray = temp.split(" ");
				key = tempArray[0];
				value = Integer.decode(tempArray[1]);
				this.data.put(key, value);
				temp = br.readLine();
			}

    	} catch (Exception error) {
			error.printStackTrace();
			System.exit(0);
		}

	}

	public void printInstructions() {
		for (MipsInstruction instruction : instructions)
			System.out.println(instruction);
	}
}
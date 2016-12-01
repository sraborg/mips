package mips_sim;

import java.util.Stack;
import java.util.Vector;
import java.util.ListIterator;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.SwingUtilities;

public class MipsSim {
	Gui gui;
	/**********************************************
	*
	*	Register Constants
	*
	***********************************************/
	/*
		Constants created for the purpose of making identifying registers easier in relation to mips.
		Instead of loking up documentation to see what register, for example, 3 is, the actual register
		name can be used instead of the number.
	*/

	public static final int $zero = 0; // Hard-wired 0
	public static final int $at = 1; // Pseudo-Instructions

	public static final int $v0 = 2, $v1 = 3;	// Function Return Values
	public static final int $a0 = 4, $a1 = 5, $a2 = 6, $a3 = 7; // Function Arguments

	// Temp Registers (Not Preserved by functions)
	public static final int $t0 = 8, $t1 = 9, $t2 = 10, $t3 = 11, $t4 = 12, $t5 = 13, $t6 = 14, $t7 = 15;

	// Saved Registers (Preserved by functions)
	public static final int $s0 = 16, $s1 = 17, $s2 = 18, $s3 = 19, $s4 = 20, $s5 = 21, $s6 = 22, $s7 = 23, $s8 = 24, $s9 = 25;

	public static final int $k0 = 26, $k1 = 27; // Kernal Registers
	public static final int $gp = 28; // Global Area Pointer
	public static final int $sp = 29; // Stack Pointer
	public static final int $fp = 30; // Frame Pointer
	public static final int $ra = 31; // Return Address

	public static final int MAX_STACK_SIZE = 100;

/*
	Creates a hex value for the simulated memory starting addresses. Displayed with instruction/data
	associated with it in order to give the illusion of using actual registers and memory
*/

	public static final Integer stackOffset = Integer.decode("0x7fffefff");
	public static final Integer textOffset = Integer.decode("0x00400000");
	public static final Integer dataOffset = Integer.decode("0x10010000");

// arrays for the simulated registers and the dynamic stack
	private Long[] register;
	private Stack stack = new Stack();

/*
	Vector that holds MipsInstruction objects, which are actually instantiated as either a derived class
	of MipsInstruction with the added properties and functions of ether RTypeInstruction or ITypeInstruction.
	Those two objects inherit MipsInstruction. MipsInstruction can't be instantiated, but is used as a factory
	to create specific instruction types.
*/
	private Vector<MipsInstruction> instructions = new Vector<MipsInstruction>(25); // Holds Instructions

/*
	Array where the hex 'DATA SEGMENT' from the given code text file is stored. Uses dictionary format, key=value,
	where key is the register value, and value is the raw data in hex form
*/

	private HashMap<String, Integer> data = new HashMap<String, Integer>(); // Holds Data

/*
	Used as an iterator of the 'instructions' vector, to keep track of which instruction is currently being
	processed.
*/

	private ListIterator<MipsInstruction> programCounter;


	//HashMap<String, String> opcodes = new HashMap<String, String>(); // Dictionary holds mapping to opcodes


	// Program Entry Point
	public static void main(String[] args) {


		System.out.println("Stack Offset: " + stackOffset);
		System.out.println("Text Offset: " + textOffset);
		System.out.println("Data Offset: " + dataOffset);
		// Setup Components
		MipsSim sim = new MipsSim(args[0]);

		// Set GUI to run in separate thread (EDT)
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				sim.gui = new Gui(sim);
				sim.loadFileInstructions(args[0]);
			}
		});

	}




	public MipsSim(String file) {
		// Declare and Manually Initialize register array
		register = new Long[32];
		for (int i=0; i < register.length; i++) {
			register[i] = 0L;
		}

	}

	/***************************************************************************
	*
	*	Simple Helper Function
	*	@param Takes register number
	*	@return Returns the common name for the register
	*
	****************************************************************************/
	public static String registerAlias(Integer register) {
		switch(register) {
			case 2:
				return "$v0";
			case 3:
				return "$v1";
			case 4:
				return "$a0";
			case 5:
				return "$a1";
			case 6:
				return "$a2";
			case 7:
				return "$a3";
			case 8:
				return "$t0";
			case 9:
				return "$t1";
			case 10:
				return "$t2";
			case 11:
				return "$t3";
			case 12:
				return "$t4";
			case 13:
				return "$t5";
			case 14:
				return "$t6";
			case 15:
				return "$t7";
			case 16:
				return "$s0";
			case 17:
				return "$s1";
			case 18:
				return "$s2";
			case 19:
				return "$s3";
			case 20:
				return "$s4";
			case 21:
				return "$s5";
			case 22:
				return "$s6";
			case 23:
				return "$s7";
			case 24:
				return "$s8";
			case 25:
				return "$s9";
			case 29:
				return "$sp";
			case 31:
				return "$ra";

			default:
				return "$" + register;
		}
	}


	/***************************************************************************
	*
	*	Intruction Controller
	*	Maps a MIPs Instruction to its corresponding simulated Instruction
	*
	****************************************************************************/
	public synchronized void processInstruction() {


		if(this.programCounter.hasNext()) {
			MipsInstruction currentInstruction = this.programCounter.next();

			if(currentInstruction instanceof ITypeInstruction) {
				ITypeInstruction iTypeInstruction = (ITypeInstruction) currentInstruction;
				Integer rs = iTypeInstruction.getrs();
				Integer rt = iTypeInstruction.getrt();
				Long immediate = iTypeInstruction.getImmediate();

				switch(currentInstruction.getOpcode()) {

					// addiu
					case "001001":
						register[rt] = register[rs] + immediate;
						System.out.println(this.register[rt] + ", " + rs +", " + immediate);
						this.gui.registersTableModel.setValueAt(String.format("%32s",Long.toBinaryString(Long.decode(Integer.toBinaryString(rt)))).replace(" ", "0"), rt, 1); // Set Binary Value
						this.gui.registersTableModel.setValueAt(register[rt], rt, 2); // Set Decimal Value
						this.gui.registersTable.revalidate();
						this.gui.registersTable.repaint();
						break;
				}
			}


			if(currentInstruction instanceof RTypeInstruction) {
				RTypeInstruction rTypeInstruction = (RTypeInstruction) currentInstruction;

				switch(currentInstruction.getOpcode()) {
					case "100000":
						register[rTypeInstruction.getrd()] = register[rTypeInstruction.getrs()] + register[rTypeInstruction.getrt()];
						break;
				}
			}
			this.gui.setCurrentInstruction(this.programCounter.previousIndex());

		} else {
			System.out.println("No More Instructions");
		}



	}

	// Simulated Functions


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
				gui.addInstruction(this.instructions.lastElement());
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

			this.programCounter = this.instructions.listIterator();
			gui.setCurrentInstruction(programCounter.nextIndex());

    	} catch (Exception error) {
			error.printStackTrace();
			System.exit(0);
		}

	}

	/*************************************************************
	*
	*	Debug Method
	*	Prints each Instruction in the Instructions List
	*
	**************************************************************/
	public void printInstructions() {
		for (MipsInstruction instruction : instructions)
			System.out.println(instruction);
	}
}

package mips_sim;

import java.util.HashMap;
import java.util.Vector;
import java.util.Stack;

public class MipsSim {
	
	/**********************************************
	* 
	*	Register Constants
	*
	***********************************************/
	
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

	public static final Integer stackOffset = Integer.decode("0x7fffefff");
	public static final Integer textOffset = Integer.decode("0x00400000");
	public static final Integer dataOffset = Integer.decode("0x10010000");

	private Integer[] register = new Integer[32];
	private Stack stack = new Stack();
	


	HashMap<String, String> opcodes = new HashMap<String, String>(); // Dictionary holds mapping to opcodes


	

	// Program Entry Point
	public static void main(String[] args) {

		// Check how many arguments were passed in
		/*if(args.length == 0)
		{
		    System.out.println("Error no file specified. \nProper Usage is: MipsSim <file>");
		    System.exit(0);
		}*/

		// Setup Components
		MipsSim sim = new MipsSim();
		//Gui gui = new Gui();
		InstructionLoader instruction = new InstructionLoader(args[0]);
		
		// Process Instructions

		instruction.printInstructions();

		System.out.println("Stack Offset: " + stackOffset);
		System.out.println("Text Offset: " + textOffset);
		System.out.println("Data Offset: " + dataOffset);
		
	}




	public MipsSim() {
		

	}

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
	private void functionCall(String instruction) {




	}

	// Simulated Functions

}


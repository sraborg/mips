package mips_sim;

public abstract class MipsInstruction {
	String instructionType;

	Integer address;
	String hexAddress;
	String binaryAddress;

	String instruction;
	String hexInstruction;
	String binaryInstruction;

	String opcode;

	protected MipsInstruction() {}

	public static MipsInstruction getInstruction(String hexAddress, String hexInstruction) {

		String binaryInstruction = String.format("%32s",Long.toBinaryString(Long.decode(hexInstruction.trim()))).replace(" ", "0");
		String opcode = binaryInstruction.substring(0,6);

		switch(opcode) {

			// R-Type
			case "000000":
				return new RTypeInstruction(hexAddress, hexInstruction, binaryInstruction, opcode);
			// I-Type
			case "001001":
			case "101011":
			case "001111":
			case "001101":
			case "100011":
			case "000101":
				return new ITypeInstruction(hexAddress, hexInstruction, binaryInstruction, opcode);
			// J-Type
			case "333333":
				return null;
			default:
				System.out.println("Unidentified Instruction detected: " + binaryInstruction);
				return null;

		}

	}

	/**********************************************
	* 
	*	Getter Methods
	*
	***********************************************/
	public String getBinaryAddress() {
		return this.binaryAddress;
	}
	public String getHexAddress() {
		return this.hexAddress;
	}
	public String getHexInstruction() {
		return this.hexInstruction;
	}
	public String getBinaryInstruction() {
		return this.binaryInstruction;
	}
	public String getInstruction() {
		return this.instruction;
	}
	public String getOpcode() {
		return this.opcode;
	}
	public String getInstructionType() {
		return this.instructionType;
	}
	/**********************************************
	* 
	*	Overriden Methods
	*
	***********************************************/
	public String toString() {
		return this.instruction;
	}

}
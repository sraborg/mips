package mips_sim;

import mips_sim.MipsSim;

public class RTypeInstruction extends MipsInstruction {

	Integer rs;
	Integer rt;
	Integer rd;
	String shift;
	String function;
	
	
	public RTypeInstruction(String hexAddress, String hexInstruc, String binaryInstruction, String opcode) {
		this.instructionType = "R-Type";
		this.hexAddress = hexAddress;
		this.binaryAddress = Long.toString(Long.decode(hexAddress.trim()));
		this.hexInstruction = hexInstruc;
		this.binaryInstruction = binaryInstruction;

		this.opcode = opcode;
		this.rs = Integer.parseInt(binaryInstruction.substring(7,11), 2);
		this.rt = Integer.parseInt(binaryInstruction.substring(11,16), 2);
		this.rd = Integer.parseInt(binaryInstruction.substring(16,21), 2);
		this.shift = binaryInstruction.substring(21,26);
		this.function = binaryInstruction.substring(26,32);

		//System.out.println(opcode + " " + registerS + " " + registerT + " " + registerD + " " + function);
		switch(function) {

			case "100000":
				instruction ="add " + MipsSim.registerAlias(this.rs) + ", " + MipsSim.registerAlias(this.rt) + ", " + MipsSim.registerAlias(this.rd);
				break;
			case "100001":
				instruction ="addu " + MipsSim.registerAlias(this.rs) + ", " + MipsSim.registerAlias(this.rt) + ", " + MipsSim.registerAlias(this.rd);
				break;
			case "001100":
				instruction = "syscall";
				break;
			case "001000":
				instruction = "jr "+ MipsSim.registerAlias(this.rs);

		}
	}

	/**********************************************
	* 
	*	Getter Methods
	*
	***********************************************/
	public Integer getrs() {
		return this.rs;
	}
	public Integer getrt() {
		return this.rt;
	}
	public Integer getrd() {
		return this.rd;
	}


}
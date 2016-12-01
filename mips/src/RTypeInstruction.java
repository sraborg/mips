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
		this.hexAddress = "0x" + String.format("%8s",Long.toBinaryString(Long.decode(hexAddress))).replace(" ", "0");
		this.binaryAddress = Long.toString(Long.decode(hexAddress.trim()));
		this.hexInstruction = hexInstruc;
		this.binaryInstruction = binaryInstruction;

		this.opcode = opcode;
		this.rs = Integer.parseInt(binaryInstruction.substring(7,11), 2);
		this.rt = Integer.parseInt(binaryInstruction.substring(11,16), 2);
		this.rd = Integer.parseInt(binaryInstruction.substring(16,21), 2);
		this.shift = binaryInstruction.substring(21,26);
		this.function = binaryInstruction.substring(26,32);

		switch(function) {

			case "000000":
				instruction = "sll " + MipsSim.registerAlias(this.rd) + ", " + MipsSim.registerAlias(this.rt) + ", " + String.valueOf(Integer.parseInt(this.shift, 2));
				break;
			case "000010":
				instruction = "srl " + MipsSim.registerAlias(this.rd) + ", " + MipsSim.registerAlias(this.rt) + ", " + String.valueOf(Integer.parseInt(this.shift, 2));
				break;
			case "100000":
				instruction = "add " + MipsSim.registerAlias(this.rd) + ", " + MipsSim.registerAlias(this.rs) + ", " + MipsSim.registerAlias(this.rt);
				break;
			case "100001":
				instruction = "addu " + MipsSim.registerAlias(this.rd) + ", " + MipsSim.registerAlias(this.rs) + ", " + MipsSim.registerAlias(this.rt);
				break;
			case "100010":
				instruction = "sub " + MipsSim.registerAlias(this.rd) + ", " + MipsSim.registerAlias(this.rs) + ", " + MipsSim.registerAlias(this.rt);
				break;
			case "100011":
				instruction = "subu " + MipsSim.registerAlias(this.rd) + ", " + MipsSim.registerAlias(this.rs) + ", " + MipsSim.registerAlias(this.rt);
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
	public Integer getshift() {
			return Integer.valueOf(this.shift);
	}
	public String getFunction() {
			return this.function;
	}

}

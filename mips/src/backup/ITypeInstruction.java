package mips_sim;

public class ITypeInstruction extends MipsInstruction {
	
	Integer rs;
	Integer rt;
	Long immediate;

	public ITypeInstruction(String hexAddress, String hexInstruc, String binaryInstruction, String opcode) {
		this.instructionType = "I-Type";
		this.hexAddress = "0x" + String.format("%8s",Long.toBinaryString(Long.decode(hexAddress))).replace(" ", "0");
		this.binaryAddress = String.format("%32s",Integer.decode(hexAddress.trim())).replace(" ", "0");
		this.hexInstruction = hexInstruc;
		this.binaryInstruction = binaryInstruction;

		this.opcode = opcode;
		this.rs = Integer.parseInt(binaryInstruction.substring(6,11), 2);
		this.rt = Integer.parseInt(binaryInstruction.substring(11,16),2);
		this.immediate = Long.parseLong(binaryInstruction.substring(16,32),2);

		switch(opcode) {

			case "001001":
				instruction = "addiu " + MipsSim.registerAlias(this.rs) + " " + MipsSim.registerAlias(this.rt) + " " + this.immediate;
				break;
			case "101011":
				instruction = "sw " + MipsSim.registerAlias(this.rt) + " " + this.immediate + "(" + MipsSim.registerAlias(this.rs) + ")";
				break;
			case "001111":
				instruction = "lui " + MipsSim.registerAlias(this.rs) + " " + this.immediate;
				break;
			case "001101":
				instruction = "ori " + MipsSim.registerAlias(this.rs) + " " + MipsSim.registerAlias(this.rt) + " " + this.immediate;
				break;
			case "100011":
				instruction = "lw " + MipsSim.registerAlias(this.rt) + " " + this.immediate + "(" + MipsSim.registerAlias(this.rs) + ")";
				break;
			case "000101":
				instruction = "bne " + MipsSim.registerAlias(this.rs) + " " + MipsSim.registerAlias(this.rt) + " " + this.immediate;
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
	public Long getImmediate() {
		return this.immediate;
	}

}
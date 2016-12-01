package mips_sim;

import mips_sim.MipsSim;

public class JTypeInstruction extends MipsInstruction {

	Integer target;


	public JTypeInstruction(String hexAddress, String hexInstruc, String binaryInstruction, String opcode) {
		this.instructionType = "J-Type";
		this.hexAddress = "0x" + String.format("%8s",Long.toBinaryString(Long.decode(hexAddress))).replace(" ", "0");
		this.binaryAddress = Long.toString(Long.decode(hexAddress.trim()));
		this.hexInstruction = hexInstruc;
		this.binaryInstruction = binaryInstruction;

		this.opcode = opcode;
		this.target = Integer.parseInt(binaryInstruction.substring(7,31), 2);

		//System.out.println(opcode + " " + target);
		switch(opcode) {

			case "000010":
				instruction ="j " + MipsSim.registerAlias(this.target);
				break;
      case "000011":
				instruction ="jal " + MipsSim.registerAlias(this.target);
				break;
		}
	}

	/**********************************************
	*
	*	Getter Method
	*
	***********************************************/
	public Integer gettarget() {
		return this.target;
	}


}

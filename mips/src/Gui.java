package mips_sim;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.JFileChooser;

public class Gui {
	MipsSim sim;
	JFrame window = new JFrame();
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenuItem openMenuItem = new JMenuItem("Open");
	//JScrollPane instructionsScrollPane = new JScrollPane();
	DefaultTableModel instructionsTableModel;
	DefaultTableModel registersTableModel;
	JTable instructionTable;
	public JTable registersTable;

	final JFileChooser fc = new JFileChooser();

	JButton processInstructionBtn = new JButton("Process Instruction");

	JPanel bottomPanel = new JPanel(new GridLayout(3,1));

	public Gui(MipsSim sim) {
		this.sim = sim;
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Mips Simulator");
		window.setSize(1280,720);
		window.setLocationRelativeTo(null);

		fileMenu.add(openMenuItem).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = fc.showOpenDialog(window);
				if (result == JFileChooser.APPROVE_OPTION){
					File selectedFile = fc.getSelectedFile();
					sim.loadFileInstructions(selectedFile.getAbsolutePath());
				}
			};
		});

		menuBar.add(fileMenu);


		// Configure Instructions Table
		instructionsTableModel = new DefaultTableModel(null, new String[]{"Address (hex)", "Instructions (hex)", "Instructions (binary)", "Instructions"}) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
		    	return false;
			}
		};
		instructionTable = new JTable(instructionsTableModel);
		//instructionTable.setRowSelectionAllowed(false);
		instructionTable.setFocusable(false);

		// Configure Registers Table
		registersTableModel = new DefaultTableModel(null, new String[]{"Register", "Value (binary)", "Value"})  {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
		    	return false;
			}
		};
		for(int i = 0; i < 32; i++)
			registersTableModel.addRow(new String[] {sim.registerAlias(i), "00000000000000000000000000000000","0"});
		registersTable = new JTable(registersTableModel);
		//registersTable.setRowSelectionAllowed(Boolean.FALSE);
		registersTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		//registersTable.setCellSelectionEnabled(Boolean.FALSE);
		registersTable.setFocusable(false);

		// Configure Process Button
		processInstructionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sim.processInstruction();
			}
		});

		bottomPanel.add(processInstructionBtn);

		window.add(new JScrollPane(registersTable), BorderLayout.WEST);
		window.add(new JScrollPane(instructionTable));
		window.add(bottomPanel, BorderLayout.SOUTH);

		window.setJMenuBar(menuBar);

		window.pack();
		window.setVisible(true);

	}

	public void addInstruction(MipsInstruction instruction) {
		instructionsTableModel.addRow(new String[] {instruction.getHexAddress(), instruction.getHexInstruction(), instruction.getBinaryInstruction(), instruction.getInstruction()});

	}

	public void setCurrentInstruction(int index) {
		instructionTable.setRowSelectionInterval(index,index);

	}

}

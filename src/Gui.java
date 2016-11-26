package mips_sim;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;


public class Gui {
	JFrame window = new JFrame();
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenuItem openMenuItem = new JMenuItem("Open");
	//JScrollPane instructionsScrollPane = new JScrollPane();
	DefaultTableModel instructionsTableModel;
	DefaultTableModel registersTableModel;
	JTable instructionTable;
	JTable registersTable;
	ListSelectionModel instructionListSelectionModel;

	public Gui() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Mips Simulator");
		window.setSize(1280,720);
		window.setLocationRelativeTo(null);

		fileMenu.add(openMenuItem);
		menuBar.add(fileMenu);


		// Configure Instructions Table
		instructionsTableModel = new DefaultTableModel(null, new String[]{"Address (hex)", "Address (binary)", "Instructions (hex)", "Instructions (binary)", "Instructions"}) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
		    	return false;
			}
		};
		instructionTable = new JTable(instructionsTableModel);
		instructionTable.setFocusable(false);

		// Configure Registers Table
		registersTableModel = new DefaultTableModel(null, new String[]{"Register", "Value (binary)", "Value"})  {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
		    	return false;
			}
		};
		for(int i = 0; i < 32; i++)
			registersTableModel.addRow(new String[] {"$"+ i, "00000000","0"});
		registersTable = new JTable(registersTableModel);
		//registersTable.setRowSelectionAllowed(Boolean.FALSE);
		registersTable.getColumnModel().getColumn(0).setPreferredWidth(10);
		registersTable.setCellSelectionEnabled(Boolean.FALSE);
		registersTable.setFocusable(false);
		instructionListSelectionModel = registersTable.getSelectionModel();

		window.add(new JScrollPane(registersTable), BorderLayout.WEST);
		window.add(new JScrollPane(instructionTable));

		window.setJMenuBar(menuBar);

		window.pack();
		window.setVisible(true);

	}

	public void addInstruction(MipsInstruction instruction) {
		instructionsTableModel.addRow(new String[] {instruction.getHexAddress(), instruction.getBinaryAddress(), instruction.getHexInstruction(), instruction.getBinaryInstruction(), instruction.getInstruction()});

	}

	public void setSelectedInterval(int index) {
		//instructionTable.setRowSelectionInterval(3,3);

	}


}
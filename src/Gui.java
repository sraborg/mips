package mips_sim;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class Gui {
	JFrame window = new JFrame();
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenuItem openMenuItem = new JMenuItem("Open");
	JScrollPane instructionsScrollPane = new JScrollPane();
	JTable instructionTable = new JTable();

	public Gui() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Mips Simulator");
		window.setSize(1280,720);
		window.setLocationRelativeTo(null);

		fileMenu.add(openMenuItem);
		menuBar.add(fileMenu);


		window.setJMenuBar(menuBar);

		//window.pack();
		window.setVisible(true);

	}


}
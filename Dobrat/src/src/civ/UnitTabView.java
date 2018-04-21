package src.civ;

import javax.swing.*;
import java.awt.*;

class UnitTabView extends JPanel{
	private JTabbedPane unitTypePane = new JTabbedPane();
	private JPanel viewAllPanel = new JPanel();
	private JLabel enhetsNamn = new JLabel();
	//private Tile t = new Tile(); 
	//private PhyiscalUnit pu = new PhysicalUnit();
	
	//private ArrayList<PhysicalUnit> allUnitsArray = new ArrayList<PhysicalUnit> ();
	//private GameMap gm = new GameMap(null);
	
	public UnitTabView(){ 
		setLayout(new BorderLayout());

		unitTypePane.addTab("<html><body leftmargin=15 topmargin=12 marginwidth=15 marginheight=10>All </body></html>", null, viewAllPanel, "Show all devices ");
		unitTypePane.addTab("<html><body leftmargin=15 topmargin=12 marginwidth=15 marginheight=10>Ships </body></html>", null, null, "Show all devices ");
		unitTypePane.addTab("<html><body leftmargin=15 topmargin=12 marginwidth=15 marginheight=10>Wardevices </body></html>", null, null, "Show all devices ");
		unitTypePane.addTab("<html><body leftmargin=15 topmargin=12 marginwidth=15 marginheight=10>Ranged </body></html>", null, null, "Show all devices ");
		unitTypePane.addTab("<html><body leftmargin=15 topmargin=12 marginwidth=15 marginheight=10>Altilerie </body></html> ", null, null, "Show artilery ");
		unitTypePane.addTab("<html><body leftmargin=15 topmargin=12 marginwidth=15 marginheight=10>Cavalerie </body></html>", null, null, "Show all chavalery devices ");

		//viewAllPanel.add();	
		
		//this.printUnitArray();
		
		add(unitTypePane, BorderLayout.NORTH);
		
	}
	
	/*public void printUnitArray() {
		for (PhysicalUnit pu : t.getUnitArray()){
			enhetsNamn.setText(pu.toString());		
		}
		
		viewAllPanel.add(enhetsNamn);
		unitTypePane.add(viewAllPanel);
		unitTypePane.repaint();
	}*/
}





















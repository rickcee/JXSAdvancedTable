package net.rickcee.swingxs.demo;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXFrame;

import net.rickcee.swingxs.demo.config.MailConfigFactory;
import net.rickcee.swingxs.demo.config.TradeColorFilter;
import net.rickcee.swingxs.demo.config.TradeTabFilter;
import net.rickcee.swingxs.demo.model.BOTrade;
import net.rickcee.swingxs.demo.model.Trade;
import net.rickcee.swingxs.ui.JXSTabbedTable;

/**
 * Created on Feb 20, 2012
 */

/**
 * @author RickCeeNet
 * 
 */
public class TabbedTableDemo extends JXFrame {

	private static final long serialVersionUID = -3170678434610659421L;

	private List<Trade> appData = new ArrayList<Trade>(); // For Initial Load ?
	private MailConfigFactory mailConfigFactory;
	private JXSTabbedTable<Trade> tabbedTable;

	/**
	 * Constructor
	 * 
	 * @param title
	 * @param exit
	 * @throws IOException 
	 */
	public TabbedTableDemo(String title, boolean exit) throws IOException {
		super(title, exit);
		this.mailConfigFactory = new MailConfigFactory();
	}

	protected void createView() throws Exception {
		tabbedTable = new JXSTabbedTable<Trade>(new TradeTabFilter());	
		
		addTableModes();
		
		getContentPane().add(tabbedTable.getTabPanel());		
	}

	public void addTableModes() throws Exception {
		tabbedTable.addMode(TradeTabFilter.MODE_ERRORS, Trade.class);
		tabbedTable.addMode(TradeTabFilter.MODE_CANCELLED, Trade.class);
		tabbedTable.addMode(TradeTabFilter.MODE_UNMATCHED, Trade.class);
		tabbedTable.addMode(TradeTabFilter.MODE_MISMATCHED, Trade.class);
		tabbedTable.addMode(TradeTabFilter.MODE_MATCHED, Trade.class);
		tabbedTable.addMode(TradeTabFilter.MODE_REJECTED, Trade.class);

		tabbedTable.addColorFilterToAllTableModes(new TradeColorFilter());
	}
	
	public void loadTableObjects() throws Exception {
		Timer timer = new Timer("DataPopulate", true);
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				tabbedTable.addToModel(BOTrade.generate(null));
				tabbedTable.refreshRowCountOnAllTableModes();
			}
		}, 250, 1000);
		
		tabbedTable.refreshRowCountOnAllTableModes();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		final TabbedTableDemo frame = new TabbedTableDemo("JXSAdvancedTable Demo", true);

		frame.createView();

		frame.setPreferredSize(new Dimension(1500, 800));
		frame.setSize(new Dimension(1500, 800));
		frame.setVisible(true);

		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				try {
					frame.loadTableObjects();
				} catch (Exception e) {
					System.err.println("Error loading Data...");
				}
			}
		});
		
	}

}

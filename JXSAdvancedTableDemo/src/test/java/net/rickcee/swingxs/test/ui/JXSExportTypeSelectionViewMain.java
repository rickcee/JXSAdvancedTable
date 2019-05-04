package net.rickcee.swingxs.test.ui;

import javax.swing.JDialog;

import net.rickcee.swingxs.ui.JXSExportTypeSelectionView;

public class JXSExportTypeSelectionViewMain {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		JDialog d = new JXSExportTypeSelectionView();
		d.setLocationRelativeTo(null);
		d.setVisible(true);
		while (Boolean.TRUE.equals(d.isVisible())) {
			Thread.sleep(1000);
		}
		System.exit(0);
	}
}

/**
 * 
 * Copyright 2009,2010,2011,2012 RickCeeNet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Created on Mar 10, 2012
 *
 */
package net.rickcee.swingxs.ui;

import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JProgressBar;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXLabel;

/**
 * @author RickCeeNet
 * 
 */
public class JXSProgressDialog extends JDialog {

	private static final long serialVersionUID = 2637623771969874252L;

	protected static final String inProgress = "<html><font color=\"#008000\"><b>Please wait.......</b></font></html>";
	protected static final String complete = "<html><font color=\"#FF0000\"><b>Process Completed.</b></font></html>";

	protected static final String inProgressCustom1 = "<html><font color=\"#008000\"><b>";
	protected static final String inProgressCustom2 = "</b></font></html>";
	protected static final String completeCustom1 = "<html><font color=\"#FF0000\"><b>";
	protected static final String completeCustom2 = "</b></font></html>";

	protected static Image image;

	protected JXImagePanel panel = new JXImagePanel();
	protected JProgressBar progressBar = new JProgressBar();
	protected JXLabel label = new JXLabel(inProgress);
	protected Thread process;

	public JXSProgressDialog() {
		super();
		setSize(300, 100);
		setPreferredSize(getSize());
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setLayout(new MigLayout("wrap 15", "[grow]"));
		progressBar.setIndeterminate(true);

		label.setAlignmentY(CENTER_ALIGNMENT);

		add(progressBar, "grow, wrap");
		add(label, "grow");
	}

	public void startProgress(String message) {
		label.setText(inProgressCustom1 + message + inProgressCustom2);
		start();
	}

	public void startProgress() {
		label.setText(inProgress);
		start();
	}

	/**
	 * 
	 */
	protected void start() {
		setVisible(true);
		progressBar.setIndeterminate(true);
	}

	public void stopProgress(String message) {
		label.setText(completeCustom1 + message + completeCustom2);
		stop(true);
	}

	public void stopProgress() {
		label.setText(complete);
		stop(true);
	}

	public void stopProgressNoWait(String message) {
		label.setText(completeCustom1 + message + completeCustom2);
		stop(false);
	}

	public void stopProgressNoWait() {
		label.setText(complete);
		stop(false);
	}
	
	/**
	 * 
	 */
	protected void stop(Boolean wait) {
		progressBar.setIndeterminate(false);
		try {
			System.out.println("Dead");
			if(wait) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setVisible(false);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		JXSProgressDialog d = new JXSProgressDialog();
		// d.setModal(true);
		// d.setUndecorated(true);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
		d.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Thread.sleep(5000);
		d.stopProgress();
		Thread.sleep(5000);
		// d.startProgress();
	}

}

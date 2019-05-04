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
 * Created on Feb 22, 2012
 * 
 */
package net.rickcee.swingxs.ui;

import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;

/**
 * @author RickCeeNet
 * 
 */
public class JXSMenuItem extends JMenuItem {

	private static final long serialVersionUID = 2984077101673730913L;

	/**
	 * Constructor
	 * 
	 * @param txt
	 */
	public JXSMenuItem(String txt) {
		super(txt);
		setUI(new JXSMenuItemUI());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JMenuItem#processMouseEvent(java.awt.event.MouseEvent,
	 * javax.swing.MenuElement[], javax.swing.MenuSelectionManager)
	 */
	public void processMouseEvent(MouseEvent e, MenuElement[] path, MenuSelectionManager manager) {
		super.processMouseEvent(e, path, manager);
	}
}

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

import javax.swing.JComponent;
import javax.swing.MenuSelectionManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

/**
 * @author RickCeeNet
 * 
 */
public class JXSCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.plaf.basic.BasicMenuItemUI#doClick(javax.swing.
	 * MenuSelectionManager)
	 */
	protected void doClick(MenuSelectionManager msm) {
		menuItem.doClick(0);
	}

	public static ComponentUI createUI(JComponent c) {
		return new JXSCheckBoxMenuItemUI();
	}
}

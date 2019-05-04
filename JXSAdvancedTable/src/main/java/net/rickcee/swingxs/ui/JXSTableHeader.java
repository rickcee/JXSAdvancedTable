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
 * Created on Feb 21, 2012
 * 
 */
package net.rickcee.swingxs.ui;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * @author RickCeeNet
 *
 */
@Deprecated
public class JXSTableHeader extends JTableHeader {

	private static final long serialVersionUID = 3913149910798867806L;

	/* (non-Javadoc)
	 * @see javax.swing.table.JTableHeader#getDefaultRenderer()
	 */
	@Override
	public TableCellRenderer getDefaultRenderer() {
		return new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4,
					int arg5) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
				JLabel jl1 = new JLabel("Test");
				JLabel jl2 = new JLabel("(1)");
				panel.add(jl1);
				panel.add(jl2);
				return panel;
			}
		};
	}

}

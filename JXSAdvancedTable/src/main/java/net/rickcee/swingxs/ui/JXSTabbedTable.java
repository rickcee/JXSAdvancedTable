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
 */
package net.rickcee.swingxs.ui;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTabbedPane;

import org.jdesktop.swingx.JXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.ITabFilter;
import net.rickcee.swingxs.mail.IMailConfig;
import net.rickcee.swingxs.model.meta.IFilterable;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTabbedTable<T> {
	private static final Logger logger = LoggerFactory.getLogger(JXSTabbedTable.class);

	protected List<T> model = new ArrayList<T>();
	protected Map<Object, String> currentModeForObject = new HashMap<Object, String>();
	protected Map<String, List<T>> subModels = new HashMap<String, List<T>>();
	protected List<String> modelModes = new ArrayList<String>();
	protected Map<String, JXSAdvancedTable<T>> tabContent = new HashMap<String, JXSAdvancedTable<T>>();
	protected Map<String, Class<?>> modelClazz = new HashMap<String, Class<?>>();
	protected Map<Integer, JXPanel> tabs = new HashMap<Integer, JXPanel>();
	protected Map<String, Integer> tabIndex = new HashMap<String, Integer>();
	protected ITabFilter filter;
	protected JTabbedPane tabPanel = new JTabbedPane();
	protected Integer index = 0;

	/**
	 * Constructor
	 * 
	 * @param filter
	 *            The row filter to apply.
	 */
	public JXSTabbedTable(ITabFilter filter) {
		super();
		this.filter = filter;
	}

	public void addMode(String mode, Class<?> clazz) throws Exception {
		List<T> modelForMode = new ArrayList<T>();
		// Add the mode to the list
		modelModes.add(mode);
		// Add the moe - associated class
		modelClazz.put(mode, clazz);
		// Then ADD the model for that mode
		subModels.put(mode, modelForMode);
		// Then ADD the new Table for that mode
		tabContent.put(mode, new JXSAdvancedTable<T>(clazz, modelForMode));

		// Process the new mode
		JXPanel tabComponent = new JXPanel();
		tabComponent.setLayout(new BorderLayout());
		tabComponent.add(tabContent.get(mode));
		tabPanel.add(mode, tabComponent);
		tabs.put(index, tabComponent);
		tabIndex.put(mode, index);
		// Increase the tab count
		index++;
	}

	public void addToModel(T viewObject) {
		String newMode = filter.getTabID(viewObject);
		String currentMode = currentModeForObject.get(viewObject);

		if (newMode != currentMode) {
			if (currentMode != null) {
				List<T> oldModel = subModels.get(currentMode);
				if (oldModel != null) {
					Boolean result = oldModel.remove(viewObject);
					if (!result) {
						logger.warn("Object not found in Model: " + viewObject);
					}
					model.remove(viewObject);
					processSubModelChange(currentMode);
					
					tabContent.get(newMode).getModel().fireTableDataChanged();
					tabContent.get(currentMode).getModel().fireTableDataChanged();
				}
			}
			currentModeForObject.put(viewObject, newMode);
		}

		List<T> subModel = subModels.get(newMode);
		if (subModel == null) {
			throw new RuntimeException("No mode defined for value[" + newMode + "]");
		}

		// Since it's a List (it will allow duplicate objects), remove first
		// then re-add
		model.remove(viewObject);
		subModel.remove(viewObject);

		model.add(viewObject);
		subModel.add(viewObject);

		// Fire RowInserted action in the appropriate model
		//tabContent.get(newMode).getModel().fireTableRowsInserted(subModel.size() - 1, subModel.size() - 1);

		processSubModelChange(newMode);
	}

	public JTabbedPane getTabPanel() {
		return tabPanel;
	}

	public void processSubModelChange(String mode) {
		List<T> subModel = subModels.get(mode);
		String title = mode + " (" + subModel.size() + ")";
		Integer index = tabIndex.get(mode);
		tabPanel.setTitleAt(index, title);
		tabContent.get(mode).getModel().fireTableDataChanged();
	}

	public JXSAdvancedTable<T> getTableForMode(String mode) {
		return tabContent.get(mode);
	}

	public List<JXSAdvancedTable<T>> getTablesForAllModes() {
		List<JXSAdvancedTable<T>> result = new ArrayList<JXSAdvancedTable<T>>();
		for (String key : tabContent.keySet()) {
			result.add(tabContent.get(key));
		}
		return result;
	}

	public void addMouseListenerToAllTableModes(MouseListener listener) {
		JXSAdvancedTable<T> table;
		for (String key : tabContent.keySet()) {
			table = tabContent.get(key);
			table.addMouseListener(listener);
		}
	}

	public void addColorFilterToAllTableModes(IFilterable filter) {
		JXSAdvancedTable<T> table;
		for (String key : tabContent.keySet()) {
			table = tabContent.get(key);
			table.addColorFilter(filter);
		}
	}

	public void addEmailFactoryToAllTableModes(IMailConfig config) {
		for (JXSAdvancedTable<?> table : getTablesForAllModes()) {
			table.setEmailFactory(config);
		}
	}
	
	public void refreshRowCountOnAllTableModes() {
		for (JXSAdvancedTable<?> table : getTablesForAllModes()) {
			// Refresh Row Count on table
			table.refreshRowCount();
		}
		// Refresh Row Count on Tab
		for(String subModel : subModels.keySet()) {
			processSubModelChange(subModel);
		}
	}
}

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
 * Created on Feb 19, 2012
 * 
 */
package net.rickcee.swingxs.ui.model;

import javax.swing.DefaultListModel;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableFilterListModel extends DefaultListModel {

	private static final long serialVersionUID = -6926398259756217127L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.DefaultListModel#addElement(java.lang.Object)
	 */
	@Override
	public void addElement(Object obj) {
		super.addElement(obj);
		((JXSTableFilterObject) obj).setListIndex(getSize() - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.DefaultListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		JXSTableFilterObject obj = (JXSTableFilterObject) super.getElementAt(index);
		obj.setListIndex(index);
		return obj;
	}

}

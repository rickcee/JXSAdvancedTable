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
 * Created on Feb 23, 2012
 *
 */
package net.rickcee.swingxs.test.ui;

import net.rickcee.swingxs.config.BeanConfigUtils;
import net.rickcee.swingxs.demo.model.Trade;
import net.rickcee.swingxs.ui.JXSTableSorterDialog;
import net.rickcee.swingxs.ui.model.JXSTableSorterModel;

/**
 * @author RickCeeNet
 * 
 */
public class JXSTableSorterDialogMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BeanConfigUtils.loadUserConfiguration(Trade.class);
		JXSTableSorterModel model = new JXSTableSorterModel(Trade.class);
		JXSTableSorterDialog d = new JXSTableSorterDialog(model);
		d.setVisible(true);
		while (Boolean.TRUE.equals(d.isVisible())) {
			Thread.sleep(1000);
		}
		System.exit(0);
	}
}

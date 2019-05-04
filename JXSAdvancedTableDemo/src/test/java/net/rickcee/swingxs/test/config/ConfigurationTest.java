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
 * Created on Mar 18, 2012
 *
 */
package net.rickcee.swingxs.test.config;

import java.awt.Color;
import java.io.File;
import java.net.URL;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanConfigUtils;
import net.rickcee.swingxs.config.BeanManager;
import net.rickcee.swingxs.demo.model.Trade;
import net.rickcee.swingxs.model.BeanAttributeConfig;
import net.rickcee.swingxs.model.BeanConfig;
import net.rickcee.swingxs.utils.Constants;

/**
 * @author RickCeeNet
 * 
 */
public class ConfigurationTest {

	private Class<?> clazz = Trade.class;

	@BeforeMethod
	public void init() {
		Constants.CONFIG_FILE_EXTENSION = ".test.cfg";
	}

	@Test
	public void testConfiguration() throws Exception {
		// 1st generate BeanConfig object from the class
		BeanConfig cfg = BeanConfigUtils.getConfigurationFromClass(clazz);

		// Then modify some attributes...
		BeanAttributeConfig bac = BeanConfigUtils.getAttributeConfig(cfg, "quantity");
		bac.setColorBackground(Color.RED);
		bac = BeanConfigUtils.getAttributeConfig(cfg, "price");
		bac.setColorForeground(Color.BLUE);

		// Convert to XML
		String xml = BeanConfigUtils.getXs().toXML(cfg);
		System.out.println(xml);

		URL fileLocation = BeanConfigUtils.getHomeFileLocation(clazz);

		// Set user and master file to the same location
		BeanManager.setUserFileLocation(clazz, fileLocation);
		BeanManager.setMasterFileLocation(clazz, fileLocation);
		System.out.println(BeanManager.getUserFileLocation(clazz));

		// Save current configuration
		BeanConfigUtils.saveConfig(cfg, false);

		// Load previously saved configuration
		BeanConfigUtils.loadUserConfiguration(clazz);

		// Get the configuration previously loaded
		BeanAttributeContainer container = BeanManager.get(clazz);

		// Make sure the previously loaded data matches
		Color background = container.getFieldBackgroundColor().get("quantity");
		Color foreground = container.getFieldForegroundColor().get("price");
		Assert.assertEquals(background, Color.RED, "Background color for Quantity");
		Assert.assertEquals(foreground, Color.BLUE, "Foreground color for Price");

		// Delete the test configuration file.
		File config = new File(fileLocation.toURI());
		try {
			config.delete();
		} catch (Exception e) {
			System.out.println("Cannot delete: " + config.getAbsolutePath());
		}
	}

}

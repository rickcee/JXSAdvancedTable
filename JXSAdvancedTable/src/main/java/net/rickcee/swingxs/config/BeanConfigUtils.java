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
 * Created on Jan 24, 2012
 * 
 */
package net.rickcee.swingxs.config;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

import net.rickcee.swingxs.exceptions.JXSException;
import net.rickcee.swingxs.model.BeanAttributeConfig;
import net.rickcee.swingxs.model.BeanConfig;
import net.rickcee.swingxs.model.DateFormatConfig;
import net.rickcee.swingxs.model.JXSTableContants;
import net.rickcee.swingxs.model.NumberFormatConfig;
import net.rickcee.swingxs.model.SortConfig;
import net.rickcee.swingxs.ui.JXSAdvancedTable;
import net.rickcee.swingxs.ui.JXSTable;
import net.rickcee.swingxs.utils.Constants;

/**
 * @author RickCeeNet
 * 
 */
public abstract class BeanConfigUtils {

	private static final Logger logger = LoggerFactory.getLogger(BeanConfigUtils.class);
	private static final XStream xs = new XStream();

	static {
		xs.alias("config", net.rickcee.swingxs.model.BeanConfig.class);
		xs.alias("attribute", net.rickcee.swingxs.model.BeanAttributeConfig.class);
	}

	/**
	 * Method that will return an instance of BeanConfig containing the current
	 * configuration of the table specified as parameter. The BeanConfig
	 * instance can be used to persist the configuration into disk.
	 * 
	 * @param _table
	 *            The instance of the JXSTable class we want to process.
	 * @return
	 */
	public static final BeanConfig getCurrentConfig(JXSAdvancedTable<?> advancedTable) {
		BeanConfig config = new BeanConfig();
		
		JXSTable<?> table = advancedTable.getTable();
		JXSTable<?> frozenTable = advancedTable.getTableFrozen();

		// The index of the column whose header was clicked
		String colName = null, format = null, attName = null;
		Color colorBg = null, colorFg = null;
		Integer align = null, width = null, position = -1;
		Boolean visible, fixed = null;
		NumberFormatConfig nfc = null;
		DateFormatConfig dfc = null;
		Collection<SortConfig> sortConfig = null;
		config = new BeanConfig();

		TableModel model = table.getModel();
		Integer totalCount = model.getColumnCount();
		logger.debug("Column Count: " + totalCount);

		// Model Index / Table Index
		Map<Integer, Integer> visibilityMap = new HashMap<Integer, Integer>();
		// Model Index / Column Name
		Map<Integer, String> nameMap = new HashMap<Integer, String>();

		for (TableColumn c : table.getColumns(true)) {
			nameMap.put(c.getModelIndex(), model.getColumnName(c.getModelIndex()));
		}
		
		for (TableColumn c : frozenTable.getColumns(true)) {
			nameMap.put(c.getModelIndex(), model.getColumnName(c.getModelIndex()));
		}
		
		logger.debug("==================================================");

		int ix = 0;
		for (TableColumn c : frozenTable.getColumns(false)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Model Index: " + c.getModelIndex() + "/ Table Index: " + ix + "/ Width: " + c.getWidth()
						+ "/Name:" + model.getColumnName(c.getModelIndex()) + "/");
			}
			visibilityMap.put(c.getModelIndex(), ix);
			ix++;
		}
		for (TableColumn c : table.getColumns(false)) {
			if (logger.isDebugEnabled()) {
				logger.debug("Model Index: " + c.getModelIndex() + "/ Table Index: " + ix + "/ Width: " + c.getWidth()
						+ "/Name:" + model.getColumnName(c.getModelIndex()) + "/");
			}
			visibilityMap.put(c.getModelIndex(), ix);
			ix++;
		}

		logger.debug("==================================================");

		BeanAttributeContainer bac = null;
		bac = BeanManager.get(table.getClazz());
		sortConfig = bac.getSortCriteria().values();

		BeanAttributeConfig baConfig = null;
		Integer lastVisibleIndex = ix;
		for (Integer modelIndex : nameMap.keySet()) {
			baConfig = new BeanAttributeConfig();

			colName = nameMap.get(modelIndex);
			attName = bac.getFieldReverseLabel().get(colName);
			align = bac.getFieldAlignment().get(attName);
			colorBg = bac.getFieldBackgroundColor().get(attName);
			colorFg = bac.getFieldForegroundColor().get(attName);
			fixed = bac.getFieldFixed().get(attName);
			width = bac.getFieldWidth().get(attName);
			format = bac.getFieldFormat().get(attName);
			dfc = bac.getFieldDateFormat().get(attName);
			nfc = bac.getFieldNumberFormat().get(attName);

			visible = visibilityMap.get(modelIndex) != null ? true : false;
			position = visibilityMap.get(modelIndex) != null ? visibilityMap.get(modelIndex) : lastVisibleIndex++;

			baConfig.setName(attName);
			baConfig.setColorBackground(colorBg);
			baConfig.setColorForeground(colorFg);
			baConfig.setFixed(fixed);
			baConfig.setLabel(colName);
			baConfig.setWidth(width);
			baConfig.setAlignment(align);
			baConfig.setFormat(format);
			baConfig.setVisible(visible);
			baConfig.setPosition(position);
			baConfig.setDateFormat(dfc);
			baConfig.setNumberFormat(nfc);
			config.getAttributes().add(baConfig);
		}

		// logger.info(xs.toXML(config));
		config.getSorters().addAll(sortConfig);
		config.setClazz(table.getClazz());
		return config;
	}

	/**
	 * Utility method to save an XML representation of the BeanConfig object
	 * containing the user's configuration of the table.
	 * 
	 * @param config
	 *            The BeanConfig object representing the configuration of the
	 *            table.
	 * @throws Exception
	 *             In case of any error.
	 */
	public static final void saveConfig(BeanConfig config, Boolean isMaster) throws Exception {
		String xml = xs.toXML(config);
		URL file;
		if (isMaster) {
			file = getHomeMasterFileLocation(config.getClazz());
		} else {
			file = getUserFileLocation(config.getClazz());
		}
		logger.info("Saving XML Config [" + file + "]\n");
		if (logger.isDebugEnabled()) {
			logger.debug("\n" + xml);
		}
		FileOutputStream fos = new FileOutputStream(new File(file.toURI()));
		fos.write(xml.getBytes());
		fos.close();
	}

	/**
	 * Utility method to allocate the user's config file either in the
	 * customized location or in the user's home directory.
	 * 
	 * @param clazz
	 *            The class to process.
	 * @return
	 * @throws Exception
	 *             In case of any error.
	 */
	protected static URL getUserFileLocation(Class<?> clazz) throws Exception {
		URL fileLocation = BeanManager.getUserFileLocation(clazz);
		if (fileLocation == null) {
			return getHomeFileLocation(clazz);
		} else {
			return fileLocation;
		}
		
	}

	/**
	 * Utility method to allocate the user's config file in it's home directory.
	 * 
	 * @param clazz
	 *            The class to process.
	 * @return The URL object representing the location of the user's config
	 *         file.
	 * @throws MalformedURLException
	 *             If the URL object cannot be constructed.
	 */
	public static URL getHomeFileLocation(Class<?> clazz) throws MalformedURLException {
		return getFileLocation(clazz, false);
	}

	/**
	 * Utility method to allocate the master config file.
	 * 
	 * @param clazz
	 *            The class to process.
	 * @return The URL object representing the location of the master config
	 *         file.
	 * @throws MalformedURLException
	 *             If the URL object cannot be constructed.
	 */
	public static URL getHomeMasterFileLocation(Class<?> clazz) throws MalformedURLException {
		return getFileLocation(clazz, true);
	}
	
	/**
	 * @param clazz
	 *            The class to process.
	 * @param isMasterFile
	 *            If we should locate the master config file
	 * @return
	 * @throws MalformedURLException
	 *             If the URL object cannot be constructed.
	 */
	public static URL getFileLocation(Class<?> clazz, Boolean isMasterFile) throws MalformedURLException {
		String home = System.getProperty("user.home");
		if (home == null) {
			throw new RuntimeException("Invalid HOME directory!");
		}
		String file;
		if (isMasterFile) {
			file = home + File.separator + clazz.getSimpleName() + "Master" + Constants.CONFIG_FILE_EXTENSION;
		} else {
			file = home + File.separator + clazz.getSimpleName() + Constants.CONFIG_FILE_EXTENSION;
		}
		URL result = new File(file).toURI().toURL();
		return result;
	}

	/**
	 * Utility method that represents the full mechanism to load the user's
	 * configuration file into the framework's configuration schema. The
	 * following steps are performed:
	 * 
	 * 1) Verify Master Configuration File 
	 * 2) Verify User Configuration File 
	 * 3a) If User Configuration File doesn't exist, then copy Master file. 
	 * 3b) If it does exist, then compare to Master to detect updates and merge if necessary. 
	 * 4) Process the User Configuration File
	 * 
	 * @param clazz
	 *            The class to process
	 * @throws Exception
	 *             In case of any error.
	 */
	public static void loadUserConfiguration(Class<?> clazz) throws Exception {
		File masterConfig = checkMasterConfigFile(clazz);
		File userConfig = checkUserConfigFile(clazz);
		if (!userConfig.exists()) {
			logger.info("Copying Master file to user's directory: ["
					+ masterConfig.getAbsolutePath() + "] -> [" + userConfig
					+ "]");
			copyMasterConfigFile(masterConfig, userConfig);
			logger.info("Setting default master file to ["
					+ userConfig.getAbsolutePath() + "]");
			BeanManager.setMasterFileLocation(clazz, masterConfig.toURI()
					.toURL());
		} else {
			if (masterConfig.lastModified() > userConfig.lastModified()) {
				logger.info("Master file is more recent, proceding to merge.");
				mergeConfigFiles(masterConfig, userConfig);
			}
		}
		
		try {
			// If there is an issue loading the user's config file, then restore
			// the master file.
			logger.info("Processing user config file: [" + userConfig.getAbsolutePath() + "]");
			processConfiguration(clazz, userConfig);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("Attempting to delete user's config file. Cause: " + e.getMessage());
			// Try to delete the invalid file and then rerun
			if (userConfig.delete()) {
				loadUserConfiguration(clazz);
				logger.info("File deleted: " + userConfig.getName());
			}
			// Contact Support
		}
	}

	/**
	 * Utility method to read the user's config file into the framework
	 * configuration schema.
	 * 
	 * @param userConfig
	 *            The File object representing the location of the user's config
	 *            file.
	 * @param clazz
	 *            The class to process.
	 * @throws Exception
	 *             In case of any error.
	 */
	private static void processConfiguration(Class<?> clazz, File userConfig) throws Exception {
		// Read configuration from User File
		BeanConfig bc = (BeanConfig) xs.fromXML(new FileInputStream(userConfig));
		// Process class-specific configuration
		BeanConfigProcessor.processClass(clazz);
		// Get the generated container for further processing
		processConfig(clazz, bc);
		// BeanConfigProcessor.processClass(clazz);
		logger.info("Processed Class: " + clazz.getSimpleName());
	}

	/**
	 * Utility method that will store the information located in the BeanConfig
	 * object into the framework's configuration schema.
	 * 
	 * @param class The class to process
	 * @param bc
	 *            The BeanConfig object representing the current table
	 *            configuration.
	 */
	public static void processConfig(Class<?> clazz, BeanConfig bc) {
		BeanAttributeContainer container = BeanManager.get(clazz);
		for (BeanAttributeConfig bac : bc.getAttributes()) {
			if (bac.getAlignment() != null) {
				container.getFieldAlignment().put(bac.getName(), bac.getAlignment());
			} else {
				container.getFieldAlignment().put(bac.getName(), JXSTableContants.DEFAULT_COLUMN_ALIGNMENT);
			}
			if (bac.getColorBackground() != null) {
				container.getFieldBackgroundColor().put(bac.getName(), bac.getColorBackground());
			}
			if (bac.getColorForeground() != null) {
				container.getFieldForegroundColor().put(bac.getName(), bac.getColorForeground());
			}
			if (bac.getFixed() != null) {
				container.getFieldFixed().put(bac.getName(), bac.getFixed());
			} else {
				container.getFieldFixed().put(bac.getName(), JXSTableContants.DEFAULT_COLUMN_FREEZE);
			}
			if (bac.getVisible() != null) {
				container.getFieldVisibility().put(bac.getName(), bac.getVisible());
			} else {
				container.getFieldVisibility().put(bac.getName(), JXSTableContants.DEFAULT_COLUMN_VISIBILITY);
			}
			if (bac.getWidth() != null) {
				container.getFieldWidth().put(bac.getName(), bac.getWidth());
			} else {
				container.getFieldWidth().put(bac.getName(), JXSTableContants.DEFAULT_COLUMN_WIDTH);
			}
			if (bac.getPosition() != null) {
				container.getFieldPosition().put(bac.getPosition(), bac.getName());
				container.getFieldReversePosition().put(bac.getName(), bac.getPosition());
			}
			container.getFieldName().put(bac.getName(), bac.getName());
			container.getFieldLabel().put(bac.getName(), bac.getLabel());
			container.getFieldReverseLabel().put(bac.getLabel(), bac.getName());
			container.getFieldFormat().put(bac.getName(), bac.getFormat());
		}
		
		for (SortConfig sc : bc.getSorters()) {
			container.getSortCriteria().put(sc.getSortName(), sc);
		}
	}
	
	/**
	 * Inspects the attributes of the class specified as parameter and generated
	 * the appropriate BeanConfig object which holds the default configuration
	 * to apply to the JXSTable
	 * 
	 * @param clazz
	 *            The class to process
	 * @return The object representing the configuration of the JXSTable
	 * @throws Exception
	 *             In case of any error
	 */
	public static BeanConfig getConfigurationFromClass(Class<?> clazz) throws Exception {
		BeanConfig result = new BeanConfig();
		BeanAttributeContainer container = BeanManager.get(clazz);
		if (container == null) {
			BeanConfigProcessor.processClass(clazz);
		}
		container = BeanManager.get(clazz);
		Integer position = 0;
		for (String attName : container.getFieldName().keySet()) {
			BeanAttributeConfig bac = new BeanAttributeConfig();
			bac.setName(attName);
			bac.setColorBackground(null);
			bac.setColorForeground(null);
			bac.setFixed(JXSTableContants.DEFAULT_COLUMN_FREEZE);
			bac.setLabel(attName);
			bac.setWidth(JXSTableContants.DEFAULT_COLUMN_WIDTH);
			bac.setAlignment(JXSTableContants.DEFAULT_COLUMN_ALIGNMENT);
			bac.setFormat(null);
			bac.setVisible(JXSTableContants.DEFAULT_COLUMN_VISIBILITY);
			bac.setPosition(position);
			result.getAttributes().add(bac);
			position++;
		}
		result.setClazz(clazz);
		return result;
	}
	
	/**
	 * Utility method to iterate over a BeanConfig object and return the config
	 * object for the specified class attribute.
	 * 
	 * @param cfg
	 *            The object representing the configuration of the JXSTable
	 * @param attName
	 *            The name of the attribute to search for
	 * @return The BeanAttributeConfig objects which represents the current
	 *         configuration of the specified class attribute.
	 * @throws JXSException
	 *             If the specified attribute cannot be found.
	 */
	public static BeanAttributeConfig getAttributeConfig(BeanConfig cfg, String attName) throws JXSException {
		for (BeanAttributeConfig bac : cfg.getAttributes()) {
			if (attName != null && attName.equals(bac.getName())) {
				return bac;
			}
		}
		throw new JXSException("Cannot find attribute [" + attName + "] in configuration bean!");
	}

	/**
	 * Utility method intended to merge both Master and User configuration
	 * files.
	 * 
	 * @param masterFile
	 *            The File object representing the location of the Master
	 *            configuration file.
	 * @param userFile
	 *            The File object representing the location of the User's
	 *            configuration file.
	 * @throws Exception
	 *             In case of any error.
	 */
	private static void mergeConfigFiles(File masterFile, File userFile) throws Exception {
		FileUtils.copyFile(masterFile, userFile);
	}

	/**
	 * Utility method intended to copy the Master configuration file to the
	 * specified user configuration file.
	 * 
	 * @param masterFile
	 *            The File object representing the location of the Master
	 *            configuration file.
	 * @param userFile
	 *            The File object representing the location of the User's
	 *            configuration file.
	 * @throws Exception
	 *             In case of any error.
	 */
	private static void copyMasterConfigFile(File masterFile, File userFile) throws Exception {
		FileUtils.copyFile(masterFile, userFile);
	}

	/**
	 * Utility method intended to check the existence of the user's
	 * configuration file.
	 * 
	 * @param clazz
	 *            The class to process
	 * @return The file object representing the user configuration file.
	 * @throws Exception
	 *             If there is an error processing the config file.
	 */
	private static File checkUserConfigFile(Class<?> clazz) throws Exception {
		URL userFile = getUserFileLocation(clazz);
		File userConfig = new File(userFile.toURI());
		return userConfig;
	}

	/**
	 * Utility method intended to check the existence of the Master
	 * configuration file.
	 * 
	 * @param clazz
	 *            The class to process
	 * @return The File object representing the master configuration file.
	 * @throws JXSException
	 *             In case of any error.
	 */
	private static File checkMasterConfigFile(Class<?> clazz) throws JXSException {
		File file;
		URL fileLocation = BeanManager.getMasterFileLocation(clazz);
		if (fileLocation == null) {
			try {
				logger.info("Master file not set, generating default master file...");
				BeanConfig cfg = BeanConfigUtils.getConfigurationFromClass(clazz);
				URL masterFileLocation = BeanConfigUtils.getHomeMasterFileLocation(clazz);
				// Save current configuration
				BeanConfigUtils.saveConfig(cfg, true);
				logger.info("Default master file generated: " + masterFileLocation.toString());
				file = new File(masterFileLocation.toURI());
				return file;
			} catch (Exception e) {
				throw new JXSException("Master file for [" + clazz.getSimpleName() + "] was not set!");
			}
		}
		try {
			file = new File(fileLocation.toURI());
		} catch (URISyntaxException e) {
			throw new JXSException("Invalid Master file location: " + fileLocation);
		}
		if (!file.exists()) {
			throw new JXSException("Master file does not exist: " + file.getName());
		}
		return file;
	}

	/**
	 * @return the xs
	 */
	public static XStream getXs() {
		return xs;
	}

}

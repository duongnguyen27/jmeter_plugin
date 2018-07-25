package com.nashtech.jmeter.plugin.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class UserVariableLoader {

	Properties props = org.apache.jmeter.util.JMeterUtils.getJMeterProperties();
	private static final Logger log = LoggerFactory.getLogger(UserVariableLoader.class);

	public String getUserFilePath(JMeterVariables vars) {
		String scriptDir = FileServer.getFileServer().getBaseDir();
		String dataFolder;

		if (Boolean.parseBoolean((String) props.get("Debug"))) {
			dataFolder = (String) props.get("DataFolderDebug");
		} else {
			dataFolder = (String) props.get("DataFolderNonDebug");
		}

		String userFilePath = scriptDir + "\\" + dataFolder;
		return userFilePath;
	}

	public void LoadVariablesFromFile(JMeterVariables vars, String fileName, String folder, boolean saveToProps) {
		try {
			String filePath = folder + "\\" + fileName;
			log.info("Variable file path: " + filePath);
			File file = new File(filePath);
			log.info("Variable file exists: " + file.exists());
			if (!file.exists()) {
				log.info("File " + filePath + " not found");
				throw new Exception("ERROR: file " + filePath + " not found");
			}

			BufferedReader bufRdr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			String line = null;

			log.info("File content:");
			while ((line = bufRdr.readLine()) != null) {
				if (line != null && !line.isEmpty()) {
					log.info(line);
					String[] raw = line.split(",");
					String val = "";

					if (raw.length > 1) {
						val = raw[1];
					}

					if (saveToProps) {
						props.put(raw[0].trim(), val);
					} else {
						vars.put(raw[0].trim(), val);
					}
				}
			}

			bufRdr.close();
		} catch (Exception ex) {
			log.error("Exception while reading file: " + ex.toString());
			System.err.println(ex.getMessage());
		} catch (Throwable thex) {
			log.error("Exception while reading file: " + thex.toString());
			System.err.println(thex.getMessage());
		}
	}

	private ArrayList<String> getUserDefinedFileNames(JMeterVariables vars) {
		ArrayList<String> list = new ArrayList<String>();
		for (Map.Entry entry : vars.entrySet()) {
			String key = (String) entry.getKey();
			if (key.startsWith("UserDefinedFile")) {
				list.add(vars.get(key));
			}
		}
		return list;
	}

	/// Loads the user defined values from the CSV files. The filename should be
	/// declared in the jMeter user defined value section which allows to enter
	/// more than one. The prefix of the key should be "UserDefinedFile"
	/// followed by number
	public void LoadUserVariables(JMeterVariables vars, String folder) {
		for (String fileName : getUserDefinedFileNames(vars)) {
			LoadVariablesFromFile(vars, fileName, folder, false);
		}
	}

	/// Returns the total records in the CSV file as an Integer value excluding
	/// the header row
	public int GetTotalRecordsCount(JMeterVariables vars, String csvDataFile) {
		int recordCount = 0;
		try {
			String filePath = getUserFilePath(vars) + "\\" + csvDataFile;
			File file = new File(filePath);
			log.info("Data file path: " + filePath);
			if (!file.exists()) {
				log.info("File " + filePath + " not found");
				throw new Exception("ERROR: file " + csvDataFile + " not found");
			}

			BufferedReader bufRdr = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
			String line = null;

			while ((line = bufRdr.readLine()) != null) {
				recordCount++;
			}
			recordCount--;
			bufRdr.close();
		} catch (Exception ex) {
			log.error("Exception while reading file: " + ex.toString());
			System.err.println(ex.getMessage());
		} catch (Throwable thex) {
			log.error("Exception while reading file: " + thex.toString());
			System.err.println(thex.getMessage());
		}

		return recordCount;
	}

	public void InitProps(JMeterVariables vars, String testName, String numberOfUsersVarName, String dataFile,
			String rampUpPeriodVarName) {
		log.info("Initializing Properties for " + testName + " test.");
		if (!Boolean.parseBoolean(props.get(testName).toString())) {
			log.info(testName + " test is disabled.");
			props.put(numberOfUsersVarName, 0 + "");
		} else {
			if (dataFile != null && !dataFile.isEmpty()) {
				props.put(numberOfUsersVarName, Integer.toString(GetTotalRecordsCount(vars, dataFile)));
				log.info(numberOfUsersVarName + "=" + props.get(numberOfUsersVarName));
			}
			props.put(rampUpPeriodVarName, (Long.parseLong(vars.get(rampUpPeriodVarName)) / 1000) + "");
			log.info(rampUpPeriodVarName + "=" + props.get(rampUpPeriodVarName));
		}
	}
}

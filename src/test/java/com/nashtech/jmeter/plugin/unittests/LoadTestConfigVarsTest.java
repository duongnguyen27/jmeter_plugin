package com.nashtech.jmeter.plugin.unittests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.services.FileServer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nashtech.jmeter.plugin.common.Common;
import com.nashtech.jmeter.plugin.functions.LoadTestConfigVars;

public class LoadTestConfigVarsTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Common.setupJmeter();
	}

	@Test
	public void loadTestConfigVarsTest() throws Exception {
		Collection<CompoundVariable> parameters = new ArrayList<CompoundVariable>();
		parameters.add(new CompoundVariable("Test Config.csv"));
		parameters.add(new CompoundVariable(FileServer.getFileServer().getBaseDir() + "\\src\\test\\resources\\data"));
		LoadTestConfigVars instance = new LoadTestConfigVars();
		instance.setParameters(parameters);
		instance.execute(null, null);
		Properties props = org.apache.jmeter.util.JMeterUtils.getJMeterProperties();
		
		// Verify variables read and saved
		Assert.assertEquals("true", props.get("Debug"));
		Assert.assertEquals("data", props.get("DataFolderNonDebug"));
		Assert.assertEquals("data_debug", props.get("DataFolderDebug"));
		Assert.assertEquals(FileServer.getFileServer().getBaseDir() + "\\data_debug", props.get("UserFilePath"));
	}
}

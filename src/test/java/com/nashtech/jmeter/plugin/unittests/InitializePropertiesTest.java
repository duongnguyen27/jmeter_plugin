package com.nashtech.jmeter.plugin.unittests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nashtech.jmeter.plugin.common.Common;
import com.nashtech.jmeter.plugin.functions.InitializeProperties;

public class InitializePropertiesTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Common.setupJmeter();
	}

	@Test
	public void initializePropertiesTest_EnabledThread() throws Exception {
		Properties props = org.apache.jmeter.util.JMeterUtils.getJMeterProperties();
		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		props.put("Debug", "true");
		props.put("DataFolderDebug", "src\\test\\resources\\data");
		props.put("Testcase01", "true");
		vars.put("RampUpPeriod1", "3000");
		
		Collection<CompoundVariable> parameters = new ArrayList<CompoundVariable>();
		parameters.add(new CompoundVariable("Testcase01"));
		parameters.add(new CompoundVariable("TotalUsers"));
		parameters.add(new CompoundVariable("Accounts.csv"));
		parameters.add(new CompoundVariable("RampUpPeriod1"));
		InitializeProperties instance = new InitializeProperties();
		instance.setParameters(parameters);
		instance.execute(null, null);
		
		Assert.assertEquals("2", props.get("TotalUsers"));
		Assert.assertEquals("3", props.get("RampUpPeriod1"));
	}
	
	@Test
	public void initializePropertiesTest_DisabledThread() throws Exception {
		Properties props = org.apache.jmeter.util.JMeterUtils.getJMeterProperties();
		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		props.put("Debug", "true");
		props.put("DataFolderDebug", "src\\test\\resources\\data");
		props.put("Testcase01", "false");
		vars.put("RampUpPeriod2", "3000");
		
		Collection<CompoundVariable> parameters = new ArrayList<CompoundVariable>();
		parameters.add(new CompoundVariable("Testcase01"));
		parameters.add(new CompoundVariable("TotalUsers"));
		parameters.add(new CompoundVariable("Accounts.csv"));
		parameters.add(new CompoundVariable("RampUpPeriod2"));
		InitializeProperties instance = new InitializeProperties();
		instance.setParameters(parameters);
		instance.execute(null, null);
		
		Assert.assertEquals("0", props.get("TotalUsers"));
		Assert.assertEquals(null, props.get("RampUpPeriod2"));
	}
}

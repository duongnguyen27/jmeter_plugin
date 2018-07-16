package com.nashtech.jmeter.plugin.unittests;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nashtech.jmeter.plugin.common.Common;
import com.nashtech.jmeter.plugin.functions.LoadUserVars;

public class LoadUserVarsTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Common.setupJmeter();
	}

	@Test
	public void loadUserVarsTest() throws Exception {

		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		vars.put("UserDefinedFile01", "Variables01.csv");
		vars.put("UserDefinedFile02", "Variables02.csv");

		Collection<CompoundVariable> parameters = new ArrayList<CompoundVariable>();
		parameters.add(new CompoundVariable(FileServer.getFileServer().getBaseDir() + "\\src\\test\\resources\\data"));
		LoadUserVars instance = new LoadUserVars();
		instance.setParameters(parameters);
		instance.execute(null, null);

		// Verify user variables read and saved
		Assert.assertEquals("value1", vars.get("Var1"));
		Assert.assertEquals("value2", vars.get("Var2"));
		Assert.assertEquals("value3", vars.get("Var3"));
		Assert.assertEquals("value4", vars.get("Var4"));
	}
}

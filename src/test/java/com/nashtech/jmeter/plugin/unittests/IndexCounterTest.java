package com.nashtech.jmeter.plugin.unittests;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nashtech.jmeter.plugin.common.Common;
import com.nashtech.jmeter.plugin.functions.IndexCounter;

public class IndexCounterTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		Common.setupJmeter();
	}

	@Test
	public void indexCounterTest() throws Exception {
		JMeterVariables vars = JMeterContextService.getContext().getVariables();
		vars.put("Index", "1");
		
		Collection<CompoundVariable> parameters = new ArrayList<CompoundVariable>();
		parameters.add(new CompoundVariable("Index"));
		IndexCounter instance = new IndexCounter();
		instance.setParameters(parameters);
		instance.execute(null, null);
		
		Assert.assertEquals("2", vars.get("Index"));
	}

}

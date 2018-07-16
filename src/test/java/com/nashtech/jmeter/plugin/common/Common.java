package com.nashtech.jmeter.plugin.common;

import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;

public class Common {

	public static void setupJmeter() {
		String baseDir = FileServer.getFileServer().getBaseDir();
		JMeterUtils.loadJMeterProperties(baseDir + "\\src\\test\\resources\\jmeter\\jmeter.properties");
		JMeterContextService.getContext().setVariables(new JMeterVariables());
	}
}

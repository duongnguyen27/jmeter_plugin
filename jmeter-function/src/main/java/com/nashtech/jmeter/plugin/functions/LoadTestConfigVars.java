package com.nashtech.jmeter.plugin.functions;

import java.util.*;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.services.FileServer;
import org.apache.jmeter.threads.JMeterVariables;

public class LoadTestConfigVars extends AbstractFunction {

	private static final List<String> desc = new LinkedList<String>();

	private static final String KEY = "__loadTestConfigVars";

	static {
		desc.add("File name (default by ${TestConfigFile})");
		desc.add("Folder path (default by \\data folder)");
	}

	private Object[] values;

	public List<String> getArgumentDesc() {
		return desc;
	}

	public LoadTestConfigVars() {
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1) throws InvalidVariableException {

		JMeterVariables vars = getVariables();
		Properties props = org.apache.jmeter.util.JMeterUtils.getJMeterProperties();

		String fileName;
		String folder;
		
		if (values.length < 1) {
			fileName = vars.get("TestConfigFile");
		} else {
			fileName = ((CompoundVariable) values[0]).execute().trim();
		}

		if (values.length < 2) {
			folder = FileServer.getFileServer().getBaseDir() + "\\data";
		} else {
			folder = ((CompoundVariable) values[1]).execute().trim();
		}

		UserVariableLoader userVariableLoader = new UserVariableLoader();
		userVariableLoader.LoadVariablesFromFile(vars, fileName, folder, true);
		
		// Store variables to use cross the thread groups		
		props.put("UserFilePath", userVariableLoader.getUserFilePath(vars));
		
		return "";
	}

	@Override
	public String getReferenceKey() {
		return KEY;
	}

	@Override
	public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
		// TODO Auto-generated method stub
		values = parameters.toArray();
	}
}

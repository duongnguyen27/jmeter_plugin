package com.nashtech.jmeter.plugin.functions;

import java.util.*;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

public class LoadVarsFromFile extends AbstractFunction {

	private static final List<String> desc = new LinkedList<String>();

	private static final String KEY = "__loadVarsFromFile";

	static {
		desc.add("File name");
		desc.add("Folder path");
		desc.add("Save variables to Jmeter properties (false as default)");
	}

	private Object[] values;

	public List<String> getArgumentDesc() {
		return desc;
	}

	public LoadVarsFromFile() {
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1) throws InvalidVariableException {

		JMeterVariables vars = getVariables();

		String fileName = ((CompoundVariable) values[0]).execute().trim();
		String folder = ((CompoundVariable) values[1]).execute().trim();
		String saveToProps;
		if (values.length > 2) {
			saveToProps = ((CompoundVariable) values[2]).execute().trim();
		} else {
			saveToProps = "false";
		}

		UserVariableLoader userVariableLoader = new UserVariableLoader();
		userVariableLoader.LoadVariablesFromFile(vars, fileName, folder, Boolean.parseBoolean(saveToProps));
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

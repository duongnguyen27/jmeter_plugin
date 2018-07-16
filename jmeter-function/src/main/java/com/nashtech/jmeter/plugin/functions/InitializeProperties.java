package com.nashtech.jmeter.plugin.functions;

import java.util.*;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

public class InitializeProperties extends AbstractFunction {
	private static final List<String> desc = new LinkedList<String>();

	private static final String KEY = "__initProps";

	static {
		desc.add("Test name");
		desc.add("Number of users variable name");
		desc.add("Data file name");
		desc.add("Ramp-up period variable name");
	}

	private Object[] values;

	public List<String> getArgumentDesc() {
		return desc;
	}

	public InitializeProperties() {
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1) throws InvalidVariableException {

		JMeterVariables vars = getVariables();

		String testName = ((CompoundVariable) values[0]).execute().trim();
		String numberOfUsersVarName = ((CompoundVariable) values[1]).execute().trim();
		String dataFile = ((CompoundVariable) values[2]).execute().trim();
		String rampUpPeriodVarName = ((CompoundVariable) values[3]).execute().trim();
		
		UserVariableLoader userVariableLoader = new UserVariableLoader();
		userVariableLoader.InitProps(vars, testName, numberOfUsersVarName, dataFile, rampUpPeriodVarName);

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

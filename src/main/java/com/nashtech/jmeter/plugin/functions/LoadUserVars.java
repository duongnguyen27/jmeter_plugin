package com.nashtech.jmeter.plugin.functions;

import java.util.*;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

public class LoadUserVars extends AbstractFunction {

	private static final List<String> desc = new LinkedList<String>();

	private static final String KEY = "__loadUserVars";

	static {
		desc.add("Folder path that contains User Variable files (Optional)");
	}

	private Object[] values;

	public List<String> getArgumentDesc() {
		return desc;
	}

	public LoadUserVars() {
	}

	@Override
	public String execute(SampleResult arg0, Sampler arg1) throws InvalidVariableException {

		JMeterVariables vars = getVariables();
		UserVariableLoader userVariableLoader = new UserVariableLoader();
		String folder;
		
		if (values.length == 0) {
			folder = userVariableLoader.getUserFilePath(vars);
		} else {
			folder = ((CompoundVariable) values[0]).execute().trim();
		}		
		
		userVariableLoader.LoadUserVariables(vars, folder);

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

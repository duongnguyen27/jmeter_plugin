package com.nashtech.jmeter.plugin.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;

public class IndexCounter extends AbstractFunction {

	    private static final List<String> desc = new LinkedList<String>();

	    private static final String KEY = "__indexCounter";

	    static {
	        desc.add("Index");
	    }

	    private Object[] values;

		public List<String> getArgumentDesc() {
			return desc;
		}
		
		public IndexCounter() {}

		@Override
		public String execute(SampleResult arg0, Sampler arg1) throws InvalidVariableException {
			
			JMeterVariables vars = getVariables();
			
			String str = ((CompoundVariable) values[0]).execute().trim();
			
			int index = Integer.parseInt(vars.get(str));
			index++;
			
			vars.put(str, Integer.toString(index));			
			
			return Integer.toString(index);
		}

		@Override
		public String getReferenceKey() {
			return KEY;
		}

		@Override
		public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
			values = parameters.toArray();			
		}
}

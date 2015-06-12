package com.ericsson.jcat.jcatwebapp.service;

import java.util.ArrayList;
import java.util.List;

import com.ericsson.axe.jcat.rm.tass.configdb.ConfigdbAxePortType;
import com.ericsson.axe.jcat.rm.tass.configdb.ConfigdbAxeWsService;
import com.ericsson.axe.jcat.rm.tass.configdb.ConfigdbFault;
import com.ericsson.axe.jcat.rm.tass.configdb.NoSuchTestbedFault;
import com.ericsson.axe.jcat.rm.tass.configdb.TestbedDataTypeV1;

public class TassProvider {
	public static java.util.List<String> getTestbedsList() throws ConfigdbFault, NoSuchTestbedFault{
		ConfigdbAxePortType configdbPort = new ConfigdbAxeWsService().getConfigdbAxeWsPort();
		List<String> testbeds = configdbPort.getTestbeds();
		List<String> tbl = new ArrayList<String>(); 
		for (String tb : testbeds) {
			TestbedDataTypeV1 tv = configdbPort.getTestbedDataV1(tb);
			if (tv.getTestbed().getUser() != null && tv.getTestbed().getUser().equalsIgnoreCase("jcatcloud")) {
				tbl.add(tb);
			}
		}
		return tbl;
	}
}

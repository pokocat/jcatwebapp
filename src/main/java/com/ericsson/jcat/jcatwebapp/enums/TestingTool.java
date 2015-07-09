package com.ericsson.jcat.jcatwebapp.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

public enum TestingTool {
	RemotePC, Tgen, MgwSim, Client4, Peip, Winfiol, MCST, SEA, JCAT, Putty, FTP, SFTP, BGWrpc;

	public enum TestingToolGroup {
		Basic(Arrays.asList(RemotePC)), 
		TrafficGenerator(Arrays.asList(Tgen, MgwSim, Client4, Peip)), 
		SingleProcess(Arrays.asList(FTP, SFTP, BGWrpc)), 
		General(Arrays.asList(Winfiol, MCST, SEA,JCAT, Putty));

		public List<TestingTool> ttSet;

		private TestingToolGroup(List<TestingTool> ttSet) {
			this.setTtSet(ttSet);
		}

		public List<TestingTool> getTtSet() {
			return ttSet; 
		}

		public void setTtSet(List<TestingTool> ttSet) {
			this.ttSet = ttSet;
		}

	}
}
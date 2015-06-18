package com.ericsson.jcat.jcatwebapp.testenv;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.ericsson.jcat.jcatwebapp.cusom.TestingTool;
import com.ericsson.jcat.jcatwebapp.cusom.TrafficGenerator;

@RunWith(MockitoJUnitRunner.class)
public class TestEnvControllerTest {

	@InjectMocks
	private TestEnvController tec = new TestEnvController();

	@Mock
	private TestEnvRepository ter;

	@Mock
	private StpInfoRepository sir;

	@Test
	public void testTestEnvRepo() {
		tec.init();
		verify(ter, times(3)).save(any(TestEnv.class));
		System.out.println("Passed!");
	}

	@Test
	public void testTestEnvDetails(){
		TestEnv testTE = new TestEnv("TestENV", "Isn't this demo fantacy? Created by me.", "admin", "JCAT",
				true, "centos_pure", "057f17e8-7192-4dac-bac7-c60ead3e9db1", null, new ArrayList<TrafficGenerator>(
						Arrays.asList(TrafficGenerator.Client4)), "", new ArrayList<TestingTool>(Arrays
						.asList(TestingTool.JCAT)), "stp777", "tp999ap1.axe.k2.ericsson.se", "expertuser",
				"expertpass", "customeruser", "customeruser");
		when(ter.findById(0)).thenReturn(testTE);
		System.out.println(""+ testTE.getToolList().add(new TestTool()));
		
		
	}
}

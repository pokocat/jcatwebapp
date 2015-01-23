package com.ericsson.jcat.jcatwebapp.service;

import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerCreationException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerExecutionException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerRunningException;
import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerStartingException;

public interface DockerService {
	String createTgen(String name) throws ContainerExecutionException;
}

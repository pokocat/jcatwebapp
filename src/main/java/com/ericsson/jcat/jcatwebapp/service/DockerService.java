package com.ericsson.jcat.jcatwebapp.service;

import com.ericsson.axe.jcat.docker.adapter.exceptions.ContainerExecutionException;

public interface DockerService {
	String createTgen(String name) throws ContainerExecutionException;
}

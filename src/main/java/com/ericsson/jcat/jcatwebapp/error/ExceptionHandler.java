package com.ericsson.jcat.jcatwebapp.error;

import org.openstack4j.api.exceptions.OS4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.github.dockerjava.api.DockerException;
import com.ericsson.jcat.jcatwebapp.support.web.CustomResultJson;
import com.google.common.base.Throwables;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Handle exceptions thrown by handlers.
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
	public ModelAndView exception(Exception exception, WebRequest request) {
		logger.error(Throwables.getStackTraceAsString(exception));
		ModelAndView modelAndView = new ModelAndView("error/general");
		modelAndView.addObject("errorMessage", Throwables.getStackTraceAsString(exception));
		return modelAndView;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(value = NullPointerException.class)
	@ResponseBody
	public CustomResultJson nullPointerException(Exception exception, WebRequest request) {
		logger.error(Throwables.getStackTraceAsString(exception));
		return new CustomResultJson("failed", exception.getLocalizedMessage(), Throwables.getStackTraceAsString(exception));
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(value = OS4JException.class)
	@ResponseBody
	public CustomResultJson OS4JException(Exception exception, WebRequest request) {
		logger.error(Throwables.getStackTraceAsString(exception));
		return new CustomResultJson("failed", "Reason: " + exception.getLocalizedMessage(), Throwables.getStackTraceAsString(exception));
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(value = DockerException.class)
	@ResponseBody
	public CustomResultJson DockerException(Exception exception, WebRequest request) {
		logger.error(Throwables.getStackTraceAsString(exception));
		return new CustomResultJson("failed", "Reason: " + exception.getLocalizedMessage(), Throwables.getStackTraceAsString(exception));
	}

}

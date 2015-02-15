package com.ericsson.jcat.jcatwebapp.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

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
		logger.error(exception.getMessage());
		ModelAndView modelAndView = new ModelAndView("error/general");
		modelAndView.addObject("errorMessage", Throwables.getStackTraceAsString(exception));
		return modelAndView;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(value = NullPointerException.class)
	@ResponseBody
	public Exception nullPointerException(Exception exception, WebRequest request) {
		logger.error(exception.getMessage());
		return exception;
	}

}

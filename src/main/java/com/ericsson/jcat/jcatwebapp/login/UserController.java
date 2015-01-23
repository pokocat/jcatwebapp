package com.ericsson.jcat.jcatwebapp.login;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ericsson.jcat.jcatwebapp.account.Account;
import com.ericsson.jcat.jcatwebapp.account.AccountRepository;
import com.ericsson.jcat.jcatwebapp.account.UserGroup;
import com.ericsson.jcat.jcatwebapp.account.UserGroupRepository;
import com.ericsson.jcat.jcatwebapp.account.UserService;
import com.ericsson.jcat.jcatwebapp.support.web.MessageHelper;

@Controller
public class UserController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String USERSERVICE_VIEW_NAME = "signin/login";

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserGroupRepository userGroupRepository;

	@Autowired
	private UserService userService;

	@ModelAttribute("allGroups")
	public List<UserGroup> populateGroups() {
		return userGroupRepository.findAll();
	}

	@RequestMapping(value = "signup")
	public ModelAndView signup(Model model) {
		model.addAttribute(new SignupForm());
		ModelAndView mav = new ModelAndView();
		mav.setViewName(USERSERVICE_VIEW_NAME);
		mav.addObject("action", "signup");
		return mav;
	}

	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
		/*
		 * if (errors.hasErrors()) {
		 * logger.error(errors.toString());
		 * return account;
		 * }
		 */
		logger.info("====> New User: " + signupForm.toString());
		Account account = accountRepository.save(signupForm.createAccount());
		userService.signin(account);
		MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "/";
	}

	@RequestMapping(value = "login")
	public ModelAndView login(Model model) {
		model.addAttribute(new SignupForm());
		ModelAndView mav = new ModelAndView();
		mav.setViewName(USERSERVICE_VIEW_NAME);
		mav.addObject("action", "login");
		return mav;
	}
}

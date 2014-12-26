package com.ericsson.jcat.jcatwebapp.um;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ericsson.jcat.jcatwebapp.account.AccountRepository;

@Controller
@RequestMapping("/UserManager")
class UserManagementController {
	private AccountRepository accountRepo;
	
	@Autowired
	public UserManagementController( AccountRepository accountRepo) {
		this.accountRepo = accountRepo;		
	}
	
	@RequestMapping("/list")
	public String getUserList(Model model){
		model.addAttribute("users",accountRepo.findAll());
		return "usermanager/list-users";
	}
}

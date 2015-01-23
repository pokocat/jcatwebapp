package com.ericsson.jcat.jcatwebapp.um;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.crypto.tls.ContentType;
import org.openstack4j.core.transport.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ericsson.jcat.jcatwebapp.account.Account;
import com.ericsson.jcat.jcatwebapp.account.AccountRepository;
import com.ericsson.jcat.jcatwebapp.account.UserGroup;
import com.ericsson.jcat.jcatwebapp.account.UserGroupRepository;

@Controller
@RequestMapping("/UserManager")
class UserManagementController {
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private UserGroupRepository groupRepository;

	@ModelAttribute("fatherpage")
	public String module() {
		return "usermanager";
	}

	@ModelAttribute("groups")
	public List<UserGroup> getGroups() {
		return groupRepository.findAll();
	}

	@RequestMapping("/overview")
	public String getOverView(Model model) {
		model.addAttribute("page", "overview");
		model.addAttribute("users", accountRepo.findAll());
		model.addAttribute("groups", groupRepository.findAll());
		return "usermanager/overview";
	}

	@RequestMapping("/userlist")
	public String getUserList(Model model) {
		model.addAttribute("page", "userlist");
		model.addAttribute("users", accountRepo.findAll());
		return "usermanager/list-users";
	}

	@RequestMapping("/grouplist")
	public String getGroupList(Model model) {
		model.addAttribute("page", "grouplist");
		model.addAttribute("groups", groupRepository.findAll());
		return "usermanager/list-groups";
	}

	@RequestMapping(value = "/user/update/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody Account userUpdate) {
		//TODO response with error callback in .ajax of Jquery.
		System.out.println(userUpdate.toString());
		Account accountToUpdate = accountRepo.findByUserId(id);
		if (userUpdate.getGroupRole() != null && !userUpdate.getGroupRole().isEmpty()) {
			accountToUpdate.setGroupRole(userUpdate.getGroupRole());
		}
		if (userUpdate.getUserGroup() != null && !userUpdate.getUserGroup().isEmpty()) {
			accountToUpdate.setUserGroup(userUpdate.getUserGroup());
		}
		accountRepo.update(accountToUpdate);
		System.out.println(accountToUpdate.toString());
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
}

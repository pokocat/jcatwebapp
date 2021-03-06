package com.ericsson.jcat.jcatwebapp.um;

import java.util.ArrayList;
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
	public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody AccountUpdateData userUpdate) {
		// TODO response with error callback in .ajax of Jquery.
		Account accountToUpdate = accountRepo.findByUserId(id);
		if (userUpdate.getGroupRole() != null && !userUpdate.getGroupRole().isEmpty()) {
			accountToUpdate.setGroupRole(userUpdate.getGroupRole());
		}
		if (userUpdate.getUserGroup() != null && !userUpdate.getUserGroup().isEmpty()) {
			List<UserGroup> ugList = new ArrayList<UserGroup>();
			for (String ugName : userUpdate.getUserGroup()) {
				ugList.add(groupRepository.findByName(ugName));
			}
			accountToUpdate.setUserGroup(ugList);
		}
		accountRepo.update(accountToUpdate);
		System.out.println(accountToUpdate.toString());
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	@RequestMapping(value = "/group/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> addUserGroup(@RequestBody UserGroup userGroup) {
		UserGroup userGroupToAdd = groupRepository.save(userGroup);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/group/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> addUserGroup(@PathVariable long id) {
		groupRepository.deleteById((int) id);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
}

class AccountUpdateData {
	List<String> userGroup;
	String groupRole;

	public List<String> getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(List<String> userGroup) {
		this.userGroup = userGroup;
	}

	public String getGroupRole() {
		return groupRole;
	}

	public void setGroupRole(String groupRole) {
		this.groupRole = groupRole;
	}

}

package at.fh.swenga.jpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.UserDao;
import at.fh.swenga.jpa.dao.UserRoleDao;
import at.fh.swenga.jpa.model.User;
import at.fh.swenga.jpa.model.UserRole;

@Controller
public class SecurityController {
	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao userRoleDao;

	@RequestMapping(value = "/adduser", method = RequestMethod.GET)
	public String addUserGet(Model model) {
		List<User> myUsers = userDao.findAll();
		model.addAttribute("users", myUsers);
		
		return "createUser";

	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	@Transactional
	public String addUserPost(@RequestParam(value = "isAdmin", required = false) Boolean isAdmin,
			@Valid User newUserModel, BindingResult bindingResult, Model model) {
		User newUser = new User(newUserModel.getUserName(), newUserModel.getPassword(), true);
		newUser.encryptPassword();
		if (isAdmin != null) {
			UserRole adminRole = userRoleDao.getRole("ROLE_ADMIN");
			UserRole userRole = userRoleDao.getRole("ROLE_USER");
			newUser.addUserRole(userRole);
			newUser.addUserRole(adminRole);
		} else {
			UserRole userRole = userRoleDao.getRole("ROLE_USER");
			newUser.addUserRole(userRole);
		}
		userDao.persist(newUser);
		return "redirect:adduser";
	}

	@RequestMapping("/fillUsers")
	@Transactional
	public String fillData(Model model) {

		UserRole adminRole = userRoleDao.getRole("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRole("ROLE_ADMIN");

		UserRole userRole = userRoleDao.getRole("ROLE_USER");
		if (userRole == null)
			userRole = new UserRole("ROLE_USER");

		User admin = new User("admin", "password", true);
		admin.encryptPassword();
		admin.addUserRole(userRole);
		admin.addUserRole(adminRole);
		userDao.persist(admin);

		User user = new User("user", "password", true);
		user.encryptPassword();
		user.addUserRole(userRole);
		userDao.persist(user);

		return "forward:login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String handleRegisterGet(Model model) {
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@Transactional
	public String handleRegisterPost(@RequestParam String username, @RequestParam String password,
			@RequestParam String passwordRetype, Model model) {
		if (!username.isEmpty() || !password.isEmpty()) {
			if(password.equals(passwordRetype))
			{
				if (!userDao.findByUsername(username).isEmpty())
				{
					model.addAttribute("errorMessage", "User already exists!");
					return "register";
				}else {
					User newUser = new User(username, password, true);
					newUser.encryptPassword();
					UserRole userRole = userRoleDao.getRole("ROLE_USER");
					newUser.addUserRole(userRole);
					userDao.persist(newUser);
					model.addAttribute("message", "Registration Succesful");
					return "forward:login";
				}
			}else {
				model.addAttribute("errorMessage", "Passwords do not Match!");
				return "register";
			}
		}else {
			model.addAttribute("errorMessage", "Username and/or Password must not be empty!");
			return "register";
		}
		
	}

	@RequestMapping(value = "/login")
	public String handleLogin(Model model) {
		return "login";
	}

	@RequestMapping("/deleteUser")
	public String deleteData(Model model, @RequestParam int id) {
		userDao.delete(id);
		
		if (id == 0) {
			model.addAttribute("errorMessage", "Could not delete User!");
			return "forward:adduser";
		}
		else {
			model.addAttribute("message", "User with ID " + id + " successfully deleted!");
			return "forward:adduser";
		}
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
}
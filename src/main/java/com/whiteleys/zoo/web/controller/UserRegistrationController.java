package com.whiteleys.zoo.web.controller;

import com.google.common.collect.ImmutableMap;
import com.whiteleys.zoo.domain.Sex;
import com.whiteleys.zoo.domain.User;
import com.whiteleys.zoo.service.UserService;
import com.whiteleys.zoo.web.Globals;
import com.whiteleys.zoo.web.validator.UserValidator;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * A form controller for handling the user registration form.
 */
@Controller
@RequestMapping("/register.*")
public class UserRegistrationController {

    private UserService userService;
	private UserValidator userValidator;

    @RequestMapping(method = RequestMethod.POST)
    public String submitForm(Map model, HttpServletRequest request,@ModelAttribute("userCommand") User command, BindingResult bindingResult) throws Exception {

        userValidator.validate(command, bindingResult);
	    if(bindingResult.hasErrors()){
		    setUpForm(model, command);
		    return "register";
	    }

        //Create the date of birth from the command
        Calendar cal = new GregorianCalendar();
        cal.set(command.getDobYear(), command.getDobMonth(), command.getDobDay(), 0, 0, 0);

        User user = userService.register(command.getUsername(), command.getPassword(), command.getSex(),
                cal.getTime(), command.getPostcode());

        // put the user (who is now logged in) into the session
        request.getSession().setAttribute("user", user);

        return "redirect:/home.html";
    }


    @RequestMapping(method = RequestMethod.GET)
    public String setUpFormAndShowView(Map model)  {
		setUpForm(model, new User());
        return "register";
    }

	private void setUpForm(Map model, User user){
		model.put("dobDays", Globals.DAYS_OF_MONTH);
		model.put("dobMonths", Globals.MONTHS_OF_YEAR);
		model.put("userCommand",user);
		model.put("dobYears", Globals.birthYears());
	}

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

	@Autowired
	public void setUserValidator(UserValidator userValidator) {
		this.userValidator = userValidator;
	}

   

}

package com.whiteleys.zoo.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

public class UserControllerTest {
	private UserController userController;

	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private AnnotationMethodHandlerAdapter handlerAdapter;

	@Before
	public void init() {
		response = new MockHttpServletResponse();
		request = new MockHttpServletRequest();
		handlerAdapter = new AnnotationMethodHandlerAdapter();
	}

	@Test
	public void addFavouriteAnimalRedirectTest() throws Exception {
		request.setMethod("POST");
		request.setServletPath("/addFavourite.html");
//		request.setUserPrincipal(new HttpPrincipal("charlie", "realm"));

		ModelAndView modelAndView = invokeController();
		Assert.assertEquals("gallery", modelAndView.getViewName());
//		Assert.assertNotNull(modelAndView.getModelMap().get("username"));
//		Assert.assertEquals("username", modelAndView.getModelMap().get("username"));
	}

	private ModelAndView invokeController() throws Exception {
		return handlerAdapter.handle(request, response, userController);
	}

	@Autowired
	public void setUserController(UserController userController) {
		this.userController = userController;
	}
}

package com.project.repo.pro;

import com.project.repo.pro.dao.UserDao;
import com.project.repo.pro.model.User;
import com.project.repo.pro.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ProApplicationTests {

	@Autowired
	UserDao userDao;

	@Autowired
	UserService userService;

	@Test
	@Transactional
	void contextLoads() {
		User user = userService.findUserById(6L);

		String url = "kdjsklfj";
		boolean flag = userService.addImage(user.getId(), url);
		if(flag) System.out.println("Успех");
		System.out.println("Error");
	}

}

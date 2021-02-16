package del.group10.java_ee.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.client.result.UpdateResult;

import del.group10.java_ee.jpa.Item;	
import del.group10.java_ee.jpa.ItemRepository;


@RestController
public class AdminController {
@Autowired ItemRepository itemRepository;


@RequestMapping("/admin")
public ModelAndView item() {
	List<Item> items = itemRepository.findAll();
	ModelAndView mv = new ModelAndView("admin");
	mv.addObject("items",items);
	return mv;
	}


}

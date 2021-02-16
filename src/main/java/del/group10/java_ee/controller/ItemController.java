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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.client.result.UpdateResult;

import del.group10.java_ee.jpa.Store;	
import del.group10.java_ee.jpa.Item;	
import del.group10.java_ee.jpa.ItemRepository;
import del.group10.java_ee.jpa.StoreRepository;


@RestController
public class ItemController {
@Autowired StoreRepository storeRepository;
@Autowired ItemRepository itemRepository;
@Autowired MongoTemplate tampungData;

@RequestMapping("/user")
public ModelAndView item() {
	List<Item> items = itemRepository.findAll();
	ModelAndView mv = new ModelAndView("user");
	mv.addObject("items",items);
	return mv;
	}

@RequestMapping("/store")
public ModelAndView store() {
	List<Store> store = storeRepository.findAll();
	ModelAndView mv = new ModelAndView("store");
	mv.addObject("stores",store);
	return mv;
	}


@GetMapping("/user/show/{id}")
public ModelAndView show(@PathVariable (value="id") String id) {
	Optional<Item> item = itemRepository.findById(id);
	Query query = new Query(Criteria.where("id").is(id));
	List<Item> item2 = tampungData.find(query, Item.class);
	if(item2 != null) {
		Update update = new Update().inc("seen", 1);
		UpdateResult result = tampungData.updateFirst(query, update, Item.class);
	}
	
	item.get().setSeen(item.get().getSeen()+1);
	ModelAndView mv = new ModelAndView("show");
	mv.addObject("item", item);
	return mv;
	}
	
	


	@GetMapping("/buyItem")
	public ModelAndView buyItem(@RequestParam(name="id") String id, @RequestParam(name="stock") double stock, @RequestParam(name="rating") double rating) {
		Optional<Item> item = itemRepository.findById(id);
		Query query = new Query(Criteria.where("id").is(id));
		List<Item> item2 = tampungData.find(query, Item.class);
		if(item2!=null) {
			Update update = new Update();
			update.inc("stock", -stock);
			update.set("sold", item.get().getSold()+stock);
			update.set("rating", item.get().getRating()+rating/2);
			UpdateResult result = tampungData.updateFirst(query, update, Item.class);
		}
		ModelAndView mv = new ModelAndView("redirect:/user");
		
		return mv;
	}
	@GetMapping("/item/show/admin/{id}")
	public ModelAndView showAdmin(@PathVariable (value="id") String id) {
		Optional<Item> item = itemRepository.findById(id);
		Query query = new Query(Criteria.where("id").is(id));
		List<Item> item2 = tampungData.find(query, Item.class);
		if(item2 != null) {
			Update update = new Update().inc("seen", 1);
			UpdateResult result = tampungData.updateFirst(query, update, Item.class);
		}
		item.get().setSeen(item.get().getSeen()+1);
		ModelAndView mv = new ModelAndView("showItem");
		mv.addObject("item", item);
		return mv;
	}


	  @GetMapping("/admin/show/store/item/{store_name}") 
	  public ModelAndView itemStore(@PathVariable(name="store_name") String store_name) {
			Query query = new Query(Criteria.where("store_name").is(store_name));
			List<Store> store = tampungData.find(query, Store.class);
			ModelAndView mv = new ModelAndView("store_item");
			mv.addObject("storeitems",store);
			return mv;
		}
     

	
	@GetMapping("/item/showFormUpdate/admin/{id}")
	public ModelAndView showFormUpdate(@PathVariable(name="id") String id) {
		Optional<Item> item = itemRepository.findById(id);
		
		ModelAndView mv = new ModelAndView("updateItem");
		mv.addObject("item", item);
		return mv;
	}
	
	@GetMapping("/updateItem")
	public ModelAndView updateItem(@RequestParam(name="id") String id, @RequestParam(name="stock") double stock, @RequestParam(name="itemDetail.weight") double weight, @RequestParam(name="itemDetail.condition") String condition, @RequestParam(name="itemDetail.category") String category) {
		Optional<Item> item = itemRepository.findById(id);
		Query query = new Query(Criteria.where("id").is(id));
		List<Item> item2 = tampungData.find(query, Item.class);
		if(item2!=null) {
			Update update = new Update();
			update.set("stock", stock);
			update.set("item_detail.weight", weight);
			update.set("item_detail.condition", condition);
			update.set("item_detail.category", category);
			UpdateResult result = tampungData.updateFirst(query, update, Item.class);
		}
		ModelAndView mv = new ModelAndView("redirect:/admin");
		
		return mv;
	}

}

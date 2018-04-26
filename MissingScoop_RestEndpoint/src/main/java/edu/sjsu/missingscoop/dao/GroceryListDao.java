package edu.sjsu.missingscoop.dao;

import java.util.List;

import edu.sjsu.missingscoop.model.Grocery;

public interface GroceryListDao {

	void save(Grocery grocery);

	Grocery getGroceriesByUserName(String userName);

}

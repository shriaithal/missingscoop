package edu.sjsu.missingscoop.dao;

import edu.sjsu.missingscoop.model.GroceryList;

public interface GroceryListDao {

	GroceryList getGroceriesByUserName(String userName);

	public void save(GroceryList groceryList);

	GroceryList removeGrocery(String userName, String grocery);

}

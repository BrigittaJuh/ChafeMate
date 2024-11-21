package modell;

import Controller.ZutatenCell;
import Database.DatabaseHandler;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Recipe {
    private int id;
    private int userid;
    private String name;
    private String ingredients;
    private String description;
    private String category;
    private String imagePath;
    private List<Ingredient> ingredientsList;

    public Recipe() {
        this.ingredientsList = new ArrayList<>();
    }

    public Recipe(int id, int userid, String name, String ingredients, String description, String category,String imagePath) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
        this.category = category;
        this.imagePath = imagePath;
        this.ingredientsList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {  // Getter az ingredients mezőhöz
        return ingredients;
    }

    public void setIngredients(String ingredients) {  // Setter az ingredients mezőhöz
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public void setRecipeIdForIngredients(int recipeId) {
        ingredientsList.forEach(ingredient -> ingredient.setRecipeId(recipeId));
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", userid=" + userid +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

}

package Database;

import modell.Ingredient;
import modell.Recipe;
import modell.Shoppinglist;
import modell.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mindrot.jbcrypt.BCrypt;

public class DatabaseHandler extends Configs {
    private static DatabaseHandler instance;
    Connection dbConnection;
    public Connection getDbConnection() throws  ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" +dbHost  + ":"
                + dbPort + "/"
                + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser,dbPass);
        return dbConnection;

    }
    public void signUpUser (User user) {
        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME
                + "," + Const.USERS_LASTNAME + "," +
                Const.USERS_USERNAME + "," +
                Const.USERS_PASSWORD + "," +
                Const.USERS_EMAIL + ") VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4,hashedPassword);
            preparedStatement.setString(5, user.getEmail());


            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Benutzer erfolgreich registriert");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public boolean authenticateUser(User user) {
        String query = "SELECT " + Const.USERS_PASSWORD + " FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_USERNAME + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, user.getUserName());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedHashedPassword = resultSet.getString(Const.USERS_PASSWORD);

                return BCrypt.checkpw(user.getPassword(), storedHashedPassword);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Falscher Benutzername oder Passwort.");
        return false;
    }


//****
    public int getUserIdByUsername(String username) {
        String query = "SELECT userid FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_USERNAME + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("userid");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void insertList(Shoppinglist shoppinglist, int userid) {
        String insert = "INSERT INTO " + Const.SHOPPINGLIST_TABLE + "(" + Const.USERS_ID
                + "," + Const.SHOPPINGLIST_ITEM
                + "," + Const.SHOPPINGLIST_QUANTILY +
                ") VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            System.out.println("SQL userId : " + userid);

            preparedStatement.setInt(1, userid);
            preparedStatement.setString(2, shoppinglist.getItem());
            preparedStatement.setInt(3, shoppinglist.getQuantily());

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public List<Shoppinglist> getShoppingList(int userid) {
        List<Shoppinglist> shoppingList = new ArrayList<>();
        String query = "SELECT * FROM shoppinglist WHERE userid = ?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userid);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("idshoppinglist");
                String item = resultSet.getString("item");
                int quantity = resultSet.getInt("quantily");
                shoppingList.add(new Shoppinglist(id, item, quantity));
            }
            System.out.println("Items retrieved: " + shoppingList.size());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return shoppingList;
    }


    public void deleteList(int idshoppinglist) {
        String deleteQuery = "DELETE FROM shoppinglist WHERE idshoppinglist = ?";

        try (PreparedStatement preparedStatement = getDbConnection().prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, idshoppinglist);
            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getRecipesWithIngredients(int userId) {
        List<Recipe> recipes = new ArrayList<>();
        String queryRecipe = "SELECT * FROM recipes WHERE userid = ?";
        String queryIngredients = "SELECT * FROM ingredients WHERE recipe_id = ?";

        try (Connection connection = getDbConnection()) {

            PreparedStatement recipeStatement = connection.prepareStatement(queryRecipe);
            recipeStatement.setInt(1, userId);
            ResultSet recipeResult = recipeStatement.executeQuery();

            while (recipeResult.next()) {
                int recipeId = recipeResult.getInt("id");
                String name = recipeResult.getString("name");
                String ingredientsSummary = recipeResult.getString("ingredients");
                String description = recipeResult.getString("description");
                String category = recipeResult.getString("category");
                String imagePath = recipeResult.getString("image_path");

                Recipe recipe = new Recipe(recipeId, userId, name, ingredientsSummary, description, category, imagePath);


                PreparedStatement ingredientStatement = connection.prepareStatement(queryIngredients);
                ingredientStatement.setInt(1, recipeId);
                ResultSet ingredientResult = ingredientStatement.executeQuery();

                List<Ingredient> ingredientList = new ArrayList<>();
                while (ingredientResult.next()) {
                    int ingredientId = ingredientResult.getInt("id");
                    String ingredientName = ingredientResult.getString("name");
                    String quantity = ingredientResult.getString("quantity");
                    String unit = ingredientResult.getString("unit");

                    ingredientList.add(new Ingredient(ingredientId, recipeId, ingredientName, quantity, unit));
                }
                recipe.setIngredientsList(ingredientList);
                recipes.add(recipe);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recipes;
    }


    public void saveRecipe(Recipe recipe, List<Ingredient> ingredients) {
        try (Connection connection = getDbConnection()) {

            String ingredientSummary = ingredients.stream()
                    .map(ingredient -> ingredient.getQuantity() + " " + ingredient.getUnit() + " " + ingredient.getName())
                    .collect(Collectors.joining(", "));
            recipe.setIngredients(ingredientSummary);

            // 1. Elmentjük a receptet az adatbázisba
            String recipeSql = "INSERT INTO recipes (userid, name, ingredients, description, image_path) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement recipeStmt = connection.prepareStatement(recipeSql, Statement.RETURN_GENERATED_KEYS)) {
                recipeStmt.setInt(1, recipe.getUserid());
                recipeStmt.setString(2, recipe.getName());
                recipeStmt.setString(3, recipe.getIngredients());
                recipeStmt.setString(4, recipe.getDescription());
                recipeStmt.setString(5, recipe.getImagePath());
                recipeStmt.executeUpdate();

                ResultSet rs = recipeStmt.getGeneratedKeys();
                if (rs.next()) {
                    int recipeId = rs.getInt(1);
                    recipe.setId(recipeId);

                    String ingredientSql = "INSERT INTO ingredients (recipe_id, name, quantity, unit) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement ingredientStmt = connection.prepareStatement(ingredientSql)) {
                        for (Ingredient ingredient : ingredients) {
                            ingredient.setRecipeId(recipeId);
                            ingredientStmt.setInt(1, ingredient.getRecipeId());
                            ingredientStmt.setString(2, ingredient.getName());
                            ingredientStmt.setString(3, ingredient.getQuantity());
                            ingredientStmt.setString(4, ingredient.getUnit());
                            ingredientStmt.addBatch();
                        }
                        ingredientStmt.executeBatch();

                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseHandler getInstance() {
        if (instance == null) {
            instance = new DatabaseHandler();
        }
        return instance;
    }
    public void deleteRecipe(int recipeId) {
        String deleteRecipeQuery = "DELETE FROM recipes WHERE id = ?";
        String deleteIngredientsQuery = "DELETE FROM ingredients WHERE recipe_id = ?";

        try (Connection connection = getDbConnection()) {
            try (PreparedStatement ingredientStmt = connection.prepareStatement(deleteIngredientsQuery)) {
                ingredientStmt.setInt(1, recipeId);
                ingredientStmt.executeUpdate();
            }

            try (PreparedStatement recipeStmt = connection.prepareStatement(deleteRecipeQuery)) {
                recipeStmt.setInt(1, recipeId);
                int rowsDeleted = recipeStmt.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Rezept erfolgreich aus der Datenbank gelöscht.");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}

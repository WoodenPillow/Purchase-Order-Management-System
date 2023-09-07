package Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private static final String FILE_PATH = "categories.txt";

    public static void main(String[] args) {
        // Create a sample category
        Category category = new Category("CA001", "Sample Category");

        // Write a new category to the text file
        writeCategoryToFile(category);

        // Retrieve all categories from the text file
        List<Category> categories = readCategoriesFromFile();
        
        // Display the retrieved categories
        for (Category c : categories) {
            System.out.println("Category ID: " + c.getId());
            System.out.println("Category Name: " + c.getName());
            System.out.println();
        }
    }

    // Represents a category
    static class Category{
        private String id;
        private String name;

        public Category(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    // Write a category to the text file
    private static void writeCategoryToFile(Category category) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String record = category.getId() + "," + category.getName();
            writer.write(record);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read all categories from the text file
    private static List<Category> readCategoriesFromFile() {
        List<Category> categories = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String id = parts[0];
                    String name = parts[1];
                    categories.add(new Category(id, name));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }
}

package Administrator;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class fileHandler {
    
    public List<String> readData(String dataFile) {
        List<String> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            // Handle file reading errors
            e.printStackTrace();
        }
        return data;
    }

    public void writeData(String dataFile, List<String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            // Handle file writing errors
            e.printStackTrace();
        }
    }
}


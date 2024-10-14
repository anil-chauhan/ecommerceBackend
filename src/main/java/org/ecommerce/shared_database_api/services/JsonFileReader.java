package org.ecommerce.shared_database_api.services;


import com.google.gson.Gson;
import org.ecommerce.shared_database_api.models.JsonDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;


@Service
public class JsonFileReader {


    public JsonDataModel readJsonFile() {

        Gson gson = new Gson();
        JsonDataModel employee = null;
        try (FileReader reader = new FileReader("my_data.json")) {
            // Convert JSON file to Java object
            employee = gson.fromJson(reader, JsonDataModel.class);
            System.out.println(employee);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return employee;


    }
}

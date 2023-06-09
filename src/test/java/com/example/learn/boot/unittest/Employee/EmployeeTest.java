package com.example.learn.boot.unittest.Employee;

import com.example.learn.boot.unittest.UnitTestApplicationTests;
import com.example.learn.boot.unittest.model.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.Id;

import java.util.Objects;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeTest extends UnitTestApplicationTests {

    private EmployeeTest Id;

    @Test
    public void createEmployeeTest() throws Exception {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName("Employee");
        dto.setAddress("Kathmandu");
        dto.setEmail("user@user.com");
        dto.setPhone("9876554433");

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Employee"))
                .andExpect(jsonPath("$.address").value("Kathmandu"))
                .andExpect(jsonPath("$.email").value("user@user.com"))
                .andExpect(jsonPath("$.phone").value("9876554433"));
    }

    @Test
    public void createEmployeeTestNegative() throws Exception {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName(null); // Set a required field to null
        dto.setAddress("Kathmandu");
        dto.setEmail("user@user.com");
        dto.setPhone("9876554433");
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()); // Verify that the response status is 400
    }

    @Test
    public void getEmployeeById() throws Exception {
        createEmployee("aaku", "pkr", "aaku@gmail.com", "67687888");


        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aaku"));

    }

    @Test
    public void getEmployeeByIdNtest() throws Exception {
        createEmployee("aaku", "pkr", "aaku@gmail.com", "67687888");

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void deleteEmployeeById() throws Exception {
        createEmployee("aaku", "pkr", "aaku@gmail.com", "67687888");

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployeeByIdTest() throws Exception {

        createEmployee("aaku", "pkr", "aaku@gmail.com", "67687888");

        // Send DELETE request to delete the employee
        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1" )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify that the employee has been deleted
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1" )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    @Test
    public void listAllEmployees() throws Exception {
        createEmployee("aaru", "bkt", "aaru@gmail.com", "6734588");
        createEmployee("aaku", "bkt", "aaku@gmail.com", "6734588");

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("aaru"))
                .andExpect(jsonPath("$[1].name").value("aaku"));

    }

    @Test
    public void listAllEmployees_InvalidRequest() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/employee/list")

                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
//                .andExpect(jsonPath("$[0].name").value("arima"));
    }


    @Test
    public void updateEmployeeTest() throws Exception {
        // Create an employee
        createEmployee("arima", "brt", "arima@gmail.com", "634567888");

        // Update the employee
        String updatedEmployeeJson = "{ \"name\": \"aaku\", \"address\": \"pkr\", \"email\": \"aaku@gmail.com\", \"phone\": \"67687888\" }";
        mockMvc.perform(MockMvcRequestBuilders.put("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(status().isOk());

        // Verify that the employee was updated
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aaku"))
                .andExpect(jsonPath("$.address").value("pkr"))
                .andExpect(jsonPath("$.email").value("aaku@gmail.com"))
                .andExpect(jsonPath("$.phone").value("67687888"));
    }


    @Test
    public void updateEmployeeNotFoundTest() throws Exception {
        // Create an employee
        createEmployee("arima", "japan", "arima@gmail.com", "4564454");

        // Update the employee
        String updatedEmployeeJson = "{ \"name\": \"deena\", \"address\": \"kathmandu\", \"email\": \"deena@gmail.com\", \"phone\": \"151544\" }";
        mockMvc.perform(MockMvcRequestBuilders.put("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(status().isOk());

        // Verify that the employee was not updated
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



    }




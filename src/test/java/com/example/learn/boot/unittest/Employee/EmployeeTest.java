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
    public void getEmployeeById() throws Exception {
        createEmployee("aaku", "pkr", "aaku@gmail.com", "67687888");


        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aaku"));

    }


    @Test
    public void deleteEmployeeById() throws Exception {
        createEmployee("aaku", "pkr", "aaku@gmail.com", "67687888");

        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    @Test
    public void listAllEmployees() throws Exception {
        createEmployee("aaru", "bkt", "aaru@gmail.com", "6734588");

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("aaru"));

    }

    @Test
    public void updateEmployeeTest() throws Exception {
        // Create an employee
        createEmployee("arima", "brt", "arima@gmail.com", "634567888");

        // Update the employee
        String updatedEmployeeJson = "{ \"name\": \"aaku_updated\", \"address\": \"pkr_updated\", \"email\": \"aaku_updated@gmail.com\", \"phone\": \"67687888\" }";
        mockMvc.perform(MockMvcRequestBuilders.put("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(status().isOk());

        // Verify that the employee was updated
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aaku_updated"))
                .andExpect(jsonPath("$.address").value("pkr_updated"))
                .andExpect(jsonPath("$.email").value("aaku_updated@gmail.com"))
                .andExpect(jsonPath("$.phone").value("67687888"));
    }
}
package bankaccount.in.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bankaccount.in.model.Cliente;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCustomersReturnsFive() throws Exception {
    	String response = mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        List<Cliente> clientes = objectMapper.readValue(response, new TypeReference<List<Cliente>>() {});
        Assertions.assertTrue(clientes.size()==5);

    }

    @Test
    public void testAdultCustomers() throws Exception {
        String response = mockMvc.perform(get("/clientes/mayores-de-edad"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        List<Cliente> clientes = objectMapper.readValue(response, new TypeReference<List<Cliente>>() {});

        LocalDate today = LocalDate.now();
        for (Cliente cliente : clientes) {
            LocalDate birthday = cliente.fechaNacimiento();
            int age = Period.between(birthday, today).getYears();
            Assertions.assertTrue(age >= 18, "Customer " + cliente.dni() + " is not adult");
        }
    }

    @Test
    public void testGetCustomersGreaterThanAmount() throws Exception {
        double amount = 30000.0;
        String response = mockMvc.perform(get("/clientes/con-cuenta-superior-a/" + amount))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        List<Cliente> clientes = objectMapper.readValue(response, new TypeReference<List<Cliente>>() {});

        for (Cliente cliente : clientes) {
            double balance = cliente.cuentas().stream().mapToDouble(c -> c.getTotal() != null ? c.getTotal() : 0.0).sum();
            Assertions.assertTrue(balance > amount, "Customer " + cliente.dni() + " balance is less than expected");
        }
    }

    @Test
    public void testGetCustomerByDni() throws Exception {
        String dni = "11111111A";
        String response = mockMvc.perform(get("/clientes/" + dni))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        Cliente cliente = objectMapper.readValue(response, Cliente.class);

        Assertions.assertEquals(dni, cliente.dni());
        Assertions.assertNotNull(cliente.nombre());
    }
}

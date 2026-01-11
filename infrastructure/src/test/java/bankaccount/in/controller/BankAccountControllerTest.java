package bankaccount.in.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import bankaccount.in.model.CuentaBancaria;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testCreateNewAccount() throws Exception {
		String dni = "99999999Z";
		String tipoCuenta = "NORMAL";
		Double balance = 1234.0;
		CuentaBancaria nuevaCuenta = new CuentaBancaria(null, dni, tipoCuenta, balance);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		String json = objectMapper.writeValueAsString(nuevaCuenta);

		String response = mockMvc
				.perform(MockMvcRequestBuilders.post("/cuentas").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		CuentaBancaria result = objectMapper.readValue(response, CuentaBancaria.class);
		Assertions.assertNotNull(result.getId(), "No new account created");
		Assertions.assertEquals(dni, result.getDniCliente());
		Assertions.assertEquals(tipoCuenta, result.getTipoCuenta());
		Assertions.assertEquals(balance, result.getTotal());
	}

	@Test
	public void testCreateNewAccountOldCustomer() throws Exception {
		String dni = "11111111A";
		String tipoCuenta = "AHORRO";
		Double balance = 1234.0;
		CuentaBancaria existingAccount = new CuentaBancaria(null, dni, tipoCuenta, balance);

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		String json = objectMapper.writeValueAsString(existingAccount);

		String response = mockMvc
				.perform(MockMvcRequestBuilders.post("/cuentas").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		CuentaBancaria result = objectMapper.readValue(response, CuentaBancaria.class);
		Assertions.assertNotNull(result.getId(), "No new account created");
		Assertions.assertEquals(existingAccount.getDniCliente(), result.getDniCliente());
		Assertions.assertEquals(existingAccount.getTipoCuenta(), result.getTipoCuenta());
		Assertions.assertEquals(existingAccount.getTotal(), result.getTotal());
	}

	@Test
	public void testUpdateAccountAmount() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		CuentaBancaria account = new CuentaBancaria(1L, null, null, 123321.0);
		String json = objectMapper.writeValueAsString(account);

		String responseUpdate = mockMvc
				.perform(MockMvcRequestBuilders.put("/cuentas/" + account.getId())
						.contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		CuentaBancaria result = objectMapper.readValue(responseUpdate, CuentaBancaria.class);
		
		Assertions.assertEquals(account.getTotal(), result.getTotal());
		Assertions.assertEquals(account.getId(), result.getId());
		Assertions.assertTrue(result.getDniCliente()!=null);
	}
	
	
}

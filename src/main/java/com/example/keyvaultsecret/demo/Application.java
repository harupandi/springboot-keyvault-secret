package com.example.keyvaultsecret.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;

@SpringBootApplication
@RestController
public class Application {

	SecretClient secretClient = new SecretClientBuilder()
		.vaultUrl("https://brpineda-kv.vault.azure.net/")
		.credential(new DefaultAzureCredentialBuilder().build())
		.buildClient();

	@GetMapping("get")
	public String get() {

		String keyVaultSecret = getSecretValue();
		return keyVaultSecret;

	}

	private String getSecretValue(){
		String secret = secretClient.getSecret("testValue").getValue();
		return secret;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
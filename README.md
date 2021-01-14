# springboot-keyvault-secret
Springboot app that connects to Azure KeyVault using Service Principal to pull Secret value. This sample project connects to Azure KeyVault using [azure-security-keyvault-secrets](https://search.maven.org/artifact/com.azure/azure-security-keyvault-secrets).

### pom.xml dependencies required to get Secrets

    <dependency>
        <groupId>com.azure</groupId>
        <artifactId>azure-identity</artifactId>
        <version>1.2.2</version>
    </dependency>
    <dependency>
        <groupId>com.azure</groupId>
        <artifactId>azure-security-keyvault-secrets</artifactId>
        <version>4.2.3</version>
    </dependency>

> Note: check [keys](https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/keyvault/azure-security-keyvault-keys) and [certificates](https://github.com/Azure/azure-sdk-for-java/tree/master/sdk/keyvault/azure-security-keyvault-certificates) if you want to get those instead of secrets.

### KeyVault secret name
In this demo, we created a secret with name "testValue" and value "secretRetrievedFromKeyVault" for the sake of testing.

[Application.java](https://github.com/harupandi/springboot-keyvault-secret/blob/master/src/main/java/com/example/keyvaultsecret/demo/Application.java)

    String secret = secretClient.getSecret("testValue").getValue();

### Azure CLI steps to create Service Principal and give it Get and List permissions on Key Vault
The following are the necessary steps to create a KeyVault, a secret, then a Service Principal for authentication and finally giving it permissions to get the secrets.

> Full steps taken from: [Reading a secret from Azure Key Vault in a Spring Boot application](https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-key-vault#create-a-new-azure-key-vault). Please consider giving it a read for an explanation on these commands and the expected output.

##### Create KeyVault:

    az keyvault create \
        --resource-group contosorg \
        --name demo-kv \
        --enabled-for-deployment true \
        --enabled-for-disk-encryption true \
        --enabled-for-template-deployment true \
        --location eastus
        --query properties.vaultUri \
        --sku standard 
    
##### Create KeyVault Secret:

    az keyvault secret set --name "testValue" \
    --vault-name "demo-kv" \
    --value "secretRetrievedFromKeyVault"
    
##### Create Service Principal:

    az ad sp create-for-rbac --name demo-sp

##### Give the Service Princpal Secret permissions

    az keyvault set-policy --name demo-kv --spn http://demo-sp --secret-permissions get list

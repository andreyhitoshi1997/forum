package dev.andrey.forum.config

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource

/**
 * Carrega variáveis de ambiente do arquivo .env
 * Este initializer é executado antes do Spring carregar as configurações
 *
 * Fluxo:
 * 1. Spring boot detecta esta classe via spring.factories
 * 2. DotenvInitializer é instantiado e executado ANTES de application.yml ser lido
 * 3. Variáveis do .env são adicionadas ao environment do Spring com alta prioridade
 * 4. application.yml pode então referenciar essas variáveis via ${VARIAVEL_NAME}
 */
class DotenvInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val environment: ConfigurableEnvironment = applicationContext.environment

        try {
            // Tenta carregar o arquivo .env da raiz do projeto
            val dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .load()

            // Cria um mapa com todas as variáveis do .env
            val dotenvProperties = mutableMapOf<String, Any>()
            dotenv.entries().forEach { entry ->
                dotenvProperties[entry.key] = entry.value
            }

            // Adiciona as propriedades ao environment do Spring no topo da pilha
            // para que tenham prioridade sobre as configurações do application.yml
            if (dotenvProperties.isNotEmpty()) {
                environment.propertySources.addFirst(
                    MapPropertySource("dotenvProperties", dotenvProperties)
                )

                println("✅ Variáveis de ambiente (.env) carregadas com sucesso!")
                println("   Total de variáveis: ${dotenvProperties.size}")

                // Log apenas os nomes das variáveis (não os valores para segurança)
                val varNames = dotenvProperties.keys.sorted().joinToString(", ")
                println("   Variáveis disponíveis: $varNames")
            } else {
                println("⚠️  Arquivo .env encontrado mas está vazio")
            }
        } catch (e: Exception) {
            println("⚠️  Erro ao carregar arquivo .env: ${e.message}")
            println("   Certifique-se de que o arquivo .env existe na raiz do projeto")
            println("   Se o arquivo existe, verifique as permissões de leitura")
        }
    }
}


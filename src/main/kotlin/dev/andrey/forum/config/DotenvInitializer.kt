package dev.andrey.forum.config

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource

/**
 * Carrega variáveis de ambiente do arquivo .env
 * Este initializer é executado antes do Spring carregar as configurações
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

            // Adiciona as propriedades ao environment do Spring
            environment.propertySources.addFirst(
                MapPropertySource("dotenvProperties", dotenvProperties)
            )

            println("✅ Arquivo .env carregado com sucesso!")
        } catch (e: Exception) {
            println("⚠️  Arquivo .env não encontrado ou erro ao carregar: ${e.message}")
            println("   Usando valores padrão das variáveis de ambiente do sistema")
        }
    }
}


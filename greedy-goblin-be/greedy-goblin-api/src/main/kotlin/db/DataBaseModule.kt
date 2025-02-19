package io.greedy.goblin.api.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.greedy.goblin.common.repo.game.IGameRepo
import io.greedy.goblin.common.repo.player.IPlayerActionRepo
import io.greedy.goblin.common.repo.scene.ISceneRepo
import io.greedy.goblin.repo.inmemory.GameRepoInMemory
import io.greedy.goblin.repo.inmemory.PlayerActionRepoInMemory
import io.greedy.goblin.repo.inmemory.SceneRepoInMemory
import io.greedy.goblin.repo.postgres.GameRepoPg
import io.greedy.goblin.repo.postgres.PlayerActionRepoPg
import io.greedy.goblin.repo.postgres.SceneRepoPg
import io.ktor.server.config.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.module.Module
import org.koin.dsl.module
import javax.sql.DataSource

fun databaseModule(appConfig: ApplicationConfig): Module {
    val environment = appConfig.property("ktor.environment").getString()
    return when (environment) {
        "test" ->
            module {
                single<IGameRepo> { GameRepoInMemory() }
                single<ISceneRepo> { SceneRepoInMemory() }
                single<IPlayerActionRepo> { PlayerActionRepoInMemory() }
            }
        else ->
            module {
                single<DataSource> {
                    val dbConfig = appConfig.config("ktor.database")

                    val hikariConfig =
                        HikariConfig().apply {
                            jdbcUrl = dbConfig.property("url").getString()
                            driverClassName = dbConfig.property("driver").getString()
                            username = dbConfig.property("username").getString()
                            password = dbConfig.property("password").getString()
                            maximumPoolSize = dbConfig.property("poolSize").getString().toInt()
                            transactionIsolation = dbConfig.property("isolation").getString()
                            isAutoCommit = false
                        }
                    val dataSource = HikariDataSource(hikariConfig)
                    Database.connect(dataSource)
                    Flyway
                        .configure()
                        .dataSource(dataSource)
                        .defaultSchema("public")
                        .load()
                        .migrate()
                    transaction {
                        addLogger(StdOutSqlLogger)
                    }
                    dataSource
                }
                single<IGameRepo> { GameRepoPg(get()) }
                single<ISceneRepo> { SceneRepoPg(get()) }
                single<IPlayerActionRepo> { PlayerActionRepoPg() }
            }
    }
}

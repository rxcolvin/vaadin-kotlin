package vaadinexamplar

import com.github.mvysny.vaadinboot.VaadinBoot
import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.shared.communication.PushMode
import com.vaadin.flow.theme.Theme
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import vaadinexamplar.db.Todos
import vaadinexamplar.db.Users

@Theme("my-theme")
@Push(PushMode.AUTOMATIC) // websocket ui updates
class AppShell : AppShellConfigurator

fun main(args: Array<String>) {
    transaction(database) {
        SchemaUtils.create(
            Users,
            Todos
        )
    }
    VaadinBoot().run()
}

val dataSource = HikariDataSource(HikariConfig().apply {
    jdbcUrl = "jdbc:sqlite:todos.db"
    driverClassName = "org.sqlite.JDBC"

    maximumPoolSize = 10
    minimumIdle = 5
    connectionTimeout = 30000
    idleTimeout = 10000
    maxLifetime = 1800000
})

val database = Database.connect(dataSource)

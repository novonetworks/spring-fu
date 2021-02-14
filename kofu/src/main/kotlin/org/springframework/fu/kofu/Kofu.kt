package org.springframework.fu.kofu

import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.support.GenericApplicationContext


typealias ContextFactory = () -> GenericApplicationContext


internal var contextFactory: ContextFactory? = null

/**
 * Declare a command-line [application][ApplicationDsl] (no server) that allows to configure a Spring Boot
 * application using Kofu DSL and functional bean registration.
 *
 * @sample org.springframework.fu.kofu.samples.webFluxApplicationDsl
 * @param dsl The `application { }` DSL
 * @see ApplicationDsl
 * @see webApplication
 * @see reactiveWebApplication
 * @author Sebastien Deleuze
 */
fun application(
    dsl: ApplicationDsl.() -> Unit,
    contextFactory: (() -> GenericApplicationContext)? = null
) = object : KofuApplication(ApplicationDsl(dsl)) {
    override fun createContext(): ConfigurableApplicationContext {
        return contextFactory?.invoke() ?: GenericApplicationContext()
    }
}

/**
 * Declare a Servlet-based web [application][ApplicationDsl] that allows to configure a Spring Boot
 * application using Kofu DSL and functional bean registration.
 *
 * @sample org.springframework.fu.kofu.samples.webFluxApplicationDsl
 * @param dsl The `application { }` DSL
 * @see ApplicationDsl
 * @author Sebastien Deleuze
 */
fun webApplication(
    dsl: ApplicationDsl.() -> Unit,
    contextFactory: (() -> GenericApplicationContext)? = null
) = object : KofuApplication(ApplicationDsl(dsl)) {
    override fun createContext(): ConfigurableApplicationContext {
        return contextFactory?.invoke() ?: ServletWebServerApplicationContext()
    }
}

/**
 * Declare a Reactive-based web [application][ApplicationDsl] that allows to configure a Spring Boot
 * application using Kofu DSL and functional bean registration.
 *
 * @sample org.springframework.fu.kofu.samples.webFluxApplicationDsl
 * @param dsl The `application { }` DSL
 * @see ApplicationDsl
 * @author Sebastien Deleuze
 */
fun reactiveWebApplication(
    dsl: ApplicationDsl.() -> Unit,
    contextFactory: (() -> GenericApplicationContext)? = null
) = object : KofuApplication(ApplicationDsl(dsl)) {
    override fun createContext(): ConfigurableApplicationContext {
        return contextFactory?.invoke() ?: ReactiveWebServerApplicationContext()
    }
}


/**
 * Define a configuration that can be imported in an application or used in tests.
 * @see ConfigurationDsl.enable
 * @sample org.springframework.fu.kofu.samples.applicationDslWithConfiguration
 */
fun configuration(dsl: ConfigurationDsl.() -> Unit) = ConfigurationDsl(dsl)

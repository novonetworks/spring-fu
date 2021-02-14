package org.springframework.fu.kofu

import org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.support.GenericApplicationContext


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
    contextFactory: (() -> GenericApplicationContext)? = null,
    dsl: ApplicationDsl.() -> Unit
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
    contextFactory: (() -> GenericApplicationContext)? = null,
    dsl: ApplicationDsl.() -> Unit
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
    contextFactory: (() -> GenericApplicationContext)? = null,
    dsl: ApplicationDsl.() -> Unit
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

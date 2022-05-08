/**
 *	The main application.
 */
package ru.kazbo.micronaut.jpa;

import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
		// start micronaut microservices
        Micronaut.run(Application.class, args);
    }
}

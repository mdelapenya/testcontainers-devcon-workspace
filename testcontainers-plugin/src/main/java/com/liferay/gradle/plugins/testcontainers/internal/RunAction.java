package com.liferay.gradle.plugins.testcontainers.internal;

import org.gradle.workers.WorkAction;
import org.gradle.workers.WorkParameters;
import org.gradle.api.provider.Property;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class RunAction implements WorkAction<RunAction.Params> {

	public interface Params extends WorkParameters {
		Property<String> getImageName();
	}

	@Override
	public void execute() {
		String image = getParameters().getImageName().get();
		System.out.println("[RunAction] Starting container: " + image);

		System.out.println("Creating Testcontainers setup for: " + image);

		// Example of how you would create a container (commented out for now)
		try (GenericContainer<?> liferayContainer = new GenericContainer<>(DockerImageName.parse(image))) {
			liferayContainer
				.withExposedPorts(8080, 11311)
				.withEnv("LIFERAY_SETUP_PERIOD_WIZARD_PERIOD_ENABLED", "false")
				// Add volume mounts, network settings, etc.
			;

			System.out.println("Starting Liferay container...");
			liferayContainer.start();

			System.out.println("Container started successfully!");
			System.out.println("Container ID: " + liferayContainer.getContainerId());
			System.out.println("Host: " + liferayContainer.getHost());
			System.out.println("Port 8080 mapped to: " + liferayContainer.getMappedPort(8080));

			// Keep container running or do additional work here
			Thread.sleep(5000); // Wait 5 seconds for demonstration

		} catch (Exception e) {
			throw new RuntimeException("[RunAction] Container run failed", e);
		}

		System.out.println("Container setup complete (demo mode)");
	}

}

package com.liferay.gradle.plugins.testcontainers;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.tasks.TaskAction;
import org.gradle.workers.WorkerExecutor;

import com.liferay.gradle.plugins.testcontainers.internal.RunAction;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

public class RunTask extends DefaultTask {

	private final WorkerExecutor workers;

	@Inject
	public RunTask(WorkerExecutor workers) {
		this.workers = workers;
	}

	@TaskAction
	public void runContainer() {
		// For now, just print hello message
		System.out.println("hello containers");

		try {
			// Get the Liferay product version from gradle properties
			Object productValue = getProject().findProperty("liferay.workspace.product");

			if (productValue == null || !productValue.toString().startsWith("dxp-")) {
				System.out.println("Invalid or missing liferay.workspace.product property");
				return;
			}

			String version = productValue.toString().substring(4); // Remove 'dxp-' prefix
			String imageName = "liferay/dxp:" + version;

			System.out.println("Testcontainers initialized successfully");
			System.out.println("Would run container with image: " + imageName);
			System.out.println("Creating Testcontainers setup for: " + imageName);

			// build an isolated classpath just for Testcontainers
			DependencyHandler deps = getProject().getDependencies();
			Configuration tcCp = getProject().getConfigurations().detachedConfiguration(
				deps.create("org.testcontainers:testcontainers:1.21.3")
			);

			// Run in an isolated classloader that only sees Testcontainers (shaded docker-java)
			workers.classLoaderIsolation(spec ->
				spec.getClasspath().from(tcCp)   // use the detached classpath here
			).submit(RunAction.class, params ->
				params.getImageName().set(imageName)
			);
		} catch (Exception e) {
			getLogger().error("Failed to run Liferay container", e);
			throw new RuntimeException("Container execution failed", e);
		}
	}

	private void run(String imageName) {
		System.out.println("Creating Testcontainers setup for: " + imageName);

		// Example of how you would create a container (commented out for now)
        try (GenericContainer<?> liferayContainer = new GenericContainer<>(DockerImageName.parse(imageName))) {
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
            getLogger().error("Container operation failed", e);
        }

		System.out.println("Container setup complete (demo mode)");
	}
}

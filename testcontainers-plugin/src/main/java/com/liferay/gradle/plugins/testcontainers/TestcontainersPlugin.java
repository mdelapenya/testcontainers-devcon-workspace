package com.liferay.gradle.plugins.testcontainers;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class TestcontainersPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		// Register the task
		project.getTasks().register("runLiferayContainer", RunTask.class, task -> {
			task.setGroup("liferay");
			task.setDescription("Runs a Liferay DXP container using Testcontainers");
		});

		System.out.println("Liferay Testcontainers Plugin applied to " + project.getName());
	}
}

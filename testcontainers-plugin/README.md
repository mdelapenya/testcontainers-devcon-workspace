# Testcontainers Plugin

This Gradle plugin allows you to run a Liferay DXP container using Testcontainers.

## Usage

First, you need to apply the plugin to your project.

```
plugins {
	id 'com.liferay.testcontainers' version '1.0.0'
}
```

Then, you can run the Liferay DXP container using the `runLiferayContainer` task.

```
./gradlew runLiferayContainer
```

## Configuration

You can configure the plugin by setting the following properties in your `gradle.properties` file:

- `liferay.workspace.product`: Set the Liferay DXP version to run.

```
liferay.workspace.product=dxp-2025.q3.2
```

## Example Project

You can find an example project in the `testcontainers-plugin-example` directory.

From the root of the project, run:

```
./gradlew :testcontainers-plugin-example:runLiferayContainer
```

A container will be started and you can access the Liferay DXP instance at `http://localhost:8080`.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

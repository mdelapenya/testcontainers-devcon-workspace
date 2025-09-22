# Liferay Workspace with Testcontainers

This project is a proof of concept to show how to use Testcontainers with Liferay Workspaces.

It includes two subprojects:

- `testcontainers-plugin`: The plugin that allows you to run a Liferay DXP container using Testcontainers.
- `testcontainers-plugin-example`: An example project that uses the plugin to run a Liferay DXP container.

## Running the example project

From the root of the project, run:

```
./gradlew runLiferayContainer
```

A container will be started and you can access the Liferay DXP instance at `http://localhost:8080`.

The image used is the same as the one used in the Liferay Workspace example project, as it reads the `liferay.workspace.product` property that is present in the `gradle.properties` file.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

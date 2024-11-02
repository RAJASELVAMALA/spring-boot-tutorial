# NestJs Documentation

<p align="center">
  <a href="http://nestjs.com/" target="blank"><img src="https://nestjs.com/img/logo-small.svg" width="120" alt="Nest Logo" /></a>
</p>

## Overview

This documentation provides an overview of a NestJs project developed using TypeScript. It lists the implemented tools and modules used in the project and outlines the folder structure for better organization.

## Requirements

1. Node.js 20 or above
2. Yarn

## Installation

To install project dependencies, run the following command:

```bash
$ yarn install
```

## Running the App

You can run the NestJs application in different modes:

### Development Mode

```bash
$ yarn start
```

### Watch Mode

```bash
$ yarn start:dev
```

### Production Mode

```bash
$ yarn start:prod
```

## Testing

You can run various types of tests for the project:

### Unit Tests

```bash
$ yarn test
```

### End-to-End (e2e) Tests

```bash
$ yarn test:e2e
```

### Test Coverage

```bash
$ yarn test:cov
```

## Table of Contents

1. [Overview](#overview)
2. [Folder Structure](#folder-structure)
   1. [Config](#config)
   2. [Helpers](#helpers)
   3. [Middleware](#middleware)
   4. [Modules](#modules)

## Implemented Tools and Modules

- **Swagger UI**: Swagger UI is integrated to provide a user-friendly interface for API documentation.
- **Exception Handling**: Exception Handle middleware and using NestJs built-in Exception class.
- **Logger**: Built-in NestJs Logger.
- **Mongoose**: Mongoose is employed as the MongoDB Object-Relational Mapping (ORM) library.
- **Bcryptjs**: Bcryptjs is used for password encryption.
- **JWT**: Built-in NestJs Security JWT Auth implementation.
- **Copyright**: The codebase is copyrighted to ICANIO Technologies.
- **Module Structure**: The project follows a modular approach, with separate folders for controllers, services, schemas, and interfaces for each module.

## Folder Structure

The project follows a well-organized folder structure for better maintainability and code separation. Each folder has a specific purpose.

### Config

The `config` folder contains configuration files related to the database, environment variables, authentication, and other configurations.

![Config Folder](src%2Fasserts%2Fdocumentation%2Fconfig.png)

### Helpers

The `helpers` folder contains common methods and classes that are used throughout the project to assist in various tasks.

![Helpers Folder](src%2Fasserts%2Fdocumentation%2Fhelpers.png)

### Middleware

The `middleware` folder houses custom middleware configurations that can be applied to the project's routes and endpoints.

![Middleware Folder](src%2Fasserts%2Fdocumentation%2Fmiddleware.png)

### Modules

The `modules` folder is used to organize all the modules in the project. Each module typically includes its own controller, services, schema, and interfaces for better code separation and maintainability.

![Modules Folder](src%2Fasserts%2Fdocumentation%2Fmodules.png)

This documentation provides an overview of the project's structure, tools, and modules, making it easier for developers to understand and work on the project. It's a valuable resource for onboarding new team members and maintaining the project.

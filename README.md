## JabberPoint Project

JabberPoint is a simple slide-show application implemented in Java. This project aims to demonstrate principles of software quality, design patterns, and unit testing, ensuring the codebase is maintainable, extensible, and flexible using SOLID principles and Design Patterns like Composite, Observer, and Strategy.

## Table of Contents
Technologies & Frameworks Used

How to Set Up the Project

Prerequisites

Clone the Repository

Build the Project

Run the Application

Run Unit Tests

View Code Coverage

Testing Details

Unit Tests

Code Coverage Reporting

File Structure

Conclusion

## Technologies & Frameworks Used
Java 17: Programming language for building the project.

Maven: Build automation tool used for dependency management, building, and running tests.

JUnit 5: Testing framework for writing unit tests.

Mockito: Mocking framework used to simulate dependencies in tests.

Jackson: JSON processing library for parsing and serializing presentations.

XML Parsing (DOM): Used to parse and save presentations in XML format.

OWASP Dependency Check: Ensures that dependencies are free from known security vulnerabilities.

JaCoCo: Code coverage tool used to generate reports.

Checkstyle: Ensures the code follows coding standards.

## How to Set Up the Project
Prerequisites
Before you begin, ensure you have the following installed on your local machine:

Java 17 or later.

Maven for managing dependencies and building the project.

Clone the Repository
Clone the project repository to your local machine:

bash
Copy
Edit
git clone https://github.com/yourusername/jabberpoint.git
cd jabberpoint
Build the Project
Once the repository is cloned, build the project using Maven:

bash
Copy
Edit
mvn clean install
This will:

Download and install the required dependencies.

Compile the code.

Run all unit tests.

Package the project for deployment.

Run the Application
To run the application, use the following Maven command:

bash
Copy
Edit
mvn exec:java
This will execute the main() method in the jabberpoint.JabberPoint class and start the slide-show application.

Run Unit Tests
To run the unit tests, execute the following:

bash
Copy
Edit
mvn test
This will run all the tests located in src/test/java, ensuring that your application behaves as expected.

View Code Coverage
After running the tests, JaCoCo generates a code coverage report. To view it:

Open target/site/jacoco/index.html in your browser.

This will show a detailed code coverage report, including line and method coverage.

To open the report:

bash
Copy
Edit
open target/site/jacoco/index.html
Testing Details
Unit Tests
The project contains unit tests for the following components:

Presentation: Ensures that the slides are correctly managed, navigated, and saved/loaded in both JSON and XML formats.

Slide & SlideItem: Verifies the correct functionality for drawing, bounding box calculation, and item management.

Design Patterns: Tests validate the proper use of the Composite, Observer, and Strategy design patterns.

Key test classes:

MenuControllerTest.java: Tests menu interactions.

KeyControllerTest.java: Verifies key press behavior.

SlideTest.java: Ensures slide behavior and item management.

PresentationTest.java: Verifies overall presentation functionality.

Code Coverage Reporting
After running the tests, JaCoCo generates a code coverage report at target/site/jacoco. This report shows the percentage of lines and methods covered by tests. Aim for at least 70% line coverage as per the project requirements.

Green: Covered code.

Red: Uncovered code.

Yellow: Partially covered code.

## File Structure

The project follows a typical Maven directory structure:

```
jabberpoint/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── jabberpoint/
│   │   │       ├── BitmapItem.java
│   │   │       ├── Presentation.java
│   │   │       └── ... (other Java classes)
│   └── test/
│       └── java/
│           └── jabberpoint/
│               ├── BitmapItemTest.java
│               ├── PresentationTest.java
│               └── ... (other test files)
├── target/
│   ├── site/
│   │   ├── jacoco/
│   │   │   └── index.html  <-- Code coverage report
├── pom.xml  <-- Maven configuration file
```

## GitHub Actions
GitHub Actions is used to automate workflows, including continuous integration (CI), testing, and deployment. For this project, we have a Java CI with Maven workflow configured in the .github/workflows/build.yml file. The workflow file (build.yml) automates the following tasks every time there is a push or pull request to the main branch

Explanation of Steps:
Checkout repository: Checks out the latest code from the repository.

Set up JDK 17: Installs JDK 17 (temurin distribution).

Build with Maven: Runs mvn clean install to build the project.

Run tests: Executes mvn test to run unit tests.

Code Quality Check: Runs Checkstyle for code style analysis (mvn checkstyle:check).

Generate Code Coverage Report: Runs JaCoCo to generate a code coverage report (mvn jacoco:report).

Security Vulnerability Scan: Uses OWASP Dependency Check to scan for known vulnerabilities in project dependencies.

Upload Code Coverage Report: Uploads the JaCoCo report to GitHub actions so you can download and view it later.

Viewing the Code Coverage Report
The Code Coverage Report generated by JaCoCo is uploaded as an artifact. You can view this report under the Artifacts section in the workflow summary, or by navigating to the target/site/jacoco/index.html file in the project directory.

Rulesets in GitHub
Branch protection rules and rulesets are configured to enforce best practices and ensure quality control before changes are merged into the main branch.

Key Rules Applied:
Restrict deletions: Only users with specific permissions can delete branches.

Require linear history: Ensures that the commit history remains clean by preventing merge commits.

Require deployments to succeed: Forces successful deployment before pushing changes to the main branch.

Require signed commits: Ensures all commits have verified signatures.

Require a pull request before merging: All changes must be made via pull requests, ensuring proper review and approval before merging into the main branch.

Block force pushes: Prevents force pushes to the protected branches.

Settings:
Require status checks to pass: All tests and quality checks must pass before merging.

Require code scanning results: Configures GitHub Code Scanning to automatically run security and code quality checks when changes are made.

This ensures that all changes pushed to the repository are thoroughly tested, verified, and secure before being merged into the main branch.

## Conclusion
This project demonstrates how to apply design patterns and SOLID principles to create a maintainable and extensible slide-show application. With comprehensive unit tests and code coverage reporting integrated into the Maven build process, the project ensures that all code is tested and of high quality.

By following the setup instructions, you should be able to build, run, and test the JabberPoint project on your machine. The code is well-structured, and the unit tests ensure that it functions as expected.

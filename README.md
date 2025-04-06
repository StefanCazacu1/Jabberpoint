JabberPoint Project
JabberPoint is a simple slide-show application implemented in Java. This project aims to demonstrate principles of software quality, design patterns, and unit testing, ensuring the codebase is maintainable, extensible, and flexible using SOLID principles and Design Patterns like Composite, Observer, and Strategy.

Table of Contents
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

Technologies & Frameworks Used
Java 17: Programming language for building the project.

Maven: Build automation tool used for dependency management, building, and running tests.

JUnit 5: Testing framework for writing unit tests.

Mockito: Mocking framework used to simulate dependencies in tests.

Jackson: JSON processing library for parsing and serializing presentations.

XML Parsing (DOM): Used to parse and save presentations in XML format.

OWASP Dependency Check: Ensures that dependencies are free from known security vulnerabilities.

JaCoCo: Code coverage tool used to generate reports.

Checkstyle: Ensures the code follows coding standards.

How to Set Up the Project
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

File Structure
The project follows a typical Maven directory structure:

css
Copy
Edit
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

Conclusion
This project demonstrates how to apply design patterns and SOLID principles to create a maintainable and extensible slide-show application. With comprehensive unit tests and code coverage reporting integrated into the Maven build process, the project ensures that all code is tested and of high quality.

By following the setup instructions, you should be able to build, run, and test the JabberPoint project on your machine. The code is well-structured, and the unit tests ensure that it functions as expected.

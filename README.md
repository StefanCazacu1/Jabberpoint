JabberPoint Project - README

Overview

JabberPoint is a simple slide-show application implemented in Java. This project aims to demonstrate principles of software quality, design patterns, and unit testing. The goal is to improve the existing codebase, making it more maintainable, extensible, and flexible using SOLID principles and Design Patterns like Composite, Observer, and Strategy.

Technologies & Frameworks Used
Java 17: The programming language used to build the project.

Maven: A build automation tool used to manage dependencies, build the project, and run tests.

JUnit 5: The testing framework used to write unit tests.

Mockito: A mocking framework used in the tests to simulate the behavior of dependencies and verify interactions.

Jackson (Jackson Databind): Used for JSON parsing (for saving and loading presentations in JSON format).

XML Parsing (DOM): Used for parsing and saving presentations in XML format.

OWASP Dependency Check: A tool to check for known vulnerabilities in dependencies.

JaCoCo: Used for code coverage reporting to ensure test effectiveness.

Checkstyle: Ensures that the code adheres to coding standards.

How to Set Up the Project
Prerequisites
Java 17: Install Java 17 or later on your system.

Maven: Install Maven to manage dependencies and build the project.

1. Clone the Repository
First, clone the repository from GitHub to your local machine.

bash
Copy
Edit
git clone https://github.com/yourusername/jabberpoint.git
cd jabberpoint
2. Build the Project
Once the repository is cloned, navigate to the project folder and build the project using Maven:

bash
Copy
Edit
mvn clean install
This command will:

Install all necessary dependencies listed in the pom.xml file.

Compile the code.

Run the tests.

Package the application.

3. Running the Application
To run the application, use the following Maven command:

bash
Copy
Edit
mvn exec:java
This will run the main class (jabberpoint.JabberPoint), starting the slide-show application.

4. Running Unit Tests
To run unit tests, execute the following command:

bash
Copy
Edit
mvn test
This will run all tests in the src/test/java directory. It includes tests for the presentation, slide, slide items, and various components like the menu and key controllers.

5. Viewing Code Coverage
After running the tests, you can view the code coverage report:

In the target directory (target/site/jacoco), open index.html in any web browser.

This page will show the detailed line coverage of your code, which ensures that at least 70% of your code is covered by unit tests.

To view the report, open the index.html file in a browser:

bash
Copy
Edit
open target/site/jacoco/index.html
6. Static Code Analysis
Maven also runs Checkstyle to ensure that the code follows a consistent style. You can view the Checkstyle results in the Maven build output. It will indicate any coding standard violations.

Testing Details
Unit Tests
The project includes extensive unit tests for verifying the functionality of various components:

Presentation: Ensures that slides can be added, navigated, and correctly loaded/saved in both JSON and XML formats.

Slide and SlideItems: Tests focus on drawing functionality, getting bounding boxes, and ensuring items are correctly added and retrieved.

Design Patterns: The tests verify that Composite, Observer, and Strategy patterns are implemented correctly.

The tests are located in the src/test/java folder and include the following key files:

MenuControllerTest.java: Verifies that the menu items (e.g., Next Slide, Previous Slide) interact correctly with the presentation logic.

KeyControllerTest.java: Verifies that key presses trigger the correct actions (e.g., Next Slide, Previous Slide).

SlideTest.java: Verifies slide-related functionality, including item management and rendering.

PresentationTest.java: Verifies overall presentation functionality, such as navigating between slides and loading presentations.

Code Coverage Reporting
After running the tests, JaCoCo generates a code coverage report. This report is stored in the target/site/jacoco directory, and you can view it by opening the index.html file in a browser. This report shows how much of the code is covered by unit tests, ensuring that the tests adequately cover the project's functionality. The goal is to achieve at least 70% line coverage.

The generated report will look something like this:

Green: Code that is covered by tests.

Red: Code that is not covered by tests.

Yellow: Code partially covered by tests.

Example of Code Coverage Output:
Method Coverage: Shows which methods were executed during the tests.

Line Coverage: Shows the lines of code that were executed.

File Structure
Here’s a brief overview of the project’s directory structure:

css
Copy
Edit
jabberpoint/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── jabberpoint/
│   │   │   │   ├── BitmapItem.java
│   │   │   │   ├── DemoPresentation.java
│   │   │   │   ├── Presentation.java
│   │   │   │   └── ... (other classes)
│   └── test/
│       └── java/
│           └── jabberpoint/
│               ├── BitmapItemTest.java
│               ├── PresentationTest.java
│               ├── ... (other test files)
├── target/
│   ├── site/
│   │   ├── jacoco/
│   │   │   └── index.html  <-- Code coverage report
├── pom.xml  <-- Maven configuration
7. Required Files for Running
Presentation Files: You can create or load existing presentations in XML or JSON format. The supported formats are:

XML: Structured with <presentation>, <slide>, and <item> tags.

JSON: Structured with title and slides as top-level keys, where each slide contains an array of items.

Conclusion
This project demonstrates the application of design patterns, unit testing, and software quality principles in a Java application. The extensive unit tests ensure that the system behaves correctly, and the CI pipeline ensures continuous integration with code quality checks and code coverage reporting.

By following the instructions in this README, you should be able to set up, run, and test the JabberPoint project on your local machine.

# Secure Code Review

This project provides a tool to perform a security review of C source code files using both static code analysis tools and manual code review. It helps in identifying common security vulnerabilities and generating a comprehensive report.

## Features

- **Automated Analysis**: Integrates with `Cppcheck` and `Flawfinder` to perform static analysis on C source files.
- **Manual Review**: Includes a custom manual code review for common insecure coding practices.
- **Report Generation**: Compiles results from automated and manual reviews into a single report for easy review.

## Prerequisites

- **Java Development Kit (JDK)**: Ensure JDK is installed on your system.
- **Cppcheck**: A static analysis tool for C/C++ code.
- **Flawfinder**: A tool to examine C/C++ source code for security flaws.

## Installation

### Install JDK
```sh
sudo apt-get install default-jdk

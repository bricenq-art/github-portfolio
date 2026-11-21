#include <iostream>
#include <sstream>
#include <vector>
#include <string>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fstream> // For file handling

// Function to parse user input into commands and arguments
void parseInput(const std::string& input, std::vector<std::string>& args) {
    std::istringstream iss(input);
    std::string token;
    while (iss >> token) {
        args.push_back(token);
    }
}

// Function to execute external commands
void executeCommand(const std::vector<std::string>& args) {
    if (args.empty()) return;

    std::vector<char*> c_args;
    for (const auto& arg : args) {
        c_args.push_back(const_cast<char*>(arg.c_str()));
    }
    c_args.push_back(nullptr); // Null-terminate the argument list

    if (fork() == 0) {
        // Child process
        if (execvp(c_args[0], c_args.data()) == -1) {
            perror("Error executing command");
        }
        exit(EXIT_FAILURE);
    } else {
        // Parent process
        int status;
        waitpid(-1, &status, 0);
    }
}

// Function to handle the "cd" command
void changeDirectory(const std::vector<std::string>& args) {
    if (args.size() < 2) {
        std::cerr << "Error: Missing directory argument for 'cd'" << std::endl;
        return;
    }

    if (chdir(args[1].c_str()) != 0) {
        perror("Error changing directory");
    }
}

// Function to display the contents of "readme.txt"
void displayReadme() {
    std::ifstream file("readme.txt");
    if (!file.is_open()) {
        std::cerr << "Error: Could not open readme.txt" << std::endl;
        return;
    }

    std::string line;
    while (std::getline(file, line)) {
        std::cout << line << std::endl;
    }

    file.close();
}

// Function to write to a specified file
void writeToFile(const std::vector<std::string>& args) {
    if (args.size() < 3) {
        std::cerr << "Error: Usage is 'write filename text'" << std::endl;
        return;
    }

    std::ofstream file(args[1], std::ios::app); // Open file in append mode
    if (!file.is_open()) {
        std::cerr << "Error: Could not open file " << args[1] << std::endl;
        return;
    }

    for (size_t i = 2; i < args.size(); ++i) {
        file << args[i] << " ";
    }
    file << std::endl;

    file.close();
    std::cout << "Text added to " << args[1] << std::endl;
}

int main() {
    std::string input;
    while (true) {
        // Prompt user
        std::cout << "my_shell> ";
        std::getline(std::cin, input);

        // Parse input into arguments
        std::vector<std::string> args;
        parseInput(input, args);

        if (args.empty()) {
            continue; // Skip empty input
        }

        if (args[0] == "exit") {
            break; // Exit the shell loop
        } else if (args[0] == "readme") {
            displayReadme(); // Handle "readme" command
        } else if (args[0] == "cd") {
            changeDirectory(args); // Handle "cd" command
        } else if (args[0] == "write") {
            writeToFile(args); // Handle "write" command
        } else {
            executeCommand(args); // Handle external commands
        }
    }
    return 0;
}
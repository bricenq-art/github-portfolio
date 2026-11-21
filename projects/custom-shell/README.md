# Custom Command-Line Shell (C++)

## Project Overview

This project was developed for COSC 4302 to demonstrate basic operating system concepts through the creation of a custom command-line shell using C++. The program, named `my_shell`, allows users to enter commands, execute external programs, navigate directories, and perform basic file operations.

The main goal of this project was to gain a better understanding of how operating systems handle user input, create processes, and execute commands.

## Technologies Used

- **Programming Language:** C++
- **Operating System:** Linux / Unix-based systems
- **Compiler:** g++
- **System Calls:** `fork()`, `execvp()`, `waitpid()`, `chdir()`
- **File Handling:** C++ file streams (`ifstream`, `ofstream`)

## Features

- **Custom Command Prompt:** Displays `my_shell>` when waiting for user input.
- **Command Execution:** Executes external commands such as `ls`, `pwd`, and `echo`.
- **Directory Navigation:** Supports the `cd` command to change directories.
- **File Reading:** Uses the `readme` command to display the contents of `readme.txt`.
- **File Writing:** Uses the `write` command to append text to a specified file.
- **Exit Command:** Allows users to close the shell by entering `exit`.
- **Error Handling:** Displays error messages when commands cannot be executed or files cannot be opened.

## How It Works

The shell operates through a continuous loop that accepts user input and processes commands.

1. Displays the `my_shell>` prompt.
2. Reads the command entered by the user.
3. Separates the command into arguments.
4. Checks whether the command is built into the shell.
5. If it is an external command, creates a child process using `fork()`.
6. Executes the command using `execvp()`.
7. Waits for the child process to finish before accepting another command.

This process demonstrates how a basic command-line interpreter interacts with the operating system.

## Compiling and Running

The program is designed for Linux or Unix-based environments.

**Step 1: Compile the program**

```bash
g++ -o Shell shell.cpp
```

**Step 2: Run the shell**

```bash
./Shell
```

**Step 3: Enter commands**

```bash
my_shell> pwd
my_shell> ls
my_shell> echo Hello World
my_shell> cd ..
my_shell> write notes.txt This is a test
my_shell> exit
```

The `readme` command can also be used when a `readme.txt` file exists in the current directory.

## Challenges and Learning Experience

One of the main challenges of this project was understanding how process creation and command execution work in Linux. Using `fork()` and `execvp()` helped demonstrate how the operating system creates child processes and executes external programs.

Another important part of the project was handling user input and separating commands into arguments. Implementing built-in commands also helped demonstrate the difference between commands handled directly by the shell and external programs executed by the operating system.

Overall, this project helped strengthen my understanding of C++, operating systems, process management, and file handling.

## Future Improvements

Potential improvements include:

- Supporting command history.
- Implementing command pipelines using `|`.
- Adding input and output redirection using `<` and `>`.
- Improving command parsing to support quoted arguments.
- Adding more advanced error handling.

## Project Files

- `shell.cpp` — Main C++ source code.
- `README.md` — Project documentation.

## Author

**Bricen Quebedeaux**  
Bachelor of Science in Computer Science  
Lamar University  
Expected Graduation: December 2026

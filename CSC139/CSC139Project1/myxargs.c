#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/wait.h>

#define MAX_ARGS 100
#define MAX_INPUT 1024

// Function prototypes
void sanitize_input(char *input);
void print_usage(void);
void replace_placeholder(char *result, const char *command, const char *placeholder, const char *replacement);
void execute_command(char *cmd_args[], int t_flag);
void handle_no_input(int argc, char *argv[], int t_flag, int r_flag);
void handle_input(int argc, char *argv[], char *input, int n, int t_flag, const char *replace_str);

int main(int argc, char *argv[]) {
    int n = MAX_ARGS;         // Default number of arguments
    int t_flag = 0;           // Flag to print command before execution
    int r_flag = 0;           // Flag to not run command if no input
    char *replace_str = NULL; // String to replace using -I option

    // Parsing command-line arguments
    int opt;
    while ((opt = getopt(argc, argv, "n:I:tr")) != -1) {
        switch (opt) {
            case 'n':
                n = atoi(optarg);
                break;
            case 'I':
                replace_str = optarg;
                break;
            case 't':
                t_flag = 1;
                break;
            case 'r':
                r_flag = 1;
                break;
            default:
                print_usage();
                return EXIT_FAILURE;
        }
    }

    // Ensure command is provided
    if (optind >= argc) {
        print_usage();
        return EXIT_FAILURE;
    }

    // Read input from stdin
    char input[MAX_INPUT];
    if (fgets(input, MAX_INPUT, stdin) == NULL) {
        if (r_flag) return 0;
    }

    sanitize_input(input);

    // Handle case with no input
    if (input[0] == '\0' || strcmp(input, "\n") == 0) {
        handle_no_input(argc, argv, t_flag, r_flag);
        return 0;
    }

    // Process input and execute commands
    handle_input(argc, argv, input, n, t_flag, replace_str);
    return 0;
}

void sanitize_input(char *input) {
    const char *special_chars = ";&|><*?()$";
    char *dst = input;
    while (*input) {
        if (!strchr(special_chars, *input)) {
            *dst++ = *input;
        }
        input++;
    }
    *dst = '\0';
}

void print_usage(void) {
    printf("Usage: myxargs [-n num] [-I replace] [-t] [-r] command\n");
}

void replace_placeholder(char *result, const char *command, const char *placeholder, const char *replacement) {
    const char *pos;
    int placeholder_len = strlen(placeholder);
    int replacement_len = strlen(replacement);
    
    while ((pos = strstr(command, placeholder)) != NULL) {
        int len = pos - command;
        strncpy(result, command, len);
        result += len;
        strcpy(result, replacement);
        result += replacement_len;
        command = pos + placeholder_len;
    }
    strcpy(result, command);
}

void execute_command(char *cmd_args[], int t_flag) {
    if (t_flag) {
        printf("+ ");
        for (int i = 0; cmd_args[i]; i++) {
            printf("%s ", cmd_args[i]);
        }
        printf("\n");
    }

    pid_t pid = fork();
    if (pid == 0) {
        execvp(cmd_args[0], cmd_args);
        perror("execvp");
        exit(EXIT_FAILURE);
    } else if (pid > 0) {
        int status;
        waitpid(pid, &status, 0);
        if (status != 0) {
            fprintf(stderr, "Command failed with status %d\n", status);
        }
    } else {
        perror("fork");
        exit(EXIT_FAILURE);
    }
}

void handle_no_input(int argc, char *argv[], int t_flag, int r_flag) {
    if (r_flag) return;

    char *cmd_args[MAX_ARGS];
    int cmd_arg_count = 0;

    for (int i = optind; i < argc; i++) {
        cmd_args[cmd_arg_count++] = argv[i];
    }
    cmd_args[cmd_arg_count] = NULL;

    execute_command(cmd_args, t_flag);
}

void handle_input(int argc, char *argv[], char *input, int n, int t_flag, const char *replace_str) {
    char *args[MAX_ARGS];
    int arg_count = 0;
    char *token = strtok(input, " \t\n");

    while (token != NULL) {
        args[arg_count++] = token;
        token = strtok(NULL, " \t\n");
        if (arg_count >= MAX_ARGS) break;
    }
    args[arg_count] = NULL;

    if (arg_count == 0 && t_flag) return;

    int executed = 0;
    char result[MAX_INPUT];

    for (int i = 0; i < arg_count; i += n) {
        int batch_size = (arg_count - i < n) ? (arg_count - i) : n;
        char *cmd_args[MAX_ARGS];
        int cmd_arg_count = 0;

        if (replace_str) {
            for (int j = optind; j < argc; j++) {
                if (strstr(argv[j], replace_str)) {
                    char batch_input[MAX_INPUT] = "";
                    for (int k = 0; k < batch_size; k++) {
                        strcat(batch_input, args[i + k]);
                        if (k < batch_size - 1) strcat(batch_input, " ");
                    }

                    replace_placeholder(result, argv[j], replace_str, batch_input);
                    cmd_args[cmd_arg_count++] = strdup(result);
                } else {
                    cmd_args[cmd_arg_count++] = argv[j];
                }
            }
        } else {
            for (int j = optind; j < argc; j++) {
                cmd_args[cmd_arg_count++] = argv[j];
            }
            for (int j = 0; j < batch_size; j++) {
                cmd_args[cmd_arg_count++] = args[i + j];
            }
        }
        cmd_args[cmd_arg_count] = NULL;

        execute_command(cmd_args, t_flag);
        executed = 1;
    }

    if (!executed && t_flag) return;
}

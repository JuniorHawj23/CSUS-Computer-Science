/**
 * CPE/CSC 159 - Operating System Pragmatics
 * California State University, Sacramento
 *
 * Kernel Functions and Helpers
 */
#ifndef KERNEL_H
#define KERNEL_H

#ifndef OS_NAME
#define OS_NAME "MyOS"
#endif

typedef enum log_level {
    KERNEL_LOG_LEVEL_NONE,
    KERNEL_LOG_LEVEL_ERROR,
    KERNEL_LOG_LEVEL_WARN,
    KERNEL_LOG_LEVEL_INFO,
    KERNEL_LOG_LEVEL_DEBUG,
    KERNEL_LOG_LEVEL_TRACE,
    KERNEL_LOG_LEVEL_ALL
} log_level_t;

void kernel_init(void);
void kernel_log_error(char *msg, ...);
void kernel_log_warn(char *msg, ...);
void kernel_log_info(char *msg, ...);
void kernel_log_debug(char *msg, ...);
void kernel_log_trace(char *msg, ...);
void kernel_panic(char *msg, ...);
int kernel_get_log_level(void);
int kernel_set_log_level(log_level_t level);
void kernel_command(char c);
void kernel_break(void);
void kernel_exit(void);

#endif

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/mmtD-63g)
### Please update your name at here

Brian Hert

<h3>Assignment - Timer Event</h3>

<p>
The objective of this assigment is to practice how to
program to use the timer interrupt events.
</p>

### Implementation
Review the provided source code, then write your own code to complete the following three functions:
```
src/main.c: Process()
src/mainc.: Main()
src/handlers.c: TimerHandler()
```

### Compile and Run your code. 
Compile into <i>MyOS.dli</i>
```
make
```
Start the target window, and you can see the `spede.sock` file in current folder,  execute the following command:
```
spede-target
```

To run your code in GDB mode, execute

```
spede-run -d
```

### Assignment Requirements

1. **src/handlers.c: `TimerHandler()`**
    - The timer-driven routine should display your name, one character at a time.
    - Each character should be displayed every 3/4 seconds.
    - The output should be centered on the screen of the target PC.
    - Once the full name is displayed, it should be erased, and the process should restart.

2. **src/main.c: `Process()`**
    - This function should run indefinitely.
    - It should terminate when a key is pressed, and you need to print a message indicating that the process has terminated.
    - Simulate a 1-second delay using `for` loop.
    - Every second, display the character 'z' at the bottom of the screen.

3. **src/main.c: `main()`**
    - Enable CPU interrupts.
    - Call the `Process()` function.

### Example code

See `example.c` file for useful SPEDE I/O functions. You may need the following functions:

```
cons_putchar()
cons_printf()
cons_kbhit()
```

### Attention
You can only see the `printf()` output at GDB mode. 

### Submission

After completing the assignment, only modify the following two files:
* src/main.c
* src/handlers.c

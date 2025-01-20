// Name: Brian Hert
// Tested OS: Linux (ECS Computing Environment)
// Hardware Configuration: Intel Core i7-9700K, 16 GB RAM
// Description: This program demonstrates multi-threaded modular product computation
// using semaphores and busy waiting for synchronization. The program divides the
// computation among multiple threads and handles edge cases like zero in the array.


#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>
#include <semaphore.h>
#include <stdbool.h> // For bool, true, false


// Constants
#define NUM_LIMIT 9973
#define MAX_THREADS 16
#define RANDOM_SEED 7649
#define MAX_SIZE 100000000
#define MAX_RANDOM_NUMBER 3000


// Global variables
long gRefTime;                          // Reference time for timing
int* gData;                             // Pointer to dynamically allocated data array
int gThreadCount;                       // Number of threads
int gDoneThreadCount;                   // Counter for threads that have completed
int gThreadProd[MAX_THREADS];           // Modular product for each thread
volatile bool gThreadDone[MAX_THREADS]; // Completion status for threads
int indices[MAX_THREADS][3];            // Indices for thread array divisions
sem_t completed;                        // Semaphore for parent-child thread signaling
sem_t mutex;                            // Mutex semaphore for shared variable protection




// Initialization functions
void GenerateArrayData(int size, int indexForZero);
void DivideArrayIndices(int arraySize, int threadCount, int indices[MAX_THREADS][3]);
void InitializeSharedVariables();


// Utility functions
int GetRandomNumber(int min, int max);
long GetCurrentTime(void);
void StartTimer(void);
long GetElapsedTime(void);


// Threaded computation functions
void* ThreadComputeWithoutSemaphore(void* param);
void* ThreadComputeWithSemaphore(void* param);


// Sequential computation and result aggregation
int SequentialProductComputation(int size);
int AggregateThreadProducts();


// Main program
int main(int argc, char* argv[]) {
    pthread_t tid[MAX_THREADS];
    int threadArgs[MAX_THREADS];
    int arraySize, indexForZero, product;


    // Validate input arguments
    if (argc != 4) {
        fprintf(stderr, "Usage: ./MTFindProd <array_size> <num_threads> <index_for_zero>\n");
        exit(EXIT_FAILURE);
    }


    arraySize = atoi(argv[1]);
    if (arraySize <= 0 || arraySize > MAX_SIZE) {
        fprintf(stderr, "Error: Array size must be between 1 and %d.\n", MAX_SIZE);
        exit(EXIT_FAILURE);
    }


    gThreadCount = atoi(argv[2]);
    if (gThreadCount <= 0 || gThreadCount > MAX_THREADS) {
        fprintf(stderr, "Error: Number of threads must be between 1 and %d.\n", MAX_THREADS);
        exit(EXIT_FAILURE);
    }


    indexForZero = atoi(argv[3]);
    if (indexForZero < -1 || indexForZero >= arraySize) {
        fprintf(stderr, "Error: Index for zero must be -1 or within the array range.\n");
        exit(EXIT_FAILURE);
    }


    // Allocate memory for the data array
    gData = malloc(arraySize * sizeof(int));
    if (!gData) {
        fprintf(stderr, "Error: Memory allocation for data array failed.\n");
        exit(EXIT_FAILURE);
    }


    // Generate array data and divide work among threads
    GenerateArrayData(arraySize, indexForZero);
    DivideArrayIndices(arraySize, gThreadCount, indices);


    // Debugging: Show thread array partitions
    printf("[INFO] Array divided among %d threads:\n", gThreadCount);
    for (int i = 0; i < gThreadCount; i++) {
        printf("Thread %d: Start = %d, End = %d\n", i, indices[i][1], indices[i][2]);
    }


    // Perform sequential computation
    StartTimer();
    product = SequentialProductComputation(arraySize);
    printf("[RESULT] Sequential computation completed in %ld ms. Product = %d\n", GetElapsedTime(), product);


    // Perform threaded computation with parent waiting for all threads
    InitializeSharedVariables();
    StartTimer();
    for (int i = 0; i < gThreadCount; i++) {
        threadArgs[i] = i;
        pthread_create(&tid[i], NULL, ThreadComputeWithoutSemaphore, &threadArgs[i]);
    }
    for (int i = 0; i < gThreadCount; i++) {
        pthread_join(tid[i], NULL);
    }
    product = AggregateThreadProducts();
    printf("[RESULT] Threaded computation with parent waiting completed in %ld ms. Product = %d\n", GetElapsedTime(), product);


    // Perform threaded computation with busy waiting
    InitializeSharedVariables();
    StartTimer();
    for (int i = 0; i < gThreadCount; i++) {
        threadArgs[i] = i;
        pthread_create(&tid[i], NULL, ThreadComputeWithoutSemaphore, &threadArgs[i]);
    }
    bool allDone = false;
    while (!allDone) {
        allDone = true;
        for (int i = 0; i < gThreadCount; i++) {
            if (!gThreadDone[i]) allDone = false;
        }
    }
    product = AggregateThreadProducts();
    printf("[RESULT] Threaded computation with busy waiting completed in %ld ms. Product = %d\n", GetElapsedTime(), product);


    // Perform threaded computation with semaphore synchronization
    InitializeSharedVariables();
    sem_init(&completed, 0, 0);
    sem_init(&mutex, 0, 1);
    StartTimer();
    for (int i = 0; i < gThreadCount; i++) {
        threadArgs[i] = i;
        pthread_create(&tid[i], NULL, ThreadComputeWithSemaphore, &threadArgs[i]);
    }
    sem_wait(&completed);
    product = AggregateThreadProducts();
    printf("[RESULT] Threaded computation with semaphore synchronization completed in %ld ms. Product = %d\n", GetElapsedTime(), product);


    // Cleanup
    sem_destroy(&completed);
    sem_destroy(&mutex);
    free(gData);
    return 0;
}


// ************************ Initialization Functions *************************


void GenerateArrayData(int size, int indexForZero) {
    srand(RANDOM_SEED);
    for (int i = 0; i < size; i++) {
        gData[i] = GetRandomNumber(1, MAX_RANDOM_NUMBER);
    }
    if (indexForZero >= 0) {
        gData[indexForZero] = 0;
    }
}


void DivideArrayIndices(int arraySize, int threadCount, int indices[MAX_THREADS][3]) {
    int baseSize = arraySize / threadCount;
    int remainder = arraySize % threadCount;
    for (int i = 0; i < threadCount; i++) {
        indices[i][1] = i * baseSize;
        indices[i][2] = (i + 1) * baseSize - 1;
    }
    indices[threadCount - 1][2] += remainder; // Assign remainder to the last thread
}


void InitializeSharedVariables() {
    for (int i = 0; i < gThreadCount; i++) {
        gThreadProd[i] = 1;
        gThreadDone[i] = false;
    }
    gDoneThreadCount = 0;
}


// ************************ Utility Functions *************************


int GetRandomNumber(int min, int max) {
    return min + rand() % (max - min + 1);
}


long GetCurrentTime(void) {
    struct timespec time;
    clock_gettime(CLOCK_MONOTONIC, &time);
    return time.tv_sec * 1000 + time.tv_nsec / 1000000;
}


void StartTimer(void) {
    gRefTime = GetCurrentTime();
}


long GetElapsedTime(void) {
    return GetCurrentTime() - gRefTime;
}


// ************************ Threaded Computation *************************


void* ThreadComputeWithoutSemaphore(void* param) {
    int threadNum = *((int*)param);
    int startIdx = indices[threadNum][1];
    int endIdx = indices[threadNum][2];
    for (int i = startIdx; i <= endIdx; i++) {
        if (gData[i] == 0) {
            gThreadProd[threadNum] = 0;
            gThreadDone[threadNum] = true;
            pthread_exit(0);
        }
        gThreadProd[threadNum] = (gThreadProd[threadNum] * gData[i]) % NUM_LIMIT;
    }
    gThreadDone[threadNum] = true;
    pthread_exit(0);
}


void* ThreadComputeWithSemaphore(void* param) {
    int threadNum = *((int*)param);
    int startIdx = indices[threadNum][1];
    int endIdx = indices[threadNum][2];
    int localProd = 1;


    for (int i = startIdx; i <= endIdx; i++) {
        if (gData[i] == 0) {
            gThreadProd[threadNum] = 0;
            sem_post(&completed);
            pthread_exit(0);
        }
        localProd = (localProd * gData[i]) % NUM_LIMIT;
    }
    gThreadProd[threadNum] = localProd;


    sem_wait(&mutex);
    gDoneThreadCount++;
    if (gDoneThreadCount == gThreadCount) {
        sem_post(&completed);
    }
    sem_post(&mutex);


    pthread_exit(0);
}


// ************************ Result Aggregation *************************


int SequentialProductComputation(int size) {
    int prod = 1;
    for (int i = 0; i < size; i++) {
        if (gData[i] == 0) return 0;
        prod = (prod * gData[i]) % NUM_LIMIT;
    }
    return prod;
}


int AggregateThreadProducts() {
    int finalProd = 1;
    for (int i = 0; i < gThreadCount; i++) {
        finalProd = (finalProd * gThreadProd[i]) % NUM_LIMIT;
    }
    return finalProd;
}
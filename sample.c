#include <stdio.h>
#include <string.h>
#include <stdlib.h>
void insecure_function() {
    char buffer[10];
    gets(buffer);
    printf("Buffer: %s\n", buffer);
}
int main() {
    char* dynamic_memory = malloc(100);
    strcpy(dynamic_memory, "This is a test");
    printf("%s\n", dynamic_memory);
    return 0;
}

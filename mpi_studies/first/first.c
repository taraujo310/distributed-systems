#include <stdio.h>
#include <string.h>
#include <mpi.h>

int main(int argc, char** argv) {
  int my_rank, processes_amount, origin_process, destiny_process, tag=0;
  char message[100];
  MPI_Status status;
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
  MPI_Comm_size(MPI_COMM_WORLD, &processes_amount);

  if(my_rank != 0) {
    sprintf(message, "Processo %d está vivo!", my_rank);
    destiny_process = 0;
    MPI_Send(message, strlen(message)+1, MPI_CHAR, destiny_process, tag, MPI_COMM_WORLD);
  } else {
    for (origin_process = 1; origin_process < processes_amount; origin_process++) {
      MPI_Recv(message, 100, MPI_CHAR, origin_process, tag, MPI_COMM_WORLD, &status);
      printf("%s\n", message);
    }
  }

  MPI_Finalize();
}

#include <stdio.h>
#include <string.h>
#include <mpi.h>

int main(int argc, char** argv) {
  int my_rank, processes_amount, origin, destiny, tag=0;
  char msg[100];
  MPI_Status status;
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
  MPI_Comm_size(MPI_COMM_WORLD, &processes_amount);

  printf("Eu sou o processo número %i de %i no grupo World\n", my_rank, processes_amount);

  MPI_Finalize();
}

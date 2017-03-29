#include <stdio.h>
#include <string.h>
#include <mpi.h>
#include <time.h>

int main(int argc, char** argv) {
  int my_rank, processes_amount, origin_process, tag=0;
  MPI_Status status;
  srand(time(NULL));
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &my_rank);
  MPI_Comm_size(MPI_COMM_WORLD, &processes_amount);

  if(my_rank != 0) {
    int number = square_rank(my_rank);
    printf("Eu sou o processo %d\n", my_rank);
    MPI_Send(&number, 1, MPI_INT, 0, tag, MPI_COMM_WORLD);
  } else {
    int received_number, sum_all=0;

    for (origin_process = 1; origin_process < processes_amount; origin_process++) {
      MPI_Recv(&received_number, 1, MPI_INT, origin_process, tag, MPI_COMM_WORLD, &status);
      sum_all += received_number;
    }

    printf("A soma do quadrado do rank de cada processo filho é %d\n", sum_all);
  }

  MPI_Finalize();
}

int square_rank(rank) {
  return rank*rank;
}

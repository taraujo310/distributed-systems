#include <stdio.h>
#include <mpi.h>
#include <math.h>

#define ARRAY_SIZE 5
#define RANGE 10
#define FINISHED_PRINT 4

void print_local_data(int *data, int rank);
void generate_local_data(int *data, int rank);
void sort();
int min(int a, int b);
int cut_process_amount(int processes_amount);
int merged_data_length(int *data, int *neighbor_data);

int main(int argc, char** argv) {
  int data[ARRAY_SIZE];
  int neighbor_data[ARRAY_SIZE];
  int rank, processes_amount, origin_process, tag=0, neighbor_printed, printed=1;
  double start, finish;
  MPI_Status status;
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &processes_amount);
  // if(rank == 0) start=MPI_Wtime();

  generate_local_data(data, rank);

  int virtual_processes_amount = processes_amount;
  while(virtual_processes_amount >= 0) {
    // printf("%d\n", virtual_processes_amount);
    if(rank < virtual_processes_amount) {
      int neighbor = find_neighbor(rank, virtual_processes_amount);

      if(rank < neighbor && neighbor > 0) {
        int father = min(rank, neighbor)/2;
        printf("NP: %d => merging processes (%d, %d) => %d\n", virtual_processes_amount, rank, neighbor, father);
      } else if(rank == neighbor) {
        int father = rank/2;
        printf("NP: %d => merging processes (%d, %d) => %d\n", virtual_processes_amount, rank, neighbor, father);
      }

      virtual_processes_amount = cut_process_amount(virtual_processes_amount);
    } else break;
  }

  // MPI_Barrier(MPI_COMM_WORLD);
  if(rank==0) print_local_data(data, rank);
  MPI_Finalize();
}

void sort() {

}

int merged_data_length(int *data, int *neighbor_data) {
  int length_my = sizeof(data)/sizeof(data[0]);
  int length_neighbor = sizeof(neighbor_data)/sizeof(neighbor_data[0]);
  return length_my + length_neighbor;
}

int cut_process_amount(int virtual_processes_amount) {
  int last_rank = virtual_processes_amount-1;
  if(last_rank == 0) return 0;
  if(last_rank % 2 == 0) return (last_rank/2)+1;
  return ((last_rank-1)/2)+1;
}

int find_neighbor(int rank, int processes_amount) {
  int neighbor;

  if(rank % 2 == 0) {
    neighbor = rank+1;
  } else {
    neighbor = rank-1;
  }

  if(neighbor < 0) return -1;
  else if (neighbor >= processes_amount) return rank;
  return neighbor;
}

int min(int a, int b) {
  if (a < b) return a;
  return b;
}

int comparator(const void* first, const void* second) {
  int a = * ((const int*) first);
  int b = * ((const int*) second);

  if (a < b) {
    return -1;
  } else if (a > b) {
    return 1;
  } else {
    return 0;
  }
}

void print_local_data(int *data, int rank) {
  int i;
  printf("Process %d: ", rank);
  for (i = 0; i < ARRAY_SIZE; i++) {
    printf("%d ", data[i]);
  }
  printf("\n");
}

void generate_local_data(int *data, int rank) {
  srand(rank+1);
  int i;

  for (i = 0; i < ARRAY_SIZE; i++) {
    data[i] = rand()%RANGE;
  }
}

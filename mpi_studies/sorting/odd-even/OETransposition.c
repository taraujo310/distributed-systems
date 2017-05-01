#include <stdio.h>
#include <mpi.h>

#define ARRAY_SIZE 1000
#define RANGE 100
#define FINISHED_PRINT 4

void print_local_data(int *data, int rank);
void generate_local_data(int *data, int rank);
void sort(int *data, int rank, int processes_amount, MPI_Status *status);
int find_neighbor(int rank, int phase, int processes_amount);
int is_even(int number);
void exchange_data(int rank, int neighbor, int *data, int *partners_data, MPI_Status *status);
void swap_elements(int *data, int *partners_data, int i, int j);

int main(int argc, char** argv) {
  int data[ARRAY_SIZE];
  int rank, processes_amount, origin_process, tag=0, neighbor_printed, printed=1;
  double start, finish;
  MPI_Status status;
  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &processes_amount);
  if(rank == 0) start=MPI_Wtime();

  generate_local_data(data, rank);
  // if(rank != 0) MPI_Recv(&neighbor_printed, 1, MPI_INT, rank-1, FINISHED_PRINT, MPI_COMM_WORLD, &status);
  // print_local_data(data, rank);
  // if(rank < processes_amount-1) MPI_Send(&printed, 1, MPI_INT, rank+1, FINISHED_PRINT, MPI_COMM_WORLD);
  //
  // MPI_Barrier(MPI_COMM_WORLD);
  // if(rank==0) printf("\n >> sort <<\n");
  sort(data, rank, processes_amount, &status);

  if(rank != 0) MPI_Recv(&neighbor_printed, 1, MPI_INT, rank-1, FINISHED_PRINT, MPI_COMM_WORLD, &status);
  print_local_data(data, rank);
  if(rank < processes_amount-1) MPI_Send(&printed, 1, MPI_INT, rank+1, FINISHED_PRINT, MPI_COMM_WORLD);

  MPI_Barrier(MPI_COMM_WORLD);
  if(rank == 0){
    finish=MPI_Wtime();
    printf("Tempo passado durante o processamento: %f\n", finish-start);
  }
  MPI_Finalize();
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

int smaller_index(int *list) {
  int smaller_index = 0, i;

  for (i = 0; i < ARRAY_SIZE; i++)
    if(list[i] < list[smaller_index])
      smaller_index = i;

  return smaller_index;
}

int greater_index(int *list) {
  int greater_index = 0, i;

  for (i = 0; i < ARRAY_SIZE; i++)
    if(list[i] > list[greater_index])
      greater_index = i;

  return greater_index;
}

void sort(int *data, int rank, int processes_amount, MPI_Status *status) {
  int i, j, partners_data[ARRAY_SIZE];

  for (i = 0; i < processes_amount; i++) {
    qsort(data, ARRAY_SIZE, sizeof(int), &comparator);

    int neighbor = find_neighbor(rank, i, processes_amount);
    if(neighbor >= 0) {
      // printf("Phase %d: swapping process %d and %d\n", i, rank, neighbor);
      exchange_data(rank, neighbor, data, partners_data, status);
      // printf("Phase %d: done swapping process %d and %d\n", i, rank, neighbor);

      int finished = 0;

      if (rank < neighbor) {
        while (!finished) {
          int mini = smaller_index(partners_data);
          int maxi = greater_index(data);
          if (partners_data[mini] < data[maxi]) {
            swap_elements(partners_data, data, mini, maxi);
          } else {
            /* else stop because the smallest are now in data */
            finished = 1;
          }
        }
      } else {
        while (!finished) {
          int maxi = greater_index(partners_data);
          int mini = smaller_index(data);
          if (partners_data[maxi] > data[mini]) {
            swap_elements(partners_data, data, maxi, mini);
          } else {
            finished = 1;
          }
        }
      }
    }
  }
}

void swap_elements(int *first, int *second, int first_i, int second_i) {
  int temp = first[first_i];
  first[first_i] = second[second_i];
  second[second_i] = temp;
}

void exchange_data(int rank, int neighbor, int *data, int *partners_data, MPI_Status *status) {
  // printf("%d\n", is_even(rank));
  if(is_even(rank)) {
    MPI_Send(data, ARRAY_SIZE, MPI_INT, neighbor, 0, MPI_COMM_WORLD);
    MPI_Recv(partners_data, ARRAY_SIZE, MPI_INT, neighbor, 0, MPI_COMM_WORLD, status);
  } else {
    MPI_Recv(partners_data, ARRAY_SIZE, MPI_INT, neighbor, 0, MPI_COMM_WORLD, status);
    MPI_Send(data, ARRAY_SIZE, MPI_INT, neighbor, 0, MPI_COMM_WORLD);
  }
}

int find_neighbor(int rank, int phase, int processes_amount) {
  int neighbor;

  if(is_even(phase)) {
    if(is_even(rank)) {
      neighbor = rank+1;
    } else {
      neighbor = rank-1;
    }
  } else {
    if(is_even(rank)) {
      neighbor = rank-1;
    } else {
      neighbor = rank+1;
    }
  }

  if(neighbor < 0 || neighbor >= processes_amount) return -1;
  return neighbor;
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

int is_even(int number) {
  return (number%2 == 0);
}

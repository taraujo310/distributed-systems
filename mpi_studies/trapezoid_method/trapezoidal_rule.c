#include <stdio.h>
#include <mpi.h>

float compute_interval(float a, float b, int process_intervals, float height);
float to_the_square(float x);

int main(int argc, char** argv) {
  // MPI specific variables
  int rank, processes_amount, source;
  double start, finish;

  // Trapezoid Method specifc variables
  float a=0.0, b=1.0, height, interval_start, interval_end, integral, total;
  int intervals=1024, process_intervals, remaining_process_intervals;

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &rank);
  MPI_Comm_size(MPI_COMM_WORLD, &processes_amount);
  if(rank == 0) start=MPI_Wtime(); /*start timer*/

  // Common calculation
  height = (b-a)/intervals;
  process_intervals = intervals/processes_amount;
  remaining_process_intervals = intervals%processes_amount;

  // Processe specific calculation
  interval_start = a + rank * process_intervals * height;
  interval_end = interval_start + process_intervals * height;
  printf("Process %d calculating the interval [%f, %f]\n", rank, interval_start, interval_end);
  integral = compute_interval(interval_start, interval_end, process_intervals, height);

  MPI_Reduce(&integral, &total, 1, MPI_FLOAT, MPI_SUM, 0, MPI_COMM_WORLD);

  MPI_Barrier(MPI_COMM_WORLD);
  if(rank == 0) {
    if (remaining_process_intervals != 0) {
      interval_start = a + processes_amount * process_intervals * height;
      interval_end = b;

      printf("Process 0 calculating the remaining interval [%f, %f]\n", interval_start, interval_end);

      total += compute_interval(interval_start, interval_end, remaining_process_intervals, height);
    }
    printf(">>> Resultado: %f\n", total);
  }

  finish=MPI_Wtime();

  if(rank == 0)
    printf("Tempo passado durante o processamento: %f\n", finish-start);

  MPI_Finalize();
}

float compute_interval(float a, float b, int process_intervals, float height) {
  float integral, x, i;

  integral = (to_the_square(a) + to_the_square(b))/2;

  x = a;
  for (i = 1; i <= process_intervals - 1; i++) {
    x += height;
    integral += to_the_square(x);
  }

  return integral*height;
}

float to_the_square(float x) {
  return x*x;
}

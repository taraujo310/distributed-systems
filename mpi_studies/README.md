# Aprendendo MPI para a disciplina Sistemas Distribuídos

Estou utilizando a biblioteca OpenMPI. Link para download: https://www.open-mpi.org/software/ompi/v2.1/


Para instalar MPI:

    wget https://www.open-mpi.org/software/ompi/v2.1/downloads/openmpi-2.1.0.tar.gz
    tar -xvf openmpi-2.1.0.tar.gz
    cd openmpi-2.1.0
    ./configure --prefix="/home/$USER/.openmpi"
    make && sudo make install
    echo 'export PATH=$PATH:/home/$USER/.openmpi/bin' >> ~/.bashrc
    echo 'export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/home/$USER/.openmpi/lib/' >> ~/.bashrc
    mpirun -V

Para rodar os programas:

    mpicc <filename>.c -o <binaryname>
    mpirun <binaryname>
    mpirun [-n 7] <binaryname>

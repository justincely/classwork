# --- targets
all: run_1 run_2 run_3

run_1: 
	c++ -o integrate.o integrate.c
	./integrate.o 1 euler1.txt -s 1
	./integrate.o 1 euler_3.txt -s .3
	./integrate.o 1 euler_1.txt -s .1
	./integrate.o 1 euler_03.txt -s .03
	./integrate.o 1 euler_01.txt -s .01

	./integrate.o 2 RK1.txt -s 1
	./integrate.o 2 RK_3.txt -s .3
	./integrate.o 2 RK_1.txt -s .1
	./integrate.o 2 RK_03.txt -s .03
	./integrate.o 2 RK_01.txt -s .01

	./integrate.o 3 leapfrog1.txt -s 1
	./integrate.o 3 leapfrog_3.txt -s .3
	./integrate.o 3 leapfrog_1.txt -s .1
	./integrate.o 3 leapfrog_03.txt -s .03
	./integrate.o 3 leapfrog_01.txt -s .01
	python plot.py

run_2: 
	c++ -o orbit.o orbit.c
	./orbit.o 2 O_RK1.txt -s 1
	./orbit.o 2 O_RK_5.txt -s .5
	./orbit.o 2 O_RK_25.txt -s .25
	./orbit.o 2 O_RK_1.txt -s .1

	./orbit.o 3 O_LF1.txt -s 1
	./orbit.o 3 O_LF_5.txt -s .5
	./orbit.o 3 O_LF_25.txt -s .25
	./orbit.o 3 O_LF_1.txt -s .1
	python plot_orbit.py


run_3:
	c++ -o pred_prey.o pred_prey.c
	./pred_prey.o pp1.txt -s 1
	./pred_prey.o pp_5.txt -s .5
	./pred_prey.o pp_25.txt -s .25
	./pred_prey.o pp_1.txt -s .1
	python plot_phase.py

# --- remove binary and executable files
clean:
	rm -f *.o
	rm -f *.txt
	rm Phase_plane.pdf
	rm Euler_int.pdf
	rm Leapfrog_int.pdf
	rm RK4_int.pdf
	rm RK4_orbit_int.pdf
	rm Leapfrog_orbit_int.pdf


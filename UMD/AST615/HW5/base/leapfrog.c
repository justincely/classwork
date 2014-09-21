/* Integration of 1-D simple harmonic motion using the Leapfrog scheme.
 * Usage:
 *	    simple_leapfrog  [-d time-step]  [-t end-time]  [-v initial-v]
 */

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
typedef double real;		/* (a convenient synonym...) */

real deriv(real x)		/* determine a = dv/dt, given x */
{
    return -1*x;
}

void advance_vel(real x, real *v, real dt2)
{
    *v += dt2*deriv(x);		/* offset the velocity by half a
				   time step using the Euler method */
}

void output(real x, real v, real t, real dt)
{
    /* Synchronize the velocity if necessary: */
    if (t > 0) advance_vel(x, &v, -0.5*dt);
    printf("%f %f %f\n", t, sin(t),x);
}

void leapfrog(real *x, real *v, real *t, real dt)
{
    *x+=dt*(*v);
    *v+=dt*deriv(*x);
    *t+=dt;
}

main(int argc, char** argv)
{
    /* Default integration parameters and initial conditions: */
    real t=0,x=0,v=1;
    real t_max=15,dt=0.1;
    int i;
    /* Parse the argument list. */
    for (i = 0; i < argc; i++)
	if (argv[i][0] == '-') {
	    switch (argv[i][1]) {
		case 'd':	dt = atof(argv[++i]); break;
		case 't':	t_max = atof(argv[++i]); break;
		case 'v':	v = atof(argv[++i]); break;
	    }
	}
    output(x, v, t, dt);
    advance_vel(x, &v, 0.5*dt);
    while (t < t_max) {
	leapfrog(&x, &v, &t, dt);
	output(x, v, t, dt);
    }
}

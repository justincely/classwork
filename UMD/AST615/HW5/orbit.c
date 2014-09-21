#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define	NDERIVS	4		//Number of derivatives

double deriv(double x){
    return -1*x;
}

void advance_vel(double x, double *v, double step){
    *v += step*deriv(x);		/* offset the velocity by half a
				   time step using the Euler method */
}

void leapfrog(double *x, double *v, double *t, double step){
    *x+=step*(*v);
    *v+=step*deriv(*x);
    *t+=step;
}

void lpf_int(char *fout,double t, double tmax, double step, double x, double zx, double v, double zv){
    /* Default integration parameters and initial conditions: */
    int i;
    double E;
    FILE *file;
    file=fopen(fout,"w");
    if (t > 0) advance_vel(x, &zx, -0.5*step);
    if (t > 0) advance_vel(v, &zv, -0.5*step);
    E=(zx*zx+zv*zv)/2.0+(-1)/(sqrt(1+2*x*x+2*v*v));
    printf("%f\t%e\t%e\t%e\t%e\t%e\n", t, x,zx,v,zv,E);
    fprintf(file,"%f\t%e\t%e\t%e\t%e\t%e\n", t, x,zx,v,zv,E);
    advance_vel(x,&zx, 0.5*step);
    advance_vel(v,&zv, 0.5*step);
    while (t < tmax) {
      leapfrog(&x, &zx, &t, step);
      leapfrog(&v, &zv, &t, step);
      E=(zx*zx+zv*zv)/2.0+(-1)/(sqrt(1+2*x*x+2*v*v));
      printf("%f\t%e\t%e\t%e\t%e\t%e\n", t, x,zx,v,zv,E);
      fprintf(file,"%f\t%e\t%e\t%e\t%e\t%e\n", t, x,zx,v,zv,E);
    }
}


double  f(double x, double y[], int i){
  //printf("%f\t%f\n",y[1],y[0]);
  if (i==0) return(y[1]);                 /* derivative of first equation */
  if (i==1) return((-2*y[0])/pow((1+2*y[0]*y[0]+2*y[2]*y[2]),(3/2)));        /* derivative of second equation */
  if (i==2) return(y[3]);                 /* derivative of first equation */
  if (i==3) return((-2*y[2])/pow((1+2*y[0]*y[0]+2*y[2]*y[2]),(3/2)));       /* derivative of second equation */
}

void runge4(double x, double y[], double step){
  int N=NDERIVS;
  double h=step/2.0,t1[N], t2[N], t3[N],k1[N], k2[N], k3[N],k4[N];
  int i;
  for (i=0;i<N;i++) t1[i]=y[i]+0.5*(k1[i]=step*f(x, y, i));
  for (i=0;i<N;i++) t2[i]=y[i]+0.5*(k2[i]=step*f(x+h, t1, i));
  for (i=0;i<N;i++) t3[i]=y[i]+(k3[i]=step*f(x+h, t2, i));
  for (i=0;i<N;i++) k4[i]=step*f(x+step, t3, i);
  for (i=0;i<N;i++) y[i]+=(k1[i]+2*k2[i]+2*k3[i]+k4[i])/6.0;
}

void RK4(char *fout,double t,double tmax,double step,double x_0,double zx_0,double v_0,double zv_0){
  double y[NDERIVS];
  int i;
  double E;
  FILE *file;
  file=fopen(fout, "w");   
  //file=fopen('orbit_RK4.txt', "w");               
  y[0]=x_0;                                       
  y[1]=zx_0;
  y[2]=v_0;
  y[3]=zv_0;
  E=(y[1]*y[1]+y[3]*y[3])/2.0+(-1)/(sqrt(1+2*y[0]*y[0]+2*y[2]*y[2]));
  fprintf(file, "0\t%e\t%e\t%e\t%e\t%e\n", y[0],y[1],y[2],y[3],E);
  printf("0\t%e\t%e\t%e\t%e\t%e\n", y[0],y[1],y[2],y[3],E);
  for (i=1; i*step<=tmax ;i++){
      t=i*step;
      runge4(t, y, step); 
      E=(y[1]*y[1]+y[3]*y[3])/2.0+(-1)/(sqrt(1+2*y[0]*y[0]+2*y[2]*y[2]));
      fprintf(file, "%f\t%e\t%e\t%e\t%e\t%e\n", t,y[0],y[1],y[2],y[3],E);
      printf("%f\t%e\t%e\t%e\t%e\t%e\n", t,y[0],y[1],y[2],y[3],E);
    }
  fclose(file);
}

int main(int argc, char** argv){
  double t=0, tmax=100, step=.1;
  double x_0=1,zx_0=0,v_0=0,zv_0=0.1;
  int i;
  char mode='l';
    /* Parse the argument list. */
    for (i = 0; i < argc; i++)
	if (argv[i][0] == '-') {
	    switch (argv[i][1]) {
		case 's':	step = atof(argv[++i]); break;
		case 't':	tmax = atof(argv[++i]); break;
		case 'v':	v_0 = atof(argv[++i]); break;
	        case 'x':	x_0 = atof(argv[++i]); break;
	    }
	}
    mode=atoi(argv[1]);
    if (mode==2){RK4(argv[2],t,tmax,step,x_0,zx_0,v_0,zv_0);}
    if (mode==3){lpf_int(argv[2],t,tmax,step,x_0,zx_0,v_0,zv_0);}
}

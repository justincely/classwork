#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <iostream>
#include <fstream>
using namespace std;

#define	NDERIVS 2		//Number of derivatives
#define G 1
#define e 0

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

double  f(double x, double y[], int i,double r[],double m){
  if (i==0) return(y[1]);         
  double a=0;
  for (int p=0;p<1000;p++){
    //if (y[0]!=y[p]) a+=(y[0]-r[p])/pow(abs(y[0]-r[p])**2+e*e,3/2);
  }
  if (i==1) return(a); 
}

void runge4(double x, double y[],double r[],double m, double step){
  int N=NDERIVS;
  double h=step/2.0,t1[N], t2[N], t3[N],k1[N], k2[N], k3[N],k4[N];
  int i;
  for (i=0;i<N;i++) t1[i]=y[i]+0.5*(k1[i]=step*f(x, y, i,r,m));
  for (i=0;i<N;i++) t2[i]=y[i]+0.5*(k2[i]=step*f(x+h, t1, i,r,m));
  for (i=0;i<N;i++) t3[i]=y[i]+(k3[i]=step*f(x+h, t2, i,r,m));
  for (i=0;i<N;i++) k4[i]=step*f(x+step, t3, i,r,m);
  for (i=0;i<N;i++) y[i]+=(k1[i]+2*k2[i]+2*k3[i]+k4[i])/6.0;
}

void RK4(char *fout,double t,double tmax,double step){
  double y[NDERIVS];
  double E;
  FILE *file;
  int N=1000;
  double m0[N],r0[3][N],v0[3][N];
  double m[N],r[3][N],v[3][N];
  double a[3][N];

  file=fopen(fout, "w");   
  //file=fopen('orbit_RK4.txt', "w");               

      ifstream inFile;
      inFile.open("p1_initials.txt");
      if (inFile.fail()) {
	printf("unable to open file for reading");
	exit(1);
      }

      for (i=0;i<N;i++){
	for (int j=0;j<7;j++){
	  if (j==0) inFile >> m0[i];
	  if (j==1) inFile >> r0[0][i];
	  if (j==2) inFile >> r0[1][i];
	  if (j==3) inFile >> r0[2][i];
	  if (j==4) inFile >> v0[0][i];
	  if (j==5) inFile >> v0[1][i];
	  if (j==6) inFile >> v0[2][i];
	};
      };

      //fprintf(file, "%e\t%e\t%e\t%e\t%e\t%e\t%e\n", m0[i],r0[0][i],r0[1][i],r0[2][i],v0[0][i],v0[1][i],v0[2][i]);
      //printf( "%e\t%e\t%e\t%e\t%e\t%e\t%e\n",m0[i],r0[0][i],r0[1][i],r0[2][i],v0[0][i],v0[1][i],v0[2][i]);
  for (int i=1; i*step<=tmax ;i++){
      t=i*step;
      for (int k=0;k<1000;k++){
	for (int j=0;j<3;j++){
	  y0[0]=r0[j][k];
	  y0[1]=v0[j][k];
	  runge4(t, y, r[j],m[k], step); 
	}
      }
      fprintf(file,  "%e\t%e\t%e\t%e\t%e\t%e\t%e\n", m[i],r[0][i],r[1][i],r[2][i],v[0][i],v[1][i],v[2][i]);
      printf( "%e\t%e\t%e\t%e\t%e\t%e\t%e\n", m[i],r[0][i],r[1][i],r[2][i],v[0][i],v[1][i],v[2][i]);
    }
  fclose(file);
}

int main(int argc, char** argv){
  double t=0, tmax=100, step=.1;
  char mode='l';
    /* Parse the argument list. */
    for (int i = 0; i < argc; i++)
	if (argv[i][0] == '-') {
	    switch (argv[i][1]) {
		case 's':	step = atof(argv[++i]); break;
		case 't':	tmax = atof(argv[++i]); break;
		case 'v':	v_0 = atof(argv[++i]); break;
	        case 'x':	x_0 = atof(argv[++i]); break;
	    }
	}
    mode=atoi(argv[1]);
    if (mode==2){RK4(argv[2],t,tmax,step);}
    if (mode==3){lpf_int(argv[2],t,tmax,step);}

}

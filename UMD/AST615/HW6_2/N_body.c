#include <stdio.h>
#include <stdlib.h>

void euler(double y[], double dydt[], void *p, int n, double t, double h){
  deriv(y,dydt,p,n,t);
  for (int i=0; i<n; i++)
    y[i] += h*dydt[i];
}

void leapfrog(double y[], double dydt[], void *p, int n, double t, double h){
  for (int i=0; i<n; i+=2)
    y[i+1]+=.5*h*dydt[i+1];

  for (int i=0; i<n;i+=2)
    y[i]+=h*y[i+1];

  deriv(y,dydt,p,n,t);

  for (int i=0; i<n; i+=2)
    y[i+1]+=.5*h*dydt[i+1];
}

void rk4(double y[], double dydt[], void *p, int n, double t, double h){
  double xh,hh,h6,*dym,*dyt,*yt;
  drm=(double *)malloc(n*sizeof(double));
  dyt=(double *)malloc(n*sizeof(double));
  yt=(double *)malloc(n*sizeof(double));
  hh=h*.5;
  h6=h/6.0;
  xh=x+hh;
  for (int i=0; i<n; i++) yt[i]=y[i]+hh*dydx[i];
  derive(yt,dyt,p,n,xh);
  for (int i=0; i<n; i++) yt[i]=y[i]+hh*dyt[i];
  derive(yt,dym,p,n,xh);
  for (int i=0;i<n;i++){
    yt[i]=y[i]+h*dym[i];
    dym[i]+=dyt[i];
  }
  derive(yt,dyt,p,n,x+h);
  for (int i=0; i<n; i++)
    y[i]+=h6*(dydx[i]+dyt[i]+2.0*dym[i]);
  free((void *)yt);
  free((void *)dyt);
  free((void *)dym);
}



import string
import numpy
import math

def leapfrog():
    for i in range(0,N_derives,2):
        y[i+1]+=.5*h*dydt[i+1]
    for i in range(0,N_derives,2):
        y[i]+=h*y[i+1]
    derivative(y,dydt,p,n,t)
    for i in range(0,N_derives,2):
        y[i+1]+=.5*h*dydt[i+1]
'''
def rk4():
    

    for i in range(N_derives):
        yt[i]=y[i]+hh*dydx[i]
    derivative(yt,dyt,p,n,xh)
    for i in range(N_derives):
        yt[i]=y[i]+hh*dyt[i]
    derivative(yt,dym,p,n,xh)
    for i in range(N_derives):
        yt[i]=y[i]+h*dym[i]
        dym[i]+=dyt[i]
    derivative(yt,dyt,p,n,x_h)
    for i in range(N_derives)
    y[i]+=h6*(dydx[i]+dyt[i]+2.0*dym[i])

def rk4(t0, h, s0, f):
    """RK4 implementation.
    t = current value of the independent variable
    h = amount to increase the independent variable (step size)
    s0 = initial state as a list. ex.: [initial_position, initial_velocity]
    f = function(state, t) to integrate"""
    r = range(len(s0))
    s1 = s0 + [f(t0, s0)]
    s2 = [s0[i] + 0.5*s1[i+1]*h for i in r]
    s2 += [f(t0+0.5*h, s2)]
    s3 = [s0[i] + 0.5*s2[i+1]*h for i in r]
    s3 += [f(t0+0.5*h, s3)]
    s4 = [s0[i] + s3[i+1]*h for i in r]
    s4 += [f(t0+h, s4)]
    return t+h, [s0[i] + (s1[i+1] + 2*(s2[i+1]+s3[i+1]) + s4[i+1])*h/6.0 for i in r]
'''
def rk4(x, v, a, dt,accell):
    """Returns final (position, velocity) tuple after
    time dt has passed.

    x: initial position (number-like object)
    v: initial velocity (number-like object)
    a: acceleration function a(x,v,dt) (must be callable)
    dt: timestep (number)"""
    x1 = x
    v1 = v
    a1 = a(x1, v1, 0,accell)

    x2 = x + 0.5*v1*dt
    v2 = v + 0.5*a1*dt
    a2 = a(x2, v2, dt/2.0,accell)

    x3 = x + 0.5*v2*dt
    v3 = v + 0.5*a2*dt
    a3 = a(x3, v3, dt/2.0,accell)

    x4 = x + v3*dt
    v4 = v + a3*dt
    a4 = a(x4, v4, dt,accell)

    xf = x + (dt/6.0)*(v1 + 2*v2 + 2*v3 + v4)
    vf = v + (dt/6.0)*(a1 + 2*a2 + 2*a3 + a4)

    return xf, vf

def accel_func(x,v,dt,accell):
    return -1*accell
    
if __name__=='__main__':
    numpy.seterr(invalid='ignore')
    N_particles=1000
    tmin=0;tmax=100;tstep=.1
    m=numpy.zeros(N_particles)
    r=numpy.zeros((3,N_particles))
    v=numpy.zeros((3,N_particles))
    a=numpy.zeros((3,N_particles))
    infile=open('p2_initial.txt')
    #ms,rx,ry,rz,vx,vy,vz=[line[0],line[1],line[2],line[3],line[4],line[5],line[6] for line in infile.readlines]
    for i,line in enumerate(infile.readlines()):
        line=string.split(line)
        m[i]=(float(line[0]))
        r[0][i]=(float(line[1]))
        r[1][i]=(float(line[2]))
        r[2][i]=(float(line[3]))
        v[0][i]=(float(line[4]))
        v[1][i]=(float(line[5]))
        v[2][i]=(float(line[6]))
    for t in numpy.linspace(tmin,tmax,tmax/tstep):
        e=0
        print t
        for i in range(N_particles):
            for k in range(3):
                nom=((m*(r[k][i]-r[k][:])))
                denom=((numpy.fabs(r[k][i]-r[k][:])**2.0)+e*e)**(3.0/2)
                final=(nom/denom)
                ok_index=numpy.isfinite(final)
                #print numpy.sum(final[ok_index])
                #raw_input()
                #print ((numpy.fabs(r[k][i]-r[k][:])**2)+e*e)**(3/2)
                #print sum((m[i]*(r[k][i]-r[k][:]))/((numpy.fabs(r[k][i]-r[k][:])**2)+e*e)**(3/2))
                #print ((numpy.fabs(r[k][i]-r[k][:])**2)+e*e)**(3/2)
                #a[k][i]=sum((m[i]*(r[k][i]-r[k][:]))/math.pow((numpy.fabs(r[k][i]-r[k][:])**2+e*e),3/2))
                #print a
                a[k][i]=numpy.sum(final[ok_index])
                #print a[2][i]
                #raw_input()
        out_r=numpy.zeros((3,N_particles))
        out_v=numpy.zeros((3,N_particles))
        if t<10:
	    t_str='0'+str(t)
	else:
	    t_str=str(t)
	ofile=open('t_'+t_str+'.txt','w')
        for i in range(N_particles):
            for axis in (0,1,2):
                out_r[axis][i],out_v[axis][i]=rk4(r[axis][i],v[axis][i],accel_func,tstep,a[axis][i])
            ofile.write('%4.4f %4.4f %4.4f %4.4f %4.4f %4.4f %4.4f\n' %(m[i],out_r[0][i],out_r[1][i],out_r[2][i],out_v[0][i],out_v[1][i],out_v[2][i]))
        r=out_r
        v=out_v

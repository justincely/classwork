import string
import numpy
import math
from optparse import OptionParser

def leapfrog(x, v, a, dt,rs,ms,e):
    x1=x
    v1=v
    a1=a(x1, v1, 0,rs,ms,e)
    
    v2=v1+.5*dt*a1
    x2=x1+dt*v2
    a2=a(x2,v2,0,rs,ms,e)

    vf=v2+.5*dt*a2
    xf=x2    

    return xf,vf

def rk4(x, v, a, dt,rs,ms,e):
    """Returns final (position, velocity) tuple after
    time dt has passed.

    x: initial position (number-like object)
    v: initial velocity (number-like object)
    a: acceleration function a(x,v,dt) (must be callable)
    dt: timestep (number)"""
    x1 = x
    v1 = v
    a1 = a(x1, v1, 0,rs,ms,e)

    x2 = x + 0.5*v1*dt
    v2 = v + 0.5*a1*dt
    a2 = a(x2, v2, dt/2.0,rs,ms,e)
 
    x3 = x + 0.5*v2*dt
    v3 = v + 0.5*a2*dt
    a3 = a(x3, v3, dt/2.0,rs,ms,e)

    x4 = x + v3*dt
    v4 = v + a3*dt
    a4 = a(x4, v4, dt,rs,ms,e)
 
    xf = x + (dt/6.0)*(v1 + 2*v2 + 2*v3 + v4)
    vf = v + (dt/6.0)*(a1 + 2*a2 + 2*a3 + a4)

    return xf, vf

def accel_func(x, v,t,rs,ms,e):
    g=1
    accel_array=-1.0*g*(ms*(x-rs))/((((x-rs)**2.0)+e*e)**(3.0/2))
    ok_index=numpy.isfinite(accel_array)    #remove NaN from overlapping particles
    total=numpy.sum(accel_array[ok_index])  #(particularly the particle and itself
    return total

if __name__=='__main__':
    #----------------------------#
    #Define and parse option and argument list
    #----------------------------#
    parser = OptionParser()
    parser.add_option("-f", "--file", dest="initial",
                      help="file containing initla conditions")
    parser.add_option("-i", "--integrator",
                      dest="integrator", default='rk4',
                      help="integrator to use: lr,rk4")
    parser.add_option("-t", "--tmax",
                      dest="tmax", default=1000,
                      help="maximum time")
    parser.add_option("-s", "--step",
                      dest="step", default=1,
                      help="time step")
    parser.add_option("-o", "--output frequency",
                      dest="frequency", default=1,
                      help="output frequency")
    parser.add_option("-e", "--softening",
                      dest="softening", default=1,
                      help="softening parameter")
    parser.add_option("-n", "--N",
                      dest="N", default=2,
                      help="Number of particles to use from initial file")
    (options, args) = parser.parse_args()

    numpy.seterr(invalid='ignore')       #ignore NaN, they are taken care of later
    N_particles=int(options.N)
    tmin=0;tmax=float(options.tmax);tstep=float(options.step)
    mode=options.integrator
    print N_particles


    #----------------------------#
    #make arrays to contain r and v vectors for N particles and 
    #read in initial conditions from input file
    #----------------------------#
    m=numpy.zeros(N_particles)
    r=numpy.zeros((3,N_particles))
    v=numpy.zeros((3,N_particles))
    infile=open(options.initial)
    for i,line in enumerate(infile.readlines()):
        line=string.split(line)
        m[i]=(float(line[0]))
        r[0][i]=(float(line[1]))
        r[1][i]=(float(line[2]))
        r[2][i]=(float(line[3]))
        v[0][i]=(float(line[4]))
        v[1][i]=(float(line[5]))
        v[2][i]=(float(line[6]))

    #----------------------------#
    #loop from t to tmax in (tmax/tstep) steps.
    #Integration takes place in this loop
    #----------------------------#
    count=0
    for t in numpy.linspace(tmin,tmax,(tmax-1)/tstep):
        count+=1
        print '%4.4f' %(t)
        #----------------------------#
        #output arrays to hold new r and v for each particle
        #----------------------------#
        out_r=numpy.zeros((3,N_particles))
        out_v=numpy.zeros((3,N_particles))
        time_str=str(float(t))
	if t<1000:
	    time_str='0'+time_str
	if t<100:
	    time_str='0'+time_str
	if t<10:
	    time_str='0'+time_str	
        if not count%int(options.frequency): ofile=open('t_'+time_str+'.txt','w')
        #----------------------------#
        #loop over N particles, integrating each axis (x,y,z) independently
        #----------------------------#
        for i in range(N_particles):
            for axis in (0,1,2):
                if mode=='rk4': out_r[axis,i],out_v[axis,i]=rk4(r[axis,i],v[axis,i],accel_func,tstep,r[axis,:],m,float(options.softening))
		if mode=='lf': out_r[axis,i],out_v[axis,i]=leapfrog(r[axis,i],v[axis,i],accel_func,tstep,r[axis,:],m,float(options.softening))
            if not count%int(options.frequency):
                ofile.write('%4.7e %4.7e %4.7e %4.7e %4.7e %4.7e %4.7e\n' %(m[i],out_r[0,i],out_r[1,i],out_r[2,i],out_v[0,i],out_v[1,i],out_v[2,i]))
                count=0
        r=out_r
        v=out_v

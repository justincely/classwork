import random
import numpy

outfile=open('p2_initial.txt','w')

rrange=10
vrange=10
for i in range(1000):
    m=numpy.sqrt(random.uniform(0,100))
    #m=(random.uniform(10e40,100*10e40))
    rx=random.uniform(-rrange,rrange)
    ry=random.uniform(-rrange,rrange)
    rz=random.uniform(-rrange,rrange)
    vx=random.uniform(-vrange,vrange)
    vy=random.uniform(-vrange,vrange)
    vz=random.uniform(-vrange,vrange)
    outfile.write('%7.9f %7.9e %7.9e %7.9e %7.9e %7.9e %7.9e \n' %(m,rx,ry,rz,vx,vy,vz))
    print '%7.4f\t %7.9f %7.9e %7.9e %7.9e %7.9e %7.9e' %(m,rx,ry,rz,vx,vy,vz)

import random

outfile=open('p2_initial.txt','w')

for i in range(1000):
    m=random.uniform(0,10)
    rx=random.uniform(-100,100)
    ry=random.uniform(-100,100)
    rz=random.uniform(-100,100)
    vx=random.uniform(-5,5)
    vy=random.uniform(-5,5)
    vz=random.uniform(-5,5)
    outfile.write('%7.4f %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f \n' %(m,rx,ry,rz,vx,vy,vz))
    print '%7.4f\t %7.4f %7.4f %7.4f %7.4f %7.4f %7.4f' %(m,rx,ry,rz,vx,vy,vz)

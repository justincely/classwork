import pylab
import string
import glob
import numpy
from mpl_toolkits.mplot3d import Axes3D
N_particles=1000
pylab.ion()
fig = pylab.figure()
ax=Axes3D(fig)
txt_list=glob.glob('t_*.txt')
txt_list.sort()
for txt in txt_list:
    infile=open(txt,'r')

    m=numpy.zeros(N_particles)
    r=numpy.zeros((3,N_particles))
    v=numpy.zeros((3,N_particles))
    a=numpy.zeros((3,N_particles))
    for i,line in enumerate(infile.readlines()):
        line=string.split(line)
        m[i]=(float(line[0]))
        r[0][i]=(float(line[1]))
        r[1][i]=(float(line[2]))
        r[2][i]=(float(line[3]))
        v[0][i]=(float(line[4]))
        v[1][i]=(float(line[5]))
        r[2][i]=(float(line[6]))
    r=r.clip(min=-500,max=500)
    ax.scatter(r[0], r[1], r[2],s=1)
    #ax.set_xlim3d(-5000,5000)
    #ax.set_ylim3d(-5000,5000)
    #ax.set_zlim3d(-5000,5000)
    #pylab.savefig(txt[:-4]+'.pdf')
    raw_input()
    print (txt[:-4]+'.pdf')
    pylab.cla()

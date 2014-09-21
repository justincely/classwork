import pylab
import string
import glob
import numpy
from mpl_toolkits.mplot3d import Axes3D
from optparse import OptionParser

def vect_mag(vect):
    return numpy.sqrt(vect[0]**2+vect[1]**2+vect[2]**2)

def vect_dot(a,b):
    return a[0]*b[0]+a[1]*b[1]+a[2]*b[2]

parser = OptionParser()
parser.add_option("-r", "--range",
                  dest="range", default=0,
                  help="maximum time")
parser.add_option("-n", "--N",
                  dest="N", default=2,
                  help="Number of particles to use from initial file")
(options, args) = parser.parse_args()

N_particles=int(options.N)
pylab.ioff()
fig = pylab.figure()
ax=Axes3D(fig)
txt_list=glob.glob('t_*.txt')
txt_list.sort()
#txt_list.insert(0,'p2_initial.txt')
E=[]
radius=[]
velocity=[]
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
        v[2][i]=(float(line[6]))
    ax.scatter(r[0], r[1], r[2],s=(1,10))
    if float(options.range):
        ends=float(options.range)
        ax.set_xlim3d(-ends,ends)
        ax.set_ylim3d(-ends,ends)
        ax.set_zlim3d(-ends,ends)
    #pylab.draw()
    pylab.savefig(txt[:-3]+'.png')
    #raw_input()
    print txt
    pylab.cla()
    E.append(.5*vect_mag(v[:,0])**2+.5*vect_mag(v[:,1])**2-1.0/(vect_mag(r[:,1])+vect_mag(r[:,0])))
    rad=((vect_mag(r[:,0])+vect_mag(r[:,1])))
    radius.append(rad)
    velocity.append(vect_dot(r,v)[0]/rad)
    #print (vect_dpt(r,v)/rad)
pylab.figure()
pylab.plot(E)
pylab.ylabel('Energy')
pylab.xlabel('Step #')
pylab.savefig('E_T.pdf')
raw_input()
pylab.cla()
pylab.plot(radius,velocity,'o-')
pylab.xlabel('Radius')
pylab.ylabel('Velocity')
pylab.savefig('R_V.pdf')
raw_input()

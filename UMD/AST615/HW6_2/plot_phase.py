import pylab
import string

labels=('Step=1','Step=.5','Step=.25','Step=.1')
for i,intxt in enumerate(('pp1.txt','pp_5.txt','pp_25.txt','pp_1.txt')):
    infile=open(intxt,'r')
    t=[]
    xs=[]
    ys=[]
    for line in infile.readlines():
        line=string.split(line)
        t.append(float(line[0]))
        xs.append(float(line[1]))
        ys.append(float(line[2]))

    pylab.plot(xs,ys,'-',label=labels[i])
pylab.legend(shadow=True)
pylab.title('Lotka-Volterra Predator-Prey Phase Plane')
pylab.xlabel('Rabbits')
pylab.ylabel('Foxes')
pylab.draw()
pylab.savefig('Phase_plane.pdf')
pylab.close()

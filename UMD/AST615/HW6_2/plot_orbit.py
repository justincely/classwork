import pylab
import string
import matplotlib

matplotlib.rcParams['figure.subplot.hspace']=.45
matplotlib.rcParams['figure.subplot.wspace']=.3


labels=('Step=1','Step=.5','Step=.25','Step=.01')
steps=(1,.5,.25,.01)


pylab.figure(figsize=(8.5,11))
for i,intxt in enumerate(('O_RK1.txt','O_RK_5.txt','O_RK_25.txt','O_RK_1.txt')):
    infile=open(intxt,'r')
    t=[]
    xs=[]
    ys=[]
    Es=[]
    for line in infile.readlines():
        line=string.split(line)
        t.append(float(line[0]))
        xs.append(float(line[1]))
        ys.append(float(line[2]))
        Es.append(float(line[5]))
    pylab.subplot(4,2,2*i+1)
    pylab.plot(xs,ys,'-',lw=2)
    pylab.ylim(-1,1)
    pylab.xlim(-1,1)
    pylab.xlabel('X')
    pylab.ylabel('Y')
    pylab.title('Step=%f'%(steps[i]))

    pylab.subplot(4,2,2*i+2)
    pylab.plot(t,Es,'-',lw=1)
    pylab.xlim(0,100)
    pylab.xlabel('Time')
    pylab.ylabel('Energy')
pylab.suptitle('RK4 Orbit Integration')
pylab.savefig('RK4_orbit_int.pdf')
pylab.close()

pylab.figure(figsize=(8.5,11))
for i,intxt in enumerate(('O_LF1.txt','O_LF_5.txt','O_LF_25.txt','O_LF_1.txt')):
    infile=open(intxt,'r')
    t=[]
    xs=[]
    ys=[]
    Es=[]
    for line in infile.readlines():
        line=string.split(line)
        t.append(float(line[0]))
        xs.append(float(line[1]))
        ys.append(float(line[2]))
        Es.append(float(line[5]))
    pylab.subplot(4,2,2*i+1)
    pylab.plot(xs,ys,'-',lw=2)
    pylab.ylim(-1,1)
    pylab.xlim(-1,1)
    pylab.xlabel('X')
    pylab.ylabel('Y')
    pylab.title('Step=%f'%(steps[i]))

    pylab.subplot(4,2,2*i+2)
    pylab.plot(t,Es,'-',lw=1)
    pylab.xlim(0,100)
    pylab.xlabel('Time')
    pylab.ylabel('Energy')
pylab.suptitle('Leapfrog Orbit integration')
pylab.savefig('Leapfrog_orbit_int.pdf')
pylab.close()


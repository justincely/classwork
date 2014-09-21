import pylab
import string
import matplotlib
from numpy import fabs

matplotlib.rcParams['figure.subplot.hspace']=.35
matplotlib.rcParams['figure.subplot.wspace']=.3

labels=('Step=1','Step=.3','Step=.1','Step=.03','Step=.01')
steps=(1,.3,.1,.03,.01)

diff=[]
pylab.figure(figsize=(8.5,11))
for i,intxt in enumerate(('euler1.txt','euler_3.txt','euler_1.txt','euler_03.txt','euler_01.txt')):
    infile=open(intxt,'r')
    t=[]
    exact=[]
    numerical=[]
    for line in infile.readlines():
        line=string.split(line)
        t.append(float(line[0]))
        exact.append(float(line[1]))
        numerical.append(float(line[2]))
    diff.append(fabs(numerical[-1]-exact[-1]))
    pylab.subplot(3,2,i+1)
    pylab.plot(t,exact,'-',lw=3,label='Exact')
    pylab.plot(t,numerical,'--',lw=2,label='Numerical')
    pylab.legend(shadow=True)
    pylab.ylim(-2,2)
    pylab.xlim(0,15)
    pylab.xlabel('Time')
    pylab.ylabel('x')
    pylab.title('Step=%f'%(steps[i]))
pylab.subplot(3,2,6)
pylab.loglog(steps,diff)
pylab.xlabel('Step Size')
pylab.ylabel('Numerical-Exact')
pylab.suptitle('Euler Integration')
pylab.savefig('Euler_int.pdf')
pylab.close()

diff=[]
pylab.figure(figsize=(8.5,11))
for i,intxt in enumerate(('RK1.txt','RK_3.txt','RK_1.txt','RK_03.txt','RK_01.txt')):
    infile=open(intxt,'r')
    t=[]
    exact=[]
    numerical=[]
    for line in infile.readlines():
        line=string.split(line)
        t.append(float(line[0]))
        exact.append(float(line[1]))
        numerical.append(float(line[2]))
    diff.append(fabs(numerical[-1]-exact[-1]))
    pylab.subplot(3,2,i+1)
    pylab.plot(t,exact,'-',lw=3,label='Exact')
    pylab.plot(t,numerical,'--',lw=2,label='Numerical')
    pylab.legend(shadow=True)
    pylab.ylim(-2,2)
    pylab.xlim(0,15)
    pylab.xlabel('Time')
    pylab.ylabel('x')
    pylab.title('Step=%f'%(steps[i]))
pylab.subplot(3,2,6)
pylab.loglog(steps,diff)
pylab.xlabel('Step Size')
pylab.ylabel('Numerical-Exact')
pylab.suptitle('RK4 integration')
pylab.savefig('RK4_int.pdf')
pylab.close()

diff=[]
pylab.figure(figsize=(8.5,11))
for i,intxt in enumerate(('leapfrog1.txt','leapfrog_3.txt','leapfrog_1.txt','leapfrog_03.txt','leapfrog_01.txt')):
    infile=open(intxt,'r')
    t=[]
    exact=[]
    numerical=[]
    for line in infile.readlines():
        line=string.split(line)
        t.append(float(line[0]))
        exact.append(float(line[1]))
        numerical.append(float(line[2]))
    diff.append(fabs(numerical[-1]-exact[-1]))
    pylab.subplot(3,2,i+1)
    pylab.plot(t,exact,'-',lw=3,label='Exact')
    pylab.plot(t,numerical,'--',lw=2,label='Numerical')
    pylab.legend(shadow=True)
    pylab.ylim(-2,2)
    pylab.xlim(0,15)
    pylab.xlabel('Time')
    pylab.ylabel('x')
    pylab.title('Step=%f'%(steps[i]))
pylab.subplot(3,2,6)
pylab.loglog(steps,diff)
pylab.xlabel('Step Size')
pylab.ylabel('Numerical-Exact')
pylab.suptitle('Leapfrog integration')
pylab.savefig('Leapfrog_int.pdf')
pylab.close()

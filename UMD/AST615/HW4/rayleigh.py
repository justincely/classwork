import random
import numpy
import pylab

def rayleigh(y):
    '''
    defines a rayleigh distribution
    '''
    y=numpy.array(y)
    r=y*numpy.exp((-1*y*y)/2.0)
    return r

def rand_to_rayleigh(y):
    '''
    transforms a random deviate into a rayleigh deviate
    '''
    y=numpy.array(y)
    r=numpy.sqrt(-2*numpy.log(y))
    return r

if __name__=='__main__':
    '''
    main function
    '''
    y=[]
    N=1000000
    x=numpy.linspace(0,5,N) #array of values from 0 to 5 in N even spaces
    for i in range(N):
        y.append(random.random())              #random number between 0 and 1
    bins2=numpy.linspace(0,5,30)               #defint bins for random histogram
    bins1=numpy.linspace(0,1,6)                #define bins for rayleigh histogram
    pylab.hist(y,label='Random Hist',normed=True,bins=bins1)                          #plot histogram of random values
    pylab.hist(rand_to_rayleigh(y),label='Ray 2',alpha=.4,normed=True,bins=bins2)     #calls rand_to_rayleigh and plots
    pylab.plot(x,rayleigh(x),label='Rayleigh',lw=4)                                   #plots rayleigh distribution
    pylab.xlabel('X')
    pylab.ylabel('Normalized Counts')
    pylab.legend(shadow=True)
    pylab.savefig('P1.pdf')

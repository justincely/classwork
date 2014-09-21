import numpy
import pylab
import math

c=2.998*10**8
#c=190
#c=1

### problem 3a

def r(z):
    k=1
    H=70
    X=1
    a=math.sinh(math.sqrt(k)*H*X)
    b=(H*math.sqrt(k))
    c=(1+z)
    print a,b,c
    return a/b/c
    
pylab.ion()
points=numpy.linspace(0,100,4000)
pylab.plot(points,r(points),'-',lw=3)
pylab.xlabel('Z')
pylab.ylabel('Angular diameter distance')
pylab.savefig('plot_1.pdf')
#raw_input()
pylab.close()

### problem 3b
M=20
H=70
points=numpy.linspace(0,1000,4000)
def d(z,omega):
    dl=((2*c)/(H*omega**2))*(omega*z-(omega-z)*(-1+(omega*z+1)**(1/2.0)))
    delta=5*numpy.log10(dl)+25
    return delta
    
for omega in (1.2,1.5,2.0):
    plot=d(points,omega)
    pylab.plot(points,plot,label='omega: '+str(omega))

def d_2(z):
    dl=((c*z)/H)*(1+z*(1+.5)/2)
    delta=5*numpy.log10(dl)+25
    return delta

pylab.plot(points,d_2(points),label='flat')
pylab.xlabel('Z')
pylab.ylabel('Delta Magnitude (m-M)')
pylab.legend(loc='lower right',shadow=True,numpoints=1)
pylab.savefig('plot_2.pdf')
raw_input()
pylab.close()

'''
###problem 2c
c=190
mpc=3.08e19
gyr=3.14e16
t_correct=(c*gyr)/mpc

def a_closed(x):
    omega=1.05
    return (1/2.0)*(omega*(1-numpy.cos(x)))/(omega-1)

def t_closed(x):
    omega=1.05
    H=70.0
    return t_correct*(.5*H)*(omega/(omega-1)**(3/2.0))*(x-numpy.sin(x))
    
def a_open(x):
    omega=.5
    return (1/2.0)*(omega*(numpy.cosh(x)-1))/(1-omega)

def t_open(x):
    omega=.5
    H=70.0
    return t_correct*(.5*H)*(omega/(1-omega)**(3/2.0))*(numpy.sinh(x)-x)
    
    
points=numpy.linspace(0,2*math.pi,100)
pylab.plot(t_open(points),a_open(points),'-',label='Open: Omega=.5')
pylab.plot(t_closed(points),a_closed(points),'--',label='Closed: Omega=1.05')
pylab.legend(shadow=True,numpoints=1,loc='upper left')
pylab.grid(True)
pylab.xlabel('t (Gyr)')
pylab.ylabel('a(t)')
pylab.savefig('two_c.pdf')
pylab.close()


#2d
    
def a_closed(x):
    return (1/2.0)*(omega*(1-numpy.cos(x)))/(omega-1)

def t_closed(x):
    H=70.0
    return t_correct*(.5*H)*(omega/(omega-1)**(3/2.0))*(x-numpy.sin(x))
    
pylab.figure()
omegas=[]
ages=[]
for omega in numpy.linspace(1.05,2,1000):
    points=numpy.linspace(0,2*math.pi,100000)
    a=a_closed(points)
    a-=1
    index=numpy.where(a>0)[0]
    closest=points[index[0]]
    ages.append(t_closed(closest))
    omegas.append(omega)
pylab.plot(omegas,ages,label='Closed')

def a_open(x):
    return (1/2.0)*(omega*(numpy.cosh(x)-1))/(1-omega)

def t_open(x):
    H=70.0
    return t_correct*(.5*H)*(omega/(1-omega)**(3/2.0))*(numpy.sinh(x)-x)
    
omegas=[]
ages=[]
for omega in numpy.linspace(.2,.99,1000):
    points=numpy.linspace(0,2*math.pi,100000)
    a=a_open(points)
    a-=1
    #pylab.plot(a)
    #raw_input()
    index=numpy.where(a>0)[0]
    closest=points[index[0]]
    ages.append(t_open(closest))
    omegas.append(omega)
pylab.plot(omegas,ages,'--',label='Open')
pylab.legend(shadow=True,numpoints=1)
pylab.ylabel('Age (Gyr)')
pylab.xlabel('Omega')
pylab.grid(True)
#raw_input()
pylab.savefig('two_d.pdf')
pylab.close()

'''

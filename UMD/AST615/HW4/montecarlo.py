import random
import pylab
import numpy
from mpl_toolkits.mplot3d import Axes3D

if __name__=="__main__":
    '''
    Main function
    '''
    pylab.ioff()
    Ns=[]         #list to contaion an N's
    densities=[]  #list to contain all density measurements
    errs=[]       #list to contain all error measurements
    for N in (10,100,1000,1E4,1E5,1E6,1E7):   #iterate over different N values
        N=int(N)
        Ns.append(N)
        xs=[]
        ys=[]
        zs=[]
        density=[]
        count=0
        for i in range(N):
            x=random.uniform(-9,9)    #random number between -9 and 9
            y=random.uniform(-9,9)    #my area is a square of side lengh 18
            z=random.uniform(-9,9)
            xs.append(x)
            ys.append(y)
            zs.append(z)
            #-------------------------------------#
            #  If the point (x,y,z) is within the #
            #  area of interest, calculated the   #
            #  density and 
            if (x>=0) and (y>=-1) and (x*x+y*y+z*z<=9): 
                count+=1
                density.append(1+x*x+3*(y+z)*(y+z))
        A=18*18*18
        density=numpy.array(density)   #convert list to array
        #object_area=(float(count)/N)*(A)
        #D=object_area*numpy.mean(density)
        D=A/float(N)*sum(density)
        densities.append(D)
        err=A*numpy.sqrt((numpy.sum(density**2)/float(N)+(numpy.sum(density)/N)**2)/N)
        errs.append(err)
        print N,D,err
        '''
        #3d figure of points
        fig = pylab.figure()
        ax = Axes3D(fig)
        ax.scatter(xs,ys,zs)
        pylab.show()
        pylab.draw()
        '''
    pylab.subplot(111,xscale='log')
    pylab.errorbar(Ns,densities,yerr=errs,lw=3)
    pylab.xlabel('N')
    pylab.ylabel('Calculated Density')
    pylab.draw()
    pylab.savefig('P2.pdf')
    pylab.close()

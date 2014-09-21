import pylab
import numpy
import string

def chi_sq(fit,y,err):
    '''
    Returns chi_sq estimate for the fit.
    '''
    X_sq=numpy.sum(((y-fit)/err)**2)
    nu=len(y)-2.0
    sigma=numpy.sum(y-fit)/nu
    return X_sq

def fit(x,y,err,which):
    '''
    Performs fit to input data.  Computes function (gauss or lorentz) with 
    a spread of function parameters, then returns the parameters for the
    function with the smalles chi squared value.
    Returns the fit to optimal parameters.
    '''
    max_index=numpy.argmax(y)
    x_0=x[numpy.argmax(y)]
    chi_all=[]
    alpha_all=[]
    x_0_all=[]
    if which=='gaussian':
        print 'Gaussian'
        for x_0 in numpy.linspace(x[max_index-4],x[max_index+4],40): #iterate from four points below and four points above the peak in 40 steps.
            for alpha in numpy.linspace(1,30,40):
                fit=(1.0/alpha)*numpy.sqrt(numpy.log(2)/numpy.pi)*numpy.exp((-1*numpy.log(2)*(x-x_0)**2)/alpha**2)
                chi_all.append(chi_sq(fit,y,err))
                alpha_all.append(alpha)
                x_0_all.append(x_0)
        alpha=alpha_all[numpy.argmin(chi_all)]   #find index of minimum in array
        x_0=x_0_all[numpy.argmin(chi_all)]       #find index of minimum in array
        print 'alpha,x_0=',alpha,x_0
        fit=(1.0/alpha)*numpy.sqrt(numpy.log(2)/numpy.pi)*numpy.exp((-1*numpy.log(2)*(x-x_0)**2)/alpha**2)
    if which=='lorentzian':
        print 'Lorentzian'
        for x_0 in numpy.linspace(x[max_index-4],x[max_index+4],40): #iterate from four points below and four points above the peak in 40 steps.
            for alpha in numpy.linspace(1,30,40):
                fit=(1.0/numpy.pi)*(alpha)/((x-x_0)**2+alpha**2)
                chi_all.append(chi_sq(fit,y,err))
                alpha_all.append(alpha)
                x_0_all.append(x_0)
        alpha=alpha_all[numpy.argmin(chi_all)]   #find index of minimum in array
        x_0=x_0_all[numpy.argmin(chi_all)]       #find index of minimum in array
        print 'alpha,x_0=',alpha,x_0
        fit=(1.0/numpy.pi)*(alpha)/((x-x_0)**2+alpha**2)  #refit with parameters of minimum chi_sq
    fit=numpy.array(fit)
    nu=len(y)-2.0
    sigma=float(numpy.sum(y-fit))/nu  #estimate error
    print 'Error=',sigma
    print
    return fit
  

###############
##Begin main  
###############
freq=[]
strn=[]
err=[]

fin=open('ps3.dat')  #open input data
for line in fin.readlines(): #iterate of lines in the file
    line=line.split() #split line at whitespaces
    freq.append(float(line[0])) #split freq,strength,and error to different lists
    strn.append(float(line[1]))
    err.append(float(line[2]))

###convert lists to arrays
freq=numpy.array(freq)
strn=numpy.array(strn)
err=numpy.array(err)

#calls fitting function in the plotting call
pylab.ioff()
pylab.errorbar(freq,strn,yerr=err,fmt='bo-',label='Data')
pylab.plot(freq,fit(freq,strn,err,'lorentzian'),'ro-',label='Lorentzian')
pylab.plot(freq,fit(freq,strn,err,'gaussian'),'go-',label='Gaussian')
pylab.legend(numpoints=1,shadow=True)
pylab.xlabel('Frequency')
pylab.ylabel('Line Strength')
#pylab.show()
pylab.savefig('fit.pdf')
pylab.close()


"""Solve the saha equation for the temperature at a given ionization and density

## Some thoughts
Why doesn't this work for large densities?  rho=100 and ionization=.75
fails becuase the temperature is negative in the exponent

"""

__author__ = 'Justin Ely'

import numpy as np
import matplotlib.pyplot as plt

#-------------------------------------------------------------------------------

def saha(ionization=.5, rho=1.0, T=1.0, precision=.0001):
    """Solve the saha equation for temperature

    The equation will be solved by recursively calling the saha function, 
    using the newly calculated new_t value as the input to the T**(3/2.) term.
    When the next iteration differs from less than the desired precision from
    the previous value, the recursion will stop and the value will be returned.

    Parameters
    ----------
    ionization : int, float
        ionization fraction of the gas
    rho : float, int
        density of the gas
    T : int, float
        initial guess for temperature
    precision : float, int
        desired level of precision of calculated value

    Returns
    -------
    new_t : float
        the calculated temperature from iteration
    
    """

    alpha = (ionization**2) / (1-ionization)
    new_t = (-1.57887e5) / np.log(((alpha * rho) / (4.0355e-9)) / (T ** (3/2.)))

    if new_t < 0:
        raise ValueError('Temperature cannot be negative')
    elif abs(T - new_t) < precision:
        return new_t
    else:
        return saha(ionization, rho, T=new_t)

#-------------------------------------------------------------------------------

def problem_4():
    """Prepare the output for problem 4 of HW 2
    
    
    """

    #-- 1000 evenly spaced densities from 1e-31 to 1e-16
    densities = np.linspace(1e-31, 1e-16, 100000)
    temperatures = [saha(.5, density) for density in densities]

    #-- Plotting begins here
    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)

    ax.plot(temperatures, densities)
    
    ax.set_xlabel('Temperature $K$')
    ax.set_ylabel('$log_{10} \rho$')
    ax.set_title('Saha equation solutions for T')
    ax.set_yscale('log')
    ax.grid()    

    fig.savefig('ely_hw2.pdf')

    #-- Write out the values to a file
    #with open('ely_hw2_table.txt', 'w') as out_txt:
    #    out_txt.write('Log10 Density    Temperature\n')
    #    out_txt.write('----------------------------\n')
    #    for d, t in zip(densities, temperatures):
    #        out_txt.write('{:.4f}         {:.4f}\n'.format(np.log10(d), t))

    #-- python2.6 compatibilty
    out_txt = open('ely_hw2_table.txt', 'w') 
    for d, t in zip(densities, temperatures):
        out_txt.write('%3.4f         %3.4f\n' % (np.log10(d), t))

#-------------------------------------------------------------------------------

if __name__ == "__main__":
    #-- Test case from the lectures.  Should be around 3300 K
    #print saha(ionization=.5, rho=1.7e-24)

    problem_4()

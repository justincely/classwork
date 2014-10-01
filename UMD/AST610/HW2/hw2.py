"""Solve the saha equation for the temperature at a given ionization and density

"""

__author__ = 'Justin Ely'

import numpy as np
import matplotlib.pyplot as plt

#-------------------------------------------------------------------------------

def saha(ionization=.5, rho=1, T=1, precision=.0001):
    """Why doesn't this work for large densities?  rho=100 and ionization=.75
    fails becuase the temperature is negative
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
 
    #-- 1000 evenly spaced densities from 1e-31 to 1e-16
    densities = np.linspace(1e-31, 1e-16, 1000)
    temperatures = [saha(.5, density) for density in densities]


    #-- Plotting begins here
    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)

    ax.plot(temperatures, densities)

    ax.set_yscale('log')
    ax.grid()
    
    ax.set_xlabel('Temperature $K$')
    ax.set_ylabel('$log_{10} \rho$')
    
    ax.set_title('Saha equation solutions')
    
    fig.savefig('hw4.pdf')

    #-- Write out the values to a file
    with open('hw4_values.txt', 'w') as out_txt:
        out_txt.write('Log10 Density    Temperature\n')
        out_txt.write('----------------------------\n')
        for d, t in zip(densities, temperatures):
            out_txt.write('{:.4f}         {:.4f}\n'.format(np.log10(d), t))

#-------------------------------------------------------------------------------

if __name__ == "__main__":
    #-- Test case from the lectures.  Should be aronud 3300 K
    print saha(ionization=.5, rho=1.7e-24)

    problem_4()

#! /user/bin/env/python
"""
Simple script to calculate the fractional mass in White Dwarfs at various ages
of a single stellar population with a Saltpeter IMF.

"""

import numpy as np
import matplotlib.pyplot as plt


def tms( mass ):
    """ Return time on main sequence for given mass

    """

    nom = 2.5E3 + (6.7E2 * mass ** 2.5) + mass**4.5
    denom = (3.3E-2 * mass ** 1.5) + (3.5E-1 * mass**4.5)

    return 1E-3 * (nom / denom)

#-------------------------------------------------------------------------------

def make_plot( times, mass ):
    """ Generate a pretty plot for question 6 of hw

    """
    
    plt.plot( times, mass )
    plt.xlabel('Age (Gyr)')
    plt.ylabel('WD mass fraction $(M_{solar})$')
    plt.savefig('astr620_hw2_pr6.pdf')

#-------------------------------------------------------------------------------

def run_population():
    """ Create and age a stellar population

    Masses range linearly between .1 and 100 solar masses.
    Number of stars in each bin determined by Saltpeter IMF.
    

    """

    masses = np.linspace(.1,101,100000)
    lifespans = np.array( map( tms, masses ) )
    nums = np.array( [ val**-2.35 for val in masses ] )

    frac_mass = []
    all_times = np.linspace( .01, 13, 100 )

    for time in all_times:
        index_low = np.where( (lifespans <= time) & (masses <= 1.4) )[0]
        index_high = np.where( (lifespans <= time) & 
                               ((masses < 8 ) & (masses > 1.4)))[0]

        wd_mass = 0
        if len( index_low ):
            wd_mass += np.sum(nums[ index_low ] * masses[ index_low])
        if len( index_high ):
            wd_mass += np.sum( nums[ index_high ] ) * 1.4

        frac_mass.append( wd_mass / (np.sum(masses*nums)) )

    make_plot( all_times, frac_mass )

#-------------------------------------------------------------------------------

if __name__ == "__main__":
    run_population()
    
    

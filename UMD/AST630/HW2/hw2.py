"""Functions and script for problems in HW2

For problem 3:
--------------
class oblate: 
  provides the mechanics for calulating the desired orbital values from an 
  initilized object of a given mass, radius, and moments.

function problem_3(): 
  function to output results using the oblate class on given scenarios



"""

import matplotlib.pyplot as plt
import numpy as np

M = 1.99e33
G = 6.67e-8
c = 2.998e10

#-------------------------------------------------------------------------------

class oblate():
    def __init__(self, mass, radius, moments=[]):
        """Initialize object

        Parameters:
        -----------
        mass : float, int
            mass of the planet in any units
        radius : float, int
            radius as a factor of the planetary radius
        moments : list, optional
            list of the moment coefficients

        """
        
        self.mass = mass
        self.radius = radius
        self.moments = moments

    @property
    def n(self):
        """Calculate the body's orbital frequency
        """

        const = np.sqrt(G * self.mass / (self.radius**3))

        coeffs = [((3.0 / 2.0) * (1.0 / self.radius)**2),
                  (-1 * (15.0 / 8.0) * (1.0 / self.radius)**4),
                  ((35.0 / 16.0) * (1.0 / self.radius)**6)]

        terms = 1
        for j, c in zip(self.moments, coeffs):
            terms += (j * c)

        return np.sqrt(const * terms)

    @property
    def k(self):
        """Calculate the body's epicyclic frequency
        """

        const = np.sqrt(G * self.mass / (self.radius**3))

        coeffs = [(-1 * (3.0 / 2.0) * (1.0 / self.radius)**2),
                  ((45.0 / 8.0) * (1.0 / self.radius)**4),
                  (-1 * (175.0 / 16.0) * (1.0 / self.radius)**6)]

        terms = 1
        for j, c in zip(self.moments, coeffs):
            terms += (j * c)

        return np.sqrt(const * terms)

    @property
    def mu(self):
        """Calculate the body's vertical frequency
        """

        return np.sqrt(2 * (self.n**2) - (self.k**2))

    @property
    def apse_precess(self):
        """Calculate the precession of the periapse longitude
        """

        return self.n - self.k

    @property
    def node_regress(self):
        """Calculate the regression of the nodes of the equitorial orbits
        """

        return self.n - self.mu

    @property
    def period(self):
        """Calculate the orbital period
        """

        return 1.0 / self.n

#-------------------------------------------------------------------------------

def problem_3():
    """Print output for problem 3 of HW4

    """

    for moments in [[], [1.63e-2], [1.63e-2, -9e-4, 1e-4]]:
        for radius in [1.3, 3]:
            saturn = oblate(568.46e27, radius, moments)
            
            if len(moments): 
                print "Using moments of: {}".format(moments)
            else:
                print "Using no moments"
            print "and radius of: {}".format(radius)
            print "the orbital period is: {}".format(saturn.period)
            print "    apse precession rate: {}".format(saturn.apse_precess)
            print "    node regression rate: {}".format(saturn.node_regress)
            print 

#-------------------------------------------------------------------------------

def precession(a, e):
    nominator = 3. * ((G * M) ** (3/2.))
    denom = (a ** (5/2.)) * (1-e**2) * (c**2)
    
    w = nominator / float(denom)
    print w

    # per second to per year
    w *= (60. * 60. * 24. * 365.)
    
    # from rad to arcsec
    w *= 206265

    return w

#-------------------------------------------------------------------------------

def ldot(radius, msat, rplan):

    ldot = (3 / 4.) * (.14 / 86)
    ldot *= (G * (msat**2) * rplan**(5))
    ldot /= (radius ** 6)

    return ldot

#-------------------------------------------------------------------------------

def problem_4():
    fig = plt.figure()
    ax = fig.add_subplot(1, 1, 1)
    ax.grid()

    r_mars = 3.3899e8 
    m_mars = .64185e27

    radii = np.linspace(0, 10 * r_mars)

    ax.plot(radii / r_mars,
            ldot(radii, 1, r_mars),
            lw=3)

    ax.axvline(x=9.38e+08 / r_mars, 
               ls = '--', 
               color='red', 
               lw=2, 
               label='Radius Phobos')

    ax.axvline(x=23.4e+08 / r_mars, 
               ls = '-', 
               lw=2, 
               color='red', 
               label='Radius Deimos')

    ax.set_yscale('log')

    ax.set_xlabel('Mars Radii')
    ax.set_ylabel('$\dot L$')
    ax.set_title('$\dot L$ vs Radii for orbit around Mars')
    ax.legend(shadow=True, numpoints=1, loc='upper right')

    plt.savefig('mars_momentum.pdf')

#-------------------------------------------------------------------------------

if __name__ == "__main__":
    problem_3()

             

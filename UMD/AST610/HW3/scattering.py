import numpy as np
import matplotlib.pyplot as plt
from scipy import integrate

#-------------------------------------------------------------------------------

def scatter(radius, theta, phi):
    """compute the scattering angle

    Parameters
    ----------
    radius : float, int
        radius to the scattering point
    theta : flot, int
        angle in radians of the observer to the plane
    phi : float, int
        azimuth angle of r in radians

    Returns
    -------
    angle : float
        the photon's scattering angle
    
    """

    radius = float(radius) * np.cos(phi)

    hyp = np.sqrt(radius**2 + 1)

    angle = (np.pi / 2.0) - (np.arccos(1 / hyp) + theta)

    return angle

#-------------------------------------------------------------------------------

def polarization(alpha):
    """compute the fractional polarization
    
    Parameters
    ----------
    alpha : float, int
        scattering angle in radians

    Returns
    -------
    frac_pol : float
        the fractional polarization
    """

    return (1 - np.cos(alpha)**2) / (1 + np.cos(alpha)**2)
       
#-------------------------------------------------------------------------------
 
def cross_section(alpha):
    """compute the new cross_section
    
    Parameters
    ----------
    alpha : float, int
        scattering angle in radians

    Returns
    -------
    cross : float
        new cross section based on scattering
    """

    sigma_t = .66524574e-25

    r0 = 2.8e-13
    sigma = abs((1/8.0) * r0**2 * (6 * alpha + np.sin(2 * alpha)))


    return sigma / sigma_t
       
#-------------------------------------------------------------------------------

def intensity(R0, radius, prob=None):
    """calulate the intensity reaching a section of the slab
    """

    distance = np.sqrt(radius**2 + 1)

    if prob is None:
        prob = np.exp(-1 * distance)

    i = ((R0 * prob) / (4 * np.pi * distance**2)) * (radius)

    return i

#-------------------------------------------------------------------------------

def calculate_polarization(r, phi, theta, return_dir=False):
    #-- Scattering angle
    alpha = scatter(r, theta, phi)

    #-- intensity with modified cross section
    I = intensity(1, r) * cross_section(alpha)
    
    #-- Fractional polarization is weighted by intensity
    frac_pol = polarization(alpha) * I
    pol_dir = 1# pol_direction(r)

    return frac_pol

#-------------------------------------------------------------------------------

def pol_direction(r):
    """Calculate the polarization direction

    computes the polarization direction as perpendicular
    to the the initial ray and scattered ray

    a value of 0 indicates left-right,
    a value of 1 indicates up-down.

    ### This function is wrong and needs modification
    ### It needs to oscillate from -1 to 1 to allow directionalites
    ### to cancel out.

    """

    return np.sin(np.arccos(1 / np.sqrt(r**2 + 1)) - np.pi/4.)

#-------------------------------------------------------------------------------

def problem_2():
    """Produce output for problem 2 of HW3
    """

    all_theta = np.linspace(0, np.pi/2, 50)

    all_pol = []

    out_table = open('polarization_table.txt', 'w')
    out_table.write("Theta   Net-Polarization\n")
    out_table.write("------------------------\n")

    print "Theta   Net-Polarization"
    print "------------------------"
    for theta in all_theta:

        #-- Integrate the calculate_polarization function
        #-- from r=0 to r=10, and from phi=0 to phi = 2 PI
        integral, err = integrate.nquad(calculate_polarization,
                                        [[0, 20],
                                         [0, 2 * np.pi]],
                                        args=(theta,))


        out_table.write("%3.3f   %3.3e" % (theta, integral))
        print "%3.3f   %3.3e" % (theta, integral)

        all_pol.append(integral)
    

    #-- Plot figure
    fig = plt.figure(figsize=(10, 10))
    ax = fig.add_subplot(1, 1, 1)

    all_theta = map(np.rad2deg, all_theta)
    ax.plot(all_theta, all_pol)
 
    ax.set_xlabel('Degrees')
    ax.set_ylabel('Polarization Fraction')
    ax.grid(True)

    fig.savefig('polarization.pdf')

#-------------------------------------------------------------------------------

def run_tests():

    #-- zero scattering scenarios
    assert scatter(0, np.pi/2., 0) == 0
    
    #--straight line in
    theta = np.pi / 4.
    r = np.sqrt(1.0 / (np.cos(np.pi / 2. - theta)**2) -1 )
    assert scatter(r, theta, 0) == 0

    #-- all phi angles should yield 0 scatter at theta = 90 or r=anything
    for phi in np.linspace(0, 2 * np.pi):
        assert scatter(0, np.pi/2., phi) == 0
    
    #-- Phi has no consequence at constant r, and theta = 0
    for r in np.arange(0, 10):
        for r in np.arange(0, 10):
            assert scatter(r, np.pi/2., phi) == scatter(r, np.pi/2., 0)
  
    assert scatter(1, 0, np.pi/2.) == np.pi/2.


#-------------------------------------------------------------------------------

if __name__ == "__main__":

    run_tests()

    problem_2()

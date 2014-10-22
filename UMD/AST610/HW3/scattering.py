import numpy as np
import matplotlib.pyplot as plt

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

    return 1
       
#-------------------------------------------------------------------------------

def intensity(R0, radius):
    """calulate the intensity"""

    p = np.exp(-1 * np.sqrt(radius**2 + 1))

    n = (R0 * p) / (4 * np.pi * r**2)

#-------------------------------------------------------------------------------

def problem_2():
    """Produce output for problem 2 of HW3
    """

    all_theta = np.linspace(0, np.pi, 100)

    fig = plt.figure(figsize=(10, 10))
    ax = fig.add_subplot(1, 1, 1)
    

#-------------------------------------------------------------------------------

if __name__ == "__main__":

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



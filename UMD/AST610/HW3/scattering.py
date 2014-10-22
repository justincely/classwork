import numpy as np

#np.rad2deg((np.pi/2. + theta) - np.cos(np.cos(theta) * phi) * np.arccos(r/(np.sqrt(r**2 + 1**2))))
#angle = np.pi - (np.arccos(float(radius)/(np.sqrt(radius**2 + 1**2))) + (np.pi/2. - theta))

def scatter(radius, theta, phi):
    radius = float(radius)
    radius *= np.cos(phi)

    hyp = np.sqrt(radius**2 + 1)

    angle = np.pi/2. - (np.arccos(1/hyp) + theta)

    return angle


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



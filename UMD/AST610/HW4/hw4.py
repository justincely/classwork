"""
Initial photon energy is always hw = .0001(m_e)(c**2)
electron speed, v=beta/c, beta = .1, distribution is 
   isotropic.

"""

import numpy as np
import random

m_e = 9.1093897e-28
c = 2.998e10

e_photon_initial = .001 * m_e * c**2
v_electron = .1 * c
n_photons = 100,000


def doppler(e_initial):
    """Doppler shift into the electron's rest frame
    """


    return e_final

import numpy as np
import matplotlib.pyplot as plt
plt.ion()


all_z = np.linspace( .2*.02, 2*.02, 50 )

y = .01
zsun = .02

def m_subz( zs, znaught=0  ):
    return (1-np.exp( -1 * (zs-znaught) / y) )

print m_subz( .25*zsun ) / m_subz( .7 * zsun )
print m_subz( .25*zsun, znaught=.004 )  / m_subz( .7 * zsun, znaught=.004 )

plt.figure()
plt.step( all_z, m_subz( all_z ) / m_subz( .7 * zsun ))
plt.step( all_z, m_subz( all_z, znaught=.004 )  / m_subz( .7 * zsun ), color='r' )
plt.axvline( x = .25 * zsun, lw=2, color='k' )
raw_input()

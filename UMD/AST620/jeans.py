import matplotlib.pyplot as plt
import numpy as np
plt.ion()

radii = np.linspace(1, 1000, 5000 )

Rnaught = 100.0
Inaught = 1.0
SIGMA = 1.0
G = 1.0


def mass( r ):
    const = (2.0 * SIGMA**2 * Rnaught **2) / (G)
    first = (r ** 3) / (Rnaught**2 + r**2)**2
    second = 1.0 + (r / Rnaught) ** 2

    return const * first * second

def light( r ):
    return Inaught * Rnaught * np.arctan( r / Rnaught )

plt.figure()
plt.loglog( radii/Rnaught, mass( radii ), 'o' )
plt.ylabel('Mass Enclosed (arbitrary units)')
plt.xlabel('Radius/$R_0$ (arbitrary units)')
raw_input()
plt.savefig('mass_enclosed.pdf')

plt.figure()
plt.semilogx( radii/Rnaught, mass( radii ) / light( radii ), 'o' )
plt.ylabel('Mass to Light (arbitrary units)')
plt.xlabel('Radius/$R_0$ (arbitrary units)')
raw_input()
plt.savefig('mass_to_light.pdf')



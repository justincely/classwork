import pylab
import os
import sys
import numpy
from scipy import optimize

def init_plots():
    try: matplotlib
    except NameError: import matplotlib
    '''
    Sets plotting defaults to make things pretty
    '''
    pylab.ioff()
    matplotlib.rcParams['lines.markeredgewidth'] = .001
    matplotlib.rcParams['lines.linewidth']=2
    matplotlib.rcParams['patch.edgecolor']='grey'
    matplotlib.rcParams['font.size']=15.0
    matplotlib.rcParams['figure.figsize']=14,12
    matplotlib.rcParams['figure.subplot.left']=.1
    matplotlib.rcParams['figure.subplot.right']=.9
    matplotlib.rcParams['figure.subplot.top']=.92
    matplotlib.rcParams['figure.subplot.bottom']=.1
    matplotlib.rcParams['figure.subplot.wspace']=.2
    matplotlib.rcParams['figure.subplot.hspace']=.2
    matplotlib.rcParams['figure.facecolor']='white'
    matplotlib.rcParams['axes.facecolor']='white'
    matplotlib.rcParams['axes.edgecolor']='black'
    matplotlib.rcParams['axes.linewidth']=1
    matplotlib.rcParams['axes.grid']=False
    matplotlib.rcParams['xtick.major.size']=7
    matplotlib.rcParams['ytick.major.size']=7
    matplotlib.rcParams['xtick.minor.size']=4
    matplotlib.rcParams['ytick.minor.size']=4

def grav(x,y,m1,m2):
    '''
    Compute effective potential at given point.  Returns scalar potential
    '''
    alpha=float(m2)/(m1+m2)
    potential=(-(1-alpha)/numpy.sqrt((x-alpha)**2+y**2)-alpha/numpy.sqrt((x+1-alpha)**2+y**2)-(x**2+y**2)/2.0)
    return potential

def accel(x,y,m1,m2):
    '''
    Compute acceleration vector of the given point.  Returns vector tuple (x,y).
    '''
    alpha=float(m2)/(m1+m2)
    x_acc=(-1*(1-alpha)*(x-alpha)/((x-alpha)**2+y**2)**(3/2.0)-alpha*(x+1-alpha)/((x+1-alpha)**2+y**2)**(3/2.0)+x)
    y_acc=(-1*(1-alpha)*(y)/((x-alpha)**2+y**2)**(3/2.0)-alpha*(y)/((x+1-alpha)**2+y**2)**(3/2.0)+y)
    return (x_acc,y_acc)

def compute_system(m1,m2,separation):
    '''
    Computes the entire system of the two bodies.  Accepts the masses of the two bodies
    and their separation as inputs.  Returns nothing but plots potential contours and acceleration vectors
    on plot.
    '''
    print 'Computing the system for M1=%d M2=%d Separation=%d'%(m1,m2,separation)
    size=100  # number of points to compute along each axis
    xs=numpy.linspace(-2,2,size)  #define 1d array from -2,2 with 100 steps 
    ys=numpy.linspace(-2,2,size)
    pot_grid=numpy.zeros((size,size))  #arrays full of zeros to hold potentials and acceleration vectors
    accell_x_grid=numpy.zeros((size,size))
    accell_y_grid=numpy.zeros((size,size))
    x_accell=[]  #empty lists to hold x and y acceleration
    y_accell=[]
    pos_m1=(((1.0/(m1+m2)))*25+50,50)  #position of masses in grid coordinates (plotted coordinates)
    pos_m2=((100-((1-(1.0/(m1+m2)))*25+50)),50)
    if os.path.exists('%d_%d_%d_system.txt'%(m1,m2,separation)):os.remove('%d_%d_%d_system.pdf'%(m1,m2,separation)) #remove output txt file if it exists
    fout=open('%d_%d_%d_system.txt'%(m1,m2,separation),'a') #open file for appending
    print 'Printing X,Y,potential,x acceleration,y acceleration to file.'
    for i,y in enumerate(ys):  #return index (i) and value (y) if the list ys and iterate.
        for j,x in enumerate(xs):
            potential=grav(x,y,m1,m2)
            pot_grid[i][j]=potential
            acc_vect=accel(x,y,m1,m2)
            accell_x_grid[i][j]=acc_vect[0]
            accell_y_grid[i][j]=acc_vect[1]
            x_accell.append(acc_vect[0])  #append acceleration components to [x,y]_accell list
            y_accell.append(acc_vect[1])
            #print x,y,potential,acc_vect[0],acc_vect[1]
            fout.write('%1.3f %1.3f %1.3f %1.3f %1.3f\n'%(y,x,potential,acc_vect[0],acc_vect[1]))
    find_l(accell_x_grid,accell_y_grid,pos_m1,pos_m2,size,separation)
    x_accell=numpy.array(x_accell) #convert list to array for indexing
    y_accell=numpy.array(y_accell)
    index=numpy.where((numpy.fabs(x_accell)>3) | (numpy.fabs(y_accell)>3))  #suppress large accelerations for clearer plot
    x_accell[index]=0
    y_accell[index]=0
    pylab.contour(pot_grid,size*4)   #plot contours of pot_grid with size*4 number of contours
    X,Y=numpy.meshgrid(range(len(pot_grid)),range(len(pot_grid))) 
    pylab.quiver(X,Y,x_accell,y_accell,units='xy',scale=1) #plot vectors at points X,Y with components x_accell,y_accell
    #pylab.show()
    pylab.savefig('%d_%d_%d_system.pdf'%(m1,m2,separation)) #save figure
    pylab.close() #close plot
    
def bracket(line):
    '''
    Simple bracketing method to find a root.  Computes the midpoint between the two ends of 
    the input 1D array, and evaluates which enpoint has a different sign than the midpoint.  Then reassigns 
    the midpoint to the appropriate enpoint and continues.  Stops when the two endpoints are sequential
    elements in the array, still with a sign change, and then evaluates the midpoint between the two.  
    Returns the index of the midpoint in the input array.
    '''
    compute=True
    a=1
    b=len(line)-1
    count=0
    while compute:
        count+=1
        mid=line[(a+b)/2]
        if (mid*line[a])<0:  #check for sign change
            b=((a+b)/2)
        elif (mid*line[b])<0:  #check for sign change
            a=((a+b)/2)
        if (numpy.fabs((numpy.where(line==line[a])[0][0]-numpy.where(line==line[b])[0][0]))==1) and ((line[a]*line[b])<0):
            # above checks if endpoints are next to eachother in array and still have signchange.  Indicates maximum precision without fitting.
            pos=(a+b)/2.0 
            compute=False
        elif count==100:
            pos=-999   #return nonsensical index if no result after 100 iterations 
            compute=False
    return pos

def find_l(x_grid,y_grid,pos_m1,pos_m2,grid_size,separation):
    '''
    Computes and prints coordinates for the Lagrange points by searching for sign changes
    in the x and y acceleration 2D arrays.  Calls bracket function for root finding.  Returns 
    nothing, but prints results and annotates L-points on the plot.
    '''
    x1=int(pos_m1[0])
    y1=int(pos_m1[1])
    x2=int(pos_m2[0])
    y2=int(pos_m2[1])
    mid=50
    l3=(bracket(x_grid[mid][:x2]),50)
    l1=(x2+bracket(x_grid[mid][x2:x1]),50)
    l2=(x1+bracket(x_grid[mid][x1:]),50)
    l4=((x1+x2)/2,50+bracket(y_grid[50:,(x1+x2)/2]))
    l5=((x1+x2)/2,bracket(y_grid[0:50,(x1+x2)/2]))
    print 'L1,L2,L3,L4,L5  (x,y) in plotted coordinates'
    print l1,l2,l3,l4,l5
    print 'L1,L2,L3,L4,L5  (x,y) in computed coordinates'
    print (separation*((l1[0]-50)/25.0),separation*((l1[1]-50)/25.0)),(separation*((l2[0]-50)/25.0),separation*((l2[1]-50)/25.0)),(separation*((l3[0]-50)/25.0),separation*((l3[1]-50)/25.0)),(separation*((l4[0]-50)/25.0),separation*((l4[1]-50)/25.0)),(separation*((l5[0]-50)/25.0),separation*((l5[1]-50)/25.0))
    print
    pylab.annotate('L1',(l1[0],l1[1]),color='b',size=22,arrowprops=dict(arrowstyle="->",ec='b'))
    pylab.annotate('L2',(l2[0],l2[1]),color='b',size=22,arrowprops=dict(arrowstyle="->",ec='b'))
    pylab.annotate('L3',(l3[0],l3[1]),color='b',size=22,arrowprops=dict(arrowstyle="->",ec='b'))
    pylab.annotate('L4',(l4[0],l4[1]),color='b',size=22,arrowprops=dict(arrowstyle="->",ec='b'))
    pylab.annotate('L5',(l5[0],l5[1]),color='b',size=22,arrowprops=dict(arrowstyle="->",ec='b'))
    pylab.annotate('M1',(x1,y1),color='k',size=22,arrowprops=dict(arrowstyle="->",ec='g'))
    pylab.annotate('M2',(x2,y2),color='k',size=22,arrowprops=dict(arrowstyle="->",ec='g'))

if __name__ =='__main__':
    '''
    Main function.  Either computes system from input parameters or computes the system for 
    the two pre-programmed system.
    '''
    init_plots()  #initializes plotting parameters
    try:  #use command line inputs if given, else use standard.
        print 'Input parameters:%d,%d,%d.'%(int(sys.argv[1]),int(sys.argv[2]),int(sys.argv[3]))
        compute_system(int(sys.argv[1]),int(sys.argv[2]),int(sys.argv[3]))
    except:
        print 'Computing with standard parameters'
        compute_system(3,1,1)
        compute_system(100,1,1)

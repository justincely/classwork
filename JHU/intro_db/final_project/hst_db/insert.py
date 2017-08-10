import glob
import os

from astropy.io import fits

#-------------------------------------------------------------------------------

def collect_pi(file_list):
    pi_names = set()
    for fname in file_list:
        with fits.open(fname) as hdu:
            
            if not 'PR_INV_F' in hdu[0].header:
                continue
            fname = hdu[0].header['PR_INV_F']
            mname = hdu[0].header['PR_INV_M']
            lname = hdu[0].header['PR_INV_L']

            pi_names.add((fname, mname, lname))

    for fname, mname, lname in list(pi_names):
        print("INSERT INTO investigator(fname, mname, lname) VALUES ('{}', '{}', '{}');".format(fname, mname, lname))
    
#-------------------------------------------------------------------------------

def collect_proposid(file_list):
    stuff = set()
    for fname in file_list:
        with fits.open(fname) as hdu:
            
            if not 'PROPOSID' in hdu[0].header:
                continue
            if not 'PROPTTL1' in hdu[0].header:
                continue

            stuff.add((hdu[0].header['PROPOSID'], hdu[0].header['PROPTTL1'], hdu[0].header['PR_INV_L']))

    for pid, title, lname in list(stuff):
        print("INSERT INTO proposal(proposid, pi, title) VALUES ('{}', '{}', '{}');".format(pid, lname, title))

#-------------------------------------------------------------------------------

def collect_targets(file_list):
    stuff = set()
    for fname in file_list:
        with fits.open(fname) as hdu:
            
            if not 'TARGNAME' in hdu[0].header:
                continue
            if not "RA_TARG" in hdu[0].header:
                continue

            stuff.add((hdu[0].header['targname'], hdu[0].header['RA_TARG'], hdu[0].header['DEC_TARG'], hdu[0].header['PROPOSID']))

    for name, ra, dec, proposid in list(stuff):
        print(name, ra, proposid);
        print("INSERT INTO target(name, ra, dec) VALUES ('{}', '{}', '{}');".format(name, ra, dec))
    
#-------------------------------------------------------------------------------

def collect_descr(file_list):    
    stuff = set()
    for fname in file_list:
        with fits.open(fname) as hdu:
            
            if not 'TARDESCR' in hdu[0].header:
                continue

            for item in hdu[0].header['tardescr'].split(';'):
                stuff.add(item)

    for thing in list(stuff):
        print("INSERT INTO description(description) VALUES ('{}');".format(thing))
    
#-------------------------------------------------------------------------------

def collect_obsstuff(file_list):    
    stuff = set()
    for fname in file_list:
        with fits.open(fname) as hdu:
            
            if not 'ASN_ID' in hdu[0].header:
                continue

            stuff.add((hdu[0].header['ASN_ID'], hdu[0].header['PROPOSID']))

    for asn, proposid in list(stuff):
        print("INSERT INTO observation(rootname, proposalid) VALUES ('{}', '{}');".format(asn, proposid))
    
#-------------------------------------------------------------------------------

def collect_expstuff(file_list):    
    stuff = set()
    for fname in file_list:
        with fits.open(fname) as hdu:
            
            if not 'ASN_ID' in hdu[0].header:
                continue
            try:
                exptime = hdu[0].header['exptime']
            except:
                exptime = hdu[1].header['exptime']
            stuff.add((hdu[0].header['rootname'],
                       hdu[0].header['ASN_ID'], 
                       hdu[0].header['instrume'],
                       hdu[0].header['detector'][:3],
                       hdu[1].header['expstart'],
                       exptime,
                       hdu[0].header['obsmode'],
                       hdu[0].header['CENWAVE'],
                       hdu[0].header['opt_elem']))

    for item in list(stuff):
        print("INSERT INTO exposure(rootname, obsname, instrument, detector, mjd, exptime, obsmode, cenwave, opt_elem) VALUES ('{}', '{}', '{}', '{}', '{}', '{}', '{}', '{}', '{}');".format(*item))

#-------------------------------------------------------------------------------
    
def collect_refstuff(file_list):    
    stuff = set()
    for fname in file_list:
        with fits.open(fname) as hdu:
            
            if not 'ASN_ID' in hdu[0].header:
                continue
                
            for ftype, fname in list(hdu[0].header['*TAB'].iteritems()) + list(hdu[0].header['*FILE'].iteritems()):
                if fname == 'N/A' or fname == '': continue
                stuff.add((fname.split('$')[-1], ftype))

    for item in list(stuff):
        print("INSERT INTO reference_file(name, type) VALUES ('{}', '{}');".format(*item))
    
#-------------------------------------------------------------------------------
    
def collect_files(file_list):    
    stuff = set()
    for fname in file_list:
        with fits.open(fname) as hdu:
            
            if not 'ASN_ID' in hdu[0].header:
                continue
                
            stuff.add((os.path.split(fname)[-1], hdu[0].header['rootname'], os.path.split(fname)[0]))

    for item in list(stuff):
        print("INSERT INTO file(name, exposure, fpath) VALUES ('{}', '{}', '{}');".format(*item))
    
#-------------------------------------------------------------------------------

if __name__ == "__main__":
    fits_files = sorted(glob.glob('/media/justin/persephone/school/spect_db/*raw*.fits'))
    
    #collect_pi(fits_files)
    #collect_proposid(fits_files)
    #collect_targets(fits_files)
    #collect_descr(fits_files)
    #collect_obsstuff(fits_files)
    collect_expstuff(fits_files)
    #collect_refstuff(fits_files)
    #collect_refmap(fits_files)
    #collect_files(fits_files)
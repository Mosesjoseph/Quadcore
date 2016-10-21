# import the necessary packages
import string
import random
import argparse
import cv2
from os import listdir
from os.path import isfile, join
import numpy
# initialize the list of reference points and boolean indicating
# whether cropping is being performed or not
refPt = []
cropping = False
blackBox=[]

def name_generator(size=10, chars=string.ascii_letters + string.digits):
    return ''.join(random.choice(chars) for _ in range(size))

# construct the argument parser and parse the arguments
ap = argparse.ArgumentParser()
ap.add_argument("-i", "--images", required=True, help="Path to the images folder")
args = vars(ap.parse_args())

# load the image, clone it, and setup the mouse callback function
image = None
clone = None
cv2.namedWindow("image")

mypath=args["images"]
onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
images = numpy.empty(len(onlyfiles), dtype=object)
for n in range(0, len(onlyfiles)):
    try:
        images[n] = cv2.imread( join(mypath,onlyfiles[n]) )
        image=images[n]
        clone=image.copy()

        flipped=cv2.flip(clone,1)    
        cv2.imwrite(join(mypath,onlyfiles[n])+"-f.jpg", flipped)
    except:
        pass

# close all open windows
cv2.destroyAllWindows()

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
def click_and_crop(event, x, y, flags, param):
	# grab references to the global variables
	global refPt, cropping,blackBox

	# if the left mouse button was clicked, record the starting
	# (x, y) coordinates and indicate that cropping is being
	# performed
	if event == cv2.EVENT_LBUTTONDOWN:
		refPt = [(x, y)]
		cropping = True
        
        elif event == cv2.EVENT_MOUSEMOVE and len(refPt) ==1:
            newxy=[(x,y)]
            cv2.rectangle(image, refPt[0], newxy[0], (0, 255, 0), -1)
            blackBox=[(refPt[0]),(newxy[0])]
            # apply the overlay
            overlay=clone.copy()
            cv2.addWeighted(overlay, 0.5, image, 0.5,0, image)
            ##cv2.imshow("image", image)
	# check to see if the left mouse button was released
	
        elif event == cv2.EVENT_LBUTTONUP:
		# record the ending (x, y) coordinates and indicate that
		# the cropping operation is finished
		refPt.append((x, y))
		cropping = False

		# draw a rectangle around the region of interest
		##cv2.rectangle(image, refPt[0], refPt[1], (0, 255, 0), 2)
		##cv2.imshow("image", image)


def crop_and_save():
# if there are two reference points, then crop the region of interest
# from teh image and display it
    global refPt
    if len(refPt) == 2:
            roi = clone[refPt[0][1]:refPt[1][1], refPt[0][0]:refPt[1][0]]
            name = name_generator()
            flipped=cv2.flip(roi,1)
            
            cv2.imwrite(name+".jpg", roi)
            cv2.imwrite(name+"-f.jpg", flipped)
            ##cv2.imshow("ROI", roi)
            ##cv2.waitKey(0)
            cv2.rectangle(image, blackBox[0],blackBox[1], (0, 0, 0), 1)
            refPt=[]

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
cv2.setMouseCallback("image", click_and_crop)


mypath=args["images"]
onlyfiles = [ f for f in listdir(mypath) if isfile(join(mypath,f)) ]
images = numpy.empty(len(onlyfiles), dtype=object)
for n in range(0, len(onlyfiles)):
    try:
        images[n] = cv2.imread( join(mypath,onlyfiles[n]) )
        image=images[n]
        clone=image.copy()
        ##cv2.imshow("ROI", images[n])

        # keep looping until the 'q' key is pressed
        while True:
            # display the image and wait for a keypress
            cv2.imshow("image", image)
            key = cv2.waitKey(1) & 0xFF

            # if the 'r' key is pressed, reset the cropping region
            if key == ord("r"):
                    image = clone.copy()

            # if the 'c' key is pressed, break from the loop
            elif key == ord("c"):
                    crop_and_save()
            
            elif key == ord("q"):
                break
        ##cv2.waitKey(0)
    except:
        pass

# close all open windows
cv2.destroyAllWindows()

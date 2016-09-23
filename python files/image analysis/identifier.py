#!/usr/bin/env python

# Python 2/3 compatibility
from __future__ import print_function

import numpy as np
import cv2

# local modules
from video import create_capture
from common import clock, draw_str


def detect(img, cascade):
    rects = cascade.detectMultiScale(img, scaleFactor=1.3, minNeighbors=4, minSize=(30, 30),
                                     flags=cv2.CASCADE_SCALE_IMAGE)
    if len(rects) == 0:
        return []
    rects[:,2:] += rects[:,:2]
    return rects

def draw_rects(img, rects, color):
    for x1, y1, x2, y2 in rects:
        cv2.rectangle(img, (x1, y1), (x2, y2), color, 2)

if __name__ == '__main__':
    import sys, getopt
    print(__doc__)
args, video_src = getopt.getopt(sys.argv[1:], '', ['cascade=', 'nested-cascade=', 'my-cascade='])
try:
    video_src = video_src[0]
except:
    video_src = 0
args = dict(args)
#cascade_fn = args.get('--cascade', "/Users/daniel/opencv/data/haarcascades/haarcascade_frontalface_default.xml")
#nested_fn  = args.get('--nested-cascade', "/Users/daniel/opencv/data/haarcascades/haarcascade_eye.xml")
#my_cascade  = args.get('--my-cascade', "/Users/daniel/Downloads/cascade_speed20_server.xml")

#cascade = cv2.CascadeClassifier(cascade_fn)
#nested = cv2.CascadeClassifier(nested_fn)
#mycascade = cv2.CascadeClassifier(my_cascade)

cas1 = cv2.CascadeClassifier('cas1.xml')
cas2 = cv2.CascadeClassifier('cas1.xml')
cas3 = cv2.CascadeClassifier('cas1.xml')
cas4 = cv2.CascadeClassifier('cas1.xml')
cas5 = cv2.CascadeClassifier('cas1.xml')

cam = create_capture(video_src, fallback='synth:bg=../data/lena.jpg:noise=0.05')

while True:
    ret, img = cam.read()
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    gray = cv2.equalizeHist(gray)
    t = clock()
    rects1 = detect(gray, cascade)
    vis1 = img.copy()
    draw_rects(vis1, rects1, (255, 255, 0))
    rects2 = detect(gray, nested)
    vis2 = img.copy()
    draw_rects(vis2, rects2, (0, 255, 0))
    rects3 = detect(gray, mycascade)
    vis3 = img.copy()
    draw_rects(vis3, rects3, (0, 255, 255))
    dt = clock() - t


    draw_str(vis1, (20, 20), 'time: %.1f ms' % (dt*1000))
    draw_str(vis2, (20, 20), 'time: %.1f ms' % (dt*1000))
    draw_str(vis3, (20, 20), 'time: %.1f ms' % (dt*1000))
    cv2.imshow('facedetect', vis1)
    cv2.imshow('facedetect', vis2)
    cv2.imshow('facedetect', vis3)

    if 0xFF & cv2.waitKey(5) == 27:
        break
cv2.destroyAllWindows()

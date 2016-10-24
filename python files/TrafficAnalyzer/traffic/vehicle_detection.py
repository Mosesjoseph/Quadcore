# -*- coding: utf-8 -*-

import cv2
print(cv2.__version__)

cascade_src = 'data_files/cars.xml'
video_src = 'images/im11.jpg'
#video_src = 'dataset/video2.avi'

#cap = cv2.VideoCapture(video_src)
cap = cv2.cv.LoadImage(video_src)
car_cascade = cv2.CascadeClassifier(cascade_src)
################################################
img = cv2.imread(video_src)
if (type(img) == type(None)):
    print "no image"
    
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
print "got grey image"

cars = car_cascade.detectMultiScale(gray, 1.04, 1)
print len(cars)
for (x,y,w,h) in cars:
    cv2.rectangle(img,(x,y),(x+w,y+h),(0,0,255),2)      
   
cv2.imshow('video', img)
cv2.cv.WaitKey(0)
cv2.destroyAllWindows()

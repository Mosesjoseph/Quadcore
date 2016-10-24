from urllib import urlopen
import re
from random import randint
import cv2

cascade_src = '/home/moses/Quadcore/python files/TrafficAnalyzer/traffic/data_files/cars.xml'
img_src=""

def getImage(imgId):
    print ("get Image")
    camIDregex=re.compile('::(.*)')
    camID=re.findall(camIDregex,imgId)
    networkIDregex=re.compile('(.*)::')
    networkID=re.findall(networkIDregex,imgId)
    #print camID[0]
    #print networkID[0]
    imgFile=open("/home/moses/Quadcore/python files/TrafficAnalyzer/traffic/images/"+camID[0]+".jpg","wb")
    try:
        imgFile.write(urlopen("https://www.i-traffic.co.za/api/cameraimage?key=c763adacf26b4b7eb5bc81bca8772975&format=xml&cameraID="+camID[0]+"&networkId="+networkID[0]).read())
        imgFile.close()
        return True
    except:
        return False
        #pass

def analyseImage(img): 
    global img_src
    camIDregex=re.compile('::(.*)')
    camID=re.findall(camIDregex,img)  
    img_src = '/home/moses/Quadcore/python files/TrafficAnalyzer/traffic/images/'+camID[0]+'.jpg'
    car_cascade = cv2.CascadeClassifier(cascade_src)
    img = cv2.imread(img_src)  
    if (type(img) == type(None)):
        return 5     
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    cars = car_cascade.detectMultiScale(gray, 1.04, 1)         
    return (int(len(cars)/6))

#getImage("GP::GP CCTV N1 205")
#print analyseImage("GP::GP CCTV N4 107")

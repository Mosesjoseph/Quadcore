from urllib import urlopen
import re
from random import randint
def getImage(imgId):
    print ("get Image")
    camIDregex=re.compile('::(.*)')
    camID=re.findall(camIDregex,imgId)
    networkIDregex=re.compile('(.*)::')
    networkID=re.findall(networkIDregex,imgId)
    #print camID[0]
    #print networkID[0]
    #imgFile=open("/home/moses/Quadcore/python files/TrafficAnalyzer/traffic/images/"+camID[0]+".jpg","wb")
    try:
        #imgFile.write(urlopen("https://www.i-traffic.co.za/api/cameraimage?key=c763adacf26b4b7eb5bc81bca8772975&format=xml&cameraID="+camID[0]+"&networkId="+networkID[0]).read())
        #imgFile.close()
        return True
    except:
        return False
        #pass

def analyseImage(img):
    print "Analysis"
    if img == "GP::GP CCTV N4 101":
        return 1
    if img == "GP::GP CCTV N4 102":
        return 1
    if img == "GP::GP CCTV N1 201":
        return 4
    if img == "GP::GP CCTV N1 203":
        return 2
    return randint(1,5)
#getImage("GP::GP CCTV N1 205")

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
    imgFile=open("/home/moses/Quadcore/python files/TrafficAnalyzer/traffic/images/"+imgId+".jpg","wb")
    try:
        imgFile.write(urlopen("https://www.i-traffic.co.za/api/cameraimage?key=c763adacf26b4b7eb5bc81bca8772975&format=xml&cameraID="+camID[0]+"&networkId="+networkID[0]).read())
        imgFile.close()
        return True
    except:
        return False
        #pass

def analyseImage(img):
    print "Analysis"
    return randint(1,5)
#getImage("GP::GP CCTV N1 205")
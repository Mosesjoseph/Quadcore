from urllib import urlopen
import re
def getImage(imgId):
    print ("get Image")
    camIDregex=re.compile('::(.*)')
    camID=re.findall(camIDregex,imgId)
    print camID[0]
    imgFile=open("images/"+imgId+".jpg","wb")
    try:
        imgFile.write(urlopen("https://www.i-traffic.co.za/api/cameraimage?key=c763adacf26b4b7eb5bc81bca8772975&format=xml&cameraID="+camID[0]+"&networkId=GP").read())
        imgFile.close()
        return True
    except:
        return False
        #pass

getImage("GP::GP CCTV N1 206")

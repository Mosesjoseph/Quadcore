#from selenium import webdriver
from urllib import urlopen
from bs4 import BeautifulSoup
import re
import time
import os
os.environ.setdefault("DJANGO_SETTINGS_MODULE", "TrafficAnalyzer.settings")
#from traffic.models import camera_info
from django.utils import timezone
import random

##driver = webdriver.Firefox()
#driver = webdriver.PhantomJS()
#driver.get("https://www.i-traffic.co.za/traffic/cameras.aspx")


def getImage(imgId):
    #page=urlopen("https://www.i-traffic.co.za/api/cameraimage?key=c763adacf26b4b7eb5bc81bca8772975&format=xml&cameraID=GP%20CCTV%20N1%20045&networkId=GP").read()
    #soup = BeautifulSoup(page, 'html.parser')

    #image=soup.find('img')
    #image_source=image.get('src')
    print ("get Image")
    #camIDregex=re.compile('&deviceID=(.*)')
    imgFile=open("images/"+imgId+".jpg","wb")
    try:
        imgFile.write(urlopen("https://www.i-traffic.co.za/api/cameraimage?key=c763adacf26b4b7eb5bc81bca8772975&format=xml&cameraID=GP%20CCTV%20N1%20045&networkId=GP").read())
        imgFile.close()
        record = camera_info(camera=camStr, traffic=random.randint(1, 100),timestamp=timezone.now())
        record.save()
    except:
        pass

getImage("foo")

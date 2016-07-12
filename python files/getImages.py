from selenium import webdriver
from urllib import urlopen
from bs4 import BeautifulSoup
import re
import time

##driver = webdriver.Firefox()
driver = webdriver.PhantomJS()
driver.get("https://www.i-traffic.co.za/traffic/cameras.aspx")

##page=urlopen("https://www.i-traffic.co.za/traffic/cameras.aspx").read()
i=0
while i<66:
    print "**************************Page:%d*****************************" % i
    i+=1
    page=driver.page_source
    soup = BeautifulSoup(page, 'html.parser')

    for img in soup.findAll('img'):
	    img_source=img.get('src')
	    print (img_source)
	    camIDregex=re.compile('&deviceID=(.*)')
	    camID=re.findall(camIDregex,img_source)
            if len(camID) == 1:
                print camID[0]
	        camStr=str(camID[0])
                camStr=camStr.replace("/","-")
                imgFile=open("traffic/"+camStr+".jpg","wb")
                try:
                        imgFile.write(urlopen(img_source).read())
                        imgFile.close()
                except:
                        pass
    try:
        link = driver.find_element_by_link_text("Next").click()
        time.sleep(1)
    except:
        pass
##for link in soup.findAll('a', href=True, text='Next'):
##    print link['href']


##page = urlopen(link).read()
##soup = BeautifulSoup(page, 'html.parser')

##while link!="":
##    link=soup.find('a', href=True, text='Next')

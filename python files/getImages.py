from selenium import webdriver
from urllib import urlopen
from bs4 import BeautifulSoup
import re
import time

def wait_for(condition_function):
    start_time = time.time()
    while time.time() < start_time + 2:
        if condition_function():
            return True
        else:
            time.sleep(0.1)
    raise Exception(
        'Timeout waiting for {}'.format(condition_function.__name__)
    )

def click_through_to_new_page(link_text):
    link = driver.find_element_by_link_text(link_text)
    link.click()

    def link_has_gone_stale():
        try:
            # poll the link with an arbitrary call
            link.find_elements_by_id('doesnt-matter') 
            return False
        except StaleElementReferenceException:
            return True

    wait_for(link_has_gone_stale)
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
        click_through_to_new_page('Next')
    except:
        pass


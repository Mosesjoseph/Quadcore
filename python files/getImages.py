from urllib import urlopen
from bs4 import BeautifulSoup
import re

page=urlopen("https://www.i-traffic.co.za/traffic/cameras.aspx").read()

soup = BeautifulSoup(page, 'html.parser')

for img in soup.findAll('img'):
	img_source=img.get('src')
	print (img_source)
	camIDregex=re.compile('CCTV\s(.*)')
	camID=re.findall(camIDregex,img_source)
	print camID
	camStr=str(camID)
	imgFile=open("traffic/"+camStr+".jpg","wb")
	try:
		imgFile.write(urlopen(img_source).read())
		imgFile.close()
	except:
		pass

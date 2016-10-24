from django.shortcuts import render_to_response
from django.http import JsonResponse
from django.http import HttpResponse
import json
from traffic.models import camera_info
from traffic.models import traffic_event
from django.core import serializers
from django.utils import timezone
import re
import geolocation as geo
import numpy as np
from django.core.cache import cache
from urllib import urlopen
from datetime import datetime
import imageHandler
from xml.etree.ElementTree import parse

cameras=[]
roadNames=[]

doc = parse("/home/moses/Quadcore/python files/TrafficAnalyzer/traffic/data_files/events.xml")

def getEvents(roadName): 
    global doc
    if cache.has_key("xml_data")==False:
        u = urlopen('https://www.i-traffic.co.za/api/incidents?key=c763adacf26b4b7eb5bc81bca8772975&format=xml')
        doc = parse(u)
        cache.set("xml_data", "xml_data", timeout=60)

    eventsRoot = doc.getroot()
    events=[]
    for ev in eventsRoot:
        if(ev[12].text==roadName):        
            roadway_ = ev[12].text
            location_ = ev[8].text
            direction_ = ev[2].text
            description_ = ev[1].text
            lanes_ = ev[4].text
            updated_ =ev[5].text
            
            tmp=traffic_event(roadway_name=roadway_, location=location_, description=description_, direction_of_travel=direction_, lanes_affected=lanes_, timestamp=datetime.now())
            events.insert(0,tmp)  
            tmp=0  
    return events

def createRoadNames(cam):
    for cm in roadNames:
        if cam.roadway_name == cm:
            return 0
    roadNames.insert(0,cam.roadway_name)

def setTrafficForCamera(camera):
    if cache.has_key(camera.camera)==False:
        if imageHandler.getImage(camera.camera)==True:                  #Fetch image
            trafficLevel=imageHandler.analyseImage(camera.camera)       #analyse the image
            camera.traffic=trafficLevel
            camera.timestamp = datetime.now()                           #update model timestamp
            camera.save()
            cache.set(camera.camera, trafficLevel, timeout=60)
    #else it is already in the database and does not need to be fetched
    

def populateCameraList(cam):
    for cm in cameras:
        if cam == cm:
            #return HttpResponse("Duplicate")
            return 0
    cameras.insert(0,cam)

def home(request):
    entries=camera_info.objects.all()
    json_data = serializers.serialize("json", entries)
    return HttpResponse(json_data, content_type='application/json')

def setCameraInfo(request, camera_id,traffic):
    record = camera_info(camera=camera_id, traffic=traffic,timestamp=timezone.now())
    record.save()
    return HttpResponse("You're setting info for <br>CameraID: %s<br>Traffic level:%s" % (camera_id,traffic))

def getCameraInfo(request, camera_id):
    cam = camera_info.objects.filter(camera=camera_id)
    json_data = serializers.serialize("json", cam)
    return HttpResponse(json_data, content_type='application/json')
    #return HttpResponse("You're requesting info for camera: %s" % camera_id)

def processPolyLine(request, polydata):
    polydata= polydata.replace('\\\\', '\\')
    nodes=geo.decodepolyline(polydata)

    boundBoxes = np.zeros((len(nodes),2,2)) # Make a 10 by 20 by 30 array
    i=0
    for node in nodes:
        loc = geo.GeoLocation.from_degrees(node[0], node[1])
        distance = 0.1  # 100m
        SW_loc,NE_loc = loc.bounding_locations(distance)
        #boundBoxes.insert(i,(SW_loc.deg_lat,SW_loc.deg_lon,",",NE_loc.deg_lat,NE_loc.deg_lon))
        boundBoxes[i][0][0]=SW_loc.deg_lat
        boundBoxes[i][0][1]=SW_loc.deg_lon
        boundBoxes[i][1][0]=NE_loc.deg_lat
        boundBoxes[i][1][1]=NE_loc.deg_lon
        i +=1
    
    i=0
    for box in boundBoxes:
        query="select * from traffic_camera_info where (latitude > %f and latitude< %f) and (longitude >%f and longitude < %f)" % (box[0][0],box[0][1],box[1][0],box[1][1])
        for cam in camera_info.objects.raw(query):
            populateCameraList(cam)
        i +=1

    for cam in cameras:
        setTrafficForCamera(cam)

    for ca in cameras:
        createRoadNames(ca)

    for road in roadNames: 
        events_array=getEvents(road)
        for event in events_array:
            cameras.insert(0,event)
    
    #XML response
    xml_data = serializers.serialize("xml", cameras)
    del cameras[:]
    return HttpResponse(xml_data, content_type='application/xml')



def viewImage(request, image_id):
    imageName="traffic/images/"+image_id+".jpg"
    try:
        image_data = open(imageName, "rb").read()
        return HttpResponse(image_data, content_type="image/jpeg")
    except IOError:
        image_data = open("traffic/images/no_image.png", "rb").read()
        return HttpResponse(image_data, content_type="image/png")




























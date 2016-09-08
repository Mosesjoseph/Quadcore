from django.shortcuts import render_to_response
from django.http import JsonResponse
from django.http import HttpResponse
import json
from traffic.models import camera_info
from django.core import serializers
from django.utils import timezone
import re
import geolocation as geo
import numpy as np
from django.core.cache import cache
from random import randint
from datetime import datetime
# Create your views here.

#def detail(request, question_id):
#    return HttpResponse("You're looking at question %s." % question_id)

#def results(request, question_id):
#    response = "You're looking at the results of question %s."
#    return HttpResponse(response % question_id)

#def vote(request, question_id):
#    return HttpResponse("You're voting on question %s." % question_id)
cameras=[]

def analyseImage(img):
    print "Analysis"
    return randint(1,5)

def getImage(Id):
    print "Get image"

def setTrafficForCamera(camera):
    if cache.has_key(camera.camera)==False:
        getImage(camera.camera)                     #Fetch image
        trafficLevel=analyseImage("image")      #analyse the image
        camera.traffic=trafficLevel
        camera.timestamp = datetime.now()
        camera.save()
        cache.set(camera.camera, trafficLevel, timeout=60)
    #else it is already in the database
    

def populateCameraList(cam):
    for cm in cameras:
        if cam == cm:
            return HttpResponse("Duplicate")
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
    ##cam = camera_info.objects.filter(camera=camera_id)
    ##json_data = serializers.serialize("json", cam)
    ##return HttpResponse(json_data, content_type='application/json')
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
        #cam = camera_info.objects.raw('select * from traffic_camera_info where (latitude > -25.740908 and latitude<-25.739112) and (longitude >28.263433 and longitude < 28.265427)')
        for cam in camera_info.objects.raw(query):
            populateCameraList(cam)
        i +=1
    for cam in cameras:
        setTrafficForCamera(cam);
    #query="select * from traffic_camera_info where (latitude > %f and latitude< %f) and (longitude >%f and longitude < %f)" % (boundBoxes[57][0][0],boundBoxes[57][0][1],boundBoxes[57][1][0],boundBoxes[57][1][1])
    #cam = camera_info.objects.raw(query)
    #json_data = serializers.serialize("json", cam)
    #return HttpResponse(json_data, content_type='application/json')
    
    #StartLatitude =-25.740908
    #StartLongitude =28.263433
    #EndLatitude =-25.739112
    #EndLongitude =28.265427
    #camera: "GP::GP CCTV N4 101"

    #JSON response
    json_data = serializers.serialize("json", cameras)
    return HttpResponse(json_data, content_type='application/json')
    
    #XML response
    #xml_data = serializers.serialize("xml", cameras)
    #return HttpResponse(xml_data, content_type='application/xml')
    
    #return HttpResponse(cameras)
    #return HttpResponse(json_data, content_type='application/json')
    ##return HttpResponse("%f,%f" % (polytuple[0][0],polytuple[0][1]))
































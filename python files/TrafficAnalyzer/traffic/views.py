from django.shortcuts import render_to_response
from django.http import JsonResponse
from django.http import HttpResponse
import json
from traffic.models import camera_info
from django.core import serializers
from django.utils import timezone
import re
import geolocation as geo
import basex
# Create your views here.

#def detail(request, question_id):
#    return HttpResponse("You're looking at question %s." % question_id)

#def results(request, question_id):
#    response = "You're looking at the results of question %s."
#    return HttpResponse(response % question_id)

#def vote(request, question_id):
#    return HttpResponse("You're voting on question %s." % question_id)

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
    boundBoxes=[]
    i=0
    for node in nodes:
        loc = geo.GeoLocation.from_degrees(node[0], node[1])
        distance = 0.1  # 100m
        SW_loc,NE_loc = loc.bounding_locations(distance)
        boundBoxes.insert(i,(SW_loc.deg_lat,SW_loc.deg_lon,NE_loc.deg_lat,NE_loc.deg_lon))
        i +=1

    #StartLatitude =-25.740908
    #StartLongitude =28.263433
    #EndLatitude =-25.739112
    #EndLongitude =28.265427
    
    return HttpResponse(boundBoxes)
    ##return HttpResponse("%f,%f" % (polytuple[0][0],polytuple[0][1]))
































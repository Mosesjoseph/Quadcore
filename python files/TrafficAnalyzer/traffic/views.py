from django.shortcuts import render_to_response
from django.http import JsonResponse
from django.http import HttpResponse
import json
from traffic.models import camera_info
from django.core import serializers
from django.utils import timezone

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

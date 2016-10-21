from django.conf.urls import patterns, include, url
from django.contrib import admin
from django.utils.safestring import mark_safe

urlpatterns = patterns('',
    # Examples:
    url(r'^$', 'traffic.views.home', name='home'),

    # ex: /polyline/5/vote/
    url(r'^polyline/(?P<polydata>.+)$', 'traffic.views.processPolyLine', name='polyProcessor'),
    
    #route to set the camera data
    #ex: /setcamera/cam123KZN/25
    url(r'^setcamera/(?P<camera_id>.+)/(?P<traffic>[0-9]+)$', 'traffic.views.setCameraInfo', name='set'),
    
    #route to get camera data   
    #ex: /camera/5/
    url(r'^getcamera/(?P<camera_id>.+)$', 'traffic.views.getCameraInfo', name='get'),
    

    #route to get camera image  
    #ex: /getimage/cam_id/
    url(r'^viewimage/(?P<image_id>.+)$', 'traffic.views.viewImage', name='get_image'),

url(r'^admin/', include(admin.site.urls)),
)

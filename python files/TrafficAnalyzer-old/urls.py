from django.conf.urls import patterns, include, url
from django.contrib import admin

urlpatterns = patterns('',
    # Examples:
    url(r'^$', 'traffic.views.home', name='home'),

    # url(r'^blog/', include('blog.urls')),
    # ex: /polls/5/
    #url(r'^(?P<question_id>[0-9]+)/$', 'traffic.views.detail', name='detail'),
    # ex: /polls/5/results/
    #url(r'^(?P<question_id>[0-9]+)/results/$', 'traffic.views.results', name='results'),
    
    # ex: /polls/5/vote/
    url(r'^polyline/(?P<polydata>.+)$', 'traffic.views.processPolyLine', name='polyProcessor'),
    
    #route to set the camera data
    #ex: /setcamera/cam123KZN/25
    url(r'^setcamera/(?P<camera_id>.+)/(?P<traffic>[0-9]+)$', 'traffic.views.setCameraInfo', name='set'),
    
    #route to get camera data   
    #ex: /camera/5/
    url(r'^getcamera/(?P<camera_id>.+)$', 'traffic.views.getCameraInfo', name='get'),
    
    
url(r'^admin/', include(admin.site.urls)),
)

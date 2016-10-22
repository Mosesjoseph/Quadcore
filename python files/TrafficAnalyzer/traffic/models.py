from django.db import models

# Create your models here.
class camera_info(models.Model):
    camera=models.CharField(max_length=50)
    roadway_name=models.CharField(max_length=50,default='name1')
    latitude=models.FloatField(default=0)
    longitude=models.FloatField(default=0)
    traffic=models.IntegerField(default=0)
    timestamp=models.DateTimeField()


class traffic_event(models.Model):
    roadway_name=models.CharField(max_length=50)
    location=models.CharField(max_length=50)
    description=models.CharField(max_length=50)
    direction_of_travel=models.CharField(max_length=50)
    lanes_affected=models.CharField(max_length=50)
    timestamp=models.DateTimeField()

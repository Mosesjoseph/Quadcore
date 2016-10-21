from django.db import models

# Create your models here.
class camera_info(models.Model):
    camera=models.CharField(max_length=50)
    latitude=models.FloatField(default=0)
    longitude=models.FloatField(default=0)
    traffic=models.IntegerField(default=0)
    timestamp=models.DateTimeField()

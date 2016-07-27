# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('traffic', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='camera_info',
            name='traffic',
            field=models.IntegerField(default=0),
            preserve_default=True,
        ),
    ]

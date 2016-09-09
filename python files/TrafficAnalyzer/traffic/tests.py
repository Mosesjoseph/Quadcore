from django.test import TestCase
import geolocation as geo
# Create your tests here.
class SimpleTest(TestCase):
    def setUp(self):
        self.polyline="tqc|CgcikD_@gN[uJGsBUiIIyCKsEIuBC[EkAE{@O}DCS?AEUWiAm@qCOu@Om@[uAI[YmAGYWiAqAyFa@cBa@gAg@}@e@i@IIw@s@SQ_@]eAeAg@cA]y@WcAQaBIaCO}CKoCCc@QuDYcHKaB"
        self.loc = geo.GeoLocation.from_degrees(-25.7587942, 28.2190005)
    
    def test_decodepolyline(self):
        res=geo.decodepolyline(self.polyline)
        self.assertEqual(45, len(res))
    
    

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
    
    def test_from_degrees(self):
        self.assertEqual(type(self.loc),type(geo.GeoLocation.from_degrees(-25.7587942, 28.2190005)))
    
    def test_bounding_locations(self):   
        distance = 0.05  # 50m
        SW_loc, NE_loc = self.loc.bounding_locations(distance)     
        self.assertEqual(-25.759243, float("%f" % SW_loc.deg_lat))
        self.assertEqual(28.218502,  float("%f" % SW_loc.deg_lon))
        self.assertEqual(-25.758345, float("%f" % NE_loc.deg_lat))
        self.assertEqual(28.219499,  float("%f" % NE_loc.deg_lon))

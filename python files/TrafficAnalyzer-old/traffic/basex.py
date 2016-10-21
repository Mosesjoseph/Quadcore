#!/usr/bin/python

import BaseXClient

# create session
session = BaseXClient.Session('localhost', 1984, 'admin', 'admin')

def query(q)
    try:
        # create query instance
        query = session.query(q)
        # loop through all results
        #for typecode, item in query.iter():
        #    print("typecode=%d" % typecode)
        #    print("item=%s" % item)
        # close query object    
        query.close()

    finally:
        # close session
        if session:
            session.close()

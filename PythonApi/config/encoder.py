import json
from decimal import Decimal
from bson import ObjectId


class JSONEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, ObjectId):
            return str(o)
        if isinstance(o, Decimal):
            return str(o)
        return json.JSONEncoder.default(self, o)

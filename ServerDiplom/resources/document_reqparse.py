from flask_restful import reqparse
from models.documents import Document

parser = reqparse.RequestParser()
parser.add_argument("documents", location='json', type=list)
# parser.add_argument('title')
# parser.add_argument('contactinfo')
# parser.add_argument('extra_info')
# parser.add_argument('userId')
# parser.add_argument('salary')
# parser.add_argument('type')
# parser.add_argument('date')


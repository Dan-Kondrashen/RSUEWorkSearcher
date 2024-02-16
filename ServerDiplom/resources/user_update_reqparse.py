from flask_restful import reqparse

parserup = reqparse.RequestParser()
parserup.add_argument('FIO', help='This field cannot be blank', required=True)
parserup.add_argument('email', help='This field cannot be blank', required=True)
parserup.add_argument('phone')
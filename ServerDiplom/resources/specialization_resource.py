from flask import jsonify
from flask_restful import abort, Resource
import sqlalchemy as sa
from models import db_sessions
from models.specializations import Specialization, special_to_education
from models.educations import Education

from resources.specialization_reqparse import parser
from resources.spec_to_edu_reqparse import parser_spec_to_edu
from resources.expToDoc_reqparse import parserlink

def find_specialization(spec_id):
    session = db_sessions.create_session()
    document = session.query(Specialization).get(spec_id)
    return document

def abort_if_specialization_not_found(spec, spec_id):
    if not spec:
        abort(404, message=f"Specialization with number {spec_id} not found")

class SpecToEduResource(Resource):
    
    def post(self):
        args = parser_spec_to_edu.parse_args()
        session = db_sessions.create_session()
        spec_id = args["spec_id"]
        spec = session.query(Specialization).get(spec_id)
        edu_id = args["edu_id"]
        education = session.query(Education).get(edu_id)
        spec.education.append(education)
        session.add(spec)
        session.commit()
        return jsonify({'success': 'OK'})
    
    def get(self):
        
        result=[]
        session = db_sessions.create_session()
        specializations = session.query(Specialization).join(Specialization.education).all()
        for specialization in specializations:
            for education in specialization.education:
                link_data = {
                    'spec_id': specialization.id,
                    'spec_name': specialization.name,
                    'edu_id': education.id,
                    'edu_name': education.name
                }
                result.append(link_data)
        return jsonify(result)
    def delete(self):
        args = parser_spec_to_edu.parse_args()
        spec_id = args["spec_id"]
        edu_id = args["edu_id"]
        session = db_sessions.create_session()
        spec = session.query(Specialization).get(spec_id)
        edu = session.query(Education).get(edu_id)
        spec.education.remove(edu)
        session.commit()
        return jsonify({'success': 'OK'})
        
        
class SpecializationListResource(Resource):
    
    def get(self):
        session = db_sessions.create_session()
        specializations = session.query(Specialization).all()
        return jsonify([item.to_dict(only=('id', 'name', 'description')) for item in specializations])
    
    def post(self):
        args = parser.parse_args()
        exp = Specialization(**args)
        exp.save_to_db()
        return jsonify({'success': 'OK'})
        
class SpecializationResource(Resource):
    
    def get(self, spec_id):
        specializations = find_specialization(spec_id)
        abort_if_specialization_not_found(specializations, spec_id)
        return jsonify(specializations.to_dict(only=('id', 'name', 'description')))
    
    def put(self,spec_id):
        args = parser.parse_args()
        if (args['name'] == ''):
            return jsonify(status="Специальность должна иметь название!")
        else:
            exp = find_specialization(spec_id)
            exp.name = args['name']
            exp.description = args['description']
            exp.update_to_db()
            return jsonify(status="Успешно")
        
    def delete(self, spec_id):
        session = db_sessions.create_session()
        session.query(Specialization).filter(Specialization.id == spec_id).delete()
        session.commit()
        return jsonify({'success': 'OK'})
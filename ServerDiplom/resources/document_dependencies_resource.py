from flask import jsonify
from flask_restful import abort, Resource
from models import db_sessions
from models.spec_to_edu_to_doc import Spec_to_Edu_to_Doc
from resources.document_dependencies_reqparse import parser

def find_link(dependence_id):
    session = db_sessions.create_session()
    dependence = session.query(Spec_to_Edu_to_Doc).get(dependence_id)
    return dependence




def abort_if_link_not_found(dependence, dependence_id):
    if not dependence:
        abort(404, message=f"Document dependence with number {dependence_id} not found")
    
class DependenciesListResource(Resource):
    def get(self, doc_id):
        session = db_sessions.create_session()
        dependencies = session.query(Spec_to_Edu_to_Doc).filter(Spec_to_Edu_to_Doc.docId == doc_id)
        return jsonify([item.to_dict(only=('id', 'specId', 'docId', 'eduId', 'documents_scan')) for item in dependencies])
    def post(self, doc_id):
        args = parser.parse_args()
        dependence = Spec_to_Edu_to_Doc(**args)
        dependence.docId = doc_id
        session = db_sessions.create_session()
        check = session.query(Spec_to_Edu_to_Doc).filter(Spec_to_Edu_to_Doc.specId==dependence.specId,
                                                                Spec_to_Edu_to_Doc.eduId == dependence.eduId, 
                                                                Spec_to_Edu_to_Doc.docId == doc_id).first()
        if not check:
            dependence.save_to_db()
        else:
            return jsonify(status="Вы уже добавили запись с этими данными!")
        
        return jsonify({'success': 'OK'})
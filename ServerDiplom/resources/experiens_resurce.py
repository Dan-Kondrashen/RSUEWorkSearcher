from flask import jsonify
from flask_restful import abort, Resource
from models import db_sessions
from models.experiens import Experiens
from models.Exp_to_Doc  import Exp_to_Doc
from resources.experiens_reqparse import parser
from resources.expToDoc_reqparse import parserlink

def find_experience(exp_id):
    session = db_sessions.create_session()
    document = session.query(Experiens).get(exp_id)
    return document
def find_exp_to_doc(doc_id, exp_id):
    session = db_sessions.create_session()
    document = session.query(Exp_to_Doc).filter_by(docId = doc_id, expId = exp_id).first()
    return document


def abort_if_experience_not_found(exp, exp_id):
    if not exp:
        abort(404, message=f"Experience with number {exp_id} not found")
        
def abort_if_experience_link_not_found(exp, exp_id):
    if not exp:
        abort(404, message=f"Experience to document with number {exp_id} not found")

class ExperiensResurce(Resource):
    def get(self, exp_id):
        experiens = find_experience(exp_id)
        abort_if_experience_not_found(experiens, exp_id)
        return jsonify(experiens.to_dict(only=('id', 'experience', 'role', 'place')))
    def put(self, exp_id):
        args = parser.parse_args()
        experiens = find_experience(exp_id)
        abort_if_experience_not_found(experiens, exp_id)
        if (args['experiens'] == "" or args['role'] == ""):
            return jsonify(status="Чтобы добавить опыт работы, все поля формы должны быть зополнены!")
        else:
            experiens.experiens = args['experience']
            experiens.role = args['role']
            experiens.place = args['place']
            experiens.update_to_db()
            return jsonify(status="Успешно")
    def delete(self, exp_id):
        session = db_sessions.create_session()
        session.query(Experiens).filter(Experiens.id == exp_id).delete()
        session.commit()
        return jsonify({'success': 'OK'})
    
class AllExperiensResource(Resource):
    def get(self):
        session = db_sessions.create_session()
        experiens = session.query(Experiens).all()
        return jsonify([item.to_dict(only=('id', 'experience', 'role', 'place')) for item in experiens])

    def post(self):
        args = parser.parse_args()
        exp = Experiens(**args)
        exp.save_to_db()
        return jsonify({'success': 'OK'})

class ExpeToDocListResource(Resource):
    def get(self, doc_id):
        session = db_sessions.create_session()
        experiens = session.query(Exp_to_Doc).filter_by(docId = doc_id)
        return jsonify([item.to_dict(only=('id','docId', 'expId', 'documents_scan')) for item in experiens])

    def post(self, doc_id):
        args = parserlink.parse_args()
        exp = Exp_to_Doc(**args)
        exp.docId = doc_id
        test1 = find_exp_to_doc(exp.docId, exp.docId)
        if not test1:
            exp.save_to_db()
            return jsonify({'success': 'OK'})
        else:
            return jsonify(status="Вы уже добавили эти данные в документ")

class ExpeToDocResource(Resource):
    def get(self, doc_id, exp_id):
        session = db_sessions.create_session()
        exp_to_doc = session.query(Exp_to_Doc).filter_by(docId = doc_id, expId = exp_id).first()
        return jsonify(exp_to_doc.to_dict(only=('id', 'expId', 'docId', 'documents_scan')))
    def put(self, doc_id, exp_id):
        args = parserlink.parse_args()
        experiens = find_exp_to_doc(doc_id, exp_id)
        abort_if_experience_not_found(experiens, exp_id)
        if (args['documents_scan'] == ""):
            return jsonify(status="Чтобы подтвердить наличие опыта, прикрепите сканы соответствуюших документов ниже!")
        else:
            experiens.docId = args['docId']
            experiens.expId = args['expId']
            experiens.documents_scan = args['documents_scan']
            experiens.update_to_db()
            return jsonify(status="Успешно")
        
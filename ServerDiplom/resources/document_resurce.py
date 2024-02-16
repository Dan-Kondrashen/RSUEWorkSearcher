from flask import jsonify
from flask import json
from flask_restful import reqparse
from flask_restful import abort, Resource
from models import db_sessions
from models.documents import Document
from resources.document_reqparse import parser
def find_document(doc_id):
    session = db_sessions.create_session()
    document = session.query(Document).get(doc_id)
    return document

def abort_if_document_not_found(doc, doc_id):
    if not doc:
        abort(404, message=f"Document with number {doc_id} not found")
    

class DocumentResource(Resource):
    def get(self, doc_id):
        document = find_document(doc_id)
        abort_if_document_not_found(document, doc_id)
        return jsonify(document.to_dict(only=('id', 'contactinfo', 'extra_info', 'userId', 'salary', 'type', 'date')))

    def put(self, doc_id):
        args = parser.parse_args()
        document = find_document(doc_id)
        abort_if_document_not_found(document, doc_id)
        document.contactinfo= args['contactinfo']
        document.extra_info= args['extra_info']
        document.salary= args['salary']
        document.userId = args['userId']
        document.date = args['date']
        document.type = args['type']
        document.update_to_db()
        return jsonify(status="Успешно")

    def delete(self, doc_id):
        session = db_sessions.create_session()
        session.query(Document).filter(Document.id == doc_id).delete()
        session.commit()
        return jsonify({'success': 'OK'})
    

class DocumentsListResource(Resource):
    def get(self):
        session = db_sessions.create_session()
        services = session.query(Document).all()
        return jsonify([item.to_dict(only=('id', 'title','type', 'date', 'contactinfo', 'extra_info', 'userId', 'salary')) for item in services])

    def post(self):
        args = parser.parse_args()
        
        # doc = Document(**args)
        # doc.save_to_db()
        # parsers = reqparse.RequestParser()
        # parsers.add_argument("documents", location='json', type=list)
        # argss = parser.parse_args()
        document = args["documents"]
        
        
        # doc1 = Document(title = "item['title']",
        #                 contactinfo = "item['contactinfo'],",
        #                 extra_info = "dswdf",
        #                 salary = "20000",
        #                 type = "Dfкансия",
        #                 userId = 1)
        # doc1.save_to_db
        for item in document:
            doc = Document(
            title = item['title'],
            contactinfo = item['contactinfo'],
            extra_info = item['extra_info'],
            salary = item['salary'],
            type = item['type'],
            userId = item['userId'])
            doc.save_to_db()
        
        
        return jsonify({'success': 'OK'})
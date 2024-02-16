import sqlalchemy
from sqlalchemy import orm
from sqlalchemy_serializer import SerializerMixin
from models import db_sessions

from .db_sessions import SqlAlchemyBase

class Spec_to_Edu_to_Doc(SqlAlchemyBase, SerializerMixin):
    __tablename__ = 'special_to_education_to_document'
    id = sqlalchemy.Column(sqlalchemy.Integer, primary_key=True, autoincrement=True)
    documents_scan= sqlalchemy.Column(sqlalchemy.String, nullable=True)
    specId = sqlalchemy.Column(sqlalchemy.Integer, sqlalchemy.ForeignKey("specializations.id"))
    specialization =  orm.relationship("Specialization")
    eduId = sqlalchemy.Column(sqlalchemy.Integer, sqlalchemy.ForeignKey("educations.id"), nullable = True)
    educetion =  orm.relationship("Education")
    docId = sqlalchemy.Column(sqlalchemy.Integer, sqlalchemy.ForeignKey("documents.id"))
    document = orm.relationship('Document')
    
    
    def __repr__(self):
        return f'<Spec_to_Edu_to_Doc> {self.id} {self.specId} {self.docId} {self.eduId} {self.documents_scan}'

    def save_to_db(self):
        session = db_sessions.create_session()
        session.add(self)
        session.commit()
        

    def update_to_db(self):
        session = db_sessions.create_session()
        session.merge(self)
        session.commit()

    def delete_from_db(self):
        session = db_sessions.create_session()
        session.delete(self)
        session.commit()
import sqlalchemy
import datetime
from sqlalchemy import orm
from sqlalchemy_serializer import SerializerMixin
from models import db_sessions

from .db_sessions import SqlAlchemyBase


class Document(SqlAlchemyBase, SerializerMixin):
    __tablename__ = 'documents'
    id = sqlalchemy.Column(sqlalchemy.Integer, primary_key=True, autoincrement=True)
    title = sqlalchemy.Column(sqlalchemy.String, nullable=False)
    contactinfo = sqlalchemy.Column(sqlalchemy.String, nullable=False)
    extra_info = sqlalchemy.Column(sqlalchemy.String, nullable=True)
    salary = sqlalchemy.Column(sqlalchemy.String, nullable =False)
    type = sqlalchemy.Column(sqlalchemy.String, nullable = True)
    date = sqlalchemy.Column(sqlalchemy.DateTime, default=datetime.datetime.now)
    userId = sqlalchemy.Column(sqlalchemy.Integer, sqlalchemy.ForeignKey("users.id"))
    user = orm.relationship('User')
    knowledge = orm.relation("Knowledge",
                                   secondary ="knowledge_to_document", 
                                   back_populates ="document")
    experience = orm.relation("Experiens",
                             secondary = 'experience_to_document',
                             back_populates = 'document')
 
    
    def __repr__(self):
        return f'<Dociment> {self.id} {self.title} {self.contactinfo} {self.salary} {self.extra_info} {self.type} {self.date} {self.userId}'

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

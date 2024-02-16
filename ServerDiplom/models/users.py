import datetime

import sqlalchemy
from sqlalchemy import orm

from sqlalchemy_serializer import SerializerMixin
from werkzeug.security import generate_password_hash, check_password_hash
from passlib.hash import pbkdf2_sha256 as sha256

from models import db_sessions

from .db_sessions import SqlAlchemyBase


class User(SqlAlchemyBase, SerializerMixin):
    __tablename__ = 'users'

    id = sqlalchemy.Column(sqlalchemy.Integer, primary_key=True, autoincrement=True)
    FIO = sqlalchemy.Column(sqlalchemy.String, nullable=True)
    phone = sqlalchemy.Column(sqlalchemy.BIGINT, nullable=True)
    email = sqlalchemy.Column(sqlalchemy.String, index=True, unique=True)
    password = sqlalchemy.Column(sqlalchemy.String, index=True)
    registration_date = sqlalchemy.Column(sqlalchemy.DateTime, default=datetime.datetime.now)
    roleId = sqlalchemy.Column(sqlalchemy.Integer, sqlalchemy.ForeignKey("userroles.id"))
    role = orm.relationship('UserRole')

    def __repr__(self):
        return f'<User> {self.id} {self.FIO} {self.email} {self.roleId}'

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

    @classmethod
    def find_by_email(cls, email):
        session = db_sessions.create_session()
        return session.query(User).filter(User.email == email).first()

    @staticmethod
    def return_all():
        def to_json(x):
            return {
                'email': x.email,
                'password': x.hashed_password
            }

        session = db_sessions.create_session()
        return {'users': list(map(lambda x: to_json(x), session.query(User).all()))}

    @staticmethod
    def generate_hash(password):
        return sha256.hash(password)

    @staticmethod
    def verify_hash(password, hash):
        return sha256.verify(password, hash)
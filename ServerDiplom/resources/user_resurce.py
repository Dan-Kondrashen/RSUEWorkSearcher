from flask import jsonify
from models import db_sessions
from models.users import User
from flask_restful import Resource, abort
from resources.user_update_reqparse import parserup

def abort_if_user_not_found(user, id):
    if not user:
        abort(404, message=f"User with number {id} not found")

class AllUsers(Resource):

    def get(self):
        session = db_sessions.create_session()
        users = session.query(User).all()
        return jsonify([item.to_dict(only=('id','FIO','email', 'registration_date')) for item in users])
    
    def delete(self):
        session = db_sessions.create_session()
        session.query(User).delete()
        session.commit()
        
class UserResource(Resource):

    def get(self, user_id):
        session = db_sessions.create_session()
        user = session.query(User).get(user_id)
        abort_if_user_not_found(user, user_id)
        return jsonify(user.to_dict(only=('id', 'FIO', 'phone', 'email')))

    def put(self, user_id):
        args = parserup.parse_args()
        session = db_sessions.create_session()
        user = session.query(User).get(user_id)
        abort_if_user_not_found(user, user_id)
        if User.find_by_email(args['email']):
            user.FIO = args['FIO']
            user.phone = args['phone']
            user.update_to_db()
            return jsonify(
                status='Пользователь обновлен успешно! Однако пользователь с таким email уже существует, поэтому email остался прежним!')
        else:
            user.FIO = args['FIO']
            user.phone = args['phone']
            user.email = args['email']
            user.update_to_db()
            return jsonify(status='Пользователь обновлен успешно!')

    def delete(self, user_id):
        session = db_sessions.create_session()
        session.query(User).filter(User.id == user_id).delete()
        session.commit()
        return jsonify({'success': 'OK'})
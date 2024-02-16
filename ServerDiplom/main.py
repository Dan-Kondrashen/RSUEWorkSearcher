
from flask import Flask
from flask_migrate import Migrate
from flask_sqlalchemy import SQLAlchemy
from bs4 import BeautifulSoup
from models import db_sessions
from resources import  user_resurce, comment_resurce, document_resurce, experiens_resurce,specialization_resource, \
education_resource, document_dependencies_resource, knowledge_resource
from flask_restful import Api, Resource, reqparse, abort

app = Flask(__name__)
conn_str = db_sessions.global_init()
app.config['SECRET_KEY'] = 'kondrashen_secret_key'
app.config['JWT_SECRET_KEY'] = 'jwt-secret-kondrashin'
app.config['SQLALCHEMY_DATABASE_URI'] = conn_str

db = SQLAlchemy(app)

migrate = Migrate(app, db_sessions)
api = Api(app, catch_all_404s=True)
api.add_resource(user_resurce.AllUsers, "/users")
api.add_resource(user_resurce.UserResource, "/users/<user_id>")
api.add_resource(comment_resurce.CommentResource, '/comments/<comm_id>')
api.add_resource(document_resurce.DocumentResource, "/documents/<doc_id>")
api.add_resource(document_resurce.DocumentsListResource, "/documents")
api.add_resource(experiens_resurce.AllExperiensResource, "/experiens")
api.add_resource(knowledge_resource.AllKnowledgesResource, "/knowledges")
api.add_resource(experiens_resurce.ExperiensResurce, "/experiens/<exp_id>")
api.add_resource(knowledge_resource.KnowledgeResurce, "/knowledges/<know_id>")
api.add_resource(experiens_resurce.ExpeToDocListResource, "/documents/<doc_id>/experiens")
api.add_resource(experiens_resurce.ExpeToDocResource, "/documents/<doc_id>/experiens/<exp_id>")
api.add_resource(specialization_resource.SpecToEduResource, "/specializations_to_educations")
api.add_resource(specialization_resource.SpecializationListResource, "/specializations")
api.add_resource(specialization_resource.SpecializationResource, "/specializations/<spec_id>")
api.add_resource(education_resource.EducationListResource, "/educations")
api.add_resource(education_resource.EducationResource, "/educations/<edu_id>")
api.add_resource(document_dependencies_resource.DependenciesListResource, "/documents/<doc_id>/dependencies")
api.add_resource(knowledge_resource.KnowToDocListResource, "/documents/<doc_id>/knowledges")




@app.route("/")
def index():
  return "Все работает?"

def main():
  app.run(host='0.0.0.0', port=8080)
  
if __name__ == '__main__':
  main()
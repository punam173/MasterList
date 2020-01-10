"""
A simple bubble tea location finding and adding flask app.
URL for index, info and view files are added
"""
import flask
from flask.views import MethodView
from index import Index
from info import Info
from view import View

app = flask.Flask(__name__)       # our Flask app

app.add_url_rule('/',
                 view_func=Index.as_view('index'),
                 methods=["GET"])

app.add_url_rule('/info/',
                 view_func=Info.as_view('info'),
                 methods=['GET', 'POST'])
app.add_url_rule('/view/',
                 view_func=View.as_view('view'),
                 methods=['GET'])

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8000, debug=True)




















































































from flask import render_template
from flask.views import MethodView
import btmodel
"""
This function use the model implemeted in sqlite and maps the parameters with the inputs
It takes the iput given by the user and the with specified url method it updates the databse
"""
class Index(MethodView):
    def get(self):
       # model = gbmodel.get_model()
        #entries = [dict(name=row[0], street_address=row[1], city=row[2], state=row[3], zip_code=row[4], store_hours=row[5], phone_number=row[6], rating=row[7], review=row[8], drink_to_order=row[9] ) for row in model.select()]
        return render_template('index.html')

































































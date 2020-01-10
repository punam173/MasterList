from flask import redirect, request, url_for, render_template
from flask.views import MethodView
import btmodel

class View(MethodView):
    def get(self):
        model = btmodel.get_model()
        entries = [dict(name=row[0], street_address=row[1], city=row[2], state=row[3], zip_code=row[4], store_hours=row[5], phone_number=row[6], rating=row[7], review=row[8], drink_to_order=row[9] ) for row in model.select()]

        return render_template('view.html', entries=entries)



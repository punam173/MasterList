from flask import redirect, request, url_for, render_template
from flask.views import MethodView
import btmodel

class Info(MethodView):
    def get(self):
        return render_template('info.html')

    def post(self):
        """
        Accepts POST requests, and processes the form;
        Redirect to index when completed.
        """
        model = btmodel.get_model()
        model.insert(request.form['name'], request.form['street_address'], request.form['city'], request.form['state'], request.form['zip_code'], request.form['store_hours'], request.form['phone_number'], request.form['rating'], request.form['review'], request.form['drink_to_order'])
        return redirect(url_for('index'))












































































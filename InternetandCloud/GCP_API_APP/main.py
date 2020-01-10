from flask import Flask, redirect, render_template, request
from google.cloud import storage
from google.cloud import vision
from google.cloud import translate
from google.cloud import speech
from google.cloud.speech import types
from google.cloud.speech import enums
from google.cloud import videointelligence
import os
import process
import io

CLOUD_STORAGE_BUCKET = os.environ.get('CLOUD_STORAGE_BUCKET')

app = Flask(__name__)

@app.route("/")
def homepage():
    return render_template('homepage.html')

@app.route("/imagepage")
def imagepage():
    return render_template('imagepage.html')

@app.route("/videopage")
def videopage():
    return render_template('videopage.html')

@app.route("/speechpage")
def speechpage():
    return render_template('speechpage.html')

# Upload image to analyze and render to the result page-by Weiwei
@app.route('/uploadImage',methods=['GET','POST'])
def uploadImage():

    # Set photo to upload to bucket
    photo = request.files['file']

    # Create a cloud storage client.
    storage_client = storage.Client()

    # Get the bucket that the file will be uploaded to.
    bucket = storage_client.get_bucket(CLOUD_STORAGE_BUCKET)

    # Create a new blob and upload the file's content.
    blob = bucket.blob(photo.filename)
    blob.upload_from_string(photo.read(), content_type=photo.content_type)

    # Make the blob publicly viewable.
    blob.make_public()

    # Create a Cloud Vision client.
    vision_client = vision.ImageAnnotatorClient()
    
    # Use the Cloud Vision API to label our image.
    source_uri = 'gs://{}/{}'.format(CLOUD_STORAGE_BUCKET, blob.name)
    image = vision.types.Image(source=vision.types.ImageSource(gcs_image_uri=source_uri))
    labels = process.label_pic(image)
    
    # Use the Cloud translation API to translate first 4 labels
    result = []
    target = request.form['target_lang']
    length = len(labels)

    # Set the number of labels to be displayed
    n = 0
    if length > 4:
        n = 4
    else:
        n = length
   
    for i in range(n):
        label = labels[i].description
        trans = process.translate_text(label, target)
        result.append(trans)

    # Redirect to the result page.
    return render_template('resultImage.html', labels=labels, result=result, n=n, source_uri=blob.public_url)


# Upload video to analyze and render to the result page-by Weiwei
@app.route('/uploadVideo',methods=['GET','POST'])
def uploadVideo():

    # Set video to upload to bucket
    video = request.files['file']

    # Create a cloud storage client.
    storage_client = storage.Client()

    # Get the bucket that the file will be uploaded to.
    bucket = storage_client.get_bucket(CLOUD_STORAGE_BUCKET)

    # Create a new blob and upload the file's content.
    blob = bucket.blob(video.filename)
    blob.upload_from_string(video.read(), content_type=video.content_type)

    # Make the blob publicly viewable.
    blob.make_public()

    # Create a Cloud Video Intelligence client.
    video_client = videointelligence.VideoIntelligenceServiceClient() 
    
    # Use the Cloud Video Intelligence API to label our video.
    source_uri = 'gs://{}/{}'.format(CLOUD_STORAGE_BUCKET, blob.name)
    segment_labels = process.analyze_labels(source_uri)

    # Use the Cloud translation API to translate the labels
    result = []
    confidence_list = []
    target = request.form['target_lang']
    length = len(segment_labels)
    
    for i, segment_label in enumerate(segment_labels):
        label = segment_label.entity.description
        for i, segment in enumerate(segment_label.segments):
            confidence = segment.confidence
            confidence_list.append(confidence)
        trans = process.translate_text(label, target)
        result.append(trans)


    # Redirect to the result page.
    return render_template('resultVideo.html', labels=segment_labels, result=result, source_uri=blob.public_url, confidence=confidence_list)


# Upload audio to analyze and render to the result page-by Punam
@app.route('/uploadSpeech',methods=['GET','POST'])
def uploadSpeech():

    # Set audio to upload to bucket
    audio = request.files['file']

    # Create a cloud storage client.
    storage_client = storage.Client()

    # Get the bucket that the file will be uploaded to.
    bucket = storage_client.get_bucket(CLOUD_STORAGE_BUCKET)

    # Create a new blob and upload the file's content.
    blob = bucket.blob(audio.filename)
    blob.upload_from_string(audio.read(), content_type=audio.content_type)

    # Make the blob publicly viewable.
    blob.make_public()

    # Create a Cloud Speech client.
    speech_client = speech.SpeechClient()
    source_uri = 'gs://{}/{}'.format(CLOUD_STORAGE_BUCKET, blob.name)
    response = process.transcribe_gcs(source_uri)

    results = []
    target = request.form['target_lang']
    info = []
    scores = []

    # Use Translate API and Natural language API
    for result in response.results:
        r = result.alternatives[0].transcript
        trans = process.translate_text(r, target)
        sentiment = process.sentiment_text(r)
        score = sentiment.score
        scores.append(score)
        results.append(trans)
        info.append(r)

    # Redirect to the result page.
    return render_template('resultSpeech.html', labels=info, result=results, source_uri=blob.public_url, scores=scores)


# Done by Punam
@app.errorhandler(500)
def server_error(e):
    logging.exception('An error occurred during a request.')
    return """
    An internal error occurred: <pre>{}</pre>
    See logs for full stacktrace.
    """.format(e), 500

if __name__ == '__main__':
    # This is used when running locally. Gunicorn is used to run the
    # application on Google App Engine. See entrypoint in app.yaml.
    app.run(host='127.0.0.1', port=8080, debug=True)




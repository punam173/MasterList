from google.cloud import texttospeech
from google.cloud import translate
from google.cloud import vision
from google.cloud import speech
from google.cloud.speech import enums
from google.cloud.speech import types
from google.cloud import language
from google.cloud.language import enums
from google.cloud.language import types
from google.cloud import videointelligence
import io

# Done by Weiwei
def translate_text(input_str, language):
    """
    Converts text from source language to target langauge.
    param input_str: user input
    param language: language option
    return label:generated labels by API
    """
    
    # Set translation client parameters 
    client = translate.Client()
    result = client.translate(input_str, target_language = language)
    return result

# Done by Weiwei
def label_pic(image):
    """
    Creates a list of labels for primary objects in an image
    param image: uploaded image
    returns labels: labels generated by API
    """

    # Set vision client parameters 
    client = vision.ImageAnnotatorClient()
    
    # Process image and return list of labels
    response = client.label_detection(image=image)
    labels = response.label_annotations
    return labels

# Done by Punam
def sentiment_text(text):
    """
    Use Cloud Natural Language API to analyze the text
    param text: gerated text by speech to text api 
    returns sentiment: sentimental score generated
    """
    client = language.LanguageServiceClient()
    try:
        text = text.decode('utf-8')
    except AttributeError:
        pass

    document = types.Document(
        content=text,
        type=enums.Document.Type.PLAIN_TEXT)

    # Detects sentiment in the document
    sentiment = client.analyze_sentiment(document).document_sentiment
    return sentiment

# Done by Weiwei
def analyze_labels(path):
    """ 
    Use Cloud Video Intelligence API to analyze the video
    based on teh path
    param path: path generated by api
    returns segment_labels: result of analyzed labels
    """
    video_client = videointelligence.VideoIntelligenceServiceClient()
    features = [videointelligence.enums.Feature.LABEL_DETECTION]
    operation = video_client.annotate_video(path, features=features)
    
    result = operation.result(timeout=90)

    segment_labels = result.annotation_results[0].segment_label_annotations
    return segment_labels


# Done by Punam
def transcribe_gcs(gcs_uri):
    """
    Use Cloud Speech-to-Text API to analyze the audio
    based on its path
    param gcs_uri: uploaded speech audio
    returns response: generated text from speech
    """
    client = speech.SpeechClient()
    audio = speech.types.RecognitionAudio(uri=gcs_uri)
    config = speech.types.RecognitionConfig(encoding='LINEAR16',
        language_code='en-US',
        sample_rate_hertz=44100,
        audio_channel_count=2,)
    response = client.recognize(config, audio)
    return response

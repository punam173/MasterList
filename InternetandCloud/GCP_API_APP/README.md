# Multimedia Recognition/Translation App

This app is used to recognize the images, videos, or audios uploaded by the users. It can also be used to translate the recognition information to different languages based on the users' choices.
Google Cloud APIs including Vision, Text-to-Speech, Natural Language Processing, Speech-to-Text, Video Intelligence, Translate were used in this app.

## Project Participants
- Weiwei Chen
- Punam Rani Pal

## Pre-reqs, Setup, and Build

Git clone, and go to the right directory
```shell
$ git clone git@bitbucket.org:waiwaixiaochen/cs430-weiwei-chen.git
$ cd cs430-weiwei-chen/final
```
 
Enable APIs
```shell
$ gcloud services enable storage-component.googleapis.com
$ gcloud services enable datastore.googleapis.com
$ gcloud services enable vision.googleapis.com
$ gcloud services enable translate.googleapis.com
$ gcloud services enable speech.googleapis.com
$ gcloud services enable language.googleapis.com
$ gcloud services enable videointelligence.googleapis.com
```

Setup credentials in Cloud Shell
```shell
$ gcloud iam service-accounts create finalapp --display-name "Final App"
$ gcloud projects add-iam-policy-binding ${DEVSHELL_PROJECT_ID} --member serviceAccount:finalapp@${DEVSHELL_PROJECT_ID}.iam.gserviceaccount.com --role roles/storage.admin
$ gcloud projects add-iam-policy-binding ${DEVSHELL_PROJECT_ID} --member serviceAccount:finalapp@${DEVSHELL_PROJECT_ID}.iam.gserviceaccount.com --role roles/datastore.user
$ gcloud iam service-accounts keys create /home/${USER}/finalapp.json --iam-account finalapp@${DEVSHELL_PROJECT_ID}.iam.gserviceaccount.com
$ export GOOGLE_APPLICATION_CREDENTIALS="/home/${USER}/finalapp.json"
$ gsutil mb gs://${DEVSHELL_PROJECT_ID}
$ export CLOUD_STORAGE_BUCKET=${DEVSHELL_PROJECT_ID}
```

Modify app.yaml
```shell
env_variables:
  CLOUD_STORAGE_BUCKET: your_own_bucket_name
```

## Run 
Setup environment and install packages to run code
```shell
$ virtualenv -p python3 env
$ source env/bin/activate
$ pip install -r requirements.txt
```

Run the app
```shell
$ python main.py
```

## Deactivate development environment, and deploy
```shell
$ deactivate
$ gcloud app deploy
```


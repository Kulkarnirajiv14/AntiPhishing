

from keras.models import Sequential
from keras.layers import Dense
from keras.models import model_from_json
import numpy
import os
import socket
import sys
import cv2
import numpy as np
import urllib.parse


import tensorflow as tf
from keras.backend.tensorflow_backend import set_session
from sklearn.metrics import accuracy_score, classification_report, confusion_matrix, label_ranking_average_precision_score, label_ranking_loss, coverage_error

config = tf.ConfigProto(
    gpu_options = tf.GPUOptions(per_process_gpu_memory_fraction=0.8)
    # device_count = {'GPU': 1}
)
config.gpu_options.allow_growth = True
session = tf.Session(config=config)
set_session(session)
######
# load json and create model
# json_file = open('model.json', 'r')
# loaded_model_json = json_file.read()
# json_file.close()
# loaded_model = model_from_json(loaded_model_json)
# # load weights into new model
# loaded_model.load_weights("model.h5")
import filehelper
loaded_model=filehelper.read_object("model.bin")
print("Loaded model from disk")

op=loaded_model.predict(np.array([[-1,-1,-1,1,-1,-1,-1,1,1,-1,1,1,1]]))
print(op)
# import pandas as pd
# df = pd.read_csv('../dataset_neural.csv')
# print(df.shape)
# df.describe()
#
#
# target_column = ['target']
# predictors = list(set(list(df.columns))-set(target_column))
# df[predictors] = df[predictors]/df[predictors].max()
# df.describe()
#
#
# from keras.utils import to_categorical
# X = df[predictors].values
# y = df[target_column].values
# y_test = to_categorical(y)
# op=loaded_model.predict(X)
# print(op)
# print(classification_report(op.argmax(axis=1), y_test.argmax(axis=1)))
# print(op.argmax(axis=1))
# print(op.argmax(axis=1))
# print(confusion_matrix(op.argmax(axis=1), y_test.argmax(axis=1)))


HOST = ''  # Symbolic name, meaning all available interfaces
PORT = 7813  # Arbitrary non-privileged port

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Socket created')

# Bind socket to local host and port
try:
    s.bind((HOST, PORT))
except socket.error as msg:
    print('Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1])
    sys.exit()

print('Socket bind complete')
classes=["phishing","normal"]
# Start listening on socket
s.listen(1000)
print('Socket now listening')

# now keep talking with the client
while 1:
    # wait to accept a connection - blocking call
    conn, addr = s.accept()
    #print('Connected with ' + addr[0] + ':' + str(addr[1]))
    try:
      data = conn.recv(1024).decode()
      image_path = data.strip().split()[1]
      print("original ", image_path)
      image_path = urllib.parse.unquote(image_path)	
      print("decoded ", image_path)
      image_path=image_path.replace("/[","");
      image_path = image_path.replace("]", "");
      image_path = image_path.replace("+", " ");
      print("final ", image_path)

      y_pred=loaded_model.predict(np.reshape(np.fromstring(image_path, dtype=float, sep=','),[1,13]))
      print("y_pred ", y_pred)

      r = ",".join(str(x) for x in classes)
      r = str(y_pred[0])+'#'+r+"#"+classes[int(y_pred[0])]
      print("rrr ", r.encode())
      conn.send(r.encode())
    except:
      type, value, traceback = sys.exc_info()
      #print('Error opening %s: %s' % (value))
    conn.close()
s.close()
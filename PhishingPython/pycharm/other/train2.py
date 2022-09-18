import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import sys
from sklearn.model_selection import train_test_split
from sklearn.svm import SVC
from sklearn.metrics import classification_report, confusion_matrix
import filehelper

bankdata = pd.read_csv("../dataset_neural.csv")
bankdata.shape
bankdata.head()
X = bankdata.drop('target', axis=1)
y = bankdata['target']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.30)
svclassifier = SVC(gamma='auto')
svclassifier.fit(X_train, y_train)
y_pred = svclassifier.predict(X_test)
print(y_pred)
print(classification_report(y_pred, y_test))

print(confusion_matrix(y_pred, y_test))
filehelper.save_object(svclassifier,"model.bin")
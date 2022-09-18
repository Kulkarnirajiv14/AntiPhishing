import pickle
from pathlib import Path
def read_file(filepath):
    f=open(filepath,'r')
    contents =f.read()
    return contents
def read_file_for_LDA(filepath):
    f=open(filepath,'r',encoding="utf8")
    contents =f.read()
    return contents
def write_file(filepath,content):
    f= open(filepath,"w+")
    f.write(content)
    f.close()
def save_object(obj, filename):
    with open(filename, 'wb') as output:  # Overwrites any existing file.
        pickle.dump(obj, output, pickle.HIGHEST_PROTOCOL)
def read_object(filename):
    with open(filename, 'rb') as input:
        return pickle.load(input)
def isFileExist(filepath):
    user_file=Path(filepath);
    if user_file.exists():
        return True
    else:
        return False
#doc1 = read_file("Topic_Dataset/temple.txt")
#print(doc1.encode("utf-8"))
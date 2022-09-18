SET D=%cd%
CD %D%
%D:~0,2%
taskkill /IM python.exe /F
python predict.py
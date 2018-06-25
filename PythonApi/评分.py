#!/usr/bin/env python2
# -*- coding: utf-8 -*-
import sys

png_path = sys.argv[1]

import math
import keras
import numpy as np
import foolbox
from keras.models import load_model
from keras.utils import np_utils
import matplotlib

matplotlib.use('Agg')
from matplotlib import pyplot as plt
from PIL import Image

original_path = "./original.jpg"
result_path = "./adversial.jpg"

# print(np.array(Image.open(original_path)))

# print(np.array(Image.open(original_path)).shape)


# img_original = np.array(Image.open(original_path))[:,:,0]
# img_result = np.array(Image.open(result_path))[:,:,0]
img_original = np.array(Image.open(original_path))
img_result = np.array(Image.open(result_path))
if len(img_original.shape) == 3:
    img_original = img_original[:, :, 0]
if len(img_result.shape) == 3:
    img_result = img_result[:, :, 0]

    # model=load_model('./Model_keras/mnist/model.hdf5')
    #
    #
    # pred_ori=np.argmax(model.predict(img_original.reshape(1,len(img_original),len(img_original[0]),1))[0])
    # pred_adv=np.argmax(model.predict(img_result.reshape(1,len(img_original),len(img_original[0]),1))[0])
    #
    # line_write = ""
    # print(pred_ori)
    # print(pred_adv)
    # if pred_ori == pred_adv:
    #     line_write = "{'code': 1, 'msg': 'attack fail', 'score': {'score': '0'}, 'title': ['score']}"
    # else:
count = np.sum((img_original.reshape(28, 28) - img_result.reshape(28, 28)) ** 2)
mse_pow = float(count) / float(len(img_original) * len(img_original[0]))
mse = math.sqrt(mse_pow)
# print(mse)
# div = mse / 70
score = 100 / (1 + math.pow(math.e, (mse - 50) / 15))
line_write = "{'code': 1, 'msg': 'attack success', 'score': {'score': %f}, 'title': ['score']}" % score
print(line_write)
# print(line_write)
# file = open("score.txt", "w")
# file.write(line_write)
# file.close()
#
#
# def f_score(mse):
#     pass

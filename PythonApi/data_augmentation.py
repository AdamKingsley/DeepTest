# -*- coding: utf-8 -*-

import numpy as np
import cv2
import base64

from io import BytesIO

from PIL import Image


def thin_down(image):
    kernel = np.ones((2, 2), np.uint8)
    erosion = cv2.erode(image, kernel, iterations=1)
    return erosion.reshape(28, 28, 1)


def get_fat(image):
    kernel = np.ones((2, 2), np.uint8)
    dilation = cv2.dilate(image, kernel, iterations=1)
    return dilation.reshape(28, 28, 1)


def thin(image_b64):
    if ',' in image_b64:
        # 获取纯的base64数据，不包含前缀
        image_b64 = image_b64.split(',')[1]
    image = base64.b64decode(image_b64)
    nparr = np.fromstring(image, np.uint8)
    image_data = cv2.imdecode(nparr, cv2.IMREAD_GRAYSCALE)
    result = thin_down(cv2.resize(image_data, (28, 28), interpolation=cv2.INTER_CUBIC))
    result_data = cv2.imencode('.png', result)[1]
    return str(base64.b64encode(result_data), 'utf-8')


def fat(image_b64):
    if ',' in image_b64:
        # 获取纯的base64数据，不包含前缀
        image_b64 = image_b64.split(',')[1]
    image = base64.b64decode(image_b64)
    nparr = np.fromstring(image, np.uint8)
    image_data = cv2.imdecode(nparr, cv2.IMREAD_GRAYSCALE)
    result = fat(cv2.resize(image_data, (28, 28), interpolation=cv2.INTER_CUBIC))
    result_data = cv2.imencode('.png', result)[1]
    return str(base64.b64encode(result_data), 'utf-8')

# -*- coding: utf-8 -*-

import base64
import json
import math
import os
import time
from io import BytesIO

import numpy as np
from PIL import Image
from keras import Model
from keras.models import load_model

import copy

from config import config
from mongo import MongoDB

import redis

redis_db = redis.StrictRedis(host='localhost', port=6379, db=0)


# 将本例中将三层隐藏层经过激活函数后的切开成三个模型
def getActivationLayers(model):
    intermediate_layer_model_1 = Model(inputs=model.input, outputs=model.get_layer("activation_7").output)
    intermediate_layer_model_2 = Model(inputs=model.input, outputs=model.get_layer("activation_8").output)
    intermediate_layer_model_3 = Model(inputs=model.input, outputs=model.get_layer("activation_9").output)
    return intermediate_layer_model_1, intermediate_layer_model_2, intermediate_layer_model_3


# 获取原始图 噪音图 混合后的图片数据
def getMutaionImage(original, compose):
    original_data = np.array(original) / float(255)
    compose_data = np.array(compose) / float(255)
    # 167/784的位置的数据有变化
    # print(len(adversial_data[np.where(adversial_data > 0)]))
    adversial_data = compose_data - original_data;
    # compose_data = original_data.copy()
    # compose_data[np.where(adversial_data != 0)] = adversial_data[np.where(adversial_data != 0)]
    return original_data, adversial_data, compose_data


# 将路径修改为符合当前系统的格式[主要是分隔符号]
def OSPath(path):
    if path:
        return path.replace('\\', os.sep).replace('/', os.sep)
    return ''


# 计算成绩
def cal_score(original_data, compose_data):
    count = np.sum((original_data.reshape(28, 28) - compose_data.reshape(28, 28)) ** 2)
    # 平方和 / 784
    mse_pow = float(count) / float(len(original_data.flatten()))
    mse = math.sqrt(mse_pow)
    score = 100 / (1 + math.pow(math.e, (mse - 50) / 15))
    return score


def process():
    print("* Initialize parameters...")
    mongodb_settings = {
        'ip': config.ip,
        'port': config.port,
        'db_name': config.db
    }
    image_base_path = config.image_path
    model_base_path = config.model_path
    generate_image_base_path = config.generate_image_path
    print("* End initialize")
    print("* Initialize mongodb connection...")
    client = MongoDB.MongoDB(settings=mongodb_settings)
    print("* Connect success")

    while True:
        queue = redis_db.lrange(config.IMAGE_QUEUE, 0, config.BATCH_SIZE - 1)
        imageIDs = []

        for q in queue:
            # ------- 初始化配置 -------------------
            q = json.loads(q.decode('utf-8'))

            imageId = q['id']
            compose_image_str = q['compose_image_str']
            exam_id = q['exam_id']
            image_id = q['image']['id']
            image_path = q['image']['path']
            models = q['mutation_models']
            standard_model_path = q['standard_model_path']
            user_id = q['user_id']
            # ------- 初始化配置end ----------------

            imageIDs.append(imageId)

            # ---------- 获取原图 | 噪音图/前景图 | 合成图的数据 生成及保存 start -------------
            # 获取干扰值的数据/前景图
            # 获取干扰值的数据/前景图
            if ',' in compose_image_str:
                # 获取纯的base64数据，不包含前缀
                compose_image_str = compose_image_str.split(',')[1]
            compose = base64.b64decode(compose_image_str)
            image_data = BytesIO(compose)
            compose_image = Image.open(image_data).convert('L').resize((28, 28))
            # 获取扰动的原始图片数据
            original_path = os.path.join(OSPath(image_base_path), OSPath(image_path))
            original_image = Image.open(original_path).convert('L')
            # 获取原始图片 前景图  合成图 的最终数据
            original_data, adversial_data, compose_data = getMutaionImage(original_image, compose_image)
            time_str = str(int(time.time()))
            # 图片的保存位置 以及保存
            adversial_path = '_'.join((str(user_id), str(exam_id), 'adversial', time_str)) + '.png'
            compose_path = '_'.join((str(user_id), str(exam_id), 'compose', time_str)) + '.png'
            adversial_image = Image.fromarray((adversial_data.reshape(28, 28) * 255).astype(np.uint8))
            compose_image = Image.fromarray((adversial_data.reshape(28, 28) * 255).astype(np.uint8))
            print('* Save images')
            print(os.path.join(OSPath(generate_image_base_path), OSPath(adversial_path)))
            print(os.path.join(OSPath(generate_image_base_path), OSPath(compose_path)))
            adversial_image.save(os.path.join(OSPath(generate_image_base_path), OSPath(adversial_path)))
            compose_image.save(os.path.join(OSPath(generate_image_base_path), OSPath(compose_path)))
            print("* Finish saving")
            # ------------------------------ end ---------------------------------------------------

            # ------------------- 处理数据 存储 数据 ------------------------------------------------
            # 插入数据的documents
            results = []
            # 预测
            # 标准模型加载预测

            # 调用模型前，清空内存防止内存泄漏
            # K.clear_session()
            standard_model = load_model(os.path.join(OSPath(model_base_path), OSPath(standard_model_path)))
            # 获取三层切分模型
            standard_layer1, standard_layer2, standard_layer3 = getActivationLayers(standard_model)
            temp = standard_model.predict(np.zeros((1, 784)))
            standard_result = np.argmax(standard_model.predict(compose_data.reshape((-1, 784)))[0], axis=0)
            standard_layer1_output = standard_layer1.predict(compose_data.reshape((-1, 784)))[0]
            standard_layer2_output = standard_layer2.predict(compose_data.reshape((-1, 784)))[0]
            standard_layer3_output = standard_layer3.predict(compose_data.reshape((-1, 784)))[0]
            standard_activation_data = [standard_layer1_output.tolist(), standard_layer2_output.tolist(),
                                        standard_layer3_output.tolist()]
            for mutation in models:
                # id 属性和 path 属性
                mutation_model = load_model(os.path.join(OSPath(model_base_path), OSPath(mutation['path'])))
                mutation_layer1, mutation_layer2, mutation_layer3 = getActivationLayers(mutation_model)
                mutation_result = np.argmax(mutation_model.predict(compose_data.reshape((-1, 784)))[0], axis=0)
                mutation_layer1_output = mutation_layer1.predict(compose_data.reshape((-1, 784)))[0]
                mutation_layer2_output = mutation_layer2.predict(compose_data.reshape((-1, 784)))[0]
                mutation_layer3_output = mutation_layer3.predict(compose_data.reshape((-1, 784)))[0]
                mutation_activation_data = [mutation_layer1_output.tolist(), mutation_layer2_output.tolist(),
                                            mutation_layer3_output.tolist()]
                isKilled = False
                score = 0.0
                if mutation_result != standard_result:
                    isKilled = True
                    # 计算成绩
                    score = cal_score(original_data, compose_data)
                result = {
                    'exam_id': exam_id,
                    'user_id': user_id,
                    'image_id': image_id,
                    'model_id': mutation['id'],
                    'isKilled': isKilled,
                    'adversial_path': adversial_path,
                    'compose_path': compose_path,
                    'standard_predict': int(standard_result),
                    'mutation_predict': int(mutation_result),
                    'standard_activation_data': standard_activation_data,
                    'mutation_activation_data': mutation_activation_data,
                    'score': score,
                    'submit_time': time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
                }
                results.append(result)

            results_cpy = copy.deepcopy(results)

            # 插入submit数据~ insert many~
            client.change_collection('submit_data')
            client.insert_many(results)
            client.conn.close()

            redis_db.set(imageId, json.dumps(results_cpy))

        if len(imageIDs) > 0:
            redis_db.ltrim(config.IMAGE_QUEUE, len(imageIDs), -1)

        time.sleep(config.SERVER_SLEEP)

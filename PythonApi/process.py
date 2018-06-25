# -*- coding:utf-8 -*-
import os, base64
# import argparse
# import configparser
import math
from mongo import MongoDB
import numpy as np
from keras import Model
from keras.models import load_model
from PIL import Image
from io import BytesIO
from config import config
import time
from keras import backend as K


def init():
    return config


# # basic config
# config = configparser.ConfigParser()
# config.read('./config/config.conf')
# image_base_path = config['basic']['image_path']
# model_base_path = config['basic']['model_path']
# generate_image_base_path = config['basic']['generate_image_path']
# print(image_base_path, model_base_path, generate_image_base_path)
#
# # mongodb config
# settings = {'ip': config['mongo']['ip'], 'port': config['mongo']['port'], 'db_name': config['mongo']['db']}
# client = MongoDB.MongoDB(settings)
#
# # test data 这些数据应该是从argparse中读取出来的
# adversial_str = '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'
# image_path = 'mnist_test/0/mnist_test_525.png'
# standard_model_path = 'standard.hdf5'
# mutation_models = [{'id': 0, 'path': 'del_neuron_model_1_0.hdf5'}, ]
# user_id = 'aaaaaaa'
# exam_id = 1


# 将本例中将三层隐藏层经过激活函数后的切开成三个模型
def getActivationLayers(model):
    intermediate_layer_model_1 = Model(inputs=model.input, outputs=model.get_layer("activation_7").output)
    intermediate_layer_model_2 = Model(inputs=model.input, outputs=model.get_layer("activation_8").output)
    intermediate_layer_model_3 = Model(inputs=model.input, outputs=model.get_layer("activation_9").output)
    return intermediate_layer_model_1, intermediate_layer_model_2, intermediate_layer_model_3


# 获取原始图 噪音图 混合后的图片数据
def getMutaionImage(original, adversial):
    original_data = np.array(original) / float(255)
    adversial_data = np.array(adversial) / float(255)
    # 167/784的位置的数据有变化
    # print(len(adversial_data[np.where(adversial_data > 0)]))
    compose_data = original_data.copy()
    compose_data[np.where(adversial_data != 0)] = adversial_data[np.where(adversial_data != 0)]
    # diff_count = 0
    # other = 0
    # change_count = 0
    # for i in range(784):
    #     if adversial_data.reshape((784,))[i] != 0:
    #         diff_count += 1
    #         if compose_data.reshape((784,))[i] == adversial_data.reshape((784,))[i]:
    #             change_count += 1
    #     else:
    #         if compose_data.reshape((784,))[i] == original_data.reshape((784,))[i]:
    #             other += 1
    #
    # print(diff_count)
    # print(change_count)
    # print(other)
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


def process(body):
    # ------ 初始化配置 start -------------
    # TODO maybe need try catch to avoid key not exist error
    config = init()
    # 根据config初始化mongodb client
    settings = {'ip': config.ip, 'port': config.port, 'db_name': config.db}
    client = MongoDB.MongoDB(settings)
    image_base_path = config.image_path
    model_base_path = config.model_path
    generate_image_base_path = config.generate_image_path
    adversial_str = body['adversial_str']
    exam_id = body['exam_id']
    image_id = body['image']['id']
    image_path = body['image']['path']
    models = body['mutation_models']
    standard_model_path = body["standard_model_path"]
    user_id = body['user_id']
    # ------- 初始化配置end ----------------

    # ---------- 获取原图 | 噪音图/前景图 | 合成图的数据 生成及保存 start -------------
    # 获取干扰值的数据/前景图
    if ',' in adversial_str:
        # 获取纯的base64数据，不包含前缀
        adversial_str = adversial_str.split(',')[1]
    adversial = base64.b64decode(adversial_str)
    image_data = BytesIO(adversial)
    adversial_image = Image.open(image_data).convert('L').resize((28, 28))
    # 获取扰动的原始图片数据
    original_path = os.path.join(OSPath(image_base_path), OSPath(image_path))
    original_image = Image.open(original_path).convert('L')
    # 获取原始图片 前景图  合成图 的最终数据
    original_data, adversial_data, compose_data = getMutaionImage(original_image, adversial_image)
    time_str = str(int(time.time()))
    # 图片的保存位置 以及保存
    adversial_path = '_'.join((str(user_id), str(exam_id), 'adversial', time_str)) + '.png'
    compose_path = '_'.join((str(user_id), str(exam_id), 'compose', time_str)) + '.png'
    adversial_image = Image.fromarray((adversial_data.reshape(28, 28) * 255).astype(np.uint8))
    compose_image = Image.fromarray((adversial_data.reshape(28, 28) * 255).astype(np.uint8))
    print(os.path.join(OSPath(generate_image_base_path), OSPath(adversial_path)))
    print(os.path.join(OSPath(generate_image_base_path), OSPath(compose_path)))
    adversial_image.save(os.path.join(OSPath(generate_image_base_path), OSPath(adversial_path)))
    compose_image.save(os.path.join(OSPath(generate_image_base_path), OSPath(compose_path)))
    # ------------------------------ end ---------------------------------------------------

    # ------------------- 处理数据 存储 数据 ------------------------------------------------
    # 插入数据的documents
    results = []
    # 预测
    # 标准模型加载预测

    # 调用模型前，清空内存防止内存泄漏
    K.clear_session()
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
            'standard_activation_data': standard_activation_data,
            'mutation_activation_data': mutation_activation_data,
            'score': score,
            'submit_time': time.strftime('%Y-%m-%d %H:%M:%S', time.localtime(time.time()))
        }
        results.append(result)
        # 存数据库
        # print(standard_result)
        # print(standard_layer1_output)
        # print(standard_layer2_output)
        # print(standard_layer3_output)
        # print(mutation_result)
        # print(mutation_layer1_output)
        # print(mutation_layer2_output)
        # print(mutation_layer3_output)
        # print('isKilled', isKilled)

    # 插入submit数据~ insert many~
    client.change_collection('submit_data')
    client.insert_many(results)
    client.conn.close()
    # ----------------------------存储数据 end ----------------------------------------------------------------

    # 返回数据
    return results

# print(original_path)
# adv = adversial_image.resize((28, 28))
# data = np.array(image) / float(255)
# new_image = Image.fromarray((data * 255).astype(np.uint8))
# new_image.save("test_test.png")
#
# print(data)
# data.resize((28, 28))
# print(data.shape)
# print(np.where(data != 0))
# print(np.where(data > 0))
# print(adversial)

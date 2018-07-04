# -*- coding: utf-8 -*-

"""
Basic
"""
image_path = '/root/DeepTest/image'
model_path = '/root/DeepTest/del_models'
generate_image_path = '/root/DeepTest/compose_image'

"""
Mongo
"""
port = 27017
ip = '120.79.89.98'
db = 'mnist_active_data'

"""
data type
"""
IMAGE_WIDTH = 28
IMAGE_HEIGHT = 28
IMAGE_CHANS = 3
IMAGE_DTYPE = "float32"

# initialize constants used for server queuing
IMAGE_QUEUE = "image_queue"
BATCH_SIZE = 32
SERVER_SLEEP = 0.25
CLIENT_SLEEP = 0.25

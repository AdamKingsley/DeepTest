# -*- coding: utf-8 -*-

"""
Basic
"""
image_path = '/Users/Hermit/Downloads/dt_keras'
model_path = '/Users/Hermit/Downloads/dt_keras/del_models'
generate_image_path = '/Users/Hermit/Downloads/dt_keras'

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
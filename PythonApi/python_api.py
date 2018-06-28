import json
import time
import uuid
from threading import Thread

import redis
from flask import Flask, jsonify, request, abort

import data_augmentation
import redis_keras_process
from config import config
from config.encoder import JSONEncoder

app = Flask(__name__)
app.json_encoder = JSONEncoder
redis_db = redis.StrictRedis(host='localhost', port=6379, db=0)


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/custom/paint', methods=['POST'])
def custom_sample():
    if not request.json:
        abort(400)
    JSON = request.json
    # 获取到的request body是一个dict类型

    k = str(uuid.uuid4())
    JSON['id'] = k

    redis_db.rpush(config.IMAGE_QUEUE, json.dumps(JSON))
    while True:
        output = redis_db.get(k)
        if output is not None:
            results = output.decode("utf-8")
            redis_db.delete(k)
            break

        time.sleep(config.CLIENT_SLEEP)
    # results = process(JSON)
    print(results)
    return jsonify(results), 200


@app.route('/custom/thin', methods=['POST'])
def custom_thin():
    if not request.json:
        abort(400)
    JSON = request.json
    return jsonify({
        'image': data_augmentation.thin(JSON['image'])
    }), 200


@app.route('/custom/fat', methods=['POST'])
def custom_fat():
    if not request.json:
        abort(400)
    JSON = request.json
    return jsonify({
        'image': data_augmentation.fat(JSON['image'])
    }), 200


if __name__ == '__main__':
    print('* Starting model service...')
    t = Thread(target=redis_keras_process.process, args=())
    t.daemon = True
    t.start()

    # start the web server
    print("* Starting web service...")
    app.run()

from flask import Flask, jsonify, request, abort
from process import process
from config.encoder import JSONEncoder

app = Flask(__name__)
app.json_encoder = JSONEncoder


@app.route('/')
def hello_world():
    return 'Hello World!'


@app.route('/custom/paint', methods=['POST'])
def custom_sample():
    if not request.json:
        abort(400)
    JSON = request.json
    # 获取到的request body是一个dict类型
    # print(type(JSON))
    # print(JSON)
    # jsonify(request.json)
    results = process(JSON)
    print(results)
    return jsonify(results), 200


if __name__ == '__main__':
    app.run()
